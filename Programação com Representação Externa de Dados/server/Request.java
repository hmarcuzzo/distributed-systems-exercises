/**
 * Este código é responsavel pela parte de criar uma classe genérica para
 * requisições Protocolo: TODO: Declarar o protocolo
 *
 * @author Henrique Marcuzzo (@hmarcuzzo)
 * @author Rafael Soratto (@soratoorafa)
 * 
 *         Data de Criação: 25 de Jul de 2021 Ultima alteração: 25 de Jul de
 *         2021
 */
public class Request {
    private String request_code;
    private Integer RA;
    private Integer grade;
    private String discipline_code;
    private String name;
    private Integer year;
    private Integer semester;

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

    public String get_name() {
        return name;
    }

    public void set_name(String name) {
        this.name = name;
    }

    public Integer get_semester() {
        return semester;
    }

    public void set_semester(Integer semester) {
        this.semester = semester;
    }

    public Integer get_grade() {
        return grade;
    }

    public void set_grade(Integer grade) {
        this.grade = grade;
    }

    public Integer get_year() {
        return year;
    }

    public void set_year(Integer year) {
        this.year = year;
    }

    public String get_discipline_code() {
        return discipline_code;
    }

    public void set_discipline_code(String discipline_code) {
        this.discipline_code = discipline_code;
    }

}