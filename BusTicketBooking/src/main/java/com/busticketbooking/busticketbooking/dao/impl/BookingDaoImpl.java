package com.busticketbooking.busticketbooking.dao.impl;

import com.busticketbooking.busticketbooking.dao.BookingDao;
import com.busticketbooking.busticketbooking.models.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoImpl implements BookingDao {

    @Override
    public List<Booking> getAllBookingByTripId(int tripId) throws SQLException {
        List<Booking> bookingList = new ArrayList<Booking>();
        String sqlQuery = "SELECT [booking_id]\n" +
                "      ,[user_id]\n" +
                "      ,[trip_id]\n" +
                "      ,[date_booking]\n" +
                "      ,[seat_number]\n" +
                "      ,[price]\n" +
                "      ,[discount]\n" +
                "      ,[booking_status]\n" +
                "  FROM [dbo].[Booking]\n" +
                "  WHERE [trip_id] = ?";
        try(Connection connection = DBContext.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, tripId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int bookingId = rs.getInt(1);
                int userId = rs.getInt(2);
                int tripIdGetFromDb = rs.getInt(3);
                Date dateBooking = rs.getDate(4);
                String seatNumber = rs.getString(5);
                float price = rs.getFloat(6);
                float discount = rs.getFloat(7);
                String bookingStatus = rs.getString(8);
                Booking booking = new Booking(bookingId, userId, tripId, dateBooking, seatNumber, price, discount, bookingStatus);
                bookingList.add(booking);
            }
        }
        return bookingList.isEmpty() ? null : bookingList;
    }

    @Override
    public boolean insert(Booking booking) throws SQLException {
        String sqlQuery  = "INSERT INTO [dbo].[Booking]\n" +
                "           ([booking_id]\n" +
                "           ,[trip_id]\n" +
                "           ,[date_booking]\n" +
                "           ,[seat_number]\n" +
                "           ,[price]\n" +
                "           ,[discount]\n" +
                "           ,[booking_status])\n" +
                "     VALUES\n" +
                "           (?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DBContext.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, booking.getBookingId());
            statement.setInt(2, booking.getTripId());
            statement.setDate(3, booking.getDateBooking());
            statement.setString(4, booking.getSeatNumber());
            statement.setFloat(5, booking.getPrice());
            statement.setFloat(6, booking.getDiscount());
            statement.setString(7, booking.getBookingStatus());
            return statement.executeUpdate() > 0;
        }
    }
}
