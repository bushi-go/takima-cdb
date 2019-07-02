package fr.takima.cdb.model.company;

import java.util.List;

// Lib : Spring
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * JPA Repository for company entity
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query(nativeQuery = true, value ="Select case when cpt.company_cpy_id is not null then cpy.name else 'Unknown' end as company_name, count(cpt.cpt_id) as decompte from company cpy right join computer cpt on cpy.cpy_id = cpt.company_cpy_id group by company_name")
    List<Object[]> getComputersByCompanyStat();
}