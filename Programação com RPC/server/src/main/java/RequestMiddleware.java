/**
 * Este código é responsável pela parte de criar uma classe genérica para requisições
 * 
 * Protocolo: RPC
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@sorattorafa)
 * 
 * Data de Criação: 01 de Agosto de 2021 
 * Ultima alteração: 01 de Agosto de 2021
 * Rafael Rampim Soratto
    
 */

import io.grpc.stub.StreamObserver;
import java.net.*;
import java.io.*;
import java.sql.*;


public class RequestMiddleware extends  RequestMiddlewareGrpc.RequestMiddlewareImplBase {
    static Connection db_connection;
    
    @Override
    public void comunication(Request request, StreamObserver<Response> responseObserver) {
        
        // create BD  connection
        db_connection = SQLiteConnection.connect();
        
        // get op code
        System.out.println("Recebido: " + request.getOpCode());
    
        Response.Builder response = Response.newBuilder();
        
        // Calls controller with  opCode */
        switch(request.getOpCode()){
            case "addnota":
                Controller.add_nota_for_json(request, response, db_connection);
            break;

            case "removenota":
                Controller.remmove_nota_for_json(request, response, db_connection);
            break;

            case "liststudents":
                Controller.list_alunos_for_json(request, response, db_connection);
            break;

            case "loggout":
                System.out.println("Conexão perdida");
                response.setResponse("404 - Client not found!");
            break;

            default:
                response.setResponse("404 - Route not found: invalid opCode!");
            break;
        }
        // set next reponse build completed
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
