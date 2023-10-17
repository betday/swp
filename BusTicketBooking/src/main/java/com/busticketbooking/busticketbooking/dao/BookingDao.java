package com.busticketbooking.busticketbooking.dao;

import com.busticketbooking.busticketbooking.models.Booking;

import java.sql.SQLException;
import java.util.List;

public interface BookingDao {
    public List<Booking> getAllBookingByTripId(int tripId) throws SQLException;
    public boolean insert(Booking booking) throws SQLException;
}
