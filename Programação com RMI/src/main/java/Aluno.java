import java.io.Serializable;

public class Aluno implements Serializable {
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