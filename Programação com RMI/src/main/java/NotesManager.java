/**
 * Implementacao do objeto remoto
 * autor: Rodrigo Campiolo
 * data: 22/11/2006
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.net.*;
import java.io.*;
public class NotesManager extends UnicastRemoteObject implements NotesManagerRMI {

    public NotesManager() throws RemoteException {
        super();
        System.out.println("Instance of Object created with success");
    } //Calc

    public Response add_nota(Integer RA, String disciplineCode, Integer disciplineYear, Integer disciplineSemester, Float grade, Integer absences) throws RemoteException {

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


        Connection db_connection = SQLiteConnection.connect(); 
        try {
   
            Statement statement = db_connection.createStatement();

            /* search for aluno */
            ResultSet resultSet = statement.executeQuery(search_aluno_query);
            if (!resultSet.isBeforeFirst()) {
                            /* nao achou */

            }

            /* search for disiplina */
            resultSet = statement.executeQuery(search_discipline_query);
            if (!resultSet.isBeforeFirst()) {
            }

            /* search for matricula */
            resultSet = statement.executeQuery(search_matricula_query);

            if (!resultSet.isBeforeFirst()) {

                statement.execute(create_matricula);
                /* search for disiplina */
                ResultSet resultSet2 = statement.executeQuery(search_discipline_query);
                if (!resultSet2.isBeforeFirst()) {
                }
                 
            } else {
                /* Atualiza grade */
                statement.execute(update_grade_query);
                 
            }

            Response resp = new Response(1);
            return resp;

        } catch (SQLException e) {
            Response resp = new Response(0);
            return resp;
        }
    } //add_grade

    public Response remove_nota (Integer RA, String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException {
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

        Connection db_connection = SQLiteConnection.connect();  
        try {
            Statement statement = db_connection.createStatement();

            /* search for aluno */
            ResultSet resultSet = statement.executeQuery(search_aluno_query);
            if (!resultSet.isBeforeFirst()) {
                //response.setResponse("RA inexistente");
                Response resp = new Response(0);
                return resp;
            }

            /* search for disiplina */
            resultSet = statement.executeQuery(search_discipline_query);
            if (!resultSet.isBeforeFirst()) {
                //response.setResponse("Disciplina inexistente");
                Response resp = new Response(0);
                return resp;
            }

            /* search for matricula */
            resultSet = statement.executeQuery(search_matricula_query);
            if (!resultSet.isBeforeFirst()) {
                Response resp = new Response(0);
                return resp;
            }

            /* remove nota */
            statement.execute(remove_nota_query);
            Response resp = new Response(1);
            return resp;

        } catch (SQLException e) {
            Response resp = new Response(1);
        }
        Response resp = new Response(1);
        return resp;
    } //remove_grade


    public Response get_notas_by_aluno (Integer RA) throws RemoteException {
        Response resp = new Response(1);
        String get_alunos_query = "SELECT *  FROM matricula WHERE (ra_aluno = '" + String.valueOf(RA) + "');";

        Connection db_connection = SQLiteConnection.connect();  
        try {
            Statement statement = db_connection.createStatement();
            ResultSet resultSet = statement.executeQuery(get_alunos_query);
            if (!resultSet.isBeforeFirst()) {
            /* n existe*/
            }

            while (resultSet.next()) {
                int faltas = resultSet.getInt("faltas");
                int ano = resultSet.getInt("ano");
                int semestre = resultSet.getInt("semestre");
                float nota_aluno = resultSet.getFloat("nota");
                String cod_disciplina = resultSet.getString("cod_disciplina");

                    /* Construindo Matricula */
                     
                NotasByAluno nota = new NotasByAluno();
                nota.set_cod_disciplina(cod_disciplina);
                nota.set_ano(ano);
                nota.set_semestre(semestre);
                nota.set_nota(nota_aluno);
                nota.set_faltas(faltas);

                resp.set_notas_by_aluno(nota);
                           
            }
            return resp;
        } catch (SQLException e) {
            Response error = new Response(0);
            error.set_error("Erro desconhecido");
            return resp;
        }

    } // divide

    public Response list_alunos (String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException {
        Response resp = new Response(1);
        String disciplina_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(disciplineCode) + "');";
        String get_alunos_query = "SELECT A.ra, A.nome FROM aluno as A JOIN matricula AS M ON A.RA = M.ra_aluno WHERE (M.ano = " + String.valueOf(disciplineYear) + " AND M.semestre = " + String.valueOf(disciplineSemester) + " AND M.cod_disciplina = '" + String.valueOf(disciplineCode) + "');";
        
        Connection db_connection = SQLiteConnection.connect(); 
        try {
            Statement statement = db_connection.createStatement();
            /* search for disiplina */
            ResultSet resultSet = statement.executeQuery(disciplina_query);
            if (!resultSet.isBeforeFirst()) {
            /* n existe*/
            }

            /* Lista alunos */
            resultSet = statement.executeQuery(get_alunos_query);
            if (!resultSet.isBeforeFirst()) {
              
            }

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                int ra = resultSet.getInt("ra");
                Aluno aluno = new Aluno();
                aluno.set_nome(nome);
                aluno.set_RA(ra);
                resp.set_alunos_by_disciplina(aluno);     
            }

        } catch (SQLException e) {
            Response error = new Response(0);
            error.set_error("Erro desconhecido");
            return error;
        }
        return resp;
    } //list_alunos

    public Response list_notas (String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException{
        Response resp = new Response(1);
        String disciplina_query = "SELECT * FROM disciplina WHERE (codigo = '" + String.valueOf(disciplineCode) + "');";
        String get_alunos_query = "SELECT A.ra, M.nota, M.faltas FROM aluno as A JOIN matricula AS M ON A.RA = M.ra_aluno WHERE (M.ano = " + String.valueOf(disciplineYear) + " AND M.semestre = " + String.valueOf(disciplineSemester) + " AND M.cod_disciplina = '" + String.valueOf(disciplineCode) + "');";
        
        Connection db_connection = SQLiteConnection.connect(); 
        try {
            Statement statement = db_connection.createStatement();
            /* search for disiplina */
            ResultSet resultSet = statement.executeQuery(disciplina_query);
            if (!resultSet.isBeforeFirst()) {
            /* n existe*/
            }

            /* Lista alunos */
            resultSet = statement.executeQuery(get_alunos_query);
            if (!resultSet.isBeforeFirst()) {
              
            }

            while (resultSet.next()) {
                float nota = resultSet.getFloat("nota");
                int faltas = resultSet.getInt("faltas");
                int ra = resultSet.getInt("ra");
                NotasByDisciplina alunoNotas = new NotasByDisciplina();
                
                alunoNotas.set_RA(ra);
                alunoNotas.set_faltas(faltas);
                alunoNotas.set_nota(nota);

                resp.set_notas_by_disciplina(alunoNotas);     
            }

        } catch (SQLException e) {
            Response error = new Response(0);
            error.set_error("Erro desconhecido");
            return error;
        }
        return resp;
    }

} //list_notas
