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

    public int add_nota (String RA, String disciplineCode, Integer disciplineYear, Integer disciplineSemester, Float grade, Integer absences) throws RemoteException {
        return 1;
    } //soma

    public int remmove_nota (String RA, String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException {
        return 1;
    } //subtrai

    public int get_notas_by_aluno (String RA) throws RemoteException {
        return 1;
    } // divide

    public int list_alunos (String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException {
        return 1;
    } //multiplica

    public list_notas (String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException{
        return 1;
    }

} //Calc
