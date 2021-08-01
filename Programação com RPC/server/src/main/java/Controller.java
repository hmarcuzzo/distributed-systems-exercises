
/**
 * Este código é responseponsavel pela parte de criar uma classe genérica para requestuisições 
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

import java.sql.*;


public class Controller {

  public static String add_nota_for_json(Request request, Response.Builder response, Connection db_connection) {
    /* GET DATA */
    int RA = request.getRA();
    String cod_disciplina = request.getCodDisciplina();
    int ano = request.getAno();
    int semestre = request.getSemestre();
    Float nota = request.getNota();
    Integer faltas = request.getFaltas();

    /* GET QUERYS */
    String search_aluno_query = "SELECT * FROM aluno WHERE (ra = " + String.valueOf(RA) + ");";
    String search_discipline_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(cod_disciplina)
        + "');";
    String search_matricula_query = "SELECT * FROM matricula WHERE (ra_aluno = " + String.valueOf(RA)
        + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "' AND ano = " + String.valueOf(ano)
        + " AND semestre = " + String.valueOf(semestre) + ");";
    String update_nota_query = "UPDATE matricula SET nota = " + String.valueOf(nota) + ", faltas = "
        + String.valueOf(faltas) + " WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '"
        + String.valueOf(cod_disciplina) + "' AND ano = " + String.valueOf(ano) + " AND semestre = "
        + String.valueOf(semestre) + ");";
    String create_matricula = "INSERT INTO matricula (ano, semestre, cod_disciplina, ra_aluno, nota, faltas) VALUES ("
      + ano + ", " + semestre + ", '" + cod_disciplina + "', " + RA + ", " + nota + ", " + faltas + ");"; 

    try {

      Statement statement = db_connection.createStatement();

      /* search for aluno */
      ResultSet resultSet = statement.executeQuery(search_aluno_query);
      if (!resultSet.isBeforeFirst()) {
        response.setResponse("RA inexistente");
        return "RA inexistente";
      }

      /* search for disiplina */
      resultSet = statement.executeQuery(search_discipline_query);
      if (!resultSet.isBeforeFirst()) {
        response.setResponse("Disciplina inexistente");
        return "Disciplina inexistente";
      }

      /* search for matricula */
      resultSet = statement.executeQuery(search_matricula_query);

      if (!resultSet.isBeforeFirst()) {
        // System.out.println(create_matricula);
        statement.execute(create_matricula);
        /* search for disiplina */
        ResultSet resultSet2 = statement.executeQuery(search_discipline_query);
        if (!resultSet2.isBeforeFirst()) {
          response.setResponse("Disciplina inexistente");
          return "Disciplina inexistente";
        }
        response.setResponse("1");
      } else {
        /* Atualiza nota */
        statement.execute(update_nota_query);
        response.setResponse("1");
      }

    } catch (SQLException e) {
      response.setResponse(String.valueOf(e.getMessage()));
      return String.valueOf(e.getMessage());
    }

    /* GET SUCCESS */
    return "1";
  }

  // public static String remmove_nota_for_json(Request request, Response response, Connection db_connection) {
  //   /* GET DATA */
  //   int RA = request.getRA();
  //   String cod_disciplina = request.getCodDisciplina();
  //   int ano = request.getAno();
  //   int semestre = request.getSemestre();

  //   /* GET QUERYS */
  //   String search_aluno_query = "SELECT * FROM aluno WHERE (ra = " + String.valueOf(RA) + ");";
  //   String search_discipline_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(cod_disciplina)
  //       + "');";
  //   String search_matricula_query = "SELECT * FROM matricula WHERE (ra_aluno = " + String.valueOf(RA)
  //       + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "' AND ano = " + String.valueOf(ano)
  //       + " AND semestre = " + String.valueOf(semestre) + ");";
  //   String remove_nota_query = "UPDATE matricula SET nota = '' WHERE (ra_aluno = " + String.valueOf(RA)
  //       + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "' AND ano = " + String.valueOf(ano)
  //       + " AND semestre = " + String.valueOf(semestre) + ");";

  //   try {

  //     Statement statement = db_connection.createStatement();

  //     /* search for aluno */
  //     ResultSet resultSet = statement.executeQuery(search_aluno_query);
  //     if (!resultSet.isBeforeFirst()) {
  //       response.setMessage("RA inexistente");
  //       return "RA inexistente";
  //     }

  //     /* search for disiplina */
  //     resultSet = statement.executeQuery(search_discipline_query);
  //     if (!resultSet.isBeforeFirst()) {
  //       response.setMessage("Disciplina inexistente");
  //       return "Disciplina inexistente";
  //     }

  //     /* search for matricula */
  //     resultSet = statement.executeQuery(search_matricula_query);
  //     if (!resultSet.isBeforeFirst()) {
  //       response.setMessage(
  //           "Matricula do aluno em " + String.valueOf(ano) + "/" + String.valueOf(semestre) + " inexistente");
  //       return ("Matricula do aluno em " + String.valueOf(ano) + "/" + String.valueOf(semestre) + " inexistente");
  //     }

  //     /* remove nota */
  //     statement.execute(remove_nota_query);
  //     response.setMessage("1");

  //   } catch (SQLException e) {
  //     response.setMessage(String.valueOf(e.getMessage()));
  //     return String.valueOf(e.getMessage());
  //   }
  //   return "1";
  // }

  // public static String list_alunos_for_json(Request request, Response response, Connection db_connection) {
  //   /* GET DATA */
  //   String cod_disciplina = request.getCodDisciplina();
  //   int ano = request.getAno();
  //   int semestre = request.getSemestre();
    
  //   String disciplina_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(cod_disciplina) + "');";
  //   String get_alunos_query = "SELECT *  FROM aluno as A JOIN matricula AS M ON A.RA = M.ra_aluno WHERE (M.ano = " + String.valueOf(ano) + " AND M.semestre = " + String.valueOf(semestre) + " AND M.cod_disciplina = '" + String.valueOf(cod_disciplina) + "');";
  //   try {

  //     Statement statement = db_connection.createStatement();

  //     /* search for disiplina */
  //     ResultSet resultSet = statement.executeQuery(disciplina_query);
  //     if (!resultSet.isBeforeFirst()) {
  //       response.setMessage("Disciplina inexistente");
  //       return "Disciplina inexistente";
  //     }

  //     /* Lista alunos */
  //     resultSet = statement.executeQuery(get_alunos_query);
  //     if (!resultSet.isBeforeFirst()) {
  //       response.setMessage(
  //           "Nesta disciplina nao há alunos matriculados em " + String.valueOf(ano) + "/" + String.valueOf(semestre));
  //       return ("Nesta disciplina nao há alunos matriculados em " + String.valueOf(ano) + "/"
  //           + String.valueOf(semestre));
  //     }

  //     while (resultSet.next()) {

  //       /* Construindo Aluno */
  //       Matricula.Builder matricula = Matricula.newBuilder();

  //       /* Adicionando valoresponse no aluno */
  //       aluno.set_RA(resultSet.getInt("ra"));
  //       aluno.set_nome(resultSet.getString("nome"));
  //       aluno.set_periodo(resultSet.getInt("periodo"));
  //       aluno.set_nota(resultSet.getFloat("nota"));
  //       aluno.set_faltas(resultSet.getInt("faltas"));

  //       /* Adicionando aluno */
  //       response.set_alunos(aluno);
  //     }

  //     response.setMessage("1");

  //   } catch (SQLException e) {
  //     response.setMessage(String.valueOf(e.getMessage()));
  //     return String.valueOf(e.getMessage());
  //   }
  //   return "1";
  // }
}
