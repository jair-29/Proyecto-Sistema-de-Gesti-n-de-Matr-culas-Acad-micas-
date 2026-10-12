package objetos;

public class Curso {
    private int codCurso;
    private String asignatura;
    private int ciclo;
    private int creditos;
    private int horas;

    //CONSTRUCTOR
    public Curso(int codCurso, String asignatura, int ciclo, int creditos, int horas) {
        this.codCurso = codCurso;
        this.asignatura = asignatura;
        this.ciclo = ciclo;
        this.creditos = creditos;
        this.horas = horas;
    }

    // Getters y Setters
    public int getCodCurso() {
        return codCurso;
    }
    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }
    public String getAsignatura() {
        return asignatura;
    }
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }
    public int getCiclo() {
        return ciclo;
    }
    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }
    public int getCreditos() {
        return creditos;
    }
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
    public int getHoras() {
        return horas;
    }
    public void setHoras(int horas) {
        this.horas = horas;
    }
    
    // Método adicional para mostrar el CICLO en texto
    public String getCicloTexto() {
    	switch (ciclo) {
    		case 0:
    			return "PRIMERO";
    		case 1:
    			return "SEGUNDO";
    		case 2:
    			return "TERCERO";
    		case 3:
    			return "CUARTO";
    		case 4:
    			return "QUINTO";
    		case 5:
    			return "SEXTO";
    		default:
    			return "Desconocido";
        }
    }
}