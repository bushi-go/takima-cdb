package fr.takima.cdb.model.company;

// Lib : Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;

import java.util.ArrayList;
import java.util.List;

// Lib : Java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.takima.cdb.model.computer.Computer;

/**
 * Company object class
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@JsonIgnoreProperties(value = { "associatedComputers" })
public class Company {


  private @Id 
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator= "company_gen") 
  @SequenceGenerator(name="company_gen", sequenceName = "company_seq", allocationSize=1)
  Long cpyId;
  @NonNull
  private String name;

  @OneToMany(mappedBy="company")
  private List<Computer> associatedComputers;

  public Company copy(Company company){
    this.setName(company.getName());
    return this;
  }
}