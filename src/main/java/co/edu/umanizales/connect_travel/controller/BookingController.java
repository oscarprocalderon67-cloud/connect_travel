package co.edu.umanizales.connect_travel.controller;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Booking;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Booking>> create(@Valid @RequestBody Booking booking) {
        try {
            BaseResponse<Booking> response = bookingService.create(booking);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al crear"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Booking>>> getAll() {
        try {
            BaseResponse<List<Booking>> response = bookingService.findAll();
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al listar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Booking>> getById(@PathVariable Long id) {
        try {
            BaseResponse<Booking> response = bookingService.findById(id);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(response, status);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(BaseResponse.error(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al buscar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Booking>> update(@PathVariable Long id, @Valid @RequestBody Booking booking) {
        try {
            BaseResponse<Booking> response = bookingService.update(id, booking);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al actualizar"), HttpStatus.BAD_REQUEST);
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
