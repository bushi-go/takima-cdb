package fr.takima.cdb.controlers.company;

// Lib : java
import java.util.Optional;
import java.util.Collections;

// Lib : Spring
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.QueryParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Components
import fr.takima.cdb.model.company.Company;
import fr.takima.cdb.model.company.CompanyModelAssembler;
import fr.takima.cdb.services.company.exceptions.CompanyNotFoundException;
import fr.takima.cdb.services.company.CompanyService;

/**
 * Controller rest for Company API
 */
@RestController
@RequestMapping("/api")
public class CompanyControler {
    private static Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;
    private static String DEFAULT_SORT_BY = "name";
    // DTO to HATEOAS Ressource assembler
    private final CompanyModelAssembler companyModelAssembler;

    // Company Service Bean
    private final CompanyService companyService;

    CompanyControler(CompanyModelAssembler companyModelAssembler, CompanyService companyService) {
        this.companyModelAssembler = companyModelAssembler;
        this.companyService = companyService;
    }
    @GetMapping(value="companies")
    public ResponseEntity<CollectionModel<EntityModel<Company>>> getCompanies() {

        Link link = linkTo(methodOn(CompanyControler.class).getCompanies()).withSelfRel().andAffordance(
                "createCompany", HttpMethod.POST, ResolvableType.forClass(Company.class),
                Collections.<QueryParameter>emptyList(),
                ResolvableType.forClassWithGenerics(ResponseEntity.class,
                        ResolvableType.forClassWithGenerics(EntityModel.class, Company.class)));

            return ResponseEntity.ok(companyModelAssembler.toCollectionModel(companyService.getAllCompanies())
            .add(link));
        }
    /**
     * Return a pageable list of companies   
     * @param int pageIndex
     * @param int pageSize
     * @param Direction direction     *
     *  @return ResponseEntity with ok status and Resource 
     *         consisting in a collection of company resources 
     *         and aggregate root
     */
    @GetMapping(value="companies", params={"pageIndex", "pageSize", "direction"})
    public ResponseEntity<CollectionModel<EntityModel<Company>>> getCompanies(@RequestParam int pageIndex,@RequestParam int pageSize, @RequestParam(required = false) Sort.Direction direction, @RequestParam(required = false) String sortBy ) {
        Pageable pageRequestOption;
        if(Optional.ofNullable(direction).isPresent() && Optional.ofNullable(sortBy).isPresent()){
                        pageRequestOption=PageRequest.of(pageIndex, pageSize, direction, sortBy);
        }else{
                pageRequestOption=PageRequest.of(pageIndex, pageSize,DEFAULT_SORT_DIRECTION,DEFAULT_SORT_BY);
        }

        Link link = linkTo(methodOn(CompanyControler.class)
                .getCompanies(pageRequestOption.getPageNumber(),pageRequestOption.getPageSize(), DEFAULT_SORT_DIRECTION,DEFAULT_SORT_BY))
                .withSelfRel()
                .andAffordance("createCompany", HttpMethod.POST, ResolvableType.forClass(Company.class),
                Collections.<QueryParameter>emptyList(),
                ResolvableType.forClassWithGenerics(ResponseEntity.class,
                        ResolvableType.forClassWithGenerics(EntityModel.class, Company.class)));
        Page<Company> page = companyService.getCompanies(pageRequestOption);
        // TODO : Pass total count and other pagination metadata through a paginated CollectionModel subclass
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-TOTAL-COUNT", String.valueOf(page.getTotalElements()));
        return new ResponseEntity<CollectionModel<EntityModel<Company>>> (companyModelAssembler.toCollectionModel(page)
        .add(link), headers, HttpStatus.OK);

        }

    /**
     * Get specific computer resource by it's id
     * @param cpyId
     * @return Response entity with ok status
     */
    @GetMapping("/companies/{cpyId}")
    public ResponseEntity<EntityModel<Company>> getCompanyById(@PathVariable Long cpyId) {
            EntityModel<Company> company = companyModelAssembler
                    .toModel(companyService.getCompanyById(cpyId));
            return ResponseEntity.ok(company);
    }

    /**
     * Replace a company with the provided company or create a new one with the given id if company not found
     * @param newCompany
     * @param cpyId
     * @return Response Entity with ok status and replacing company resource
     */
    @PutMapping("/companies/{cpyId}")
    public ResponseEntity<EntityModel<Company>> replaceCompany(@RequestBody Company newCompany,
            @PathVariable Long cpyId) {
            EntityModel<Company> companyReplaced = companyModelAssembler
                    .toModel(companyService.replaceCompany(newCompany,cpyId));
            return ResponseEntity.ok(companyReplaced);
    }

    /**
     * Update a company with the data of the provided company, and throw an exception if no company is found at this id
     * @param newCompany
     * @param cpyId
     * @return ResponseEntity with ok status and updated company resource
     */
    @PatchMapping("/companies/{cpyId}")
    public ResponseEntity<EntityModel<Company>> updateCompany(@RequestBody Company newCompany,
            @PathVariable Long cpyId) {
            EntityModel<Company> companyReplaced = companyModelAssembler
                    .toModel(companyService.replaceCompany(newCompany,cpyId));
            return ResponseEntity.ok(companyReplaced);
    }

    /**
     * Create a whole new company resource and returns it
     * @param newCompany
     * @return Response entity with created status and new company resource
     */
    @PostMapping("/companies")
    public ResponseEntity<EntityModel<Company>> createCompany(@RequestBody Company newCompany) {
            EntityModel<Company> savedCompanyModel = companyModelAssembler.toModel(companyService.createCompany(newCompany));
            return ResponseEntity.created(savedCompanyModel.getLink(IanaLinkRelations.SELF).orElseThrow(()-> new CompanyNotFoundException(savedCompanyModel.getContent().getCpyId())).toUri()).body(savedCompanyModel);

    }

    /**
     * Delete company resource at the given id
     * @param cpyId
     * @return Response Entity with ok status
     */
    @DeleteMapping("/companies/{cpyId}")
    public ResponseEntity<?>  deleteCompany(@PathVariable Long cpyId){
        companyService.deleteCompany(cpyId);
        return ResponseEntity.ok().build();
    }
}