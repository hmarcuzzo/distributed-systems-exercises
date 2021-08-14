import java.io.Serializable;

public class NotasByDisciplina implements Serializable {
    private int RA;
    private float nota;
    private int faltas;
  
    public NotasByDisciplina() {
  
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