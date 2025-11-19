package co.edu.umanizales.connect_travel.controller;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Trip;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Trip>> create(@Valid @RequestBody Trip trip) {
        try {
            BaseResponse<Trip> response = tripService.create(trip);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al crear"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Trip>>> getAll() {
        try {
            BaseResponse<List<Trip>> response = tripService.findAll();
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al listar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Trip>> getById(@PathVariable Long id) {
        try {
            BaseResponse<Trip> response = tripService.findById(id);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(response, status);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(BaseResponse.error(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al buscar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Trip>> update(@PathVariable Long id, @Valid @RequestBody Trip trip) {
        try {
            BaseResponse<Trip> response = tripService.update(id, trip);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al actualizar"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        try {
            BaseResponse<Void> response = tripService.delete(id);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al eliminar"), HttpStatus.BAD_REQUEST);
        }
    }
}
