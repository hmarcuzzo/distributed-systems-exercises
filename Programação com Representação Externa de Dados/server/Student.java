/**
 * Este código é responsavel por fomalizar o modelo do aluno
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@soratoorafa)
 * 
 * Data de Criação: 25 de Jul de 2021
 * Ultima alteração: 25 de Jul de 2021
 */

public class Student {
    private Integer RA;
    private String name;
    private Integer period;
    private float grade;
    private Integer absences;
  
    public String get_nome() {
      return name;
    }
  
    public void set_nome(String name) {
      this.name = name;
    }

    public float get_nota() {
      return grade;
    }
  
    public void set_nota(float grade) {
      this.grade = grade;
    }

    public void set_RA(Integer registro_academico) {
      this.RA = registro_academico;
    }

    public Integer get_RA() {
      return RA;
    }
  
    public Integer get_absences() {
      return absences;
    }
  
    public void set_absences(Integer absences) {
      this.absences = absences;
    }

  
    public Integer get_period() {
      return period;
    }
  
    public void set_period(Integer period) {
      this.period = period;
    }
  
  }