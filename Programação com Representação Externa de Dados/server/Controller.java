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

  public static String add_nota_for_json(Request request, Response response, Connection db_connection) {
    /* GET DATA */
    int RA = request.get_RA();
    String cod_disciplina = request.get_cod_disciplina();
    int ano = request.get_ano();
    int semestre = request.get_semestre();
    float nota = request.get_nota();

    String search_student_query = "SELECT * FROM aluno WHERE (ra = " + String.valueOf(RA) + ");";
    String search_discipline_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(cod_disciplina) + "');";
    String search_matricula_query = "SELECT * FROM matricula WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "' AND ano = "+ String.valueOf(ano) +" AND semestre = "+ String.valueOf(semestre) +");";
    String update_nota_query = "UPDATE matricula SET nota = " + String.valueOf(nota) + " WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "' AND ano = "+ String.valueOf(ano) +" AND semestre = "+ String.valueOf(semestre) +");";
    String remove_nota_query = "UPDATE matricula SET nota = '' WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "' AND ano = "+ String.valueOf(ano) +" AND semestre = "+ String.valueOf(semestre) +");";
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
        response.set_response("Matricula do student em " + String.valueOf(ano) + "/" + String.valueOf(semestre) + " inexistente");
        return ("Matricula do student em " + String.valueOf(ano) + "/" + String.valueOf(semestre) + " inexistente");
      }

      /* Atualiza nota */
      statement.execute(update_nota_query);
      response.set_response("1");
      

    } catch (SQLException e) {
      response.set_response(String.valueOf(e.getMessage()));
      return String.valueOf(e.getMessage());
    }
    return "1";
  }

  public static String remmove_nota_for_json(Request request, Response response, Connection db_connection) {
    /* Obtendo os dados para a busca e insercao */
    int RA = request.get_RA();
    String cod_disciplina = request.get_cod_disciplina();
    int ano = request.get_ano();
    int semestre = request.get_semestre();
    float nota = request.get_nota();
    String search_student_query = "SELECT * FROM aluno WHERE (ra = " + String.valueOf(RA) + ");";
    String search_discipline_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(cod_disciplina) + "');";
    String search_matricula_query = "SELECT * FROM matricula WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "' AND ano = "+ String.valueOf(ano) +" AND semestre = "+ String.valueOf(semestre) +");";
    String update_nota_query = "UPDATE matricula SET nota = " + String.valueOf(nota) + " WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "' AND ano = "+ String.valueOf(ano) +" AND semestre = "+ String.valueOf(semestre) +");";
    String remove_nota_query = "UPDATE matricula SET nota = '' WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "' AND ano = "+ String.valueOf(ano) +" AND semestre = "+ String.valueOf(semestre) +");";
   
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
        response.set_response("Matricula do aluno em " + String.valueOf(ano) + "/" + String.valueOf(semestre) + " inexistente");
        return ("Matricula do student em " + String.valueOf(ano) + "/" + String.valueOf(semestre) + " inexistente");
      }

      /* remove nota */
      statement.execute(remove_nota_query);
      response.set_response("1");

    } catch (SQLException e) {
      response.set_response(String.valueOf(e.getMessage()));
      return String.valueOf(e.getMessage());
    }
    return "1";
  }

  public static String list_students_for_json(Request request, Response response, Connection db_connection) {
    /* Obtendo os dados para a busca */
    String cod_disciplina = request.get_cod_disciplina();
    int ano = request.get_ano();
    int semestre = request.get_semestre();

    try {

      Statement statement = db_connection.createStatement();

      /* search for disiplina */
      ResultSet resultSet = statement.executeQuery("SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(cod_disciplina) + "');");
      if(!resultSet.isBeforeFirst()){
        response.set_response("Disciplina inexistente");
        return "Disciplina inexistente";
      }


      /* Lista students */
      resultSet = statement.executeQuery("SELECT * FROM aluno, matricula WHERE (select ra_aluno FROM matricula WHERE ano = " + String.valueOf(ano) + " AND semestre = " + String.valueOf(semestre) + " AND cod_disciplina = '" + String.valueOf(cod_disciplina) + "') AND matricula.ra_aluno = aluno.ra;");
      if(!resultSet.isBeforeFirst()){
        response.set_response("Nesta disciplina nao há alunos matriculados em " + String.valueOf(ano) + "/" + String.valueOf(semestre));
        return ("Nesta disciplina nao há alunos matriculados em " + String.valueOf(ano) + "/" + String.valueOf(semestre));
      }

      while (resultSet.next()) {

        /* Construindo Student */
        Student student = new Student();
        
        /* Adicionando valoresponse no student */
        student.set_RA(resultSet.getInt("ra"));
        student.set_nome(resultSet.getString("nome"));
        student.set_period(resultSet.getInt("periodo"));
        student.set_nota(resultSet.getFloat("nota"));
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
