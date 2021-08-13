/**
 * Este código é responsável pela parte de criar uma conexão com o banco SQLITE
 * 
 * Protocolo: RPC
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@sorattorafa)
 * 
 * Data de Criação: 11 de Agosto de 2021 
 * Ultima alteração: 11 de Agosto de 2021
 */

public class Response {
  static int response_status_code;
  static String error;
  static String response_data;

  public Response(){
    System.out.println("constructor with no parameter");
  }

  public Response(int response_status_code){
    System.out.println("constructor with one parameter");
  }
   public static int get_status(){
     return response_status_code;
   }

  public Response get(){
    return this;
  }
}