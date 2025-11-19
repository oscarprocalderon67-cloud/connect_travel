package co.edu.umanizales.connect_travel.service.impl;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Trip;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.CsvService;
import co.edu.umanizales.connect_travel.service.TripService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;

@Service
public class TripServiceImpl implements TripService {
    private final List<Trip> store;
    private final CsvService csv;
    private static final String FILE = "trips.csv";

    public TripServiceImpl(CsvService csv) {
        this.store = new ArrayList<>();
        this.csv = csv;
    }

    @PostConstruct
    void init() {
        List<Trip> loaded = csv.loadList(FILE, Trip.class);
        if (loaded != null && !loaded.isEmpty()) {
            store.clear();
            store.addAll(loaded);
        }
    }

    @Override
    public BaseResponse<Trip> create(Trip trip) {
        try {
            if (trip == null || trip.getId() == null) return BaseResponse.error("Datos inválidos");
            store.add(trip);
            csv.saveList(FILE, store);
            return BaseResponse.success("Creado", trip);
        } catch (Exception e) { return BaseResponse.error("Error al crear"); }
    }

    @Override
    public BaseResponse<List<Trip>> findAll() {
        try { return BaseResponse.success("OK", List.copyOf(store)); }
        catch (Exception e) { return BaseResponse.error("Error al listar"); }
    }

    @Override
    public BaseResponse<Trip> findById(Long id) throws NotFoundException {
        try {
            Trip t = store.stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NotFoundException("No existe el registro"));
            return BaseResponse.success("OK", t);
        } catch (NotFoundException e) { throw e; }
        catch (Exception e) { return BaseResponse.error("Error al buscar"); }
    }

    @Override
    public BaseResponse<Trip> update(Long id, Trip trip) {
        try {
            int i = indexOf(id);
            if (i == -1) throw new NotFoundException("No existe el registro");
            store.set(i, trip);
            csv.saveList(FILE, store);
            return BaseResponse.success("Actualizado", trip);
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
