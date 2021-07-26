/**
 * Este código é responsavel pela parte do receber a mensagem do cliente, executar o banco, processar a requisição e
 * retornar a resposta para o cliente em formato Protocol Buffer ou JSON, via socket TCP.
 * 
 * Protocolo: 
 *  TODO: Declarar o protocolo
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@soratoorafa)
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

import java.net.*;
import java.io.*;
import java.sql.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ServidorTcpDatabase {
    static Connection db_connection;
    private static Charset UTF8_CHARSET = Charset.forName("UTF-8");
   
    public static void main(String args[]) {
        db_connection = SQLiteConnection.connect();
        try {
            // Conexao com banco de dados

            int serverPort = 7000; 
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {    
                Socket clientSocket = listenSocket.accept();
                
                /* cria um thread para atender a conexao */
                ClientThread c = new ClientThread(clientSocket);

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

    public ClientThread(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
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

            try {
                protocolMessage = in.readLine();
                // System.out.println(protocolMessage);
            } catch (IOException e) {
                //TODO: handle exception
            }
            
            try {
                requestType = Integer.parseInt(in.readLine());
                // System.out.println(requestType);
            } catch (IOException e) {
                //TODO: handle exception
            }
            
            try {
                 /* Recebe a mensagem */
                messageSize = in.readLine();
                int sizeBuffer = Integer.valueOf(messageSize);
                buffer = new byte[sizeBuffer];
                in.read(buffer);
            } catch (IOException e) {
                //TODO: handle exception
            }

            if (requestType == 0) break;
            
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
