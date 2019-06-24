package fr.takima.cdb.model.company;

// Lib : Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

// Lib : Java
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Company object class
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Company {

  private @Id @GeneratedValue Long cpyId;
  private String name;

  public Company(String name) {
    this.name = name;
  }

  public Company copy(Company company){
    this.setName(company.getName());
    return this;
  }
}