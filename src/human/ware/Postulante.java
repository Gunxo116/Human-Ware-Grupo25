package human.ware;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un candidato registrado en el portal HUMAN-Ware.
 *
 * Relaciones:
 *   - Composición 1-1 con PerfilDatos  (datos personales)
 *   - Composición 1-1..* con PostulanteSkill  (habilidades propias)
 *   - Asociación 1-1 con Degree  (un único título)
 *   - Asociación 1-1 con Usuario  (cuenta de acceso)
 *   - Asociación 1-1..3 con Application (solicitudes activas)
 */
public class Postulante {

    // ── Constantes ─────────────────────────────────────────────────────────
    private static final int MAX_SOLICITUDES_ACTIVAS = 3;

    // ── Atributo de identidad ──────────────────────────────────────────────
    private static int contadorNro = 1;
    private final int  nroPostulante;       // PK, autoincremental

    // ── Perfil laboral ─────────────────────────────────────────────────────
    private double            retribucionMinima;
    private TipoJornada       tipoJornada;          // COMPLETA | PARCIAL | AMBAS
    private boolean           disponibilidadViaje;
    private String            vehiculo;             // null si no tiene
    private Degree            titulacion;           // 1 título obligatorio
    private List<PostulanteSkill> skills;           // al menos 1

    // ── Perfil personal (composición) ─────────────────────────────────────
    private PerfilDatos perfilDatos;

    // ── Cuenta de acceso (asociación) ─────────────────────────────────────
    private Usuario usuario;

    // ── Historial de solicitudes ───────────────────────────────────────────
    private List<Application> solicitudes;

    // ── Constructor ────────────────────────────────────────────────────────
    public Postulante(double retribucionMinima, TipoJornada tipoJornada,
                      boolean disponibilidadViaje, String vehiculo,
                      Degree titulacion, PerfilDatos perfilDatos, Usuario usuario) {

        if (titulacion == null)
            throw new IllegalArgumentException("El postulante debe tener un título.");
        if (perfilDatos == null)
            throw new IllegalArgumentException("El perfil de datos es obligatorio.");
        if (usuario == null)
            throw new IllegalArgumentException("El postulante debe tener un usuario.");

        this.nroPostulante      = contadorNro++;
        this.retribucionMinima  = retribucionMinima;
        this.tipoJornada        = tipoJornada;
        this.disponibilidadViaje = disponibilidadViaje;
        this.vehiculo           = vehiculo;
        this.titulacion         = titulacion;
        this.perfilDatos        = perfilDatos;
        this.usuario            = usuario;
        this.skills             = new ArrayList<>();
        this.solicitudes        = new ArrayList<>();
    }

    // ── Gestión de Skills ──────────────────────────────────────────────────
    /**
     * Agrega una habilidad al perfil. No puede repetirse la misma Skill.
     */
    public void agregarSkill(PostulanteSkill ps) {
        if (ps == null)
            throw new IllegalArgumentException("PostulanteSkill no puede ser null.");
        if (skills.contains(ps))
            throw new IllegalStateException("Ya existe esa skill en el perfil.");
        skills.add(ps);
    }

