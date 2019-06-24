package fr.takima.cdb.services.computer.exceptions;

/**
 * Exception thrown if a computer resource could not be created
 */
public class ComputerNotCreatedException extends RuntimeException {

  private static final String COULD_NOT_CREATE_COMPUTER = "Could not create computer";
  private static final long serialVersionUID = 1L;

  public ComputerNotCreatedException() {
    super(COULD_NOT_CREATE_COMPUTER);
  }
}