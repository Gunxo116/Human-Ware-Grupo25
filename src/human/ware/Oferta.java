package human.ware;

import java.util.List;

/**
 * Oferta de empleo publicada por una empresa.
 * IMPLEMENTACIÓN COMPLETA A CARGO DE DIVISIÓN 1.
 * Stub mínimo para que División 2 compile independientemente.
 */
public class Oferta {
    private String codigo;
    private String titulo;
    private Degree titulacionRequerida;
    private List<OfertaSkill> skillsRequeridas;
    private EstadoOferta estado;

    public Oferta(String codigo, String titulo, Degree titulacionRequerida,
                  List<OfertaSkill> skillsRequeridas) {
        this.codigo               = codigo;
        this.titulo               = titulo;
        this.titulacionRequerida  = titulacionRequerida;
        this.skillsRequeridas     = skillsRequeridas != null ? skillsRequeridas : List.of();
        this.estado               = EstadoOferta.PUBLICADA;
    }

    public String             getCodigo()               { return codigo; }
    public String             getTitulo()               { return titulo; }
    public Degree             getTitulacionRequerida()  { return titulacionRequerida; }
    public List<OfertaSkill>  getSkillsRequeridas()     { return List.copyOf(skillsRequeridas); }
    public EstadoOferta       getEstado()               { return estado; }
    public boolean            estaActiva()              { return estado == EstadoOferta.PUBLICADA; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Oferta other)) return false;
        return codigo.equals(other.codigo);
    }

    @Override
    public int hashCode() { return codigo.hashCode(); }

    @Override
    public String toString() { return "Oferta{" + codigo + ", " + titulo + "}"; }
}
