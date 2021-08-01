/**
 * Este código é responsavel pela parte do receber a mensagem do cliente, executar o banco, processar a requisição e
 * retornar a resposta para o cliente em formato Protocol Buffer ou JSON, via socket TCP.
 * 
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@sorattorafa)
 * 
 * Data de Criação: 1 de Ago de 2021
 * Ultima alteração: 1 de Ago de 2021
 */


import io.grpc.ServerBuilder;
import java.io.IOException;


public class Server {
    public static void main(String[] args) {
        io.grpc.Server server = ServerBuilder
                .forPort(7777)
                // .addService(new RequestMiddleware())
                .build();
        
        
        try {
            server.start();
            System.out.println("Servidor iniciado.");
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro: " + e);
        }
    
    }
}
