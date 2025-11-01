# Diagrama de Clases - Connect Travel

## Enumeraciones (Enums)

### DocumentType
```
+-------------+
| DocumentType|
+-------------+
| CC          |
| CE          |
| TI          |
| PP          |
| NIT         |
| RC          |
| PEP         |
+-------------+
```

### LicenseType
```
+-------------+
| LicenseType |
+-------------+
| A1          |
| A2          |
| B1          |
| B2          |
| B3          |
| C1          |
| C2          |
| C3          |
+-------------+
```

### VehicleType
```
+---------------+
|  VehicleType  |
+---------------+
| BUS           |
| VAN           |
| CAR           |
| MINIBUS       |
| MOTORCYCLE    |
| BICYCLE       |
| TAXI          |
| TRUCK         |
+---------------+
```

### TripStatus
```
+---------------+
|  TripStatus   |
+---------------+
| SCHEDULED     |
| IN_PROGRESS   |
| COMPLETED     |
| CANCELLED     |
| DELAYED       |
| BOARDING      |
| ARRIVED       |
+---------------+
```

## Clases Principales

### User
```
+------------------+
|      User        |
+------------------+
| - id: Long       |
| - name: String   |
| - email: String  |
| - phone: String  |
| - password: String|
| - role: String   |
+------------------+
| + getBookings()  |
+------------------+
```

### Driver
```
+----------------------------+
|          Driver            |
+----------------------------+
| - id: Long                 |
| - firstName: String        |
| - lastName: String         |
| - documentNumber: String   |
| - documentType: DocumentType|
| - dateOfBirth: LocalDate   |
| - phone: String            |
| - email: String            |
| - licenseNumber: String    |
| - licenseType: LicenseType |
| - licenseExpiry: LocalDate |
| - status: DriverStatus     |
+----------------------------+
```

### Vehicle
```
+--------------------------+
|        Vehicle          |
+--------------------------+
| - id: Long              |
| - licensePlate: String  |
| - make: String          |
| - model: String         |
| - year: int             |
| - capacity: int         |
| - vehicleType: VehicleType|
| - status: VehicleStatus |
| - color: String         |
+--------------------------+
```

### Trip
```
+--------------------------------+
|             Trip               |
+--------------------------------+
| - id: Long                     |
| - origin: Destination          |
| - destination: Destination     |
| - departureTime: LocalDateTime |
| - arrivalTime: LocalDateTime   |
| - capacity: int                |
| - price: double                |
| - status: TripStatus           |
+--------------------------------+
| + getBookings()                |
+--------------------------------+
```

### Booking
```
+--------------------------------+
|           Booking              |
+--------------------------------+
| - id: Long                     |
| - user: User                   |
| - trip: Trip                   |
| - bookingDate: LocalDateTime   |
| - passengerCount: int          |
| - totalPrice: double           |
| - status: BookingStatus        |
| - specialRequests: String      |
+--------------------------------+
```

## Relaciones

```
+------------+       +------------+       +------------+
|            |       |            |       |            |
|   User     |1    * |  Booking   |*    1 |    Trip    |
|            |-------|            |-------|            |
+------------+       +------------+       +------------+
     |                     |                    |
     |                     |                    |
     |                     |                    |
     |               +-----+-----+              |
     |               |           |              |
     |               |  Payment  |              |
     |               |           |              |
     |               +-----------+              |
     |                                           |
     |                                           |
+----+----+                                +-----+-----+
|          |                                |          |
|  Driver  |1                            *  |  Review  |
|          |                                |          |
+----+-----+                                +-----+----+
     |                                           |
     |                                           |
     |                                           |
+----+-----+                                +-----+----+
|          |                                |          |
| Vehicle  |*                            *  |  Route   |
|          |                                |          |
+----------+                                +-----+----+
                                                 |
                                                 |
                                           +-----+----+
                                           |          |
                                           |   Stop   |
                                           |          |
                                           +-----+----+
                                                 |
                                                 |
                                           +-----+----+
                                           |          |
                                           |  Destination
                                           |          |
                                           +----------+
```

## Leyenda de Relaciones
- `1` - Uno
- `*` - Muchos
- `1..*` - Uno a muchos
- `0..1` - Cero o uno

## Notas
- Las relaciones muestran cómo interactúan las entidades entre sí
- Los números en los extremos de las líneas indican la cardinalidad
- Los signos `+` indican visibilidad pública
- Los signos `-` indican visibilidad privada
```

# Diagrama de Clases - Connect Travel

Este diagrama muestra las principales entidades del sistema y sus relaciones. Los enums definen los diferentes estados y tipos que pueden tener las entidades principales.

## Leyenda
- **Enums**: Enumeraciones que definen tipos y estados
- **Clases Principales**: Entidades principales del sistema
- **Relaciones**: Conexiones entre las entidades (1 a muchos, muchos a 1, etc.)

## Notas
- Los enums se muestran en la parte superior del diagrama
- Las relaciones indican la cardinalidad entre las entidades
- Los atributos más importantes se incluyen en cada clase

Para visualizar este diagrama, puedes usar cualquier herramienta que soporte Mermaid.js como:
- [Mermaid Live Editor](https://mermaid.live/)
- Plugins de VS Code para Mermaid
- GitHub Markdown (si el repositorio soporta Mermaid)
