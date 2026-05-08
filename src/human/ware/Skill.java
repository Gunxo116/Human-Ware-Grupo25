package human.ware;

/**
 * Catálogo de habilidades del sistema.
 * IMPLEMENTACIÓN COMPLETA A CARGO DE DIVISIÓN 1.
 * Este archivo es un stub para que División 2 compile de forma independiente.
 */
public class Skill {
    private int    codigo;
    private String nombre;
    private String descripcion;

    public Skill(int codigo, String nombre, String descripcion) {
        this.codigo      = codigo;
        this.nombre      = nombre;
        this.descripcion = descripcion;
    }

    public int    getCodigo()      { return codigo; }
    public String getNombre()      { return nombre; }
    public String getDescripcion() { return descripcion; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Skill other)) return false;
        return codigo == other.codigo;
    }

    @Override
    public int hashCode() { return Integer.hashCode(codigo); }

    @Override
    public String toString() { return "Skill{" + codigo + ", " + nombre + "}"; }
}
