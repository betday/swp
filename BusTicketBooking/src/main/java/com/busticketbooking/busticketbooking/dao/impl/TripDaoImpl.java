package com.busticketbooking.busticketbooking.dao.impl;

import com.busticketbooking.busticketbooking.dao.TripDao;
import com.busticketbooking.busticketbooking.models.Route;
import com.busticketbooking.busticketbooking.models.Trip;
import com.busticketbooking.busticketbooking.models.Vehicle;

import java.sql.*;

public class TripDaoImpl implements TripDao {
    @Override
    public Trip getTripById(int id) throws SQLException {
        String sqlQuery = "SELECT \n" +
                "    Trip.trip_id, Trip.route_id AS routeId, Trip.vehicle_id AS vehicleId, Trip.date, Trip.time, Trip.quantity, Trip.seating_information, Trip.passenger_list, Trip.trip_status,\n" +
                "    Route.origin, Route.destination, Route.distance, Route.duration, Route.fare, Route.vehicle_type, Route.route_status,\n" +
                "    Vehicle.vehicle_type AS vehicleType, Vehicle.image_vehicle, Vehicle.license_plate, Vehicle.capacity, Vehicle.vehicle_status\n" +
                "FROM Trip \n" +
                "INNER JOIN Route ON Trip.route_id = Route.route_id\n" +
                "INNER JOIN Vehicle ON Trip.vehicle_id = Vehicle.vehicle_id\n" +
                "WHERE Trip.trip_id = ?\n";
        try(Connection connection = DBContext.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(!rs.next()){
                return null;
            }
            int tripId = rs.getInt(1);
            int routeId = rs.getInt(2);
            int vehicleId = rs.getInt(3);
            Date date = rs.getDate(4);
            Time time = rs.getTime(5);
            int quantity = rs.getInt(6);
            String seatingInformation = rs.getString(7);
            String passengerList = rs.getString(8);
            String tripStatus = rs.getString(9);
            String origin = rs.getString(10);
            String destination = rs.getString(11);
            float distance = rs.getFloat(12);
            float duration = rs.getFloat(13);
            float fare = rs.getFloat(14);
            String vehicleType = rs.getString(15);
            String routeStatus = rs.getString(16);
            String imageVehicle = rs.getString(18);
            String licensePlate = rs.getString(19);
            int capacity = rs.getInt(20);
            String vehicleStatus = rs.getString(21);
            Vehicle vehicle = new Vehicle(vehicleId, vehicleType, imageVehicle, licensePlate, capacity, vehicleStatus);
            Route route = new Route(routeId, origin, destination, distance, duration, fare, vehicleType, routeStatus);
            return new Trip(tripId, date, time, quantity, seatingInformation, passengerList, tripStatus, routeId, route, vehicleId, vehicle);
        }
    }
}
