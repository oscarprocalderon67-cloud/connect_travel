package co.edu.umanizales.connect_travel.service.impl;

import co.edu.umanizales.connect_travel.exception.NotFoundException;
import co.edu.umanizales.connect_travel.model.Booking;
import co.edu.umanizales.connect_travel.service.BaseResponse;
import co.edu.umanizales.connect_travel.service.CsvService;
import co.edu.umanizales.connect_travel.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;

@Service
public class BookingServiceImpl implements BookingService {
    private final List<Booking> store;
    private final CsvService csv;
    private static final String FILE = "bookings.csv";

    public BookingServiceImpl(CsvService csv) {
        this.store = new ArrayList<>();
        this.csv = csv;
    }

    @PostConstruct
    void init() {
        List<Booking> loaded = csv.loadList(FILE, Booking.class);
        if (loaded != null && !loaded.isEmpty()) {
            store.clear();
            store.addAll(loaded);
        }
    }

    @Override
    public BaseResponse<Booking> create(Booking booking) {
        try {
            if (booking == null || booking.getId() == null) return BaseResponse.error("Datos inválidos");
            store.add(booking);
            csv.saveList(FILE, store);
            return BaseResponse.success("Creado", booking);
        } catch (Exception e) { return BaseResponse.error("Error al crear"); }
    }

    @Override
    public BaseResponse<List<Booking>> findAll() {
        try { return BaseResponse.success("OK", List.copyOf(store)); }
        catch (Exception e) { return BaseResponse.error("Error al listar"); }
    }

    @Override
    public BaseResponse<Booking> findById(Long id) throws NotFoundException {
        try {
            Booking b = store.stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NotFoundException("No existe el registro"));
            return BaseResponse.success("OK", b);
        } catch (NotFoundException e) { throw e; }
        catch (Exception e) { return BaseResponse.error("Error al buscar"); }
    }

    @Override
    public BaseResponse<Booking> update(Long id, Booking booking) {
        try {
            int i = indexOf(id);
            if (i == -1) throw new NotFoundException("No existe el registro");
            store.set(i, booking);
            csv.saveList(FILE, store);
            return BaseResponse.success("Actualizado", booking);
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
