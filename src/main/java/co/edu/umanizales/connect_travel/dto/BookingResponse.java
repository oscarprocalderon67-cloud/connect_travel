package co.edu.umanizales.connect_travel.dto;

import co.edu.umanizales.connect_travel.model.Booking;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private Long id;
    private Long userId;
    private Long tripId;
    private UserInfo user;
    private TripInfo trip;
    private LocalDateTime bookingDate;
    private int passengerCount;
    private double totalPrice;
    private String status;

    public static BookingResponse fromBooking(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setUserId(booking.getUserId());
        response.setTripId(booking.getTripId());
        response.setBookingDate(booking.getBookingDate());
        response.setPassengerCount(booking.getPassengerCount());
        response.setTotalPrice(booking.getTotalPrice());
        response.setStatus(booking.getStatus());
        
        return response;
    }

    @Data
    public static class UserInfo {
        private Long id;
        private String name;
        private String email;
        private String phone;
    }

    @Data
    public static class TripInfo {
        private Long id;
        private String origin;
        private String destination;
        private LocalDateTime departureTime;
        private double price;
    }
}
