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


import org.json.JSONObject;

public class Response {
  static int response_status_code;
  static JSONObject object;

  public Response(int response_status_code , JSONObject object){
    System.out.println("constructor with Two parameters");
  }
  public Response(int response_status_code){
    System.out.println("constructor with one parameter");
  }
   public static int get_status(){
     return response_status_code;
   }

  public static int get_object(){
     return object;
   }

  public Response get(){
    return this;
  }
}