package fr.takima.cdb.services.computer.exceptions;

/**
 * Exception thrown if a computer resource could not be created
 */
public class ComputerIncoherentDatesException extends RuntimeException {

    private static final String COMPUTER_DATE_INCOHERENT = "Introduction date must be before discontinution date";
    private static final long serialVersionUID = 1L;

    public ComputerIncoherentDatesException() {
    super(COMPUTER_DATE_INCOHERENT);
  }
}