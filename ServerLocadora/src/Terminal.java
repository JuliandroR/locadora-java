
import java.util.Scanner;

public class Terminal extends Thread {

    Scanner reader = new Scanner(System.in);
    Data data;
    //Estrutura para sempre executar o terminal

    public Terminal(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("#####  Interface servidor  #####");
            System.out.println("1 - Cadastrar nova filial");
            System.out.println("2 - Cadastrar novo veículo");
            int opcao = reader.nextInt();

            reader.nextLine();

            switch (opcao) {
                case 1:
                    //Cadastrar filial
                    System.out.println("Digite o nome da filial");
                    String nomeFilial = reader.nextLine();

                    System.out.println("Digite o login");
                    String login = reader.nextLine();

                    System.out.println("Digite a senha");
                    String senha = reader.nextLine();

                    if (data.CadastrarFilial(nomeFilial, login, senha)) {
                        System.out.println("Filial criada com sucesso");
                    } else {
                        System.out.println("Erro! \nTente novamente");
                    }
                    break;
                case 2:
                    //Cadastrar veículo
                    System.out.println("Digite a placa");
                    String placa = reader.nextLine();

                    System.out.println("Digite o nome");
                    String nome = reader.nextLine();

                    System.out.println("Digite a restrição do veículo (A-B-C-D-E)");
                    String restricao = reader.nextLine();

                    //Exibe a lista de filiais já cadastradas
                    data.ListarFiliais();
                    System.out.println("Digite o ID da filial");
                    int id_locacao = reader.nextInt();

                    System.out.println("Digite o valor por segundo");
                    double valor = reader.nextDouble();

                    //Envia os dados coletados para o cadastro e espera sucesso ou erro (true or false)
                    if (data.CadastrarVeiculo(placa, nome, restricao, id_locacao, valor)) {
                        System.out.println("Veículo criado com sucesso");
                    } else {
                        System.out.println("Erro! \nTente novamente");
                    }
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }
}
