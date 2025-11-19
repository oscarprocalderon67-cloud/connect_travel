package co.edu.umanizales.connect_travel.controller;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Driver;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Driver>> create(@Valid @RequestBody Driver driver) {
        try {
            BaseResponse<Driver> response = driverService.create(driver);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al crear"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Driver>>> getAll() {
        try {
            BaseResponse<List<Driver>> response = driverService.findAll();
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al listar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Driver>> getById(@PathVariable Long id) {
        try {
            BaseResponse<Driver> response = driverService.findById(id);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(response, status);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(BaseResponse.error(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al buscar"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Driver>> update(@PathVariable Long id, @Valid @RequestBody Driver driver) {
        try {
            BaseResponse<Driver> response = driverService.update(id, driver);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al actualizar"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        try {
            BaseResponse<Void> response = driverService.delete(id);
            HttpStatus status = "success".equals(response.getStatus()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            return new ResponseEntity<>(BaseResponse.error("Error al eliminar"), HttpStatus.BAD_REQUEST);
        }
    }
}
