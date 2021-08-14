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

  public void set_alunos_by_disciplina(Aluno[] alunosByDisciplina) {
    this.alunosByDisciplina.add(alunosByDisciplina);
  }

  public void set_notas_by_disciplina(NotasByDisciplina[] notasByDisciplina) {
    this.notasByDisciplina.add(notasByDisciplina);
  }

  public NotasByAluno[] get_notas_by_aluno() {
    return this.notasByAlunoResponse;
  }

  public Aluno[] get_alunos_by_disciplina() {
    return this.alunosByDisciplina;
  }

  public NotasByDisciplina[] get_notas_by_disciplina() {
    return this.notasByDisciplina;
  }
}


public class NotasByAluno {
  private String cod_disciplina;
  private int ano;
  private int semestre;
  private float nota;
  private int faltas;

  public NotasByAluno(){
  
  }

  public void set_cod_disciplina(String cod_disciplina) {
    this.cod_disciplina = cod_disciplina;
  }

  public void set_ano(int ano) {
    this.ano = ano;
  }

  public void set_semestre(int semestre) {
    this.semestre = semestre;
  }

  public void set_nota(float nota) {
    this.nota = nota;
  }

  public void set_faltas(int faltas) {
    this.faltas = faltas;
  }

  public String get_cod_disciplina() {
    return this.cod_disciplina;
  }

  public int get_ano() {
    return this.ano;
  }

  public int get_semestre() {
    return this.semestre;
  }

  public float get_nota() {
    return this.nota;
  }

  public int get_faltas() {
    return this.faltas;
  }

}


public class Aluno {
  private int RA;
  private String nome;

  public Aluno(){

  }

  public void set_RA(int RA) {
    this.RA = RA;
  }

  public void set_nome(String nome) {
    this.nome = nome;
  }

  public int get_RA() {
    return this.RA;
  }

  public String get_nome() {
    return this.nome;
  }
}


public class NotasByDisciplina {
  private int RA;
  private float nota;
  private int faltas;

  public NotasByDisciplina(){

  }

  public void set_RA(int RA) {
    this.RA = RA;
  }

  public void set_nota(float nota) {
    this.nota = nota;
  }

  public void set_faltas(int faltas) {
    this.faltas = faltas;
  }

  public int get_RA() {
    return this.RA;
  }

  public float get_nota() {
    return this.nota;
  }

  public int get_faltas() {
    return this.faltas;
  }
}