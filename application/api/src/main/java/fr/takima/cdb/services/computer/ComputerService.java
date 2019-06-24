package fr.takima.cdb.services.computer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.takima.cdb.model.computer.Computer;

/**
 * Interface of computer service bean
 */
public interface ComputerService {
 
    /**
     * Get the full, exhaustive list of all computers
     * @return
     */
    List<Computer> getAllComputers();

    /**
     * Get a subset, optionnaly sorted, of all computers
     * @return
     */
    Page<Computer> getComputers(Pageable pageRequest);
    
    /**
     * Fetch a computer by its id
     * @param id
     * @return
     */
    Computer getComputerById(Long cptId);

    /**
     * Create a new computer
     * @param computer
     * @return
     */
    Computer createComputer(Computer computer);
    /**
     * Fully replace a computer by a new version
     * @param computer
     * @return
     */
    Computer replaceComputer(Computer computer, Long cptId);
    /**
     * Update a computer
     * @param computer
     * @return
     */
    Computer updateComputer(Computer computer, Long cptId);

    /**
     * Delete a computer by its id
     * @param cptId
     */
    void deleteComputer(Long cptId);
    
}