/**
 * Este código é responsável pela parte de criar uma conexão com o banco SQLITE
 * 
 * Protocolo: RPC
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@sorattorafa)
 * 
 * Data de Criação: 11 de Agosto de 2021 
 * Ultima alteração: 11 de Agosto de 2021
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable {
  private int response_status_code;
  private String error;
  private List<NotasByAluno> notasByAlunoResponse;
  private List<Aluno> alunosByDisciplina;
  private List<NotasByDisciplina> notasByDisciplina;

  public Response() {
    notasByAlunoResponse = new ArrayList<NotasByAluno>();
    alunosByDisciplina = new ArrayList<Aluno>();
    notasByDisciplina = new ArrayList<NotasByDisciplina>();
  }

  public Response(int response_status_code) {
    this.response_status_code = response_status_code;

    notasByAlunoResponse = new ArrayList<NotasByAluno>();
    alunosByDisciplina = new ArrayList<Aluno>();
    notasByDisciplina = new ArrayList<NotasByDisciplina>();
  }

  public void set_response_status_code(int response_status_code) {
    this.response_status_code = response_status_code;
  }

  public int get_response_status_code() {
    return this.response_status_code;
  }

  
  public void set_error(String error) {
    this.error = error;
  }

  public String get_error() {
    return this.error;
  }  

  public void set_notas_by_aluno(NotasByAluno notasByAlunoResponse) {
    this.notasByAlunoResponse.add(notasByAlunoResponse);
  }

  public void set_alunos_by_disciplina(Aluno alunosByDisciplina) {
    this.alunosByDisciplina.add(alunosByDisciplina);
  }

  public void set_notas_by_disciplina(NotasByDisciplina notasByDisciplina) {
    this.notasByDisciplina.add(notasByDisciplina);
  }

  public List<NotasByAluno> get_notas_by_aluno() {
    return this.notasByAlunoResponse;
  }

  public List<Aluno> get_alunos_by_disciplina() {
    return this.alunosByDisciplina;
  }

  public List<NotasByDisciplina> get_notas_by_disciplina() {
    return this.notasByDisciplina;
  }
}
