package fr.takima.cdb.services.computer.exceptions;

/**
 * Exception thrown if a computer resource could not be found
 */
public class ComputerNotFoundException extends RuntimeException {
    
    private static final String COULD_NOT_FIND_COMPUTER_WITH_ID = "Could not find computer with id : ";
    private static final long serialVersionUID = 1L;

    public ComputerNotFoundException(Long id) {
        super(COULD_NOT_FIND_COMPUTER_WITH_ID + id);
    }
}