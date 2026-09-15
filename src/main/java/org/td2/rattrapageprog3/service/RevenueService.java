package org.td2.rattrapageprog3.service;

import org.springframework.stereotype.Service;
import org.td2.rattrapageprog3.model.Driver;
import org.td2.rattrapageprog3.model.Trip;
import org.td2.rattrapageprog3.model.TripStatus;
import org.td2.rattrapageprog3.repository.DataRetriever;

import java.time.LocalDate;
import java.util.List;

@Service
public class RevenueService {
    private static final double COOPERATIVE_RATE = 0.15;
    private final DataRetriever dataRetriever;
    public RevenueService(DataRetriever dataRetriever) {
        this.dataRetriever = dataRetriever;
    }
    public long computeDriverRevenue(String driverId, LocalDate from, LocalDate to) {
        long total = 0;
        List<Trip> trips = dataRetriever.findTripsByDriver(driverId);
        for (Trip trip : trips) {
            if (trip.getTripDate().compareTo(from) >= 0
                    && trip.getTripDate().compareTo(to) <= 0
                    && trip.isBillable()) {
                total += trip.getBilledAmount();
            }
        }
        return total;
    }
    public long computeCooperativeFee(String driverId, LocalDate from, LocalDate to) {
        return Math.round(computeDriverRevenue(driverId, from, to) * COOPERATIVE_RATE);
    }
    public double computeAveragePricePerKm(String driverId) {
        List<Trip> trips = dataRetriever.findTripsByDriver(driverId);
        double total = 0;
        int count = 0;
        for (Trip trip : trips) {
            if (trip.getStatus() == TripStatus.COMPLETED) {
                total += trip.pricePerKm();
                count++;
            }
        }
        return count == 0 ? 0.0 : total / count;
    }
    public Driver findTopEarningDriver(LocalDate from, LocalDate to) {
        Driver top = null;
        long bestRevenue = Long.MIN_VALUE;
        for (Driver driver : dataRetriever.findAllDrivers()) {
            long revenue = computeDriverRevenue(driver.getId(), from, to);
            if (revenue > bestRevenue) {
                bestRevenue = revenue;
                top = driver;
            }
        }
        return top;
    }

}
