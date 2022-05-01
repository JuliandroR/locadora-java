
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {

    public static void main(String argv[]) {
        try {
            
            System.out.println("Inicializando o servidor...");

            Registry registry = LocateRegistry.createRegistry(9000);
            
            Naming.rebind("rmi://localhost:9000/matriz", new Servidor());

        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.toString());
            e.getMessage();
        }
    }

}
