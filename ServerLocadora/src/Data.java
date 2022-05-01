
import java.io.FileReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import models.Cliente;
import models.Filial;
import models.Locacao;
import models.Veiculo;

public class Data {

    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private ArrayList<Filial> filiais = new ArrayList<Filial>();
    private ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
    private ArrayList<Locacao> locacoes = new ArrayList<Locacao>();

    //Função para cadastrar as filiais iniciais do sistema
    public void CadastrarFiliaisBase() {
        //System.out.println("Invocou cadastrar filial base");

        try {
            Scanner carregaFilial = new Scanner(new FileReader("filiais.txt")).useDelimiter("\\||\\n");

            while (carregaFilial.hasNext()) {
                String nome = carregaFilial.next();
                String login = carregaFilial.next();
                String senha = carregaFilial.next();

                CadastrarFilial(nome, login, senha);

            }
            carregaFilial.close();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
            e.getMessage();

            CadastrarFilial("Filial 01", "filial01", "123");
            CadastrarFilial("Filial 02", "filial02", "123");
            CadastrarFilial("Filial 03", "filial03", "123");

        }
    }

    //Função para cadastrar os veículos iniciais do sistema
    public void CadastrarVeiculosBase() {
        //System.out.println("Invocou cadastrar veiculos base");
        try {
            Scanner carregaVeiculo = new Scanner(new FileReader("veiculos.txt")).useDelimiter("\\||\\n");

            while (carregaVeiculo.hasNext()) {

                String placa = carregaVeiculo.next();
                String nome = carregaVeiculo.next();
                String restricao = carregaVeiculo.next();
                int filial_localizacao = Integer.parseInt(carregaVeiculo.next());
                double valor = Double.parseDouble(carregaVeiculo.next());

                CadastrarVeiculo(placa, nome, restricao, filial_localizacao, valor);
            }
            carregaVeiculo.close();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
            e.getMessage();

            CadastrarVeiculo("abc-1234", "Sandero RS", "b", 0, 125);
            CadastrarVeiculo("xvd-0809", "Hb20", "c", 0, 180);
            CadastrarVeiculo("blw-3912", "Onix sedan", "b", 2, 220);

        }
    }

    //Função para cadastradas os clientes iniciais do sistema
    public void CadastrarClientesBase() {
        try {
            Scanner carregaCliente = new Scanner(new FileReader("clientes.txt")).useDelimiter("\\||\\n");

            while (carregaCliente.hasNext()) {

                String cpf = carregaCliente.next();
                String nome = carregaCliente.next();
                String categoria = carregaCliente.next();

                CadastrarCliente(cpf, nome, categoria);
            }
            carregaCliente.close();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
            e.getMessage();

            CadastrarCliente("123", "Juliandro", "b");
            CadastrarCliente("132", "Bruno", "c");
            CadastrarCliente("321", "Poly", "b");
        }
    }

