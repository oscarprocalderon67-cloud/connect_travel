package co.edu.umanizales.connect_travel.service;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Driver;

import java.util.List;

public interface DriverService {
    BaseResponse<Driver> create(Driver driver);
    BaseResponse<List<Driver>> findAll();
    BaseResponse<Driver> findById(Long id) throws NotFoundException;
    BaseResponse<Driver> update(Long id, Driver driver);
    BaseResponse<Void> delete(Long id);
}
