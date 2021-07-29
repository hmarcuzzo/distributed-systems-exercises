/**
 * Este código é responsavel pela parte do receber a mensagem do cliente, executar o banco, processar a requisição e
 * retornar a resposta para o cliente em formato Protocol Buffer ou JSON, via socket TCP.
 * 
 * Protocolo: 
 *  TODO: Declarar o protocolo
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@sorattorafa)
 * 
 * Data de Criação: 25 de Jul de 2021
 * Ultima alteração: 25 de Jul de 2021
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;
import com.google.gson.*;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ServidorTcpDatabase {
    static Connection db_connection;
   
    public static void main(String args[]) {
        try {
            // Conexao com banco de dados
            db_connection = SQLiteConnection.connect();
            int serverPort = 7000; 
            // listen port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                // socket accept
                Socket clientSocket = listenSocket.accept();
                
                /* cria um thread para atender a conexao */
                ClientThread c = new ClientThread(clientSocket, db_connection);

                /* inicializa a thread */
                c.start();
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        } 
    }
}


class ClientThread extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    Connection db_connection;

    /* constructor*/
    public ClientThread(Socket clientSocket, Connection db_connection) {
        try {
            this.clientSocket = clientSocket;
            this.db_connection = db_connection;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("Connection:" + ioe.getMessage());
        }
    } //construtor

    /* metodo executado ao iniciar a thread - start() */
    @Override
    public void run() {
        while (true) {

            String protocolMessage = "protobuf";
            Integer requestType = 0;
            String messageSize;
            byte[] buffer = "empty".getBytes();
            Charset UTF8_CHARSET = Charset.forName("UTF-8");


            try {
                protocolMessage = in.readLine(); // json or protobuf
                // System.out.println(protocolMessage);
            } catch (IOException e) {
                //TODO: handle exception
            }
            
            try {
                String type = in.readLine(); // método escolhido
                requestType = Integer.parseInt(type);
                // System.out.println(requestType);
            } catch (IOException e) {
                //TODO: handle exception
            }

            if (requestType == 0) break;

            try {
                /* Recebe a mensagem */
                messageSize = in.readLine();
                int sizeBuffer = Integer.valueOf(messageSize);
                buffer = new byte[sizeBuffer];
                in.read(buffer);
                String decode;

                if(protocolMessage.equals("protobuf")) {
                    // /* realiza o unmarshalling */
                    Database.Matricula discipline = Database.Matricula.parseFrom(buffer);
                    JSONObject jsonData = new JSONObject();

                    jsonData.put("RA", discipline.getRA());
                    jsonData.put("cod_disciplina", discipline.getCodDisciplina()); 
                    jsonData.put("ano", discipline.getAno()); 
                    jsonData.put("semestre", discipline.getSemestre()); 
                    jsonData.put("nota", discipline.getNota());
                    jsonData.put("faltas", discipline.getFaltas());
                    
                    decode = jsonData.toString();
                } else {
                    /* realiza o unmarshalling */
                    decode = new String(buffer, UTF8_CHARSET);
                }
                
                Gson gson = new Gson();

                /* Faz a conversão de Json para a classe Request */
                Request request = gson.fromJson(decode, Request.class);
                
                String request_code;
                
                switch (requestType) {
                    case 1:
                        request_code = "addnota";
                        break;

                    case 2:
                        request_code = "removenota";
                        break;

                    default:
                        request_code = "liststudents";
                        break;

                }
                request.set_request_code(request_code);
                // request_code = request.get_request_code();

                /* Instancia a resposta */
                Response response = new Response();


                /* Chama a funcionalidade de acordo com o opCode */
                switch (request_code) {
                    case "addnota":
                        Controller.add_nota_for_json(request, response, db_connection);
                        break;

                    case "removenota":
                        Controller.remmove_nota_for_json(request, response, db_connection);
                        break;

                    case "liststudents":
                        Controller.list_alunos_for_json(request, response, db_connection);
                        break;

                    default:
                        response.set_response("Invalid option!");
                        break;
                }

                if(protocolMessage.equals("protobuf")) {
                    String respMsg = response.get_response();

                    /*  Codifica a mensagem para UTF8 */
                    byte [] respMsgEncode = respMsg.getBytes("UTF8");
                    out.write(respMsgEncode);

                    if (request_code.equals("liststudents")) {
                        
                    }

                } else {
                    /* Formata a resposta para Json */
                    String msg = gson.toJson(response);
    
                    /*  Codifica a mensagem para UTF8 */
                    byte [] msgEncode = msg.getBytes("UTF8");
            
                    /* Manda tamanho da resposta */
                    String msgSize = String.valueOf(msgEncode.length) + " \n";
                    byte[] size = msgSize.getBytes();
                    out.write(size);
                    
                    /* Manda resposta */
                    out.write(msgEncode);
                }

            } catch (IOException e) {
                //TODO: handle exception
            }
            
            if(protocolMessage.equals("protobuf")) {
                try {
                    // /* realiza o unmarshalling */
                    Database.Matricula discipline = Database.Matricula.parseFrom(buffer);
                    // System.out.println(discipline.getFaltas());
                    
                    /* exibe na tela */
                    System.out.println("--\n" + discipline + "--\n");

                    // TODO: Parte que envolve o banco de dados
                } catch (Exception e) {
                    //TODO: handle exception
                }
            } else {
                JSONObject discipline = new JSONObject(new String(buffer));
                System.out.println("--\n" + discipline + "\n--\n");
                // System.out.println("--\n" + discipline.getInt("RA") + "\n--\n");
            }
        }

        try {
            this.clientSocket.close();
        } catch (IOException e) {
            //TODO: handle exception
        }
    }
}
