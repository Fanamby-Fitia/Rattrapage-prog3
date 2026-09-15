package org.td2.rattrapageprog3.repository;

import org.springframework.stereotype.Repository;
import org.td2.rattrapageprog3.model.*;
import org.td2.rattrapageprog3.model.Driver;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataRetriever {
    private final Connection connection;
    public DataRetriever(Connection connection) {
        this.connection = connection;
    }
    public Driver findDriverById(String id) {
        String sql = "SELECT id, name, license_category, affiliation_date FROM driver WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapDriver(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Driver> findAllDrivers() {
        String sql = "SELECT id, name, license_category, affiliation_date FROM driver";
        List<Driver> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.add(mapDriver(rs));
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Vehicle findVehicleById(String id) {
        String sql = "SELECT id, plate_number, type, capacity_tons FROM vehicle WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapVehicle(rs);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Trip findTripById(String id) {
        String sql = "SELECT id, driver_id, vehicle_id, trip_date, departure_city, " +
                "arrival_city, distance_km, billed_amount, status FROM trip WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapTrip(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Trip> findTripsByDriver(String driverId) {
        String sql = "SELECT id, driver_id, vehicle_id, trip_date, departure_city, " +
                "arrival_city, distance_km, billed_amount, status FROM trip WHERE driver_id = ?";
        List<Trip> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, driverId);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) result.add(mapTrip(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Trip> findTripsByPeriod(LocalDate from, LocalDate to) {
        String sql = "SELECT id, driver_id, vehicle_id, trip_date, departure_city, " +
                "arrival_city, distance_km, billed_amount, status " +
                "FROM trip WHERE trip_date >= ? AND trip_date <= ?";
        List<Trip> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(mapTrip(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Trip> findAllTrips() {
        String sql = "SELECT id, driver_id, vehicle_id, trip_date, departure_city, " +
                "arrival_city, distance_km, billed_amount, status FROM trip";
        List<Trip> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) result.add(mapTrip(rs));
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Trip saveTrip(Trip trip) {
        String checkSql = "SELECT id FROM trip WHERE id = ?";
        String insertSql = "INSERT INTO trip " +
                "(id, driver_id, vehicle_id, trip_date, departure_city, arrival_city, distance_km, billed_amount, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE trip SET driver_id = ?, vehicle_id = ?, trip_date = ?, " +
                "departure_city = ?, arrival_city = ?, distance_km = ?, billed_amount = ?, status = ? WHERE id = ?";
        try (PreparedStatement check = connection.prepareStatement(checkSql)) {
            check.setString(1, trip.getId());
            boolean exists;
            try (ResultSet rs = check.executeQuery()) {
                exists = rs.next();
            }
            if (exists) {
                try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                    ps.setString(1, trip.getDriver().getId());
                    ps.setString(2, trip.getVehicle().getId());
                    ps.setDate(3, Date.valueOf(trip.getTripDate()));
                    ps.setString(4, trip.getDepartureCity());
                    ps.setString(5, trip.getArrivalCity());
                    ps.setInt(6, trip.getDistanceKm());
                    ps.setLong(7, trip.getBilledAmount());
                    ps.setString(8, trip.getStatus().name());
                    ps.setString(9, trip.getId());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                    ps.setString(1, trip.getId());
                    ps.setString(2, trip.getDriver().getId());
                    ps.setString(3, trip.getVehicle().getId());
                    ps.setDate(4, Date.valueOf(trip.getTripDate()));
                    ps.setString(5, trip.getDepartureCity());
                    ps.setString(6, trip.getArrivalCity());
                    ps.setInt(7, trip.getDistanceKm());
                    ps.setLong(8, trip.getBilledAmount());
                    ps.setString(9, trip.getStatus().name());
                    ps.executeUpdate();
                }
            }
            return findTripById(trip.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Trip updateTripStatus(String tripId, TripStatus status) {
        String sql = "UPDATE trip SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setString(2, tripId);
            int updated = ps.executeUpdate();
            if (updated == 0) return null;
            return findTripById(tripId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Driver mapDriver(ResultSet rs) throws SQLException {
        return new Driver(
                rs.getString("id"),
                rs.getString("name"),
                LicenseCategory.valueOf(rs.getString("license_category")),
                rs.getDate("affiliation_date").toLocalDate()
        );
    }
    private Vehicle mapVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getString("id"),
                rs.getString("plate_number"),
                VehicleType.valueOf(rs.getString("type")),
                rs.getDouble("capacity_tons")
        );
    }
    private Trip mapTrip(ResultSet rs) throws SQLException {
        Driver driver = findDriverById(rs.getString("driver_id"));
        Vehicle vehicle = findVehicleById(rs.getString("vehicle_id"));
        return new Trip(
                rs.getString("id"),
                driver,
                vehicle,
                rs.getDate("trip_date").toLocalDate(),
                rs.getString("departure_city"),
                rs.getString("arrival_city"),
                rs.getInt("distance_km"),
                rs.getLong("billed_amount"),
                TripStatus.valueOf(rs.getString("status"))
        );
    }
}


