/**
 * Implementacao do objeto remoto
 * autor: Rodrigo Campiolo
 * data: 22/11/2006
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NotesManager extends UnicastRemoteObject implements Calculadora {

    public NotesManager() throws RemoteException {
        super();
        System.out.println("Instance of Object created with success");
    } //Calc

    public Response add_nota (String RA, String disciplineCode, Integer disciplineYear, Integer disciplineSemester, Float grade, Integer absences) throws RemoteException {
        Response resp = new Response(1);
        return resp.get();
    } //soma

    public Response remmove_nota (String RA, String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException {
         Response resp = new Response(1);
        return resp.get();
    } //subtrai

    public Response get_notas_by_aluno (String RA) throws RemoteException {
         Response resp = new Response(1);
        return resp.get();
    } // divide

    public Response list_alunos (String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException {
         Response resp = new Response(1);
        return resp.get();
    } //multiplica

    public Response list_notas (String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException{
         Response resp = new Response(1);
        return resp.get();
    }

} //Calc
