/**
 * 
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
        //     DB CONNECTION
        db_connection = SQLiteConnection.connect();
            
        System.out.println("Recebido: " + request.getOpCode());
        //     /* Instancia a resposta */
        Response.Builder response = Response.newBuilder();
        
        // /* Chama a funcionalidade de acordo com o opCode */
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
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
