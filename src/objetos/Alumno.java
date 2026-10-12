package objetos;

public class Alumno {
    private int codAlumno; // C�digo del alumno
    private String nombres;
    private String apellidos;
    private String dni;
    private int edad;
    private int celular;
    private int estado;  // 0=registrado, 1=matriculado, 2=retirado

    //CONSTRUCTOR
    public Alumno(int codAlumno, String nombres, String apellidos, String dni, int edad, int celular, int estado) {
        this.codAlumno = codAlumno;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.edad = edad;
        this.celular = celular;	
        this.estado = estado;
    }

    // Getters y Setters
    public int getCodAlumno() {
        return codAlumno;
    }
    public void setCodAlumno(int codAlumno) {
        this.codAlumno = codAlumno;
    }
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public int getCelular() {
        return celular;
    }
    public void setCelular(int celular) {
        this.celular = celular;
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }

    // M�todo adicional para mostrar el estado como texto
    public String getEstadoTexto() {
        switch (estado) {
        	case 0:
        		return "REGISTRADO";
        	case 1:
        		return "MATRICULADO";
        	case 2:
        		return "RETIRADO";
        	default:
        		return "DESCONOCIDO";
        }
    }
}