package human.ware;

import java.util.ArrayList;
import java.util.List;

public class Postulante {

    private static final int MAX_SOLICITUDES_ACTIVAS = 3;
    private static int contadorNro = 1;

    private final int nroPostulante;
    private double retribucionMinima;
    private TipoJornada tipoJornada;
    private boolean disponibilidadViaje;
    private String vehiculo;
    private Degree titulacion;
    private List<PostulanteSkill> skills;

    // Composicion 1-1
    private PerfilDatos perfilDatos;

    // Asociacion con la cuenta de acceso
    private Usuario usuario;

    // Historial de solicitudes
    private List<Application> solicitudes;

    public Postulante(double retribucionMinima, TipoJornada tipoJornada,
                      boolean disponibilidadViaje, String vehiculo,
                      Degree titulacion, PerfilDatos perfilDatos, Usuario usuario) {
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

    // ── Skills ────────────────────────────────────────────────────────────

    public void agregarSkill(PostulanteSkill ps) {
        if (skills.contains(ps))
            throw new IllegalStateException("Ya existe esa skill en el perfil.");
        skills.add(ps);
    }

    public void actualizarNivelSkill(Skill skill, int nuevoNivel) {
        for (PostulanteSkill ps : skills) {
            if (ps.getSkill().equals(skill)) {
                ps.setNivel(nuevoNivel);
                return;
            }
        }
        throw new IllegalArgumentException("Skill no encontrada.");
    }

    public void eliminarSkill(Skill skill) {
        boolean eliminado = skills.removeIf(ps -> ps.getSkill().equals(skill));
        if (!eliminado)
            throw new IllegalArgumentException("Skill no encontrada.");
        if (skills.isEmpty())
            throw new IllegalStateException("El postulante debe tener al menos una skill.");
    }

    // ── Solicitudes ───────────────────────────────────────────────────────

    public Application aplicarOferta(Oferta oferta) {
        if (getSolicitudesActivas().size() >= MAX_SOLICITUDES_ACTIVAS)
            throw new IllegalStateException("No puede tener mas de 3 solicitudes activas.");

        // Verifica que no este ya postulado activo a la misma oferta
        for (Application a : solicitudes) {
            if (a.getOferta().equals(oferta) && a.isActiva())
                throw new IllegalStateException("Ya tiene una solicitud activa para esta oferta.");
        }

        Application app = new Application(oferta, this);
        solicitudes.add(app);
        return app;
    }

    public void cancelarSolicitud(Application app) {
        if (!solicitudes.contains(app))
            throw new IllegalArgumentException("Solicitud no encontrada.");
        app.inactivar();
    }

    // ── Idoneidad ─────────────────────────────────────────────────────────

    // Verifica titulo requerido y nivel minimo en cada skill de la oferta
    public boolean cumpleRequisitos(Oferta oferta) {
        // 1. Debe tener la titulacion requerida
        if (!this.titulacion.equals(oferta.getTitulacionRequerida()))
            return false;

        // 2. Para cada skill requerida, debe tenerla con nivel suficiente
        for (OfertaSkill os : oferta.getSkillsRequeridas()) {
            boolean cumple = false;
            for (PostulanteSkill ps : skills) {
                if (ps.getSkill().equals(os.getSkill()) && ps.cumpleMinimo(os.getNivelMinimo())) {
                    cumple = true;
                    break;
                }
            }
            if (!cumple) return false;
        }
        return true;
    }

    // Suma los niveles del postulante solo en las skills requeridas por la oferta
    public int calcularPuntaje(Oferta oferta) {
        if (!cumpleRequisitos(oferta)) return 0;

        int puntaje = 0;
        for (OfertaSkill os : oferta.getSkillsRequeridas()) {
            for (PostulanteSkill ps : skills) {
                if (ps.getSkill().equals(os.getSkill())) {
                    puntaje += ps.getNivel();
                    break;
                }
            }
        }
        return puntaje;
    }

    // ── Getters ───────────────────────────────────────────────────────────

    public int getNroPostulante()             { return nroPostulante; }
    public double getRetribucionMinima()      { return retribucionMinima; }
    public TipoJornada getTipoJornada()       { return tipoJornada; }
    public boolean isDisponibilidadViaje()    { return disponibilidadViaje; }
    public String getVehiculo()               { return vehiculo; }
    public Degree getTitulacion()             { return titulacion; }
    public PerfilDatos getPerfilDatos()       { return perfilDatos; }
    public Usuario getUsuario()               { return usuario; }
    public String getNombre()                 { return perfilDatos.getNombre(); }
    public String getEmail()                  { return perfilDatos.getEmail(); }

    public List<PostulanteSkill> getSkills()  { return List.copyOf(skills); }
    public List<Application> getSolicitudes() { return List.copyOf(solicitudes); }

    public List<Application> getSolicitudesActivas() {
        List<Application> activas = new ArrayList<>();
        for (Application a : solicitudes) {
            if (a.isActiva()) activas.add(a);
        }
        return activas;
    }

    public List<Application> getHistorialSolicitudes() {
        return List.copyOf(solicitudes);
    }

    // ── Setters ───────────────────────────────────────────────────────────

    public void setRetribucionMinima(double r)    { this.retribucionMinima = r; }
    public void setTipoJornada(TipoJornada tj)    { this.tipoJornada = tj; }
    public void setDisponibilidadViaje(boolean d) { this.disponibilidadViaje = d; }
    public void setVehiculo(String v)             { this.vehiculo = v; }
    public void setTitulacion(Degree d)           { this.titulacion = d; }

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