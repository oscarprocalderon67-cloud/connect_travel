package co.edu.umanizales.connect_travel.service.impl;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Vehicle;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.CsvService;
import co.edu.umanizales.connect_travel.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final List<Vehicle> store;
    private final CsvService csv;
    private static final String FILE = "vehicles.csv";

    public VehicleServiceImpl(CsvService csv) {
        this.store = new ArrayList<>();
        this.csv = csv;
    }

    @PostConstruct
    void init() {
        List<Vehicle> loaded = csv.loadList(FILE, Vehicle.class);
        if (loaded != null && !loaded.isEmpty()) {
            store.clear();
            store.addAll(loaded);
        }
    }

    @Override
    public BaseResponse<Vehicle> create(Vehicle vehicle) {
        try {
            if (vehicle == null || vehicle.getId() == null) return BaseResponse.error("Datos inválidos");
            store.add(vehicle);
            csv.saveList(FILE, store);
            return BaseResponse.success("Creado", vehicle);
        } catch (Exception e) { return BaseResponse.error("Error al crear"); }
    }

    @Override
    public BaseResponse<List<Vehicle>> findAll() {
        try { return BaseResponse.success("OK", List.copyOf(store)); }
        catch (Exception e) { return BaseResponse.error("Error al listar"); }
    }

    @Override
    public BaseResponse<Vehicle> findById(Long id) throws NotFoundException {
        try {
            Vehicle v = store.stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NotFoundException("No existe el registro"));
            return BaseResponse.success("OK", v);
        } catch (NotFoundException e) { throw e; }
        catch (Exception e) { return BaseResponse.error("Error al buscar"); }
    }

    @Override
    public BaseResponse<Vehicle> update(Long id, Vehicle vehicle) {
        try {
            int i = indexOf(id);
            if (i == -1) throw new NotFoundException("No existe el registro");
            store.set(i, vehicle);
            csv.saveList(FILE, store);
            return BaseResponse.success("Actualizado", vehicle);
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
