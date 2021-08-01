/**
 * Este código é responsável pela parte de criar uma classe
 * que recebe requisições, faz um processamento no banco e
 * retorna uma resposta
 * Protocolo: RPC
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@sorattorafa)
 * 
 * Data de Criação: 01 de Agosto de 2021 
 * Ultima alteração: 01 de Agosto de 2021
 */

import java.sql.*;


public class Controller {

  /*
    Method to create and update grade of students
  */

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


  /*
    Method to delete grade of students
  */
  public static String remmove_nota_for_json(Request request, Response.Builder response, Connection db_connection) {
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
    String remove_nota_query = "UPDATE matricula SET nota = '' WHERE (ra_aluno = " + String.valueOf(RA)
        + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "' AND ano = " + String.valueOf(ano)
        + " AND semestre = " + String.valueOf(semestre) + ");";

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
        response.setResponse(
            "Matricula do aluno em " + String.valueOf(ano) + "/" + String.valueOf(semestre) + " inexistente");
        return ("Matricula do aluno em " + String.valueOf(ano) + "/" + String.valueOf(semestre) + " inexistente");
      }

      /* remove nota */
      statement.execute(remove_nota_query);
      response.setResponse("1");

    } catch (SQLException e) {
      response.setResponse(String.valueOf(e.getMessage()));
      return String.valueOf(e.getMessage());
    }
    return "1";
  }

    /*
    Method to list students of matricula
  */
  public static String list_alunos_for_json(Request request, Response.Builder response, Connection db_connection) {
    /* GET DATA */
    String cod_disciplina = request.getCodDisciplina();
    int ano = request.getAno();
    int semestre = request.getSemestre();
    
    String disciplina_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(cod_disciplina) + "');";
    String get_alunos_query = "SELECT *  FROM aluno as A JOIN matricula AS M ON A.RA = M.ra_aluno WHERE (M.ano = " + String.valueOf(ano) + " AND M.semestre = " + String.valueOf(semestre) + " AND M.cod_disciplina = '" + String.valueOf(cod_disciplina) + "');";
    try {

      Statement statement = db_connection.createStatement();

      /* search for disiplina */
      ResultSet resultSet = statement.executeQuery(disciplina_query);
      if (!resultSet.isBeforeFirst()) {
        response.setResponse("Disciplina inexistente");
        return "Disciplina inexistente";
      }

      /* Lista alunos */
      resultSet = statement.executeQuery(get_alunos_query);
      if (!resultSet.isBeforeFirst()) {
        response.setResponse(
            "Nesta disciplina nao há alunos matriculados em " + String.valueOf(ano) + "/" + String.valueOf(semestre));
        return ("Nesta disciplina nao há alunos matriculados em " + String.valueOf(ano) + "/"
            + String.valueOf(semestre));
      }

      while (resultSet.next()) {

        /* Construindo Matricula */
        Matricula.Builder matricula = Matricula.newBuilder();
        
        /* Adicionando valores a matricula */
        matricula.setRA(resultSet.getInt("ra_aluno"));
        matricula.setNota(resultSet.getFloat("nota"));
        matricula.setFaltas(resultSet.getInt("faltas"));
        matricula.setCodDisciplina(resultSet.getString("cod_disciplina"));
        matricula.setAno(resultSet.getInt("ano"));
        matricula.setSemestre(resultSet.getInt("semestre"));

        /* Adicionando matricula */
        response.addMatriculas(matricula);
      }
      
      response.setResponse("1");

    } catch (SQLException e) {
      response.setResponse(String.valueOf(e.getMessage()));
      return String.valueOf(e.getMessage());
    }
    return "1";
  }

}
