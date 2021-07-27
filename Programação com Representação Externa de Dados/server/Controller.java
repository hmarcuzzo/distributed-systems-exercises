/**
 * Este código é responseponsavel pela parte de criar uma classe genérica para
 * requestuisições Protocolo: TODO: Declarar o protocolo
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@soratoorafa)
 * 
 *         Data de Criação: 25 de Jul de 2021 Ultima alteração: 25 de Jul de
 *         2021
 */

import java.sql.*;

public class Controller {

  
  public static String addNotaForJson(Request request, Response response, Connection db_connection) {
    /* GET DATA */
    int RA = request.get_RA();
    String discipline_code = request.get_discipline_code();
    int year = request.get_year();
    int semester = request.get_semester();
    float nota = request.get_grade();
    String search_student_query = "SELECT * FROM aluno WHERE (ra = " + String.valueOf(RA) + ");";
    String search_discipline_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(discipline_code) + "');";
    String search_matricula_query = "SELECT * FROM matricula WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(discipline_code) + "' AND year = "+ String.valueOf(year) +" AND semester = "+ String.valueOf(semester) +");";
    String update_grade_query = "UPDATE matricula SET nota = " + String.valueOf(nota) + " WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(discipline_code) + "' AND ano = "+ String.valueOf(year) +" AND semestre = "+ String.valueOf(semester) +");";
    String remove_grade_query = "UPDATE matricula SET nota = '' WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(discipline_code) + "' AND year = "+ String.valueOf(year) +" AND semester = "+ String.valueOf(semester) +");";
    try {

      Statement statement = db_connection.createStatement();

      /* search for student */
      
      ResultSet resultSet = statement.executeQuery(search_student_query);
      if(!resultSet.isBeforeFirst()){
        response.set_response("RA inexistente");
        return "RA inexistente";
      }

      /* search for disiplina */
      resultSet = statement.executeQuery();
      if(!resultSet.isBeforeFirst()){
        response.set_response("Disciplina inexistente");
        return "Disciplina inexistente";
      }

      /* search for matricula */
      resultSet = statement.executeQuery(search_matricula_query);
      if(!resultSet.isBeforeFirst()){
        response.set_response("Matricula do student em " + String.valueOf(year) + "/" + String.valueOf(semester) + " inexistente");
        return ("Matricula do student em " + String.valueOf(year) + "/" + String.valueOf(semester) + " inexistente");
      }

      /* Atualiza nota */
      statement.execute(update_grade_query);
      response.set_response("1");
      

    } catch (SQLException e) {
      response.set_response(String.valueOf(e.getMessage()));
      return String.valueOf(e.getMessage());
    }
    return "1";
  }

  public static String rmNotaForJson(Request request, Response response, Connection db_connection) {
    /* Obtendo os dados para a busca e insercao */
    int RA = request.get_RA();
    String discipline_code = request.get_discipline_code();
    int year = request.get_year();
    int semester = request.get_semester();
    String search_student_query = "SELECT * FROM aluno WHERE (ra = " + String.valueOf(RA) + ");";
    String search_discipline_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(discipline_code) + "');";
    String search_matricula_query = "SELECT * FROM matricula WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(discipline_code) + "' AND year = "+ String.valueOf(year) +" AND semester = "+ String.valueOf(semester) +");";
    String update_grade_query = "UPDATE matricula SET nota = " + String.valueOf(nota) + " WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(discipline_code) + "' AND ano = "+ String.valueOf(year) +" AND semestre = "+ String.valueOf(semester) +");";
    String remove_grade_query = "UPDATE matricula SET nota = '' WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(discipline_code) + "' AND year = "+ String.valueOf(year) +" AND semester = "+ String.valueOf(semester) +");";
   
    try {

      Statement statement = db_connection.createStatement();

      /* search for student */
      ResultSet resultSet = statement.executeQuery(search_student_query);
      if(!resultSet.isBeforeFirst()){
        response.set_response("RA inexistente");
        return "RA inexistente";
      }

      /* search for disiplina */
      resultSet = statement.executeQuery(search_discipline_query);
      if(!resultSet.isBeforeFirst()){
        response.set_response("Disciplina inexistente");
        return "Disciplina inexistente";
      }

      /* search for matricula */
      resultSet = statement.executeQuery(search_matricula_query);
      if(!resultSet.isBeforeFirst()){
        response.set_response("Matricula do aluno em " + String.valueOf(year) + "/" + String.valueOf(semester) + " inexistente");
        return ("Matricula do student em " + String.valueOf(year) + "/" + String.valueOf(semester) + " inexistente");
      }

      /* remove grade */
      statement.execute(remove_grade_query);
      response.set_response("1");

    } catch (SQLException e) {
      response.set_response(String.valueOf(e.getMessage()));
      return String.valueOf(e.getMessage());
    }
    return "1";
  }

  public static String listStudentsForJson(Request request, Response response, Connection db_connection) {
    /* Obtendo os dados para a busca */
    String discipline_code = request.get_discipline_code();
    int year = request.get_year();
    int semester = request.get_semester();

    try {

      Statement statement = db_connection.createStatement();

      /* search for disiplina */
      ResultSet resultSet = statement.executeQuery(search_discipline_query);
      if(!resultSet.isBeforeFirst()){
        response.set_response("Disciplina inexistente");
        return "Disciplina inexistente";
      }


      /* Lista students */
      resultSet = statement.executeQuery();
      if(!resultSet.isBeforeFirst()){
        response.set_response("Nesta disciplina nao há alunos matriculados em " + String.valueOf(year) + "/" + String.valueOf(semester));
        return ("Nesta disciplina nao há alunos matriculados em " + String.valueOf(year) + "/" + String.valueOf(semester));
      }

      while (resultSet.next()) {

        /* Construindo Student */
        Student student = new Student();
        
        /* Adicionando valoresponse no student */
        student.set_RA(resultSet.getInt("ra"));
        student.set_name(resultSet.getString("nome"));
        student.set_period(resultSet.getInt("periodo"));
        student.set_grade(resultSet.getFloat("nota"));
        student.set_absences(resultSet.getInt("faltas"));

        /* Adicionando student */
        response.set_students(student);
      }
  
      response.set_response("1");

    } catch (SQLException e) {
      response.set_response(String.valueOf(e.getMessage()));
      return String.valueOf(e.getMessage());
    }
    return "1";
  }
}
