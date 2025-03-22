package com.stockmarket.app.controller;

import com.stockmarket.app.service.StockPriceSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that provides endpoints to manually trigger the stock price simulator
 * for testing purposes.
 */
@RestController
@RequestMapping("/api/simulator")
public class SimulatorController {

    private final StockPriceSimulatorService simulatorService;

    @Autowired
    public SimulatorController(StockPriceSimulatorService simulatorService) {
        this.simulatorService = simulatorService;
    }

    /**
     * Manually trigger a stock price simulation run.
     * This is useful for testing the simulator without waiting for the scheduled task.
     * 
     * @return Response indicating that the simulation was triggered
     */
    @PostMapping("/trigger")
    public ResponseEntity<String> triggerSimulation() {
        simulatorService.simulateStockPriceChanges();
        return ResponseEntity.ok("Stock price simulation triggered successfully");
    }
} 