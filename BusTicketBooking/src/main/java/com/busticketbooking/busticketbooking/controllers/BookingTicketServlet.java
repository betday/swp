package com.busticketbooking.busticketbooking.controllers;

import com.busticketbooking.busticketbooking.Utils.ConvertUtil;
import com.busticketbooking.busticketbooking.dao.BookingDao;
import com.busticketbooking.busticketbooking.dao.TripDao;
import com.busticketbooking.busticketbooking.dao.impl.BookingDaoImpl;
import com.busticketbooking.busticketbooking.dao.impl.TripDaoImpl;
import com.busticketbooking.busticketbooking.models.Booking;
import com.busticketbooking.busticketbooking.models.Trip;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "bookingTicket", value = "/booking-ticket")
public class BookingTicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String tripIdParameter = request.getParameter("tripId");
            int tripId = ConvertUtil.convert(tripIdParameter);
            TripDao tripDao = new TripDaoImpl();
            Trip trip = tripDao.getTripById(tripId);
            BookingDao bookingDao = new BookingDaoImpl();
            List<Booking> bookingList = bookingDao.getAllBookingByTripId(tripId);
            if (trip != null) {
                request.setAttribute("trip", trip);
            }
            if (bookingList != null) {
                request.setAttribute("bookingList", bookingList);
            }
            request.getRequestDispatcher("booking.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(BookingTicketServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            StringBuilder jsonBuffer = new StringBuilder();
            String line = null;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }

            // Parse JSON data
            JSONObject jsonObject = new JSONObject(jsonBuffer.toString());
            int tripId = jsonObject.getInt("tripId");
            JSONArray seatNumbersArray = jsonObject.getJSONArray("seatNumbers");

            BookingDao bookingDao = new BookingDaoImpl();
            TripDao tripDao = new TripDaoImpl();
            Trip trip = tripDao.getTripById(tripId);
            java.sql.Date sqlDateNow = new java.sql.Date(System.currentTimeMillis());
            // Process tripId and seat numbers
            for (int i = 0; i < seatNumbersArray.length(); i++) {
                String seatNumber = seatNumbersArray.getString(i);
                Booking booking = new Booking();
                booking.setBookingId(ConvertUtil.getRandomNumberInRange(1, 100) + i);
                booking.setTripId(tripId);
                booking.setSeatNumber(seatNumber);
                booking.setDateBooking(sqlDateNow);
                booking.setDiscount(0.05f);
                booking.setPrice(trip.getRoute().getFare());
                booking.setBookingStatus("WaitingPayment");
                bookingDao.insert(booking);
            }

            JSONObject jobj = new JSONObject();
            String urlToRedirect = "booking-ticket?tripId="+tripId;
            jobj.put("url",urlToRedirect);
            response.getWriter().write(jobj.toString());
        }
        catch (Exception ex){
            Logger.getLogger(BookingTicketServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
