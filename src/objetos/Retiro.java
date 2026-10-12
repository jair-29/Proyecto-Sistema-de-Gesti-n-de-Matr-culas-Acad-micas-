package objetos;

public class Retiro {

    private int numRetiro; // Código correlativo
    private int numMatricula; 
    private String fecha;     
    private String hora; 

    // Constructor
    public Retiro(int numRetiro, int numMatricula, String fecha, String hora) {
        this.numRetiro = numRetiro;
        this.numMatricula = numMatricula;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters y Setters
    public int getNumRetiro() {
        return numRetiro;
    }
    public void setNumRetiro(int numRetiro) {
        this.numRetiro = numRetiro;
    }
    public int getNumMatricula() {
        return numMatricula;
    }
    public void setNumMatricula(int numMatricula) {
        this.numMatricula = numMatricula;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
}
