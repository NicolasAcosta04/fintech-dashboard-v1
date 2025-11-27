package com.nicolasacosta.dashboard.service;

import com.nicolasacosta.dashboard.data.GDPData;
import com.nicolasacosta.dashboard.enums.Countries;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@Slf4j
@AllArgsConstructor
public class EconService {

    private static final String[][] ukGdpHistory = {
            {"Q1 2018", "519880"},
            {"Q2 2018", "524731"},
            {"Q3 2018", "532945"},
            {"Q4 2018", "535995"},
            {"Q1 2019", "541021"},
            {"Q2 2019", "551898"},
            {"Q3 2019", "556672"},
            {"Q4 2019", "557545"},
            {"Q1 2020", "550973"},
            {"Q2 2020", "475519"},
            {"Q3 2020", "540164"},
            {"Q4 2020", "543763"},
            {"Q1 2021", "542607"},
            {"Q2 2021", "577800"},
            {"Q3 2021", "587198"},
            {"Q4 2021", "601673"},
            {"Q1 2022", "621064"},
            {"Q2 2022", "619570"},
            {"Q3 2022", "626009"},
            {"Q4 2022", "639616"},
            {"Q1 2023", "647110"},
            {"Q2 2023", "675109"},
            {"Q3 2023", "685204"},
            {"Q4 2023", "677169"},
            {"Q1 2024", "688138"},
            {"Q2 2024", "702893"},
            {"Q3 2024", "712652"},
            {"Q4 2024", "727199"},
            {"Q1 2025", "738159"},
            {"Q2 2025", "754397"}
    };

    public GDPData getLatestUkGdp() {
        int value = Integer.parseInt(ukGdpHistory[ukGdpHistory.length - 1][1]);
        int previousValue = Integer.parseInt(ukGdpHistory[ukGdpHistory.length - 2][1]);
        double growthRate = ((double) (value - previousValue) / previousValue) * 100.0;
        String quarter = ukGdpHistory[ukGdpHistory.length - 1][0];
        return new GDPData(Countries.UK, quarter, value, Math.round(growthRate * 100.0) / 100.0);
    }

    public GDPData getLatestUSGdp() {
        // Placeholder for actual US GDP retrieval logic
        return new GDPData(Countries.USA, "Q2 2025", 26000000, 2.5);
    }

    public double getLatestBOEInterestRate() {
        // Placeholder for actual BOE interest rate retrieval logic
        return 4.00;
    }

    public double getLatestFEDInterestRate() {
        // Placeholder for actual FED interest rate retrieval logic
        return 5.25;
    }
}
