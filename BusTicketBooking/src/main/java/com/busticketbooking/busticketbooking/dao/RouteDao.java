package com.busticketbooking.busticketbooking.dao;

import com.busticketbooking.busticketbooking.models.Route;

import java.sql.SQLException;

public interface RouteDao {

    public Route getRouteById(int id) throws SQLException;
}
