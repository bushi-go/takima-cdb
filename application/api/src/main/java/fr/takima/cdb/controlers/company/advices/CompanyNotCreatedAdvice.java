package fr.takima.cdb.controlers.company.advices;

// Lib : Spring
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// Components
import fr.takima.cdb.services.company.exceptions.CompanyNotCreatedException;

@ControllerAdvice
public class CompanyNotCreatedAdvice {

  @ResponseBody
  @ExceptionHandler(CompanyNotCreatedException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotCreatedHandler(CompanyNotCreatedException ex) {
    return ex.getMessage();
  }
}