    /**
     * Actualiza el nivel de una skill ya registrada.
     */
    public void actualizarNivelSkill(Skill skill, int nuevoNivel) {
        skills.stream()
              .filter(ps -> ps.getSkill().equals(skill))
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException("Skill no encontrada en el perfil."))
              .setNivel(nuevoNivel);
    }

    public void eliminarSkill(Skill skill) {
        boolean eliminado = skills.removeIf(ps -> ps.getSkill().equals(skill));
        if (!eliminado)
            throw new IllegalArgumentException("Skill no encontrada en el perfil.");
        if (skills.isEmpty())
            throw new IllegalStateException("El postulante debe tener al menos una skill.");
    }

    // ── Gestión de Solicitudes (Application) ──────────────────────────────
    /**
     * Aplica a una oferta laboral creando una nueva Application.
     * Regla de negocio: máximo 3 solicitudes activas simultáneas.
     *
     * @param oferta  la oferta a la que se postula
     * @return la Application creada
     */
    public Application aplicarOferta(Oferta oferta) {
        if (oferta == null)
            throw new IllegalArgumentException("La oferta no puede ser null.");
        if (getSolicitudesActivas().size() >= MAX_SOLICITUDES_ACTIVAS)
            throw new IllegalStateException(
                "Límite alcanzado: no puede tener más de 3 solicitudes activas.");
        // Verificar que no esté ya postulado a la misma oferta activa
        boolean yaPostulado = solicitudes.stream()
            .anyMatch(a -> a.getOferta().equals(oferta) && a.isActiva());
        if (yaPostulado)
            throw new IllegalStateException("Ya tiene una solicitud activa para esta oferta.");

        Application app = new Application(oferta, this);
        solicitudes.add(app);
        return app;
    }

    /**
     * Cancela una solicitud activa. La solicitud queda inactiva.
     */
    public void cancelarSolicitud(Application app) {
        if (app == null || !solicitudes.contains(app))
            throw new IllegalArgumentException("Solicitud no encontrada.");
        if (!app.isActiva())
            throw new IllegalStateException("La solicitud ya está inactiva.");
        app.inactivar();
    }

    // ── Métodos de selección / idoneidad ──────────────────────────────────
    /**
     * Verifica si el postulante cumple los requisitos mínimos de una oferta:
     *   1. Tiene la titulación requerida.
     *   2. Tiene todas las skills requeridas con nivel >= al mínimo.
     */
    public boolean cumpleRequisitos(Oferta oferta) {
        if (!this.titulacion.equals(oferta.getTitulacionRequerida()))
            return false;

        for (OfertaSkill os : oferta.getSkillsRequeridas()) {
            boolean cumple = skills.stream()
                .filter(ps -> ps.getSkill().equals(os.getSkill()))
                .anyMatch(ps -> ps.cumpleMinimo(os.getNivelMinimo()));
            if (!cumple) return false;
        }
        return true;
    }

    /**
     * Calcula el puntaje total del postulante para una oferta dada.
     * Solo se suman las skills requeridas por la oferta (las demás no cuentan).
     *
     * @param oferta oferta para la cual se calcula el puntaje
     * @return suma de niveles en las skills requeridas, 0 si no cumple requisitos
     */
    public int calcularPuntaje(Oferta oferta) {
        if (!cumpleRequisitos(oferta)) return 0;

        int puntaje = 0;
        for (OfertaSkill os : oferta.getSkillsRequeridas()) {
            puntaje += skills.stream()
                .filter(ps -> ps.getSkill().equals(os.getSkill()))
                .mapToInt(PostulanteSkill::getNivel)
                .findFirst()
                .orElse(0);
        }
        return puntaje;
    }

    // ── Getters ────────────────────────────────────────────────────────────
    public int    getNroPostulante()        { return nroPostulante; }
    public double getRetribucionMinima()    { return retribucionMinima; }
    public TipoJornada getTipoJornada()     { return tipoJornada; }
    public boolean isDisponibilidadViaje()  { return disponibilidadViaje; }
    public String  getVehiculo()            { return vehiculo; }
    public Degree  getTitulacion()          { return titulacion; }
    public PerfilDatos getPerfilDatos()     { return perfilDatos; }
    public Usuario getUsuario()             { return usuario; }
    public String  getEmail()               { return perfilDatos.getEmail(); }
    public String  getNombre()              { return perfilDatos.getNombre(); }

    public List<PostulanteSkill> getSkills() {
        return List.copyOf(skills);     // inmutable para proteger encapsulamiento
    }

    public List<Application> getSolicitudes() {
        return List.copyOf(solicitudes);
    }

    public List<Application> getSolicitudesActivas() {
        return solicitudes.stream()
                .filter(Application::isActiva)
                .toList();
    }

    public List<Application> getHistorialSolicitudes() {
        return List.copyOf(solicitudes);
    }

    // ── Setters (actualización de perfil) ──────────────────────────────────
    public void setRetribucionMinima(double r)          { this.retribucionMinima = r; }
    public void setTipoJornada(TipoJornada tj)          { this.tipoJornada = tj; }
    public void setDisponibilidadViaje(boolean d)       { this.disponibilidadViaje = d; }
    public void setVehiculo(String v)                   { this.vehiculo = v; }
    public void setTitulacion(Degree d) {
        if (d == null) throw new IllegalArgumentException("Titulación no puede ser null.");
        this.titulacion = d;
    }

    // ── Object overrides ───────────────────────────────────────────────────
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Postulante other)) return false;
        return nroPostulante == other.nroPostulante;
    }

    @Override
    public int hashCode() { return Integer.hashCode(nroPostulante); }

    @Override
    public String toString() {
        return "Postulante{nro=" + nroPostulante +
               ", nombre='" + getNombre() + "'" +
               ", titulacion='" + titulacion.getNombre() + "'" +
               ", skills=" + skills.size() +
               ", solicitudesActivas=" + getSolicitudesActivas().size() + "}";
    }
}
