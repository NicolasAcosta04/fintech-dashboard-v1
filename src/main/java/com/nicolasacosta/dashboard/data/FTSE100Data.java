package com.nicolasacosta.dashboard.data;

import com.nicolasacosta.dashboard.enums.Status;

public record FTSE100Data(
        String symbol,
        String name,
        double price,
        double change,
        double changePercent,
//        long volume,
//        double bid,
//        double ask,
        long timestamp,
        Status status,
//        double dayHigh,
//        double dayLow,
//        double previousClose,
        int tickDirection) {
}