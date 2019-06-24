package fr.takima.cdb.services.company.exceptions;

/**
 * Exception thrown in case a Computer resource could not be created
 */
public class CompanyNotCreatedException extends RuntimeException {

  private static final String COULD_NOT_CREATE_COMPANY = "Could not create company";
  private static final long serialVersionUID = 1L;

  public CompanyNotCreatedException() {
    super(COULD_NOT_CREATE_COMPANY);
  }
}