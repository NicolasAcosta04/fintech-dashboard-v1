package com.nicolasacosta.dashboard.data;

import com.nicolasacosta.dashboard.enums.FearAndGreedLevels;

public record FearAndGreedData(
        int value,
        FearAndGreedLevels level
) {
}
