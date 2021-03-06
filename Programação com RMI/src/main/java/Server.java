/**
 * Inicializa o servico
 * autor: Rodrigo Campiolo
 * data: 22/11/2006
 * modificado em :03/05/2019
 */
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String args[]) {
       try {
            /* inicializa um objeto remoto */
            NotesManager notesManager = new NotesManager();

            /* registra o objeto remoto no Binder */
            Registry registry = LocateRegistry.getRegistry("localhost");
            registry.rebind("NotesManagerService", notesManager);

            /* aguardando invocacoes remotas */
	        System.out.println("Server ready ...");
	    } catch (Exception e) {
	        System.out.println(e);
        } //catch
    } //main
} //Servidor