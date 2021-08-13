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

import java.io.Serializable;

public class Response implements Serializable {
  private int response_status_code;
  private String error;
  private String response_data;

  public Response(){
    System.out.println("constructor with no parameter");
  }

  public Response(int response_status_code) {
    this.response_status_code = response_status_code;
    System.out.println("constructor with one parameter");
  }
  
  public int get_status() {
    return this.response_status_code;
  }

  public String get_error() {
    return this.error;
  }

  public void set_error(String error) {
    this.error = error;
  }
    
  public Response get(){
    return this;
  }
}