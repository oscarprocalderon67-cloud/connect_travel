package co.edu.umanizales.connect_travel.service;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Booking;

import java.util.List;

public interface BookingService {
    BaseResponse<Booking> create(Booking booking);
    BaseResponse<List<Booking>> findAll();
    BaseResponse<Booking> findById(Long id) throws NotFoundException;
    BaseResponse<Booking> update(Long id, Booking booking);
    BaseResponse<Void> delete(Long id);
}
