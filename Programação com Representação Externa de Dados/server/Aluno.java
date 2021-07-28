/**
 * Este código é responsavel por fomalizar o modelo do aluno
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@soratoorafa)
 * 
 * Data de Criação: 25 de Jul de 2021
 * Ultima alteração: 25 de Jul de 2021
 */

public class Aluno {
    private Integer RA;
    private String nome;
    private Integer periodo;
    private float nota;
    private Integer faltas;
  
    public String get_nome() {
      return nome;
    }
  
    public void set_nome(String nome) {
      this.nome = nome;
    }

    public float get_nota() {
      return nota;
    }
  
    public void set_nota(float nota) {
      this.nota = nota;
    }

    public void set_RA(Integer registro_academico) {
      this.RA = registro_academico;
    }

    public Integer get_RA() {
      return RA;
    }
  
    public Integer get_faltas() {
      return faltas;
    }
  
    public void set_faltas(Integer faltas) {
      this.faltas = faltas;
    }

  
    public Integer get_periodo() {
      return periodo;
    }
  
    public void set_periodo(Integer periodo) {
      this.periodo = periodo;
    }
  
}