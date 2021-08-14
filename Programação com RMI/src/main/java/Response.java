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
<<<<<<< HEAD
  private String response_data;
  private NotasByAluno[] notasByAlunoResponse;
  private Aluno[] alunosByDisciplina;
  private NotasByDisciplina[] notasByDisciplina;
=======
  private List<NotasByAluno> notasByAlunoResponse;
  private List<Aluno> alunosByDisciplina;
  private List<NotasByDisciplina> notasByDisciplina;
>>>>>>> e3f9962468646836f0f7e686e9acac2c0b52ace2

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

<<<<<<< HEAD
  public NotasByAluno[] get_notas_by_aluno(){
    return this.notasByAlunoResponse;
  }

  public Aluno[] get_alunos_by_disciplina(){
    return this.alunosByDisciplina;
  }

  public NotasByDisciplina[] get_notas_by_disciplina(){
    return this.notasByDisciplina
=======
  
  public void set_error(String error) {
    this.error = error;
>>>>>>> e3f9962468646836f0f7e686e9acac2c0b52ace2
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
<<<<<<< HEAD


public class NotasByAluno {
    private String cod_disciplina;
    private int ano;
    private int semestre;
    private float nota;
    private int faltas;

  public NotasByAluno(){
    System.out.println("constructor with no parameter");
  }

}

public class Aluno {
    private int RA;
    private String nome;
    public Aluno(){
      System.out.println("constructor with no parameter");
    }
}



public class NotasByDisciplina {
    private int RA;
    private float nota;

    public NotasByDisciplina(){
      System.out.println("constructor with no parameter");
    }
}
=======
>>>>>>> e3f9962468646836f0f7e686e9acac2c0b52ace2