    //Função para cadastrar novos veículos
    public synchronized boolean CadastrarVeiculo(String placa, String nome, String restricao, int filial_localizacao, double valor_locacao) {
        //System.out.println("Invocou cadastrar veiculo");

        for (Veiculo veiculo : veiculos) {
            if (placa.equals(veiculo.getPlaca())) {
                System.out.println("Placa já cadastrada");
                return false;
            }
        }

        try {
            Veiculo veiculo = new Veiculo(placa.toUpperCase(), nome, restricao.toUpperCase(), filial_localizacao, valor_locacao);
            veiculos.add(veiculo);
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    //Função para cadastrar novas filiais
    public boolean CadastrarFilial(String nome, String login, String senha) {
        //System.out.println("Invocou Cadastrar filial");

        for (Filial filial : filiais) {
            if (login.equals(filial.getLogin())) {
                System.out.println("Login já cadastrado");
                return false;
            }
        }

        try {
            int id = filiais.size();
            Filial filial = new Filial(id, nome, login, senha);
            filiais.add(filial);
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    //Função para listar todas as filiais
    public void ListarFiliais() {
        System.out.println("Filiais");
        for (Filial filial : filiais) {
            System.out.println("-> " + filial.getId() + " - Filial: " + filial.getNome());
        }
    }

    //Função para cadastrar o cliente
    public synchronized boolean CadastrarCliente(String cpf, String nome, String categoria) {
        //System.out.println("Invocou cadastrar cliente");
        try {
            int id = clientes.size();
            Cliente cliente = new Cliente(id, cpf, nome, categoria.toUpperCase());
            clientes.add(cliente);
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    //Função para verificar se o cliente já existe
    public boolean ClienteExists(String cpf) {
        //System.out.println(cpf);

        analisaLocacoes();

        for (Cliente cliente : clientes) {
            if (cpf.equals(cliente.getCpf())) {
                return true;
            }
        }

        return false;
    }

    //Função de login das filiais
    public boolean Login(String login, String senha) {

        //Estrutura de repetição para encontrar a filial que está logando
        for (Filial filial : filiais) {

            //verifica se o login informado está cadastrado
            if (login.equals(filial.getLogin())) {
                //caso tenha encontrado o login, verifica-se a senha está correta
                if (senha.equals(filial.getSenha())) {
                    //se atendeu as 2 verificações retorna como true;
                    return true;
                } else {
                    //Caso a senha não bata, é retornado false;
                    return false;
                }
            }
        }

        return false;
    }

    //Listar todos os veículos disponíveis para locação em toda a rede
    public ArrayList<Veiculo> ListarTodosVeiculosDisponiveis() {

        analisaLocacoes();

        ArrayList<Veiculo> v = new ArrayList<Veiculo>();

        for (Veiculo veiculo : veiculos) {
            if (!veiculo.isIsLocado()) {
                v.add(veiculo);
            }
        }

        return v;
    }

    //Retorna todos os dados de uma filial com base em seu ID
    public Filial BuscaFilialId(int id) {

        for (Filial filial : filiais) {
            if (filial.getId() == id) {
                return filial;
            }
        }

        return new Filial();
    }

    //Retorna todos os dados de uma filial com base em seu login
    public Filial BuscaDadosFilial(String login) {

        for (Filial filial : filiais) {
            if (login.equals(filial.getLogin())) {
                return filial;
            }
        }

        return null;
    }

    //Retorna os dados do cliente com base no cpf informado
    public Cliente BuscaDadosCliente(String cpf) {

        analisaLocacoes();

        for (Cliente cliente : clientes) {
            if (cpf.equals(cliente.getCpf())) {
                return cliente;
            }
        }

        return null;
    }

    //Retorna uma lista com todos os veículos dentro de uma filial
    public ArrayList<Veiculo> VeiculosPorFilial(int id_filial) {

        analisaLocacoes();

        ArrayList<Veiculo> v = new ArrayList<Veiculo>();

        for (Veiculo veiculo : veiculos) {
            if (veiculo.getFilial_localizacao() == id_filial) {
                if (!veiculo.isIsLocado()) {
                    v.add(veiculo);
                }
            }
        }

        return v;
    }

    //Retorna uma lista com todas as filiais cadastradas no sistema
    public ArrayList<Filial> ListaFiliais() {
        return filiais;
    }

    //Cadastra uma nova locação e atualiza o veículo para não disponível
    public synchronized boolean CadastrarLocacao(String valor, int id_cliente, String placa_veiculo, int id_locadora_retirada, int id_locadora_dovlucao, int tempo) {
        try {
            int indexOfVeiculo = 0;
            Veiculo veiculoLocado = new Veiculo();

            //Localiza o veículo que está sendo locado
            for (Veiculo veiculo : veiculos) {
                if (placa_veiculo.equals(veiculo.getPlaca())) {
                    veiculoLocado = veiculo;
                }
            }

            //Encontra a indexação do veículo
            indexOfVeiculo = veiculos.indexOf(veiculoLocado);

            //Atualiza o status do veiculo para locação
            veiculoLocado.setIsLocado(true);
            veiculoLocado.setFilial_localizacao(id_locadora_dovlucao);

            //Atualiza na lista de dados o veículo, com seu novo status
            veiculos.set(indexOfVeiculo, veiculoLocado);

            //Localiza o cliente que está locando
            Cliente clienteLocador = new Cliente();
            for (Cliente cliente : clientes) {
                if (id_cliente == cliente.getId()) {
                    clienteLocador = cliente;
                }
            }

            //Encontra a indexação do cliente que está locando
            int indexOfCliente = clientes.indexOf(clienteLocador);

            //Atualiza o status de possíbilidade de locação para falso
            clienteLocador.setPodeLocar(false);

            //Atualiza na lista de dados o cliente, com sua nova imposição de possibilidade de locação
            clientes.set(indexOfCliente, clienteLocador);

            //Captura o tempo atual da locação e calcula quando será a devolução
            Date date = new Date();
            Long hora_locacao = date.getTime();
            Long hora_devolucao = hora_locacao + (tempo * 1000);

            int id_locacao = locacoes.size();
            Locacao locacao = new Locacao(
                    id_locacao,
                    hora_locacao,
                    hora_devolucao,
                    valor,
                    id_cliente,
                    placa_veiculo,
                    id_locadora_retirada,
                    id_locadora_dovlucao
            );

            locacoes.add(locacao);
            return true;
        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
            return false;
        }
    }

    //Retornar se o cliente pode realizar uma locação
    public synchronized boolean ClientePodeLogar(int id) {
        analisaLocacoes();

        for (Cliente cliente : clientes) {
            if (id == cliente.getId()) {
                return cliente.isPodeLocar();
            }
        }

        return false;
    }

    //Libera o cliente para realizar novas locações
    public synchronized void liberaCliente(int id_cliente) {
        //Localiza o cliente que está locando
        Cliente clienteLocador = new Cliente();
        for (Cliente cliente : clientes) {
            if (id_cliente == cliente.getId()) {
                clienteLocador = cliente;
            }
        }

        //Encontra a indexação do cliente que está locando
        int indexOfCliente = clientes.indexOf(clienteLocador);

        //Atualiza o status de possíbilidade de locação para verdadeiro
        if (!clienteLocador.isPodeLocar()) {
            clienteLocador.setPodeLocar(true);
        }

        //Atualiza na lista de dados o cliente, com sua nova imposição de possibilidade de locação
        clientes.set(indexOfCliente, clienteLocador);

    }

    //Libera o veiculo para realizar novas locações
    public synchronized void liberaVeiculo(String placa_veiculo) {
        Veiculo veiculoLocado = new Veiculo();

        //Localiza o veículo que está sendo locado
        for (Veiculo veiculo : veiculos) {
            if (placa_veiculo.equals(veiculo.getPlaca())) {
                veiculoLocado = veiculo;
            }
        }

        //Encontra a indexação do veículo
        int indexOfVeiculo = veiculos.indexOf(veiculoLocado);

        //Atualiza o status do veiculo para locação
        if (veiculoLocado.isIsLocado()) {
            veiculoLocado.setIsLocado(false);
        }

        //Atualiza na lista de dados o veículo, com seu novo status
        veiculos.set(indexOfVeiculo, veiculoLocado);
    }

    //Analisa se tanto o cliente quanto o veículo preso a uma locação já estão livres
    public synchronized void analisaLocacoes() {
        for (Locacao locacao : locacoes) {
            Date date = new Date();
            if (locacao.getHora_devolucao() < date.getTime()) {
                liberaCliente(locacao.getId_cliente());
                liberaVeiculo(locacao.getPlaca_veiculo());
            }
        }
    }

    //Busca dados do veículo com base em sua placa
    public Veiculo BuscaDadosVeiculo(String placa) {

        for (Veiculo vec : veiculos) {
            if (placa.equals(vec.getPlaca())) {
                return vec;
            }
        }

        return new Veiculo();
    }

    //Retorna todas as locações que foram realizadas na locadora com id passado
    public ArrayList<Locacao> listaLocacoesLocadora(int id_locadora) {

        ArrayList<Locacao> retorno = new ArrayList<Locacao>();

        for (Locacao loc : locacoes) {
            if (loc.getId_locadora_retirada() == id_locadora) {
                retorno.add(loc);
            }
        }

        return retorno;
    }

    //Busca dados do cliente com base em seu id
    public Cliente BuscaDadosClienteId(int id_cliente) {

        for (Cliente cli : clientes) {
            if (cli.getId() == id_cliente) {
                return cli;
            }
        }

        return new Cliente();
    }

    public ArrayList<Locacao> listaLocacoesCliente(String cpf) {

        Cliente cliente = new Cliente();

        for (Cliente cli : clientes) {
            if (cpf.equals(cli.getCpf())) {
                cliente = cli;
            }
        }

        ArrayList<Locacao> retorno = new ArrayList<Locacao>();

        for (Locacao loc : locacoes) {
            if (loc.getId_cliente() == cliente.getId()) {
                retorno.add(loc);
            }
        }

        return retorno;
    }
}
