package fr.takima.cdb.services.computer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.takima.cdb.model.computer.Computer;
import fr.takima.cdb.model.computer.ComputerRepository;
import fr.takima.cdb.services.computer.exceptions.ComputerIncoherentDatesException;
import fr.takima.cdb.services.computer.exceptions.ComputerNotFoundException;
import fr.takima.cdb.services.computer.specifications.ComputerDateSpecification;

/**
 * Computer Service bean
 */
@Service
class ComputerServiceImpl implements ComputerService {

    private ComputerRepository computerRepo;
    ComputerServiceImpl(ComputerRepository computerRepo){
        this.computerRepo = computerRepo;
    }
    @Override
    public List<Computer> getAllComputers() {
        return computerRepo.findAll();
    }

    @Override
    public Page<Computer> getComputers(Pageable pageRequest) {
        return computerRepo.findAll(pageRequest);
    }

    @Override
    public Computer getComputerById(Long cptId) {
        return computerRepo.findById(cptId).orElseThrow(()-> new ComputerNotFoundException(cptId));
    }

    @Override
    public Computer createComputer(Computer computer){
        if(!ComputerDateSpecification.getComputerDateSpecification().isSatisfiedBy(computer)){
            throw new ComputerIncoherentDatesException();
        }
        return computerRepo.save(computer);
    }

    @Override
    public Computer replaceComputer(Computer newComputer, Long cptId) {
        
        if(!ComputerDateSpecification.getComputerDateSpecification().isSatisfiedBy(newComputer)){
            throw new ComputerIncoherentDatesException();
        }
        return computerRepo.findById(cptId).map(computer -> {
            computer.copy(newComputer);
            return computerRepo.save(computer);
        }).orElseGet(() -> {
            newComputer.setCptId(cptId);
            return computerRepo.save(newComputer);
        });
    }

    @Override
    public Computer updateComputer(Computer newComputer, Long cptId) {
        if(!ComputerDateSpecification.getComputerDateSpecification().isSatisfiedBy(newComputer)){
            throw new ComputerIncoherentDatesException();
        }
        return computerRepo.findById(cptId).map(computer -> {
            computer.copy(newComputer);
            return computerRepo.save(computer);
        }).orElseThrow(() -> new ComputerNotFoundException(cptId));
    }

    @Override
    public void deleteComputer(Long cptId) {
        computerRepo.deleteById(cptId);
    }
    
}