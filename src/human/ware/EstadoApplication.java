package human.ware;

/**
 * Estados posibles de una solicitud de empleo (Application).
 */
public enum EstadoApplication {
    ACTIVA,       // el postulante está en proceso de evaluación
    INACTIVA,     // cancelada por el postulante o por cierre de oferta
    CONTRATADA    // el puesto fue cubierto con este postulante
}
