package human.ware;

// Clase de asociacion Postulante - Skill. Guarda el nivel del candidato en esa habilidad.
public class PostulanteSkill {

    private Skill skill;
    private int nivel; // 1 (muy bajo) a 5 (muy alto)

    public PostulanteSkill(Skill skill, int nivel) {
        this.skill = skill;
        this.nivel = nivel;
    }

    // Devuelve true si el nivel del postulante alcanza el minimo requerido
    public boolean cumpleMinimo(int nivelMinimo) {
        return this.nivel >= nivelMinimo;
    }

    public Skill getSkill() { return skill; }
    public int getNivel()   { return nivel; }

    public void setNivel(int nivel) {
        if (nivel < 1 || nivel > 5)
            throw new IllegalArgumentException("El nivel debe ser entre 1 y 5.");
        this.nivel = nivel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PostulanteSkill other)) return false;
        return skill.equals(other.skill);
    }

    @Override
    public int hashCode() { return skill.hashCode(); }

    @Override
    public String toString() {
        return "PostulanteSkill{skill= " + skill.getNombre() + ", nivel= " + nivel + "}";
    }
}