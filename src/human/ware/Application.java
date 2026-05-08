package human.ware;

import java.time.LocalDate;

/**
 * Solicitud de empleo (Application).
 * Vincula un Postulante con una Oferta.
 *
 * Reglas de negocio:
 *  - Un postulante puede tener entre 1 y 3 solicitudes activas.
 *  - Si la oferta se cancela o cubre, todas sus solicitudes pasan a INACTIVA.
 *  - Si la oferta se cubre, la fechaContrato se asigna.
 *  - El postulante puede cancelar voluntariamente (queda INACTIVA sin fechaContrato).
 *
 * Relaciones:
 *   - Asociación → Oferta   (1)
 *   - Asociación → Postulante (1)
 */
public class Application {

    // ── Atributos ──────────────────────────────────────────────────────────
    private static int contadorId = 1;
    private final int  id;                     // PK autoincremental

    private final Oferta      oferta;          // inmutable una vez creada
    private final Postulante  postulante;      // inmutable una vez creada
    private final LocalDate   fechaCreacion;
    private LocalDate         fechaContrato;   // null hasta que se cubre el puesto
    private EstadoApplication estado;

    // ── Constructor ────────────────────────────────────────────────────────
    public Application(Oferta oferta, Postulante postulante) {
        if (oferta == null)
            throw new IllegalArgumentException("La oferta no puede ser null.");
        if (postulante == null)
            throw new IllegalArgumentException("El postulante no puede ser null.");

        this.id             = contadorId++;
        this.oferta         = oferta;
        this.postulante     = postulante;
        this.fechaCreacion  = LocalDate.now();
        this.fechaContrato  = null;
        this.estado         = EstadoApplication.ACTIVA;
    }

    // ── Métodos de estado ──────────────────────────────────────────────────
    /**
     * Activa la solicitud (por si se reactivara en algún flujo futuro).
     */
    public void activar() {
        if (estado == EstadoApplication.ACTIVA)
            throw new IllegalStateException("La solicitud ya está activa.");
        this.estado = EstadoApplication.ACTIVA;
    }

    /**
     * Marca la solicitud como INACTIVA (cancelación del postulante
     * o cierre de la oferta sin cobertura).
     */
    public void inactivar() {
        if (estado == EstadoApplication.INACTIVA)
            throw new IllegalStateException("La solicitud ya está inactiva.");
        this.estado = EstadoApplication.INACTIVA;
    }

    /**
     * Marca la solicitud como CONTRATADA: pasa a inactiva pero con fechaContrato.
     * Se llama cuando la empresa cubre el puesto.
     *
     * @param fechaContrato fecha en que se efectivizó la contratación
     */
    public void asignarFechaContrato(LocalDate fechaContrato) {
        if (fechaContrato == null)
            throw new IllegalArgumentException("La fecha de contrato no puede ser null.");
        if (this.fechaContrato != null)
            throw new IllegalStateException("Ya existe una fecha de contrato asignada.");
        this.fechaContrato = fechaContrato;
        this.estado        = EstadoApplication.CONTRATADA;
    }

    // ── Consultas ──────────────────────────────────────────────────────────
    public boolean isActiva() {
        return estado == EstadoApplication.ACTIVA;
    }

    public boolean isContratada() {
        return estado == EstadoApplication.CONTRATADA;
    }

    // ── Getters ────────────────────────────────────────────────────────────
    public int             getId()            { return id; }
    public Oferta          getOferta()        { return oferta; }
    public Postulante      getPostulante()    { return postulante; }
    public LocalDate       getFechaCreacion() { return fechaCreacion; }
    public LocalDate       getFechaContrato() { return fechaContrato; }
    public EstadoApplication getEstado()      { return estado; }

    // ── Object overrides ───────────────────────────────────────────────────
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
               ", fechaContrato=" + (fechaContrato != null ? fechaContrato : "pendiente") +
               "}";
    }
}
