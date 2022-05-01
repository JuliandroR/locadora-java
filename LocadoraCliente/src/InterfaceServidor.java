
import java.rmi.*;
import java.util.ArrayList;
import models.Cliente;
import models.Filial;
import models.Locacao;
import models.Veiculo;

public interface InterfaceServidor extends Remote{
    public boolean CadastrarCliente(String cpf, String nome, String categoria) throws RemoteException;
    public boolean ClienteExists(String cpf) throws RemoteException;
    public boolean Login(String login, String senha) throws RemoteException;
    public boolean CadastrarLocacao(String valor, int id_cliente, String placa_veiculo, int id_locadora_retirada, int id_locadora_dovlucao, int tempo) throws RemoteException;
    public boolean ClientePodeLogar(int id) throws RemoteException;
    
    public ArrayList<Veiculo> ListarTodosVeiculosDisponiveis() throws RemoteException;
    public ArrayList<Veiculo> VeiculosPorFilial(int id_filial) throws RemoteException;
    public ArrayList<Filial> ListaFiliais() throws RemoteException;
    public ArrayList<Locacao> listaLocacoesLocadora(int id_locadora) throws RemoteException;
    public ArrayList<Locacao> listaLocacoesCliente(String cpf) throws RemoteException;
    
    public Filial BuscaFilialId(int id) throws RemoteException;  
    public Filial BuscaDadosFilial(String login) throws RemoteException;
    public Cliente BuscaDadosCliente(String cpf) throws RemoteException;
    public Cliente BuscaDadosClienteId(int id_cliente) throws RemoteException;
    public Veiculo BuscaDadosVeiculo(String placa) throws RemoteException;
    
    
}
