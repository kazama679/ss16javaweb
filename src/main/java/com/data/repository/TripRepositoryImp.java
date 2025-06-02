package com.data.repository;

import com.data.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TripRepositoryImp implements TripRepository {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Trip> searchTrips(String departure, String destination, int offset, int limit) {
        List<Trip> trips = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            CallableStatement call = conn.prepareCall("{call search_trips(?, ?, ?, ?)}");
            call.setString(1, "%" + departure + "%");
            call.setString(2, "%" + destination + "%");
            call.setInt(3, offset);
            call.setInt(4, limit);
            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                Trip t = new Trip();
                t.setId(rs.getInt("id"));
                t.setDeparture(rs.getString("departure"));
                t.setDestination(rs.getString("destination"));
                t.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
                t.setPrice(rs.getDouble("price"));
                trips.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trips;
    }

    @Override
    public int countTrips(String departure, String destination) {
        int total = 0;
        try (Connection conn = dataSource.getConnection()) {
            CallableStatement call = conn.prepareCall("{call count_trips(?, ?)}");
            call.setString(1, "%" + departure + "%");
            call.setString(2, "%" + destination + "%");
            ResultSet rs = call.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}