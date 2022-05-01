
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import models.Cliente;
import models.Filial;
import models.Locacao;
import models.Veiculo;

public class Servidor extends UnicastRemoteObject implements InterfaceServidor {

    Data data = new Data();

    public Servidor() throws RemoteException {
        System.out.println("Novo Servidor instanciado...");

        //Funções para cadastro dos dados base
        data.CadastrarFiliaisBase();
        data.CadastrarVeiculosBase();
        data.CadastrarClientesBase();

        new Terminal(data).start();
    }

    //Envelopa a função de cadastrar cliente, que está dentro de data, para
    //que possa ser acessada pelo cliente
    @Override
    public synchronized boolean CadastrarCliente(String cpf, String nome, String categoria) throws RemoteException {
        return data.CadastrarCliente(cpf, nome, categoria);
    }

    //Envelopa a função de verificar se o cliente já existe, que está dentro de data, para
    //que possa ser acessada pelo cliente
    @Override
    public boolean ClienteExists(String cpf) throws RemoteException {
        return data.ClienteExists(cpf);
    }

    //Envelopa a função de login, que está dentro de data, para
    //que possa ser acessada pelo cliente
    @Override
    public boolean Login(String login, String senha) throws RemoteException {
        return data.Login(login, senha);
    }

    //Envelopa a função de listar todos os veículos disponíveis em toda a rede
    @Override
    public ArrayList<Veiculo> ListarTodosVeiculosDisponiveis() throws RemoteException {
        return data.ListarTodosVeiculosDisponiveis();
    }

    //Envelopa a função listar filial com base no ID
    @Override
    public Filial BuscaFilialId(int id) throws RemoteException {
        return data.BuscaFilialId(id);
    }

    //Envelopa a função de listar filial com base no login
    @Override
    public Filial BuscaDadosFilial(String login) throws RemoteException {
        return data.BuscaDadosFilial(login);
    }

    //Envelopa a função de retorno de dados do cliente com base no CPF
    @Override
    public Cliente BuscaDadosCliente(String cpf) throws RemoteException {
        return data.BuscaDadosCliente(cpf);
    }

    //Envelopa a função de busca de veículos por filial
    @Override
    public ArrayList<Veiculo> VeiculosPorFilial(int id_filial) throws RemoteException {
        return data.VeiculosPorFilial(id_filial);
    }

    @Override
    public ArrayList<Filial> ListaFiliais() throws RemoteException {
        return data.ListaFiliais();
    }

    @Override
    public boolean CadastrarLocacao(String valor, int id_cliente, String placa_veiculo, int id_locadora_retirada, int id_locadora_dovlucao, int tempo) throws RemoteException {
        return data.CadastrarLocacao(valor, id_cliente, placa_veiculo, id_locadora_retirada, id_locadora_dovlucao, tempo);
    }

    @Override
    public boolean ClientePodeLogar(int id) throws RemoteException {
        return data.ClientePodeLogar(id);
    }

    @Override
    public ArrayList<Locacao> listaLocacoesLocadora(int id_locadora) throws RemoteException {
        return data.listaLocacoesLocadora(id_locadora);
    }

    @Override
    public Veiculo BuscaDadosVeiculo(String placa) throws RemoteException {
        return data.BuscaDadosVeiculo(placa);
    }

    @Override
    public Cliente BuscaDadosClienteId(int id_cliente) throws RemoteException {
        return data.BuscaDadosClienteId(id_cliente);
    }

    @Override
    public ArrayList<Locacao> listaLocacoesCliente(String cpf) throws RemoteException {
        return data.listaLocacoesCliente(cpf);
    }

}
