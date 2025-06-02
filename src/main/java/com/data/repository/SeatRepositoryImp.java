package com.data.repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.data.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SeatRepositoryImp implements SeatRepository {

    @Autowired
    private DataSource dataSource;

    @Override
    public void save(Seat seat) {
        String sql = "INSERT INTO seats (name_seat, price, bus_id, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, seat.getNameSeat());
            stmt.setDouble(2, seat.getPrice());
            stmt.setInt(3, seat.getBusId());
            stmt.setBoolean(4, seat.isStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Seat> findByBusId(int busId) {
        List<Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE bus_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, busId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Seat seat = new Seat();
                seat.setId(rs.getInt("id"));
                seat.setNameSeat(rs.getString("name_seat"));
                seat.setPrice(rs.getDouble("price"));
                seat.setBusId(rs.getInt("bus_id"));
                seat.setStatus(rs.getBoolean("status"));
                list.add(seat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void deleteByBusId(int busId) {
        String sql = "DELETE FROM seats WHERE bus_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, busId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}