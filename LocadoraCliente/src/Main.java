
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import models.Cliente;
import models.Filial;
import models.Locacao;
import models.Veiculo;

public class Main {

    public Main() {
        System.out.println("Inicializando o Cliente...");

        try {
            matriz = (InterfaceServidor) Naming.lookup("rmi://localhost:9000/matriz");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro ao conectar cliente");
            System.exit(0);
        }
    }

    public static void main(String[] args) throws RemoteException {

        Main server = new Main();

        Scanner reader = new Scanner(System.in);

        boolean run = true;
        boolean logged = false;
        Filial filial_logged = null;

        //Repete o terminal enquanto o usuário não seleciona logout
        while (run) {

            //Loop enquanto o usuário e senha não coincidirem com o cadastrado no sistema
            while (!logged) {
                System.out.println("Informe o login");
                String login = reader.nextLine();

                System.out.println("Informe a senha");
                String senha = reader.nextLine();

                try {
                    if (server.login(login, senha)) {
                        logged = true;
                        filial_logged = server.BuscaDadosFilial(login);
                        System.out.println("Bem vindo!");
                    } else {
                        System.out.println("Usuário e/ou senha incorretos");
                    }
                } catch (Exception e) {
                    System.out.println("Ocorreu um erro ao se comunicar com o servidor \n Erro: " + e.getMessage());
                }
            }

            int option = 0;
            System.out.println("############################################");
            System.out.println("1 - Cadastrar novo cliente");
            System.out.println("2 - Locar um veículo");
            System.out.println("3 - Listar veículos por locadora");
            System.out.println("4 - Listar todos os veículos disponíveis para locação na rede");
            System.out.println("5 - Listar locações");
            System.out.println("0 - Logout");
            option = reader.nextInt();
            System.out.println("############################################");

            reader.nextLine();

            switch (option) {
                case 1:
                    //Cadastrar novo cliente
                    System.out.println("Informe o CPF");
                    String cpf = reader.nextLine();

                    if (server.ClienteExists(cpf)) {
                        System.out.println("Cliente já cadastrado");
                    } else {
                        System.out.println("Informe o nome");
                        String nome = reader.nextLine();

                        System.out.println("Informe a categoria de habilitação");
                        String categoria = reader.nextLine();

                        if (server.CadastrarCliente(cpf, nome, categoria)) {
                            System.out.println("Cliente cadastrado com sucesso");
                        } else {
                            System.out.println("Erro, tente novamente");
                        }
                    }
                    break;
                case 2:
                    //Locar um veículo
                    System.out.println("Informe o CPF do cliente");
                    String cpfCliente = reader.nextLine();

                    //Verifica se o CPF informado possui cadastro
                    if (server.ClienteExists(cpfCliente)) {
                        Cliente cliente = server.BuscaDadosCliente(cpfCliente);

                        if (server.ClientePodeLogar(cliente.getId())) {
                            String placa_veiculo = "";
                            boolean prosseguir = false;

                            //Enquanto a placa informada não for encontrada na lista de veículos
                            //da locadora o loop não se encerra
                            while (!prosseguir) {
                                if (server.ListarTodosVeiculosDisponiveisRestricao(filial_logged.getId(), cliente.getCategoria())) {
                                    System.out.println("Informe a placa do veículo desejado");
                                    placa_veiculo = (reader.nextLine()).toUpperCase();

                                    prosseguir = server.VeiculoExiste(placa_veiculo, filial_logged.getId());
                                } else {
                                    prosseguir = true;
                                    System.out.println("Nenhum veículo disponível");
                                }
                            }

                            if (!placa_veiculo.equals("")) {
                                server.ListaFiliais();
                                System.out.println("Informe o ID da locadora onde será devolvido o veículo");
                                int id_locadora = reader.nextInt();

                                System.out.println("Informe o tempo de locação do veículo");
                                int tempo = reader.nextInt();

                                double valor = server.CalculaValorLocacao(placa_veiculo, tempo);
                                System.out.println("O valor para essa locação será de: " + valor);
                                System.out.println("Confirmar? \n 1 - Sim \n 2 - Não");
                                int confirm = reader.nextInt();

                                switch (confirm) {
                                    case 1:
                                        boolean success = server.CadastrarLocacao(
                                                "" + valor,
                                                cliente.getId(),
                                                placa_veiculo,
                                                filial_logged.getId(),
                                                id_locadora,
                                                tempo
                                        );
                                        if (success) {
                                            System.out.println("Locação realizada com sucesso!");
                                        } else {
                                            System.out.println("Erro, tente novamente");
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Locação cancelada");
                                        break;
                                    default:
                                        System.out.println("Opção inválida");
                                        break;
                                }
                            }
                        } else {
                            System.out.println("Cliente ainda não pode locar um novo veículo");
                        }

                    } else {
                        System.out.println("Cliente não encontrado!"
                                + "\nRealize o cadastrado dele ou tente novamente");
                    }
                    break;
                case 3:
                    //Listar veículos por locadora
                    server.ListaFiliais();
                    System.out.println("Informe o ID da locadora");
                    int id_locadora = reader.nextInt();

                    server.ListaVeiculosPorFilial(id_locadora);
                    break;
                case 4:
                    //Listar todos os veículos disponíveis para locação na rede
                    server.ListarTodosVeiculosDisponiveis();
                    break;
                case 5:
                    //Lista opções de listagem das locações
                    System.out.println(" 1 - Listar locações realizadas nessa filial");
                    System.out.println(" 2 - Listar locações realizadas por cliente");
                    int opcao_listagem = reader.nextInt();

                    reader.nextLine();

                    if (opcao_listagem == 1) {
                        //Listagem de todas as locações realizadas na filial logada
                        server.listaLocacoesLocadora(filial_logged.getId());
                    } else if (opcao_listagem == 2) {
                        //Listagem das locações realizadas pelo cliente
                        System.out.println("Informe o CPF do cliente");
                        String cpf_cliente = reader.nextLine();

                        //Verifica se o CPF informado possui cadastro
                        if (server.ClienteExists(cpf_cliente)) {
                            server.listaLocacoesCliente(cpf_cliente);
                        } else {
                            System.out.println("Cliente não encontrado");
                        }
                    } else {
                        System.out.println("Opção inválida");
                    }
                    break;
                case 0:
                    run = false;
                    System.out.println("Desconectando....");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }

        }
    }

    public boolean login(String login, String senha) throws RemoteException {
        return matriz.Login(login, senha);
    }

    public boolean ClienteExists(String cpf) throws RemoteException {
        return matriz.ClienteExists(cpf);
    }

    public boolean ClientePodeLogar(int id) throws RemoteException {
        return matriz.ClientePodeLogar(id);
    }

    public boolean CadastrarCliente(String cpf, String nome, String categoria) throws RemoteException {
        return matriz.CadastrarCliente(cpf, nome, categoria);
    }

    public void ListarTodosVeiculosDisponiveis() throws RemoteException {
        ArrayList<Veiculo> veiculos = matriz.ListarTodosVeiculosDisponiveis();

        for (Veiculo veiculo : veiculos) {
            Filial filial_veiculo = BuscaFilialId(veiculo.getFilial_localizacao());

            System.out.println("Veículo: " + veiculo.getNome()
                    + " | Placa: " + veiculo.getPlaca()
                    + " | Restrição: " + veiculo.getRestricao()
                    + " | Disponível em: " + filial_veiculo.getNome()
                    + " | Valor: " + veiculo.getValor_locacao());

        }
    }

    public void ListarTodosVeiculosDisponiveisLocadora(int id) throws RemoteException {
        ArrayList<Veiculo> veiculos = matriz.ListarTodosVeiculosDisponiveis();

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getFilial_localizacao() == id) {

                System.out.println("Placa: " + veiculo.getPlaca()
                        + " | Veículo: " + veiculo.getNome()
                        + " | Restrição: " + veiculo.getRestricao()
                        + " | Valor: " + veiculo.getValor_locacao());
            }
        }
    }

    public boolean ListarTodosVeiculosDisponiveisRestricao(int id, String restricao) throws RemoteException {
        ArrayList<Veiculo> veiculos = matriz.ListarTodosVeiculosDisponiveis();

        boolean retorno = false;

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getFilial_localizacao() == id) {
                if (restricao.equals(veiculo.getRestricao())) {
                    retorno = true;
                    System.out.println("Placa: " + veiculo.getPlaca()
                            + " | Veículo: " + veiculo.getNome()
                            + " | Restrição: " + veiculo.getRestricao()
                            + " | Valor: " + veiculo.getValor_locacao());
                }
            }
        }

        return retorno;
    }

    public boolean VeiculoExiste(String placa, int id_filial) throws RemoteException {
        ArrayList<Veiculo> veiculos = matriz.ListarTodosVeiculosDisponiveis();

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getFilial_localizacao() == id_filial) {
                if (placa.equals(veiculo.getPlaca())) {
                    return true;
                }
            }
        }

        return false;
    }

    public Filial BuscaFilialId(int id) throws RemoteException {
        return matriz.BuscaFilialId(id);
    }

    public Filial BuscaDadosFilial(String login) throws RemoteException {
        return matriz.BuscaDadosFilial(login);
    }

    public Cliente BuscaDadosCliente(String cpf) throws RemoteException {
        return matriz.BuscaDadosCliente(cpf);
    }

    public void ListaFiliais() throws RemoteException {
        ArrayList<Filial> filiais = matriz.ListaFiliais();

        for (Filial filial : filiais) {
            System.out.println("ID: " + filial.getId() + " - Nome: " + filial.getNome());
        }

    }

    public void ListaVeiculosPorFilial(int id_filial) throws RemoteException {
        ArrayList<Veiculo> veiculos = matriz.VeiculosPorFilial(id_filial);

        for (Veiculo veiculo : veiculos) {
            System.out.println("Placa: " + veiculo.getPlaca()
                    + " | Veículo: " + veiculo.getNome()
                    + " | Restrição: " + veiculo.getRestricao()
                    + " | Valor: " + veiculo.getValor_locacao());
        }
    }

    public double CalculaValorLocacao(String placa, int tempo) throws RemoteException {
        ArrayList<Veiculo> veiculos = matriz.ListarTodosVeiculosDisponiveis();

        for (Veiculo veiculo : veiculos) {
            if (placa.equals(veiculo.getPlaca())) {
                return veiculo.getValor_locacao() * tempo;
            }
        }

        return 0.0;
    }

    public boolean CadastrarLocacao(String valor, int id_cliente, String placa_veiculo, int id_locadora_retirada, int id_locadora_dovlucao, int tempo) throws RemoteException {
        return matriz.CadastrarLocacao(valor, id_cliente, placa_veiculo, id_locadora_retirada, id_locadora_dovlucao, tempo);
    }

    public Veiculo BuscaDadosVeiculo(String placa) throws RemoteException {
        return matriz.BuscaDadosVeiculo(placa);
    }

    public Cliente BuscaDadosClienteId(int id_cliente) throws RemoteException {
        return matriz.BuscaDadosClienteId(id_cliente);
    }

    public void listaLocacoesLocadora(int id_locadora) throws RemoteException {
        
        ArrayList<Locacao> locacoes = matriz.listaLocacoesLocadora(id_locadora);

        for (Locacao loc : locacoes) {
            Veiculo vec = matriz.BuscaDadosVeiculo(loc.getPlaca_veiculo());
            Filial fil = matriz.BuscaFilialId(loc.getId_locadora_dovlucao());
            Cliente cli = matriz.BuscaDadosClienteId(loc.getId_cliente());

            System.out.println("Veiculo: " + vec.getNome()
                    + " | Placa: " + vec.getPlaca()
                    + " | CPF cliente: " + cli.getCpf()
                    + " | Nome cliente: " + cli.getNome()
                    + " | Tempo locado: " + (loc.getHora_devolucao() - loc.getHora_locacao()) / 1000
                    + " | Local de devolução: " + fil.getNome()
                    + " | Valor: " + loc.getValor());
        }
    }

    public void listaLocacoesCliente(String cpf) throws RemoteException {

        ArrayList<Locacao> locacoes = matriz.listaLocacoesCliente(cpf);

        for (Locacao loc : locacoes) {
            Veiculo vec = matriz.BuscaDadosVeiculo(loc.getPlaca_veiculo());
            Filial fil = matriz.BuscaFilialId(loc.getId_locadora_dovlucao());
            Filial fil2 = matriz.BuscaFilialId(loc.getId_locadora_retirada());
            Cliente cli = matriz.BuscaDadosClienteId(loc.getId_cliente());

            System.out.println("Veiculo: " + vec.getNome()
                    + " | Placa: " + vec.getPlaca()
                    + " | Tempo locado: " + (loc.getHora_devolucao() - loc.getHora_locacao()) / 1000
                    + " | Local da locação: " + fil2.getNome()
                    + " | Local de devolução: " + fil.getNome()
                    + " | Valor: " + loc.getValor());
        }
    }

    private InterfaceServidor matriz;
}
