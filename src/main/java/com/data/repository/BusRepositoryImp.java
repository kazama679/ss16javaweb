package com.data.repository;

import com.data.model.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BusRepositoryImp implements BusRepository {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<Bus> findAll() {
        List<Bus> list = new ArrayList<>();
        String sql = "SELECT * FROM buses";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bus bus = new Bus();
                bus.setId(rs.getInt("id"));
                bus.setLicensePlate(rs.getString("license_plate"));
                bus.setBusType(rs.getString("bus_type"));
                bus.setRowSeat(rs.getInt("row_seat"));
                bus.setColSeat(rs.getInt("col_seat"));
                bus.setTotalSeat(rs.getInt("total_seat"));
                bus.setImage(rs.getString("image"));
                list.add(bus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Bus findById(int id) {
        String sql = "SELECT * FROM buses WHERE id = ?";
        Bus bus = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bus = new Bus();
                bus.setId(rs.getInt("id"));
                bus.setLicensePlate(rs.getString("license_plate"));
                bus.setBusType(rs.getString("bus_type"));
                bus.setRowSeat(rs.getInt("row_seat"));
                bus.setColSeat(rs.getInt("col_seat"));
                bus.setTotalSeat(rs.getInt("total_seat"));
                bus.setImage(rs.getString("image"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bus;
    }

    @Override
    public void save(Bus bus) {
        String sql = "INSERT INTO buses (license_plate, bus_type, row_seat, col_seat, total_seat, image) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, bus.getLicensePlate());
            stmt.setString(2, bus.getBusType());
            stmt.setInt(3, bus.getRowSeat());
            stmt.setInt(4, bus.getColSeat());
            stmt.setInt(5, bus.getRowSeat() * bus.getColSeat());
            stmt.setString(6, bus.getImage());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                bus.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Bus bus) {
        String sql = "UPDATE buses SET license_plate = ?, bus_type = ?, row_seat = ?, col_seat = ?, total_seat = ?, image = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bus.getLicensePlate());
            stmt.setString(2, bus.getBusType());
            stmt.setInt(3, bus.getRowSeat());
            stmt.setInt(4, bus.getColSeat());
            stmt.setInt(5, bus.getRowSeat() * bus.getColSeat());
            stmt.setString(6, bus.getImage());
            stmt.setInt(7, bus.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM buses WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}