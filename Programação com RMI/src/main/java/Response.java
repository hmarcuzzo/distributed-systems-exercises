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

public class Response implements Serializable {
  private int response_status_code;
  private String error;
  private String response_data;
  private NotasByAluno[] notasByAlunoResponse;
  private Aluno[] alunosByDisciplina;
  private NotasByDisciplina[] notasByDisciplina;

  public Response(){
    System.out.println("constructor with no parameter");
  }

  public Response(int response_status_code) {
    this.response_status_code = response_status_code;
    System.out.println("constructor with one parameter");
  }
  
  public int get_status() {
    return this.response_status_code;
  }

  public NotasByAluno[] get_notas_by_aluno(){
    return this.notasByAlunoResponse;
  }

  public Aluno[] get_alunos_by_disciplina(){
    return this.alunosByDisciplina;
  }

  public NotasByDisciplina[] get_notas_by_disciplina(){
    return this.notasByDisciplina
  }

  public String get_error() {
    return this.error;
  }

  public void set_error(String error) {
    this.error = error;
  }
    
  public Response get(){
    return this;
  }
}


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