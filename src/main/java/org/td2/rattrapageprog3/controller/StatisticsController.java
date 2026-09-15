package org.td2.rattrapageprog3.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.td2.rattrapageprog3.dto.TopEarningResponse;
import org.td2.rattrapageprog3.model.Driver;
import org.td2.rattrapageprog3.service.RevenueService;

import java.time.LocalDate;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final RevenueService revenueService;

    public StatisticsController(RevenueService revenueService) {
        this.revenueService = revenueService;
    }

    @GetMapping("/top-earning-driver")
    public ResponseEntity<TopEarningResponse> topEarningDriver(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) throws BadRequestException {

        if (from.isAfter(to)) {
            throw new BadRequestException(
                    "La date de début doit être antérieure ou égale à la date de fin"
            );
        }

        Driver driver = revenueService.findTopEarningDriver(from, to);

        if (driver == null) {
            throw new ResourceNotFoundException(
                    "Aucun chauffeur trouvé pour cette période"
            );
        }

        TopEarningResponse r = new TopEarningResponse();

        r.driverId = driver.getId();
        r.driverName = driver.getName();
        r.revenue = revenueService.computeDriverRevenue(
                driver.getId(),
                from,
                to
        );

        return ResponseEntity.ok(r);
    }
}