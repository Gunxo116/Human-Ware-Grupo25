package human.ware;

/**
 * Tipo de jornada laboral.
 * Compartido entre Oferta (División 1) y Postulante (División 2).
 */
public enum TipoJornada {
    COMPLETA,
    PARCIAL,
    AMBAS    // solo aplica a Postulante: puede trabajar en cualquiera de las dos
}
