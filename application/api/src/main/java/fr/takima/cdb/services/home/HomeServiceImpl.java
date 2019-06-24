package fr.takima.cdb.services.home;

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
        return new HomeStats(computersCount, companyCount);
    }

}