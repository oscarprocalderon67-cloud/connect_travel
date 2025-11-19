package co.edu.umanizales.connect_travel.service;

public class BaseResponse<T> {
    private final String status;
    private final String message;
    private final T data;

    private BaseResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>("success", message, data);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>("error", message, null);
    }

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}
