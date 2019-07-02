package fr.takima.cdb.model.computer;

import java.time.ZonedDateTime;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import fr.takima.cdb.model.company.Company;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Computer Object class
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Computer {

  private @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "computer_gen") @SequenceGenerator(name = "computer_gen", sequenceName = "computer_seq", allocationSize = 1) Long cptId;
  @NonNull
  private String name;
  @Basic(optional = true)
  private ZonedDateTime introduced;
  @Basic(optional=true)
  private ZonedDateTime discontinued;
  @ManyToOne
  @JoinColumn(name="company_cpy_id", nullable =true)
  private Company company;

   Computer(String name, Company company){
    this.name=name;
    this.company=company;
  }
   Computer(String name, ZonedDateTime introduced, ZonedDateTime discontinued, Company company) {
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