package co.edu.umanizales.connect_travel.service;

import co.edu.umanizales.connect_travel.config.StorageProperties;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {
    private final StorageProperties storageProperties;

    public CsvService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    public <T> void saveList(String filename, List<T> data) {
        try {
            Path path = ensureFile(filename);
            try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (T item : data) {
                    bw.write(serialize(item));
                    bw.newLine();
                }
            }
        } catch (Exception ignored) { }
    }

    public <T> List<T> loadList(String filename, Class<T> type) {
        List<T> list = new ArrayList<>();
        try {
            Path path = ensureFile(filename);
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;
                T obj = deserialize(trimmed, type);
                if (obj != null) list.add(obj);
            }
        } catch (Exception ignored) { }
        return list;
    }

    private Path ensureFile(String filename) throws IOException {
        String baseDir = storageProperties.getStorageDir();
        Path dir = Paths.get(baseDir == null || baseDir.isBlank() ? "data" : baseDir);
        if (Files.notExists(dir)) Files.createDirectories(dir);
        Path file = dir.resolve(filename);
        if (Files.notExists(file)) Files.createFile(file);
        return file;
    }

    private <T> String serialize(T obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            f.setAccessible(true);
            Object value = f.get(obj);
            String cell = toCell(value);
            sb.append(cell);
            if (i < fields.length - 1) sb.append(",");
        }
        return sb.toString();
    }

    private String toCell(Object value) {
        if (value == null) return "";
        if (value instanceof LocalDate d) return d.toString();
        if (value instanceof LocalDateTime dt) return dt.toString();
        if (value instanceof LocalTime t) return t.toString();
        if (isSimple(value.getClass())) return value.toString();
        // nested object: write its id if present
        try {
            Field id = value.getClass().getDeclaredField("id");
            id.setAccessible(true);
            Object idVal = id.get(value);
            return idVal == null ? "" : idVal.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private <T> T deserialize(String line, Class<T> type) {
        try {
            String[] parts = line.split(",", -1);
            Field[] fields = type.getDeclaredFields();
            T instance = newInstance(type);
            for (int i = 0; i < fields.length && i < parts.length; i++) {
                Field f = fields[i];
                f.setAccessible(true);
                String raw = parts[i];
                Object val = fromCell(raw, f.getType());
                f.set(instance, val);
            }
            return instance;
        } catch (Exception e) { return null; }
    }

    private Object fromCell(String raw, Class<?> target) {
        if (raw == null || raw.isEmpty()) return null;
        if (target.equals(String.class)) return raw;
        if (target.equals(Long.class) || target.equals(long.class)) return parseLong(raw);
        if (target.equals(Integer.class) || target.equals(int.class)) return parseInt(raw);
        if (target.equals(Double.class) || target.equals(double.class)) return parseDouble(raw);
        if (target.equals(Boolean.class) || target.equals(boolean.class)) return Boolean.parseBoolean(raw);
        if (target.equals(LocalDate.class)) return LocalDate.parse(raw);
        if (target.equals(LocalDateTime.class)) return LocalDateTime.parse(raw);
        if (target.equals(LocalTime.class)) return LocalTime.parse(raw);
        if (target.isEnum()) return parseEnum(target, raw);
        // nested object with id only
        try {
            Object nested = newInstance(target);
            Field id = target.getDeclaredField("id");
            id.setAccessible(true);
            id.set(nested, parseLong(raw));
            return nested;
        } catch (Exception e) { return null; }
    }

    private Object parseEnum(Class<?> enumType, String raw) {
        try {
            Object[] constants = enumType.getEnumConstants();
            for (Object c : constants) if (c.toString().equals(raw)) return c;
        } catch (Exception ignored) { }
        return null;
    }

    private <T> T newInstance(Class<T> type) throws Exception {
        Constructor<T> ctor = type.getDeclaredConstructor();
        ctor.setAccessible(true);
        return ctor.newInstance();
    }

    private boolean isSimple(Class<?> c) {
        return c.isPrimitive() || c.equals(String.class) ||
                Number.class.isAssignableFrom(c) || c.equals(Boolean.class) ||
                c.isEnum();
    }

    private Long parseLong(String s) { try { return Long.parseLong(s); } catch (Exception e) { return null; } }
    private Integer parseInt(String s) { try { return Integer.parseInt(s); } catch (Exception e) { return null; } }
    private Double parseDouble(String s) { try { return Double.parseDouble(s); } catch (Exception e) { return null; } }
}
