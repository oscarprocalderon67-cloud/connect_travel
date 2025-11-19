package co.edu.umanizales.connect_travel.service;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Vehicle;

import java.util.List;

public interface VehicleService {
    BaseResponse<Vehicle> create(Vehicle vehicle);
    BaseResponse<List<Vehicle>> findAll();
    BaseResponse<Vehicle> findById(Long id) throws NotFoundException;
    BaseResponse<Vehicle> update(Long id, Vehicle vehicle);
    BaseResponse<Void> delete(Long id);
}
