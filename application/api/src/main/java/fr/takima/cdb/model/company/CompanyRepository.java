package fr.takima.cdb.model.company;

// Lib : Spring
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for company entity
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {}