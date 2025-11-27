package com.nicolasacosta.dashboard.service;

import com.nicolasacosta.dashboard.data.VIXData;
import com.nicolasacosta.dashboard.enums.Status;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Component
public class VolatilityService {

    private final double startingVIXVal = 16.37;
    private volatile double currentVixVal = startingVIXVal;

    public VIXData generateTick() {
        double newVixVal = getNewVix();
        double change = newVixVal - currentVixVal;
        double changePercent = (change / currentVixVal) * 100;

        currentVixVal = newVixVal; // Update current VIX value for next tick

        return new VIXData(
                "VIX",
                "CBOE Volatility Index",
                Math.round(newVixVal * 100.0) / 100.0, // Round to 2 decimal places
                Math.round(change * 100.0) / 100.0,
                Math.round(changePercent * 100.0) / 100.0,
                System.currentTimeMillis(),
                Status.OPEN,
                change > 0 ? 1 : (change < 0 ? -1 : 0)
        );
    }

    /**
     * Calculates the new VIX value using the Cox-Ingersoll-Ross (CIR) model with the following equation:
     * dVIX = kappa * (theta - VIX) * dt + sigma * sqrt(VIX) * dW
     */
    private double getNewVix() {
        Random random = new Random();
        double dt = 1.0 / (365 * 24 * 60); // 1 second interval in a year
        double dW = random.nextGaussian() * Math.sqrt(dt); // Wiener process increment
        double theta = 21.00; // long term mean of the VIX
        double sigma = 5.0; // volatility of the VIX (random value for simulation)
        double kappa = 3.0; // speed of mean reversion (typical values are between 1.0 and 5.0, the volatility of stocks usually dissipates over a short time period of months, this is random for simulation)
        double dVIX = kappa * (theta - currentVixVal) * dt + sigma * Math.sqrt(currentVixVal) * dW;
        double newVix = currentVixVal + dVIX;
        return Math.max(newVix, 0.0); // VIX cannot be negative
    }
}
