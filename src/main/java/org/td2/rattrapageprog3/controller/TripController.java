package org.td2.rattrapageprog3.controller;

import org.apache.coyote.BadRequestException;
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
            @RequestParam(required = false) LocalDate to) throws BadRequestException {

        List<Trip> trips;

        if (driverId != null) {

            Driver driver = dataRetriever.findDriverById(driverId);

            if (driver == null) {
                throw new ResourceNotFoundException("Chauffeur introuvable");
            }

            trips = dataRetriever.findTripsByDriver(driverId);

        } else {
            trips = dataRetriever.findAllTrips();
        }

        if (from != null || to != null) {

            if (from == null || to == null) {
                throw new BadRequestException(
                        "Les paramètres from et to doivent être fournis ensemble"
                );
            }

            if (from.isAfter(to)) {
                throw new BadRequestException(
                        "La date de début doit être antérieure ou égale à la date de fin"
                );
            }

            trips = trips.stream()
                    .filter(t ->
                            !t.getTripDate().isBefore(from)
                                    && !t.getTripDate().isAfter(to)
                    )
                    .toList();
        }

        List<TripResponse> response = trips.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripResponse> saveTrip(
            @PathVariable String tripId,
            @RequestBody TripRequest request) throws BadRequestException {

        if (request == null) {
            throw new BadRequestException("Le corps de la requête est obligatoire");
        }

        if (request.driverId == null
                || request.vehicleId == null
                || request.tripDate == null
                || request.departureCity == null
                || request.arrivalCity == null
                || request.distanceKm <= 0
                || request.billedAmount < 0) {

            throw new BadRequestException("Données du trajet invalides");
        }

        Driver driver = dataRetriever.findDriverById(request.driverId);

        if (driver == null) {
            throw new ResourceNotFoundException("Chauffeur introuvable");
        }

        Vehicle vehicle = dataRetriever.findVehicleById(request.vehicleId);

        if (vehicle == null) {
            throw new ResourceNotFoundException("Véhicule introuvable");
        }

        boolean existed = dataRetriever.findTripById(tripId) != null;

        Trip trip = new Trip(
                tripId,
                driver,
                vehicle,
                request.tripDate,
                request.departureCity,
                request.arrivalCity,
                request.distanceKm,
                request.billedAmount,
                TripStatus.COMPLETED
        );

        Trip saved = dataRetriever.saveTrip(trip);

        return ResponseEntity
                .status(existed ? HttpStatus.OK : HttpStatus.CREATED)
                .body(toResponse(saved));
    }

    @PutMapping("/{tripId}/status")
    public ResponseEntity<TripResponse> updateStatus(
            @PathVariable String tripId,
            @RequestBody StatusRequest request) throws BadRequestException {

        if (request == null || request.status == null) {
            throw new BadRequestException(
                    "Le statut est obligatoire"
            );
        }

        Trip trip = dataRetriever.findTripById(tripId);

        if (trip == null) {
            throw new ResourceNotFoundException(
                    "Trajet introuvable"
            );
        }

        Trip updated = dataRetriever.updateTripStatus(
                tripId,
                request.status
        );

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