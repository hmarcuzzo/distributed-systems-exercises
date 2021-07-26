/**
 * Este código é responsavel pela parte de criar um singleton
 * para a conexão com o banco de dados, sempre a variável 
 * connection será acessada durante aas requisições
 * Protocolo: 
 *  TODO: Declarar o protocolo
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@soratoorafa)
 * 
 * Data de Criação: 25 de Jul de 2021
 * Ultima alteração: 25 de Jul de 2021
 */

import java.sql.*;

public class SQLiteConnection{
  static Connection connection;

  public static Connection connect() {    
    try {
      connection = DriverManager.getConnection( "jdbc:sqlite:../database/gerenciamento_notas.db");
      System.out.println("BD connection success!");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return connection;
  }
}