package co.edu.umanizales.connect_travel.model.enums;

public enum ReviewStatus {
    PENDING,    // Revisión pendiente de moderación
    APPROVED,   // Revisión aprobada y visible
    REJECTED,   // Revisión rechazada (no cumple con las políticas)
    EDITED,     // Revisión editada y pendiente de revisión
    FLAGGED     // Revisión marcada para revisión por contenido inapropiado
}
