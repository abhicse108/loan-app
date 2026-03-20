package service;

import domain.enums.RiskBand;
import org.springframework.stereotype.Service;

@Service
public class RiskService {

    public RiskBand determineRisk(int creditScore) {
        if (creditScore >= 750) return RiskBand.LOW;
        if (creditScore >= 650) return RiskBand.MEDIUM;
        return RiskBand.HIGH;
    }
}