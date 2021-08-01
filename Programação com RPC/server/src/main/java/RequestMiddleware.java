/**
 *

 */


import io.grpc.stub.StreamObserver;
import java.net.*;
import java.io.*;
import java.sql.*;


public class RequestMiddleware {
    // static Connection dbConnection;
    
    // @Override
    // public void mensagem(Requisicao req, StreamObserver<Resposta> responseObserver) {
    //     // Conexao com banco de dados
    //     dbConnection = SQLiteJDBCDriverConnection.connect();
        
    //     System.out.println("Recebido: " + req.getOpCode());
    //     /* Instancia a resposta */
    //     Resposta.Builder res = Resposta.newBuilder();
    
    //         /* Chama a funcionalidade de acordo com o opCode */
    //         switch(req.getOpCode()){
    //           case "insertNota":
    //             Functionalities.insertNota(req, res, dbConnection);
    //           break;

    //           case "selectNota":
    //             Functionalities.selectNota(req, res, dbConnection);
    //           break;
              
    //           case "rmNota":
    //             Functionalities.rmNota(req, res, dbConnection);
    //           break;

    //           case "updateNota":
    //             Functionalities.updateNota(req, res, dbConnection);
    //           break;
              
    //           case "selectNotasFaltas":
    //             Functionalities.selectNotasFaltas(req, res, dbConnection);
    //           break;

    //           case "listAlunos":
    //             Functionalities.listAlunos(req, res, dbConnection);
    //           break;
    
    //           default:
    //             res.setMessage("opCode invalido!");
    //           break;
    //         }
    //     responseObserver.onNext(res.build());
    //     responseObserver.onCompleted();
    // }
}
