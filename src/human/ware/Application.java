package human.ware;

import java.time.LocalDate;

// Solicitud de empleo. Vincula un Postulante con una Oferta.
public class Application {

    private static int contadorId = 1;
    private final int id;

    private final Oferta oferta;
    private final Postulante postulante;
    private final LocalDate fechaCreacion;
    private LocalDate fechaContrato;
    private EstadoApplication estado;

    public Application(Oferta oferta, Postulante postulante) {
        this.id = contadorId++;
        this.oferta = oferta;
        this.postulante = postulante;
        this.fechaCreacion = LocalDate.now();
        this.fechaContrato = null;
        this.estado = EstadoApplication.ACTIVA;
    }

    public void activar() {
        this.estado = EstadoApplication.ACTIVA;
    }

    public void inactivar() {
        this.estado = EstadoApplication.INACTIVA;
    }

    // Se llama cuando la empresa cubre el puesto con este postulante
    public void asignarFechaContrato(LocalDate fechaContrato) {
        this.fechaContrato = fechaContrato;
        this.estado = EstadoApplication.CONTRATADA;
    }

    public boolean isActiva() {
        return estado == EstadoApplication.ACTIVA;
    }

    public boolean isContratada() {
        return estado == EstadoApplication.CONTRATADA;
    }

    public int getId()                     { return id; }
    public Oferta getOferta()              { return oferta; }
    public Postulante getPostulante()      { return postulante; }
    public LocalDate getFechaCreacion()    { return fechaCreacion; }
    public LocalDate getFechaContrato()    { return fechaContrato; }
    public EstadoApplication getEstado()   { return estado; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Application other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() { return Integer.hashCode(id); }

    @Override
    public String toString() {
        return "Application{id=" + id +
               ", postulante=" + postulante.getNombre() +
               ", oferta='" + oferta.getTitulo() + "'" +
               ", estado=" + estado +
               ", fechaCreacion=" + fechaCreacion +
               ", fechaContrato=" + (fechaContrato != null ? fechaContrato : "pendiente") + "}";
    }
}