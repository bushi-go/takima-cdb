package fr.takima.cdb.model.company;

// Lib : Spring
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

// Components
import fr.takima.cdb.controlers.company.CompanyControler;


/**
 * Mapper to transform Computer data object into Computer web resource
 */
@Component
public class CompanyModelAssembler implements RepresentationModelAssembler<Company, EntityModel<Company>>{
    private static int DEFAULT_PAGE_INDEX=0;
    private static int DEFAULT_PAGE_SIZE=50;
    private static Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;
    private static String DEFAULT_SORT_BY="name";
    @Override
    public EntityModel<Company> toModel(Company company) {
        return new EntityModel<>(company,
            linkTo(methodOn(CompanyControler.class).getCompanyById(company.getCpyId())).withSelfRel()
                .andAffordance(afford(methodOn(CompanyControler.class).updateCompany(null, company.getCpyId())))
                .andAffordance(afford(methodOn(CompanyControler.class).replaceCompany(null, company.getCpyId())))
                .andAffordance(afford(methodOn(CompanyControler.class).deleteCompany(company.getCpyId()))),
            linkTo(methodOn(CompanyControler.class).getCompanies(DEFAULT_PAGE_INDEX,DEFAULT_PAGE_SIZE, DEFAULT_SORT_DIRECTION,DEFAULT_SORT_BY)).withRel("companies")
            );
    }

}