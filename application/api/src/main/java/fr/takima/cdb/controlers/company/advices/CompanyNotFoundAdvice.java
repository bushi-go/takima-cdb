package fr.takima.cdb.controlers.company.advices;

// Lib : Spring
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
// Components
import fr.takima.cdb.services.company.exceptions.CompanyNotFoundException;

@ControllerAdvice
public class CompanyNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(CompanyNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(CompanyNotFoundException ex) {
    return ex.getMessage();
  }
}