package co.edu.umanizales.connect_travel.service.impl;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Destination;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.CsvService;
import co.edu.umanizales.connect_travel.service.DestinationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;

@Service
public class DestinationServiceImpl implements DestinationService {
    private final List<Destination> store;
    private final CsvService csv;
    private static final String FILE = "destinations.csv";

    public DestinationServiceImpl(CsvService csv) {
        this.store = new ArrayList<>();
        this.csv = csv;
    }

    @PostConstruct
    void init() {
        List<Destination> loaded = csv.loadList(FILE, Destination.class);
        if (loaded != null && !loaded.isEmpty()) {
            store.clear();
            store.addAll(loaded);
        }
    }

    @Override
    public BaseResponse<Destination> create(Destination destination) {
        try {
            if (destination == null || destination.getId() == null) return BaseResponse.error("Datos inválidos");
            store.add(destination);
            csv.saveList(FILE, store);
            return BaseResponse.success("Creado", destination);
        } catch (Exception e) { return BaseResponse.error("Error al crear"); }
    }

    @Override
    public BaseResponse<List<Destination>> findAll() {
        try { return BaseResponse.success("OK", List.copyOf(store)); }
        catch (Exception e) { return BaseResponse.error("Error al listar"); }
    }

    @Override
    public BaseResponse<Destination> findById(Long id) throws NotFoundException {
        try {
            Destination d = store.stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NotFoundException("No existe el registro"));
            return BaseResponse.success("OK", d);
        } catch (NotFoundException e) { throw e; }
        catch (Exception e) { return BaseResponse.error("Error al buscar"); }
    }

    @Override
    public BaseResponse<Destination> update(Long id, Destination destination) {
        try {
            int i = indexOf(id);
            if (i == -1) throw new NotFoundException("No existe el registro");
            store.set(i, destination);
            csv.saveList(FILE, store);
            return BaseResponse.success("Actualizado", destination);
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
