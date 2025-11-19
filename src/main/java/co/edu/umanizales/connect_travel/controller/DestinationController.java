package co.edu.umanizales.connect_travel.controller;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Destination;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.DestinationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    @Autowired
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Destination>> create(@Valid @RequestBody Destination destination) {
        try {
            BaseResponse<Destination> response = destinationService.create(destination);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al crear"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Destination>>> getAll() {
        try {
            BaseResponse<List<Destination>> response = destinationService.findAll();
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al listar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Destination>> getById(@PathVariable Long id) {
        try {
            BaseResponse<Destination> response = destinationService.findById(id);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(response, status);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(BaseResponse.error(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al buscar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Destination>> update(@PathVariable Long id, @Valid @RequestBody Destination destination) {
        try {
            BaseResponse<Destination> response = destinationService.update(id, destination);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al actualizar"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        try {
            BaseResponse<Void> response = destinationService.delete(id);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al eliminar"), HttpStatus.BAD_REQUEST);
        }
    }
}
