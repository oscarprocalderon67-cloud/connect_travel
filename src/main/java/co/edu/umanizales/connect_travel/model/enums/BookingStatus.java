package co.edu.umanizales.connect_travel.model.enums;

public enum BookingStatus {
    PENDING,        // Reserva pendiente de confirmación
    CONFIRMED,      // Reserva confirmada
    CANCELLED,      // Reserva cancelada
    COMPLETED,      // Viaje completado
    NO_SHOW,        // Usuario no se presentó
    IN_PROGRESS,    // Viaje en progreso
    REFUNDED        // Reembolsado
}
