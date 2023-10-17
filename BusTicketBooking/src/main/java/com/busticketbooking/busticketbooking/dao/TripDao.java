package com.busticketbooking.busticketbooking.dao;

import com.busticketbooking.busticketbooking.models.Trip;

import java.sql.SQLException;

public interface TripDao {

    public Trip getTripById(int id) throws SQLException;
}
