package human.ware;

/**
 * Clase de asociación Oferta–Skill con nivel mínimo requerido.
 * IMPLEMENTACIÓN COMPLETA A CARGO DE DIVISIÓN 1.
 * Stub para División 2.
 */
public class OfertaSkill {
    private Skill skill;
    private int   nivelMinimo;

    public OfertaSkill(Skill skill, int nivelMinimo) {
        this.skill        = skill;
        this.nivelMinimo  = nivelMinimo;
    }

    public Skill getSkill()       { return skill; }
    public int   getNivelMinimo() { return nivelMinimo; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OfertaSkill other)) return false;
        return skill.equals(other.skill);
    }

    @Override
    public int hashCode() { return skill.hashCode(); }
}
