package co.edu.umanizales.connect_travel.model.enums;

public enum PaymentStatus {
    PENDING,    // Pago pendiente de procesar
    COMPLETED,  // Pago completado exitosamente
    FAILED,     // Pago fallido
    REFUNDED,   // Pago reembolsado
    CANCELLED,  // Pago cancelado
    EXPIRED,    // Pago expirado
    IN_REVIEW   // Pago en revisión
}
