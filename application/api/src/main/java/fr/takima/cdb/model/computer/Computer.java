package fr.takima.cdb.model.computer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonFormat;

import fr.takima.cdb.model.company.Company;

/**
 * Computer Object class
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Computer {

    private @Id @GeneratedValue Long cptId;
    private String name;
    @Basic(optional=true)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate introduced;
    @Basic(optional=true)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate discontinued;
    @ManyToOne(optional=false) @MapsId("cpy_id")
    private Company company;

   Computer(String name, Company company){
    this.name=name;
    this.company=company;
  }
   Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.company = company;
  }

public Computer copy(Computer computer) {
  this.setName(computer.getName());
  this.setCompany(computer.getCompany());
  this.setIntroduced(computer.getIntroduced());
  this.setDiscontinued(computer.getDiscontinued());
  return this;
}

}