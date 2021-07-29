/**
 * Este código é responsavel pela parte de criar uma classe genérica para requisições 
 * 
 * Protocolo: 
 *  TODO: Declarar o protocolo
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@sorattorafa)
 * 
 * Data de Criação: 25 de Jul de 2021 
 * Ultima alteração: 25 de Jul de 2021
 */
public class Request {
    private String request_code;
    private Integer RA;
    private float nota;
    private String cod_disciplina;
    private String nome;
    private Integer ano;
    private Integer semestre;
    private Integer faltas;


    public Integer get_faltas() {
        return faltas;
    }

    public void set_faltas(Integer faltas) {
        this.faltas = faltas;
    }
    public String get_request_code() {
        return request_code;
    }

    public void set_request_code(String request_code) {
        this.request_code = request_code;
    }

    public Integer get_RA() {
        return RA;
    }

    public void set_RA(Integer RA) {
        this.RA = RA;
    }

    public String get_nome() {
        return nome;
    }

    public void set_nome(String nome) {
        this.nome = nome;
    }

    public Integer get_semestre() {
        return semestre;
    }

    public void set_semestre(Integer semestre) {
        this.semestre = semestre;
    }

    public float get_nota() {
        return nota;
    }

    public void set_nota(float nota) {
        this.nota = nota;
    }

    public Integer get_ano() {
        return ano;
    }

    public void set_ano(Integer ano) {
        this.ano = ano;
    }

    public String get_cod_disciplina() {
        return cod_disciplina;
    }

    public void set_cod_disciplina(String cod_disciplina) {
        this.cod_disciplina = cod_disciplina;
    }

}