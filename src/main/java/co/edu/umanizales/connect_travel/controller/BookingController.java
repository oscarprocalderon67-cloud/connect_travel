package co.edu.umanizales.connect_travel.controller;

import co.edu.umanizales.connect_travel.dto.BookingResponse;
import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Booking;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.umanizales.connect_travel.model.User;
import co.edu.umanizales.connect_travel.model.Trip;
import co.edu.umanizales.connect_travel.service.UserService;
import co.edu.umanizales.connect_travel.service.TripService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    private UserService userService;
    
    @Autowired
    private TripService tripService;
    
    @PostMapping
    public ResponseEntity<BaseResponse<BookingResponse>> create(@Valid @RequestBody Booking booking) {
        try {
            // Verificar que el usuario existe
            if (booking.getUserId() == null) {
                return new ResponseEntity<>(BaseResponse.error("Se requiere el ID del usuario"), HttpStatus.BAD_REQUEST);
            }
            
            // Verificar que el viaje existe
            if (booking.getTripId() == null) {
                return new ResponseEntity<>(BaseResponse.error("Se requiere el ID del viaje"), HttpStatus.BAD_REQUEST);
            }
            
            // Cargar el usuario para verificar que existe
            BaseResponse<User> userResponse = userService.findById(booking.getUserId());
            if (userResponse.getData() == null) {
                return new ResponseEntity<>(BaseResponse.error("Usuario no encontrado"), HttpStatus.NOT_FOUND);
            }
            
            // Cargar el viaje para verificar que existe
            BaseResponse<Trip> tripResponse = tripService.findById(booking.getTripId());
            if (tripResponse.getData() == null) {
                return new ResponseEntity<>(BaseResponse.error("Viaje no encontrado"), HttpStatus.NOT_FOUND);
            }
            
            // Establecer valores por defecto
            booking.setBookingDate(LocalDateTime.now());
            if (booking.getStatus() == null || booking.getStatus().isEmpty()) {
                booking.setStatus("PENDING");
            }
            
            // Crear la reserva
            BaseResponse<Booking> response = bookingService.create(booking);
            if ("success".equals(response.getStatus())) {
                // Obtener la reserva creada con los datos completos
                BaseResponse<Booking> createdBooking = bookingService.findById(response.getData().getId());
                
                // Crear la respuesta con los IDs del usuario y viaje
                BookingResponse bookingResponse = new BookingResponse();
                bookingResponse.setId(createdBooking.getData().getId());
                bookingResponse.setUserId(createdBooking.getData().getUserId());
                bookingResponse.setTripId(createdBooking.getData().getTripId());
                bookingResponse.setBookingDate(createdBooking.getData().getBookingDate());
                bookingResponse.setPassengerCount(createdBooking.getData().getPassengerCount());
                bookingResponse.setTotalPrice(createdBooking.getData().getTotalPrice());
                bookingResponse.setStatus(createdBooking.getData().getStatus());
                
                // Agregar información adicional del usuario si está disponible
                if (userResponse.getData() != null) {
                    BookingResponse.UserInfo userInfo = new BookingResponse.UserInfo();
                    userInfo.setId(userResponse.getData().getId());
                    userInfo.setName(userResponse.getData().getName());
                    userInfo.setEmail(userResponse.getData().getEmail());
                    bookingResponse.setUser(userInfo);
                }
                
                // Agregar información adicional del viaje si está disponible
                if (tripResponse.getData() != null) {
                    BookingResponse.TripInfo tripInfo = new BookingResponse.TripInfo();
                    tripInfo.setId(tripResponse.getData().getId());
                    if (tripResponse.getData().getOrigin() != null) {
                        tripInfo.setOrigin(tripResponse.getData().getOrigin().getName());
                    }
                    if (tripResponse.getData().getDestination() != null) {
                        tripInfo.setDestination(tripResponse.getData().getDestination().getName());
                    }
                    tripInfo.setDepartureTime(tripResponse.getData().getDepartureTime());
                    tripInfo.setPrice(tripResponse.getData().getPrice());
                    bookingResponse.setTrip(tripInfo);
                }
                
                return new ResponseEntity<>(
                    BaseResponse.success("Reserva creada exitosamente", bookingResponse), 
                    HttpStatus.CREATED
                );
            } else {
                return new ResponseEntity<>(
                    BaseResponse.error(response.getMessage() != null ? response.getMessage() : "Error al crear la reserva"), 
                    HttpStatus.BAD_REQUEST
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                BaseResponse.error("Error al crear: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<BookingResponse>>> getAll() {
        try {
            BaseResponse<List<Booking>> response = bookingService.findAll();
            if ("success".equals(response.getStatus())) {
                List<BookingResponse> bookingResponses = response.getData().stream()
                    .map(BookingResponse::fromBooking)
                    .collect(Collectors.toList());
                return new ResponseEntity<>(BaseResponse.success("OK", bookingResponses), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(BaseResponse.error(response.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al listar: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<BookingResponse>> getById(@PathVariable Long id) {
        try {
            BaseResponse<Booking> response = bookingService.findById(id);
            if ("success".equals(response.getStatus())) {
                BookingResponse bookingResponse = BookingResponse.fromBooking(response.getData());
                return new ResponseEntity<>(BaseResponse.success("OK", bookingResponse), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(BaseResponse.error(response.getMessage()), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al buscar: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<BookingResponse>> update(@PathVariable Long id, @Valid @RequestBody Booking booking) {
        try {
            // Verificar que la reserva existe
            BaseResponse<Booking> existingBooking = bookingService.findById(id);
            if (existingBooking.getData() == null) {
                return new ResponseEntity<>(BaseResponse.error("Reserva no encontrada"), HttpStatus.NOT_FOUND);
            }
            
            // Verificar que el usuario existe si se está actualizando
            if (booking.getUserId() != null) {
                BaseResponse<User> userResponse = userService.findById(booking.getUserId());
                if (userResponse.getData() == null) {
                    return new ResponseEntity<>(BaseResponse.error("Usuario no encontrado"), HttpStatus.NOT_FOUND);
                }
            }
            
            // Verificar que el viaje existe si se está actualizando
            if (booking.getTripId() != null) {
                BaseResponse<Trip> tripResponse = tripService.findById(booking.getTripId());
                if (tripResponse.getData() == null) {
                    return new ResponseEntity<>(BaseResponse.error("Viaje no encontrado"), HttpStatus.NOT_FOUND);
                }
            }
            
            // Actualizar solo los campos proporcionados
            Booking existing = existingBooking.getData();
            if (booking.getUserId() != null) existing.setUserId(booking.getUserId());
            if (booking.getTripId() != null) existing.setTripId(booking.getTripId());
            if (booking.getBookingDate() != null) existing.setBookingDate(booking.getBookingDate());
            if (booking.getPassengerCount() > 0) existing.setPassengerCount(booking.getPassengerCount());
            if (booking.getTotalPrice() > 0) existing.setTotalPrice(booking.getTotalPrice());
            if (booking.getStatus() != null && !booking.getStatus().isEmpty()) {
                existing.setStatus(booking.getStatus());
            }
            
            BaseResponse<Booking> response = bookingService.update(id, existing);
            if ("success".equals(response.getStatus())) {
                // Obtener la reserva actualizada con los datos completos
                BaseResponse<Booking> updatedBooking = bookingService.findById(id);
                
                // Crear la respuesta con los datos del usuario y viaje
                BookingResponse bookingResponse = new BookingResponse();
                bookingResponse.setId(updatedBooking.getData().getId());
                bookingResponse.setBookingDate(updatedBooking.getData().getBookingDate());
                bookingResponse.setPassengerCount(updatedBooking.getData().getPassengerCount());
                bookingResponse.setTotalPrice(updatedBooking.getData().getTotalPrice());
                bookingResponse.setStatus(updatedBooking.getData().getStatus());
                
                // Obtener información del usuario
                BaseResponse<User> userResponse = userService.findById(updatedBooking.getData().getUserId());
                if (userResponse.getData() != null) {
                    BookingResponse.UserInfo userInfo = new BookingResponse.UserInfo();
                    userInfo.setId(userResponse.getData().getId());
                    userInfo.setName(userResponse.getData().getName());
                    userInfo.setEmail(userResponse.getData().getEmail());
                    bookingResponse.setUser(userInfo);
                }
                
                // Obtener información del viaje
                BaseResponse<Trip> tripResponse = tripService.findById(updatedBooking.getData().getTripId());
                if (tripResponse.getData() != null) {
                    BookingResponse.TripInfo tripInfo = new BookingResponse.TripInfo();
                    tripInfo.setId(tripResponse.getData().getId());
                    tripInfo.setOrigin(tripResponse.getData().getOrigin() != null ? 
                        tripResponse.getData().getOrigin().getName() : "Origen no especificado");
                    tripInfo.setDestination(tripResponse.getData().getDestination() != null ? 
                        tripResponse.getData().getDestination().getName() : "Destino no especificado");
                    tripInfo.setDepartureTime(tripResponse.getData().getDepartureTime());
                    tripInfo.setPrice(tripResponse.getData().getPrice());
                    bookingResponse.setTrip(tripInfo);
                }
                
                return new ResponseEntity<>(
                    BaseResponse.success("Reserva actualizada exitosamente", bookingResponse), 
                    HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                    BaseResponse.error(response.getMessage() != null ? response.getMessage() : "Error al actualizar la reserva"), 
                    HttpStatus.BAD_REQUEST
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                BaseResponse.error("Error al actualizar: " + e.getMessage()), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        try {
            BaseResponse<Void> response = bookingService.delete(id);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al eliminar"), HttpStatus.BAD_REQUEST);
        }
    }
}
