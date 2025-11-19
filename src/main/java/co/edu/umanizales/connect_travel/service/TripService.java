package co.edu.umanizales.connect_travel.service;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Trip;

import java.util.List;

public interface TripService {
    BaseResponse<Trip> create(Trip trip);
    BaseResponse<List<Trip>> findAll();
    BaseResponse<Trip> findById(Long id) throws NotFoundException;
    BaseResponse<Trip> update(Long id, Trip trip);
    BaseResponse<Void> delete(Long id);
}
