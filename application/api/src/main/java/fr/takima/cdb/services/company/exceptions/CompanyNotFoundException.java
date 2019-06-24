package fr.takima.cdb.services.company.exceptions;


/**
 * Exception thrown if a computer could not be found
 */
public class CompanyNotFoundException extends RuntimeException {

  private static final String COULD_NOT_FIND_COMPANY_WITH_ID = "Could not find company with id : ";
  private static final long serialVersionUID = 1L;

  public CompanyNotFoundException(Long id) {
    super(COULD_NOT_FIND_COMPANY_WITH_ID + id);
  }
}