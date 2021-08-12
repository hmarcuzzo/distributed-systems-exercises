/**
 * Implementacao do objeto remoto
 * autor: Rodrigo Campiolo
 * data: 22/11/2006
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class NotesManager extends UnicastRemoteObject implements Calculadora {

    public NotesManager() throws RemoteException {
        super();
        System.out.println("Instance of Object created with success");
    } //Calc

    public Response add_grade (String RA, String disciplineCode, Integer disciplineYear, Integer disciplineSemester, Float grade, Integer absences) throws RemoteException {

        String search_aluno_query = "SELECT * FROM aluno WHERE (ra = " + String.valueOf(RA) + ");";
        String search_discipline_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(disciplineCode)
            + "');";
        String search_matricula_query = "SELECT * FROM matricula WHERE (ra_aluno = " + String.valueOf(RA)
            + " AND cod_disciplina = '" + String.valueOf(disciplineCode) + "' AND ano = " + String.valueOf(disciplineYear)
            + " AND semestre = " + String.valueOf(disciplineSemester) + ");";
        String update_grade_query = "UPDATE matricula SET grade = " + String.valueOf(grade) + ", faltas = "
            + String.valueOf(absences) + " WHERE (ra_aluno = " + String.valueOf(RA) + " AND cod_disciplina = '"
            + String.valueOf(disciplineCode) + "' AND ano = " + String.valueOf(disciplineYear) + " AND semestre = "
            + String.valueOf(disciplineSemester) + ");";
        String create_matricula = "INSERT INTO matricula (ano, semestre, cod_disciplina, ra_aluno, nota, faltas) VALUES ("
        + disciplineYear + ", " + disciplineSemester + ", '" + disciplineCode + "', " + RA + ", " + grade + ", " + absences + ");"; 

        try {

            
            Connection db_connection = SQLiteConnection.connect();    
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
                /* Atualiza grade */
                statement.execute(update_grade_query);
                response.setResponse("1");
            }

            Response resp = new Response(1);
            return resp.get();

        } catch (SQLException e) {
            response.setResponse(String.valueOf(e.getMessage()));
            Response resp = new Response(0);
            return resp.get();
        }
    } //add_grade

    public Response remove_grade (String RA, String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException {
        /* GET QUERYS */
        String search_aluno_query = "SELECT * FROM aluno WHERE (ra = " + String.valueOf(RA) + ");";
        String search_discipline_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(disciplineCode)
            + "');";
        String search_matricula_query = "SELECT * FROM matricula WHERE (ra_aluno = " + String.valueOf(RA)
            + " AND cod_disciplina = '" + String.valueOf(disciplineCode) + "' AND ano = " + String.valueOf(disciplineYear)
            + " AND semestre = " + String.valueOf(disciplineSemester) + ");";
        String remove_nota_query = "UPDATE matricula SET nota = '' WHERE (ra_aluno = " + String.valueOf(RA)
            + " AND cod_disciplina = '" + String.valueOf(disciplineCode) + "' AND ano = " + String.valueOf(disciplineYear)
            + " AND semestre = " + String.valueOf(disciplineSemester) + ");";

        try {
            Connection db_connection = SQLiteConnection.connect();    
            Statement statement = db_connection.createStatement();

            /* search for aluno */
            ResultSet resultSet = statement.executeQuery(search_aluno_query);
            if (!resultSet.isBeforeFirst()) {
                //response.setResponse("RA inexistente");
                Response resp = new Response(0);
                return resp.get();
            }

            /* search for disiplina */
            resultSet = statement.executeQuery(search_discipline_query);
            if (!resultSet.isBeforeFirst()) {
                //response.setResponse("Disciplina inexistente");
                Response resp = new Response(0);
                return resp.get();
            }

            /* search for matricula */
            resultSet = statement.executeQuery(search_matricula_query);
            if (!resultSet.isBeforeFirst()) {
                Response resp = new Response(0);
                return resp.get();
            }

            /* remove nota */
            statement.execute(remove_nota_query);
            Response resp = new Response(1);
            return resp.get();

        } catch (SQLException e) {
            statement.execute(remove_nota_query);
            Response resp = new Response(1);
        }
        Response resp = new Response(1);
        return resp.get();
    } //remove_grade


    public Response get_grades_by_aluno (String RA) throws RemoteException {
         Response resp = new Response(1);
        return resp.get();
    } // divide

    public Response list_alunos (String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException {
        
        String disciplina_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(disciplineCode) + "');";
        String get_alunos_query = "SELECT *  FROM aluno as A JOIN matricula AS M ON A.RA = M.ra_aluno WHERE (M.ano = " + String.valueOf(disciplineYear) + " AND M.semestre = " + String.valueOf(disciplineSemester) + " AND M.cod_disciplina = '" + String.valueOf(disciplineCode) + "');";
        
        try {

            Connection db_connection = SQLiteConnection.connect(); 
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
                    "Nesta disciplina nao há alunos matriculados em " + String.valueOf(disciplineYear) + "/" + String.valueOf(disciplineSemester));
                return ("Nesta disciplina nao há alunos matriculados em " + String.valueOf(disciplineYear) + "/"
                    + String.valueOf(disciplineSemester));
            }

            while (resultSet.next()) {

                /* Construindo Matricula */
                Matricula.Builder matricula = Matricula.newBuilder();
                
                /* Adicionando valores a matricula */
                matricula.setRA(resultSet.getInt("ra_aluno"));
                matricula.setNota(resultSet.getFloat("nota"));
                matricula.setFaltas(resultSet.getInt("faltas"));
                matricula.setCodDisciplina(resultSet.getString("disciplineCode"));
                matricula.setAno(resultSet.getInt("disciplineYear"));
                matricula.setSemestre(resultSet.getInt("disciplineSemester"));

                /* Adicionando matricula */
                response.addMatriculas(matricula);
            }
            
            response.setResponse("1");

        } catch (SQLException e) {
            Response resp = new Response(0);
            return resp.get();
        }
        Response resp = new Response(1);
        return resp.get();
    } //multiplica

    public Response list_grades (String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException{
         Response resp = new Response(1);
        return resp.get();
    }

} //get_grades_by_aluno
