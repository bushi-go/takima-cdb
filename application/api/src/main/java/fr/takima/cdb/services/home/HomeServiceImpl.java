package fr.takima.cdb.services.home;

import java.util.List;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.takima.cdb.model.company.CompanyRepository;
import fr.takima.cdb.model.computer.ComputerRepository;
import fr.takima.cdb.model.home.HomeStats;

@Service
public class HomeServiceImpl implements HomeService {

    private ComputerRepository computerRepo;
    private CompanyRepository companyRepo;
    
    HomeServiceImpl(ComputerRepository computerRepo,CompanyRepository companyRepo){
        this.computerRepo=computerRepo;
        this.companyRepo = companyRepo;
    }

    @Override
    public HomeStats getHomeStats() {
        int computersCount = Long.valueOf(computerRepo.count()).intValue();
        int companyCount = Long.valueOf(companyRepo.count()).intValue();
        List<Object[]> result =companyRepo.getComputersByCompanyStat();
        
        Map<String,BigInteger> computerByCompanyCount = new HashMap<>();
        if(Optional.ofNullable(result).isPresent() && !result.isEmpty()){
            result.forEach(objArray -> {
                computerByCompanyCount.put((String)objArray[0], (BigInteger)objArray[1]);
            });
        }
        return new HomeStats(computersCount, companyCount, computerByCompanyCount);
    }

}