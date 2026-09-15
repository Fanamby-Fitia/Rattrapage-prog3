package org.td2.rattrapageprog3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
                @RequestParam LocalDate to) {
            if (from == null || to == null || from.isAfter(to))
                return ResponseEntity.badRequest().build();
            Driver driver = revenueService.findTopEarningDriver(from, to);
            if (driver == null)
                return ResponseEntity.notFound().build();
            TopEarningResponse r = new TopEarningResponse();
            r.driverId = driver.getId();
            r.driverName = driver.getName();
            r.revenue = revenueService.computeDriverRevenue(driver.getId(), from, to);
            return ResponseEntity.ok(r);
        }

    }
