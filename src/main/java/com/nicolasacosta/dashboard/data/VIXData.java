package com.nicolasacosta.dashboard.data;

import com.nicolasacosta.dashboard.enums.Status;

public record VIXData(
        String symbol,
        String name,
        double value,
        double change,
        double changePercent,
        long timestamp,
        Status status,
        int tickDirection
) {
}
