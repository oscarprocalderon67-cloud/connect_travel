package co.edu.umanizales.connect_travel.service.impl;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.User;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.CsvService;
import co.edu.umanizales.connect_travel.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {
    private final List<User> store;
    private final CsvService csv;
    private static final String FILE = "users.csv";

    public UserServiceImpl(CsvService csv) {
        this.store = new ArrayList<>();
        this.csv = csv;
    }

    @PostConstruct
    void init() {
        List<User> loaded = csv.loadList(FILE, User.class);
        if (loaded != null && !loaded.isEmpty()) {
            store.clear();
            store.addAll(loaded);
        }
    }

    @Override
    public BaseResponse<User> create(User user) {
        try {
            if (user == null || user.getId() == null) return BaseResponse.error("Datos inválidos");
            store.add(user);
            csv.saveList(FILE, store);
            return BaseResponse.success("Creado", user);
        } catch (Exception e) { return BaseResponse.error("Error al crear"); }
    }

    @Override
    public BaseResponse<List<User>> findAll() {
        try { return BaseResponse.success("OK", List.copyOf(store)); }
        catch (Exception e) { return BaseResponse.error("Error al listar"); }
    }

    @Override
    public BaseResponse<User> findById(Long id) throws NotFoundException {
        try {
            User u = store.stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NotFoundException("No existe el registro"));
            return BaseResponse.success("OK", u);
        } catch (NotFoundException e) { throw e; }
        catch (Exception e) { return BaseResponse.error("Error al buscar"); }
    }

    @Override
    public BaseResponse<User> update(Long id, User user) {
        try {
            int i = indexOf(id);
            if (i == -1) throw new NotFoundException("No existe el registro");
            store.set(i, user);
            csv.saveList(FILE, store);
            return BaseResponse.success("Actualizado", user);
        } catch (NotFoundException e) { return BaseResponse.error(e.getMessage()); }
        catch (Exception e) { return BaseResponse.error("Error al actualizar"); }
    }

    @Override
    public BaseResponse<Void> delete(Long id) {
        try {
            int i = indexOf(id);
            if (i == -1) throw new NotFoundException("No existe el registro");
            store.remove(i);
            csv.saveList(FILE, store);
            return BaseResponse.success("Eliminado", null);
        } catch (NotFoundException e) { return BaseResponse.error(e.getMessage()); }
        catch (Exception e) { return BaseResponse.error("Error al eliminar"); }
    }

    private int indexOf(Long id) {
        for (int i = 0; i < store.size(); i++) if (store.get(i).getId().equals(id)) return i;
        return -1;
    }
}
