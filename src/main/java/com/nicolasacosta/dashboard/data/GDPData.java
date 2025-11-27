package com.nicolasacosta.dashboard.data;

import com.nicolasacosta.dashboard.enums.Countries;

public record GDPData(
        Countries country,
        String quarter,
        int value,
        double growthRate
) {
}
