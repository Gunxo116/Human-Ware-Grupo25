package human.ware;

/**
 * Clase de asociación entre Postulante y Skill.
 * Registra qué habilidad tiene el postulante y con qué nivel (1 a 5).
 * Materializa la relación: Postulante ◆──1..*──> PostulanteSkill ──1──> Skill
 */
public class PostulanteSkill {

    // ── Atributos ──────────────────────────────────────────────────────────
    private Skill skill;       // referencia a la habilidad (catálogo División 1)
    private int   nivel;       // puntuación 1 (muy bajo) a 5 (muy alto)

    // ── Constructor ────────────────────────────────────────────────────────
    public PostulanteSkill(Skill skill, int nivel) {
        if (skill == null)
            throw new IllegalArgumentException("Skill no puede ser null.");
        validarNivel(nivel);
        this.skill = skill;
        this.nivel = nivel;
    }

    // ── Getters ────────────────────────────────────────────────────────────
    public Skill getSkill() { return skill; }
    public int   getNivel() { return nivel; }

    // ── Setters ────────────────────────────────────────────────────────────
    public void setNivel(int nivel) {
        validarNivel(nivel);
        this.nivel = nivel;
    }

    // ── Métodos de negocio ─────────────────────────────────────────────────
    /**
     * Verifica si el nivel del postulante en esta skill alcanza el mínimo
     * requerido por una oferta.
     *
     * @param nivelMinimo nivel mínimo exigido por la oferta (1-5)
     * @return true si cumple o supera el mínimo
     */
    public boolean cumpleMinimo(int nivelMinimo) {
        return this.nivel >= nivelMinimo;
    }

    // ── Helpers privados ───────────────────────────────────────────────────
    private void validarNivel(int n) {
        if (n < 1 || n > 5)
            throw new IllegalArgumentException("Nivel debe estar entre 1 y 5. Recibido: " + n);
    }

    // ── Object overrides ───────────────────────────────────────────────────
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PostulanteSkill other)) return false;
        return skill.equals(other.skill);   // misma skill = mismo registro
    }

    @Override
    public int hashCode() { return skill.hashCode(); }

    @Override
    public String toString() {
        return "PostulanteSkill{skill=" + skill.getNombre() + ", nivel=" + nivel + "}";
    }
}
