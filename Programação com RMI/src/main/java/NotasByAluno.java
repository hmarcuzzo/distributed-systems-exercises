import java.io.Serializable;

public class NotasByAluno implements Serializable {
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