package fr.takima.cdb.model.home;

import java.math.BigInteger;
import java.util.Map;

import lombok.Value;

@Value
public class HomeStats{
    private int computersCount;
    private int companyCount;
    private Map<String,BigInteger> computerCountByCompany;
}