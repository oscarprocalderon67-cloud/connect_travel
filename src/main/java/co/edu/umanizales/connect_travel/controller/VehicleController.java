package co.edu.umanizales.connect_travel.controller;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Vehicle;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Vehicle>> create(@Valid @RequestBody Vehicle vehicle) {
        try {
            BaseResponse<Vehicle> response = vehicleService.create(vehicle);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al crear"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Vehicle>>> getAll() {
        try {
            BaseResponse<List<Vehicle>> response = vehicleService.findAll();
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al listar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Vehicle>> getById(@PathVariable Long id) {
        try {
            BaseResponse<Vehicle> response = vehicleService.findById(id);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(response, status);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(BaseResponse.error(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al buscar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Vehicle>> update(@PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
        try {
            BaseResponse<Vehicle> response = vehicleService.update(id, vehicle);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al actualizar"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        try {
            BaseResponse<Void> response = vehicleService.delete(id);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al eliminar"), HttpStatus.BAD_REQUEST);
        }
    }
}
