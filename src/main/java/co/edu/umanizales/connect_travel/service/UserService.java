package co.edu.umanizales.connect_travel.service;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.User;

import java.util.List;

public interface UserService {
    BaseResponse<User> create(User user);
    BaseResponse<List<User>> findAll();
    BaseResponse<User> findById(Long id) throws NotFoundException;
    BaseResponse<User> update(Long id, User user);
    BaseResponse<Void> delete(Long id);
}
