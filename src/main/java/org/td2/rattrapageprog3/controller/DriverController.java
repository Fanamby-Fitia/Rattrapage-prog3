package org.td2.rattrapageprog3.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.td2.rattrapageprog3.dto.RevenueResponse;
import org.td2.rattrapageprog3.dto.StatisticsResponse;
import org.td2.rattrapageprog3.model.Driver;
import org.td2.rattrapageprog3.model.Trip;
import org.td2.rattrapageprog3.repository.DataRetriever;
import org.td2.rattrapageprog3.service.RevenueService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DataRetriever dataRetriever;
    private final RevenueService revenueService;

    public DriverController(DataRetriever dataRetriever, RevenueService revenueService) {
        this.dataRetriever = dataRetriever;
        this.revenueService = revenueService;
    }

    @GetMapping("/{driverId}/revenue")
    public ResponseEntity<RevenueResponse> revenue(
            @PathVariable String driverId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) throws BadRequestException {

        if (from.isAfter(to)) {
            throw new BadRequestException("La date de début doit être antérieure ou égale à la date de fin");
        }

        Driver driver = dataRetriever.findDriverById(driverId);

        if (driver == null) {
            throw new ResourceNotFoundException("Chauffeur introuvable");
        }

        RevenueResponse r = new RevenueResponse();

        r.driverId = driver.getId();
        r.driverName = driver.getName();
        r.from = from;
        r.to = to;
        r.revenue = revenueService.computeDriverRevenue(driverId, from, to);
        r.cooperativeFee = revenueService.computeCooperativeFee(driverId, from, to);

        return ResponseEntity.ok(r);
    }

    @GetMapping("/{driverId}/statistics")
    public ResponseEntity<StatisticsResponse> statistics(
            @PathVariable String driverId) {

        Driver driver = dataRetriever.findDriverById(driverId);

        if (driver == null) {
            throw new ResourceNotFoundException("Chauffeur introuvable");
        }

        List<Trip> trips = dataRetriever.findTripsByDriver(driverId);

        int completedTrips = 0;
        int totalDistance = 0;

        for (Trip trip : trips) {
            if (trip.isBillable()) {
                completedTrips++;
                totalDistance += trip.getDistanceKm();
            }
        }

        StatisticsResponse r = new StatisticsResponse();

        r.driverId = driver.getId();
        r.driverName = driver.getName();
        r.completedTrips = completedTrips;
        r.totalDistanceKm = totalDistance;
        r.averagePricePerKm = revenueService.computeAveragePricePerKm(driverId);

        return ResponseEntity.ok(r);
    }
}