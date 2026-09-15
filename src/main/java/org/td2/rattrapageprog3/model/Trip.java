package org.td2.rattrapageprog3.model;

import java.time.LocalDate;

public class Trip {
    private String id;
    private Driver driver;
    private Vehicle vehicle;
    private LocalDate tripDate;
    private String departureCity;
    private String arrivalCity;
    private int distanceKm;
    private long billedAmount;
    private TripStatus status;
    public Trip() {
    }
    public Trip(String id, Driver driver, Vehicle vehicle, LocalDate tripDate,
                String departureCity, String arrivalCity, int distanceKm,
                long billedAmount, TripStatus status) {
        this.id = id;
        this.driver = driver;
        this.vehicle = vehicle;
        this.tripDate = tripDate;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.distanceKm = distanceKm;
        this.billedAmount = billedAmount;
        this.status = status;
    }
    public boolean isBillable() {
        return status == TripStatus.COMPLETED;
    }
    public double pricePerKm() {
        if (distanceKm <= 0) {
            return 0.0;
        }
        return (double) billedAmount / distanceKm;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public LocalDate getTripDate() {
        return tripDate;
    }
    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }
    public String getDepartureCity() {
        return departureCity;
    }
    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }
    public String getArrivalCity() {
        return arrivalCity;
    }
    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }
    public int getDistanceKm() {
        return distanceKm;
    }
    public void setDistanceKm(int distanceKm) {
        this.distanceKm = distanceKm;
    }
    public long getBilledAmount() {
        return billedAmount;
    }
    public void setBilledAmount(long billedAmount) {
        this.billedAmount = billedAmount;
    }
    public TripStatus getStatus() {
        return status;
    }
    public void setStatus(TripStatus status) {
        this.status = status;
    }
}
