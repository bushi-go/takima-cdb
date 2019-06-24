package fr.takima.cdb.services.company;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.takima.cdb.model.company.Company;

/**
 * Interface of company service bean
 */
public interface CompanyService {
        /**
     * Get the full, exhaustive list of all Companys
     * @return
     */
    List<Company> getAllCompanys();

    /**
     * Get a subset, optionnaly sorted, of all Companys
     * @return
     */
    Page<Company> getCompanys(Pageable pageRequest);

    /**
     * Fetch a Company by its id
     * @param id
     * @return
     */
    Company getCompanyById(Long cptId);

    /**
     * Create a new Company
     * @param Company
     * @return
     */
    Company createCompany(Company Company);
    /**
     * Fully replace a Company by a new version
     * @param Company
     * @return
     */
    Company replaceCompany(Company Company, Long cpyId);
    /**
     * Update a Company
     * @param Company
     * @return
     */
    Company updateCompany(Company Company, Long cpyId);

    /**
     * Delete a Company by its id
     * @param cpyId
     */
    void deleteCompany(Long cpyId);
}