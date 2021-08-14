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

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.List;

public class Client {
    private static Integer getStudentRA() {
        // Este método irá coletar o RA do aluno.

        System.out.println("Digite o RA do aluno: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    private static String getDisciplineCode() {
        // Este método irá coletar o CÓDIGO da disciplina.

        System.out.println("Digite o CÓDIGO da disciplina: ");
        return new Scanner(System.in).nextLine();
    }

    private static Integer getDisciplineYear() {
        // Este método irá coletar o ANO da disciplina.

        System.out.println("Digite o ANO da disciplina: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    private static Integer getDisciplineSemester() {
        // Este método irá coletar o SEMESTRE da disciplina.

        System.out.println("Digite o SEMESTRE da disciplina: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    private static Float getStudentGrade() {
        // Este método irá coletar a NOTA do aluno.

        System.out.println("Digite a NOTA do aluno: ");
        return Float.parseFloat(new Scanner(System.in).nextLine());
    }

    private static Integer getStudentAbsences() {
        // Este método irá coletar o NÚMERO de faltas do aluno.

        System.out.println("Digite o NÚMERO de faltas do aluno: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    }

    private static Object[] getInsertData() {
        // Este método irá coletar os dados para inserir uma nova nota para o aluno.

        Object[] data = new Object[6];

        data[0] = getStudentRA();
        data[1] = getDisciplineCode();
        data[2] = getDisciplineYear();
        data[3] = getDisciplineSemester();
        data[4] = getStudentGrade();
        data[5] = getStudentAbsences();

        return data;
    }

    private static Object[] getDeleteData() {
        // Este método irá coletar os dados para remover as notas do aluno de um determinando ano e semestre.

        Object[] data = new Object[6];

        data[0] = getStudentRA();
        data[1] = getDisciplineCode();
        data[2] = getDisciplineYear();
        data[3] = getDisciplineSemester();
        data[4] = -1.0f;
        data[5] = -1;

        return data;
    }

    private static Object[] getConsultStudent() {
        // Este método irá coletar os dados para consultar as notas de um determinado aluno.

        Object[] data = new Object[6];

        data[0] = getStudentRA();
        data[1] = "";
        data[2] = -1;
        data[3] = -1;
        data[4] = -1.0f;
        data[5] = -1;

        return data;
    }

    private static Object[] getConsultDiscipline() {
        // Este método irá coletar os dados para consultar as notas ou os alunos de uma disciplina em um determinando ano/semestre.


        Object[] data = new Object[6];

        data[0] = -1;
        data[1] = getDisciplineCode();
        data[2] = getDisciplineYear();
        data[3] = getDisciplineSemester();
        data[4] = -1.0f;
        data[5] = -1;

        return data;
    }

    private static void showResponse(Response response, Integer requestType) {
        // Este método irá mostrar a resposta do servidor.
        System.out.println("SERVIDOR:");
        System.out.println("--");
        if (response.get_response_status_code() == 1) { 
            if (requestType == 1 || requestType == 2) { 
                System.out.println("Requisição feita com sucesso!");
            } else {
                if (requestType == 3) {
                    List<NotasByAluno> allNotasByAluno = response.get_notas_by_aluno();

                    for (NotasByAluno notaByAluno : allNotasByAluno) {
                        System.out.println("------");
                        System.out.println("Código: " + notaByAluno.get_cod_disciplina());
                        System.out.println("Ano: " + notaByAluno.get_ano());
                        System.out.println("Semestre: " + notaByAluno.get_semestre());
                        System.out.println("Nota: " + notaByAluno.get_nota());
                        System.out.println("Faltas: " + notaByAluno.get_faltas());
                        System.out.println("------");
                    }

                } else if (requestType == 4) {
                    List<Aluno> allAlunosByDisciplina = response.get_alunos_by_disciplina();

                    for (Aluno alunoByDisciplina : allAlunosByDisciplina) {
                        System.out.println("------");
                        System.out.println("RA: " + alunoByDisciplina.get_RA());
                        System.out.println("Nome: " + alunoByDisciplina.get_nome());
                        System.out.println("------");
                    }

                } else if (requestType == 5) {
                    List<Nota
                }
            }
        } else {
            System.out.println("A requisição falhou:\n\t" + response.get_error());
        }
        System.out.println("--");
    }

    public static void main(String args[]) {
        try {
            /* OPCIONAL: configurar e ativar o Security Manager */
            System.setProperty("java.security.policy", "policy.txt");
            System.setSecurityManager(new SecurityManager());

            System.out.println ("Cliente iniciado ...");

            /* obtem a referencia para o objeto remoto */
            Registry registry = LocateRegistry.getRegistry("localhost");
            NotesManagerRMI nm = (NotesManagerRMI)registry.lookup("NotesManagerService");
            
            Integer requestType;

            while (true) {
                System.out.println("\nDeseja fazer a Inserção de Notas (1), Remoção de Notas (2), Consultar Notas (3), Consultar Notas em um ano/semestre (4), Consultar Alunos em um ano/semestre (5) ou Sair (0): ");
                requestType = Integer.parseInt(new Scanner(System.in).nextLine());  
                while (requestType > 5 || requestType < 0) {
                    System.out.println("Não conheço este tipo de requisição.\n");
                    System.out.println("\nDeseja fazer a Inserção de Notas (1), Remoção de Notas (2), Consultar Notas de um aluno (3), Consultar Notas em um ano/semestre (4), Consultar Alunos em um ano/semestre (5) ou Sair (0): ");
                    requestType = Integer.parseInt(new Scanner(System.in).nextLine());
                }
                
                Object[] data = new Object[6];
                Response response;
                System.out.println("\nCLIENTE:\n--");
                if (requestType == 1) {
                    data = getInsertData();
                    response = nm.add_nota(Integer.parseInt(data[0].toString()), data[1].toString(), Integer.parseInt(data[2].toString()), Integer.parseInt(data[3].toString()), Float.parseFloat(data[4].toString()), Integer.parseInt(data[5].toString()));
                } else if (requestType == 2) {
                    data = getDeleteData();
                    response = nm.remove_nota(Integer.parseInt(data[0].toString()), data[1].toString(), Integer.parseInt(data[2].toString()), Integer.parseInt(data[3].toString()));
                } else if (requestType == 3) {
                    data = getConsultStudent();
                    response = nm.get_notas_by_aluno(Integer.parseInt(data[0].toString()));
                } else if (requestType == 4) {
                    data = getConsultDiscipline();
                    response = nm.list_alunos(data[1].toString(), Integer.parseInt(data[2].toString()), Integer.parseInt(data[3].toString()));
                } else if (requestType == 5) {
                    data = getConsultDiscipline();
                    response = nm.list_notas(data[1].toString(), Integer.parseInt(data[2].toString()), Integer.parseInt(data[3].toString()));
                } else {
                    break;
                }
                System.out.println("--\n");

                // System.out.println(data[0] + " " + data[1] + " " + data[2] + " " + data[3] + " " + data[4] + " " + data[5]);

                showResponse(response, requestType);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
