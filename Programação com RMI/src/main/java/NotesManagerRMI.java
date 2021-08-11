/**
 * Este código é responsavel pela parte do cliente e comunicação com o servidor, ele ira tratar o envio o dos dados
 * utilizando a ferramenta RMI e o recebimento da mensagem no mesmo formato.
 *
 * Autores:
 *     @hmarcuzzo (Henrique Marcuzzo)
 *     @sorattorafa (Rafael Soratto)
 *
 * Data de Criação: 10 de Ago de 2021
 * Ultima alteração: 10 de Ago de 2021
*/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotesManagerRMI extends Remote {
    public Response add_nota (String RA, String disciplineCode, Integer disciplineYear, Integer disciplineSemester, Float grade, Integer absences) throws RemoteException;
    public Response remmove_nota (String RA, String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException;
    public Response get_notas_by_aluno (String RA) throws RemoteException;
    public Response list_alunos (String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException;
    public Response list_notas (String disciplineCode, Integer disciplineYear, Integer disciplineSemester) throws RemoteException;
}
