package fr.takima.cdb.services.company;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.takima.cdb.model.company.Company;
import fr.takima.cdb.model.company.CompanyRepository;
import fr.takima.cdb.services.company.exceptions.CompanyNotFoundException;

/**
 * Company Service bean
 */
@Service
class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepo;

    CompanyServiceImpl(CompanyRepository companyRepo){
        this.companyRepo = companyRepo;
    }

    @Override
    public List<Company> getAllCompanys() {
        return companyRepo.findAll();
    }

    @Override
    public Page<Company> getCompanys(Pageable pageRequest) {
        return companyRepo.findAll(pageRequest);
    }

    @Override
    public Company getCompanyById(Long cpyId) {
        return companyRepo.findById(cpyId).orElseThrow(()-> new CompanyNotFoundException(cpyId));
    }

    @Override
    public Company createCompany(Company company){
        return companyRepo.save(company);
    }

    @Override
    public Company replaceCompany(Company newCompany, Long cpyId) {
        return companyRepo.findById(cpyId).map(company -> {
            company.copy(newCompany);
            return companyRepo.save(company);
        }).orElseGet(() -> {
            newCompany.setCpyId(cpyId);
            return companyRepo.save(newCompany);
        });
    }

    @Override
    public Company updateCompany(Company newCompany, Long cpyId) {
        return companyRepo.findById(cpyId).map(company -> {
            company.copy(newCompany);
            return companyRepo.save(company);
        }).orElseThrow(() -> new CompanyNotFoundException(cpyId));
    }

    @Override
    public void deleteCompany(Long cpyId) {
        companyRepo.deleteById(cpyId);
    }
    
}