/**
 * Este código é responsavel pela parte do cliente e comunicação com o servidor, ele ira tratar o envio o dos dados
 * utilizando a ferramenta RMI e o recebimento da mensagem no mesmo formato.
 *
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 10 de Ago de 2021
 * Ultima alteração: 10 de Ago de 2021
*/

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Client {
    public static void main(String args[]) {
        try {
           /* OPCIONAL: configurar e ativar o Security Manager */
           System.setProperty("java.security.policy", "policy.txt");
           System.setSecurityManager(new SecurityManager());

           System.out.println ("Cliente iniciado ...");

           /* obtem a referencia para o objeto remoto */
           Registry registry = LocateRegistry.getRegistry("localhost");
           GerenciadorNotasRMI c = (GerenciadorNotasRMI)registry.lookup("ServicoCalculadora");

           System.out.println("20+4=" + c.soma(20,4));
           System.out.println("20-4=" + c.subtrai(20,4));
           System.out.println("20*4=" + c.multiplica(20,4));
           System.out.println("20/4=" + c.divide(20,4));
        } catch (Exception e) {
           System.out.println(e);
        }
    }
}
