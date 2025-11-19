package co.edu.umanizales.connect_travel.service;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Destination;

import java.util.List;

public interface DestinationService {
    BaseResponse<Destination> create(Destination destination);
    BaseResponse<List<Destination>> findAll();
    BaseResponse<Destination> findById(Long id) throws NotFoundException;
    BaseResponse<Destination> update(Long id, Destination destination);
    BaseResponse<Void> delete(Long id);
}
