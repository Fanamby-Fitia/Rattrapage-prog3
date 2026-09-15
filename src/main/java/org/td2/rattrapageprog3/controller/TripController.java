package org.td2.rattrapageprog3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.td2.rattrapageprog3.dto.*;
import org.td2.rattrapageprog3.model.Driver;
import org.td2.rattrapageprog3.model.Trip;
import org.td2.rattrapageprog3.model.TripStatus;
import org.td2.rattrapageprog3.model.Vehicle;
import org.td2.rattrapageprog3.repository.DataRetriever;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {
    private final DataRetriever dataRetriever;

    public TripController(DataRetriever dataRetriever) {
        this.dataRetriever = dataRetriever;
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> findTrips(
            @RequestParam(required = false) String driverId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        List<Trip> trips;
        if (driverId != null) {
            if (dataRetriever.findDriverById(driverId) == null)
                return ResponseEntity.notFound().build();
            trips = dataRetriever.findTripsByDriver(driverId);
        } else {
            trips = dataRetriever.findAllTrips();
        }
        if (from != null || to != null) {
            if (from == null || to == null || from.isAfter(to))
                return ResponseEntity.badRequest().build();
            trips = trips.stream()
                    .filter(t -> !t.getTripDate().isBefore(from)
                            && !t.getTripDate().isAfter(to))
                    .toList();
        }
        List<TripResponse> response = trips.stream().map(this::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripResponse> saveTrip(
            @PathVariable String tripId,
            @RequestBody TripRequest request) {
        if (request.driverId == null || request.vehicleId == null
                || request.tripDate == null || request.departureCity == null
                || request.arrivalCity == null || request.distanceKm <= 0
                || request.billedAmount < 0) {
            return ResponseEntity.badRequest().build();
        }
        Driver driver = dataRetriever.findDriverById(request.driverId);
        Vehicle vehicle = dataRetriever.findVehicleById(request.vehicleId);
        if (driver == null || vehicle == null)
            return ResponseEntity.notFound().build();
        boolean existed = dataRetriever.findTripById(tripId) != null;
        Trip trip = new Trip(
                tripId, driver, vehicle, request.tripDate,
                request.departureCity, request.arrivalCity,
                request.distanceKm, request.billedAmount,
                TripStatus.COMPLETED
        );
        Trip saved = dataRetriever.saveTrip(trip);
        return ResponseEntity.status(existed ? HttpStatus.OK : HttpStatus.CREATED)
                .body(toResponse(saved));
    }

    @PutMapping("/{tripId}/status")
    public ResponseEntity<TripResponse> updateStatus(
            @PathVariable String tripId,
            @RequestBody StatusRequest request) {
        if (request == null || request.status == null)
            return ResponseEntity.badRequest().build();
        if (dataRetriever.findTripById(tripId) == null)
            return ResponseEntity.notFound().build();
        Trip updated = dataRetriever.updateTripStatus(tripId, request.status);
        return ResponseEntity.ok(toResponse(updated));
    }

    private TripResponse toResponse(Trip trip) {
        TripResponse r = new TripResponse();
        r.id = trip.getId();
        r.driver = new DriverSummary();
        r.driver.id = trip.getDriver().getId();
        r.driver.name = trip.getDriver().getName();
        r.vehicle = new VehicleSummary();
        r.vehicle.id = trip.getVehicle().getId();
        r.vehicle.plateNumber = trip.getVehicle().getPlateNumber();
        r.tripDate = trip.getTripDate();
        r.departureCity = trip.getDepartureCity();
        r.arrivalCity = trip.getArrivalCity();
        r.distanceKm = trip.getDistanceKm();
        r.billedAmount = trip.getBilledAmount();
        r.status = trip.getStatus();
        return r;
    }
}