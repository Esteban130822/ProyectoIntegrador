
package app.model;

public class Plan {
    private int id;
    private String titulo;
    private String nivel;
    private String grado;
    private String contenidos;
    private String competencias;
    private String estado;
    private int creadoPor;

    public Plan(int id, String titulo, String nivel, String grado, String contenidos, String competencias, String estado, int creadoPor) {
        this.id = id;
        this.titulo = titulo;
        this.nivel = nivel;
        this.grado = grado;
        this.contenidos = contenidos;
        this.competencias = competencias;
        this.estado = estado;
        this.creadoPor = creadoPor;
    }

    public Plan(String titulo, String nivel, String grado, String contenidos, String competencias, String estado, int creadoPor) {
        this(-1, titulo, nivel, grado, contenidos, competencias, estado, creadoPor);
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getNivel() { return nivel; }
    public String getGrado() { return grado; }
    public String getContenidos() { return contenidos; }
    public String getCompetencias() { return competencias; }
    public String getEstado() { return estado; }
    public int getCreadoPor() { return creadoPor; }

    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public void setGrado(String grado) { this.grado = grado; }
    public void setContenidos(String contenidos) { this.contenidos = contenidos; }
    public void setCompetencias(String competencias) { this.competencias = competencias; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCreadoPor(int creadoPor) { this.creadoPor = creadoPor; }
}
