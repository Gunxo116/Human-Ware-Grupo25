package human.ware;

/**
 * Catálogo de titulaciones disponibles.
 * IMPLEMENTACIÓN COMPLETA A CARGO DE DIVISIÓN 1.
 * Stub para compilación independiente de División 2.
 */
public class Degree {
    private int    codigo;
    private String nombre;
    private String descripcion;

    public Degree(int codigo, String nombre, String descripcion) {
        this.codigo      = codigo;
        this.nombre      = nombre;
        this.descripcion = descripcion;
    }

    public int    getCodigo()      { return codigo; }
    public String getNombre()      { return nombre; }
    public String getDescripcion() { return descripcion; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Degree other)) return false;
        return codigo == other.codigo;
    }

    @Override
    public int hashCode() { return Integer.hashCode(codigo); }

    @Override
    public String toString() { return "Degree{" + codigo + ", " + nombre + "}"; }
}
