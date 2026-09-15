package org.td2.rattrapageprog3.dto;

import org.td2.rattrapageprog3.model.TripStatus;

import java.time.LocalDate;

public class TripResponse {
    public String id;
    public DriverSummary driver;
    public VehicleSummary vehicle;
    public LocalDate tripDate;
    public String departureCity;
    public String arrivalCity;
    public int distanceKm;
    public long billedAmount;
    public TripStatus status;

}
