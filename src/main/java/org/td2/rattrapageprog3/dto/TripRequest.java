package org.td2.rattrapageprog3.dto;

import java.time.LocalDate;

public class TripRequest {
    public String driverId;
    public String vehicleId;
    public LocalDate tripDate;
    public String departureCity;
    public String arrivalCity;
    public int distanceKm;
    public long billedAmount;
}
