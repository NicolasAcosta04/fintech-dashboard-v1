package com.nicolasacosta.dashboard.service;

import com.nicolasacosta.dashboard.data.FTSE100Data;
import com.nicolasacosta.dashboard.enums.Status;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Random;

@Service
@Component
public class FTSE100Service {

    private volatile double currentPrice = 9491.25; // closing price on Friday 3rd October 2025
    private volatile double averagePrice = 9000.00;

    public FTSE100Data generateTick() {
        var volatility = getCurrentVolatility();
        var newPrice = getNewPrice(volatility);
        var change = newPrice - currentPrice;
        var changePercent = (change / currentPrice) * 100.0;
        var tickDirection = change > 0 ? 1 : (change < 0 ? -1 : 0);

        // Update current price for next tick
        currentPrice = newPrice;

        return new FTSE100Data(
                "UKX",
                "FTSE 100",
                Math.round(newPrice * 100.0) / 100.0, // Round to 2 decimal places
                Math.round(change * 100.0) / 100.0,
                Math.round(changePercent * 100.0) / 100.0,
                System.currentTimeMillis(),
                Status.OPEN,
                tickDirection
        );
    }

    private double getCurrentVolatility() {
        LocalTime now = LocalTime.now();

        // Higher volatility at market open/close
        if (now.isAfter(LocalTime.of(8, 0)) && now.isBefore(LocalTime.of(9, 30))) {
            return 0.025; // 2.5% daily volatility
        } else if (now.isAfter(LocalTime.of(15, 30))) {
            return 0.020; // 2% daily volatility
        } else {
            return 0.015; // 1.5% daily volatility
        }
    }

    private double getNewPrice(double volatility) {
//        var changePercent = (random.nextDouble() * 2.0 - 1.0) * 0.001; // -1% to +1%
//        return currentPrice * (1 + changePercent);
        Random random = new Random();
//        double dt = 1.0 / (365 * 24 * 60 * 60); // 1 second interval in a year
//        double drift = -0.0001 * (currentPrice - 9000); // Mean reversion to 9000
//
//        return (((drift * dt) + (volatility * Math.sqrt(dt))) * random.nextGaussian()) * currentPrice;
        double dt = 1.0 / (252 * 24);; // 1 second interval in a year
//        double mu = -0.0001 * (currentPrice - averagePrice); // Mean reversion drift
        double mu = 0.0001 * Math.sin(System.currentTimeMillis() / 60000.0); // Variable drift based on time
        double Z = random.nextGaussian();
        return currentPrice * Math.exp((mu - 0.5 * volatility * volatility) * dt + volatility * Math.sqrt(dt) * Z);
    }
}
