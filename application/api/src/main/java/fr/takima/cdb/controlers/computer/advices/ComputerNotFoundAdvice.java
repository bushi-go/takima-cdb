package fr.takima.cdb.controlers.computer.advices;

// Lib : Spring
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
// Components
import fr.takima.cdb.services.computer.exceptions.ComputerNotFoundException;

@ControllerAdvice
class ComputerNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(ComputerNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(ComputerNotFoundException ex) {
    return ex.getMessage();
  }
}