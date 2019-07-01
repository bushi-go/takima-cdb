package fr.takima.cdb.controlers.computer;

// Lib : Java
import java.util.Optional;
import java.util.Collections;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Components
import fr.takima.cdb.model.computer.Computer;
import fr.takima.cdb.model.computer.ComputerModelAssembler;
import fr.takima.cdb.services.computer.exceptions.ComputerNotFoundException;
import fr.takima.cdb.services.computer.ComputerService;


@RestController
public class ComputerControler {
        private static Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;
        private static String DEFAULT_SORT_BY = "name";
    // DTO to HATEOAS Ressource assembler
    private final ComputerModelAssembler computerModelAssembler;

    private final ComputerService computerService;
    ComputerControler(ComputerModelAssembler computerModelAssembler, ComputerService computerService) {
        this.computerModelAssembler = computerModelAssembler;
        this.computerService = computerService;
    }

    @GetMapping(value="/computers")
    public ResponseEntity<CollectionModel<EntityModel<Computer>>> getComputers() {

        Link link = linkTo(methodOn(ComputerControler.class).getComputers()).withSelfRel().andAffordance(
                "createComputer", HttpMethod.POST, ResolvableType.forClass(Computer.class),
                Collections.<QueryParameter>emptyList(),
                ResolvableType.forClassWithGenerics(ResponseEntity.class,
                        ResolvableType.forClassWithGenerics(EntityModel.class, Computer.class)));

        List<Computer> list = computerService.getAllComputers();    
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-TOTAL-COUNT", String.valueOf(list.size()));

        return new ResponseEntity<CollectionModel<EntityModel<Computer>>>(computerModelAssembler.toCollectionModel(list)
        .add(link), headers, HttpStatus.OK);
        }

    /**
     * Return a pageable list of computers resource
     * @param int pageIndex
     * @param int pageSize
     * @param Direction direction
     * @return ResponseEntity with ok status and Resource 
     *         consisting in a collection of computer resources 
     *         and aggregate root
     */
    @GetMapping(value="/computers", params={"pageIndex", "pageSize", "direction"})
    public ResponseEntity<CollectionModel<EntityModel<Computer>>> getComputers(@RequestParam int pageIndex,@RequestParam int pageSize, @RequestParam(required = false) Sort.Direction direction, @RequestParam(required = false) String sortBy ) {
        Pageable pageRequestOption;
        if(Optional.ofNullable(direction).isPresent()){
                        pageRequestOption=PageRequest.of(pageIndex, pageSize, direction, sortBy);
        }else{
                pageRequestOption=PageRequest.of(pageIndex, pageSize, Sort.unsorted() );
        }

        Link link = linkTo(methodOn(ComputerControler.class).getComputers(pageRequestOption.getPageNumber(),pageRequestOption.getPageSize(), DEFAULT_SORT_DIRECTION, DEFAULT_SORT_BY)).withSelfRel().andAffordance(
                "createComputer", HttpMethod.POST, ResolvableType.forClass(Computer.class),
                Collections.<QueryParameter>emptyList(),
                ResolvableType.forClassWithGenerics(ResponseEntity.class,
                        ResolvableType.forClassWithGenerics(EntityModel.class, Computer.class)));

        Page<Computer> page = computerService.getComputers(pageRequestOption);    
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-TOTAL-COUNT", String.valueOf(page.getTotalElements()));

        return new ResponseEntity<CollectionModel<EntityModel<Computer>>>(computerModelAssembler.toCollectionModel(page)
        .add(link), headers, HttpStatus.OK);
        }
    /**
     * Get specific computer resource by it's id
     * @param cptId
     * @return Response entity with ok status and requested computer resource
     */
    @GetMapping("/computers/{cptId}")
    public ResponseEntity<EntityModel<Computer>> getComputerById(@PathVariable Long cptId) {
            EntityModel<Computer> computer = computerModelAssembler
                    .toModel(computerService.getComputerById(cptId));
            return ResponseEntity.ok(computer);
    }
    /**
     * Replace a computer with the provided computer or create a new one with the given id if computer not found
     * @param newComputer
     * @param cptId
     * @return Response Entity with ok status and replacing computer resource
     */
    @PutMapping("/computers/{cptId}")
    public ResponseEntity<EntityModel<Computer>> replaceComputer(@RequestBody Computer newComputer,
            @PathVariable Long cptId) {
            EntityModel<Computer> computerReplaced = computerModelAssembler
                    .toModel(computerService.replaceComputer(newComputer,cptId));
            return ResponseEntity.ok(computerReplaced);
    }

    /**
     * Update a computer with the data of the provided computer, and throw an exception if no computer is found at this id
     * @param newComputer
     * @param cptId
     * @return ResponseEntity with ok status and updated computer resource
     */
    @PatchMapping("/computers/{cptId}")
    public ResponseEntity<EntityModel<Computer>> updateComputer(@RequestBody Computer newComputer,
            @PathVariable Long cptId) {
            EntityModel<Computer> computerReplaced = computerModelAssembler
                    .toModel(computerService.replaceComputer(newComputer,cptId));
            return ResponseEntity.ok(computerReplaced);
    }
    /**
     * Create a whole new computer resource and returns it
     * @param newComputer
     * @return Response entity with created status and new computer resource
     */
    @PostMapping("/computers")
    public ResponseEntity<EntityModel<Computer>> createComputer(@RequestBody Computer newComputer) {
            EntityModel<Computer> savedComputerModel = computerModelAssembler.toModel(computerService.createComputer(newComputer));
            return ResponseEntity.created(savedComputerModel.getLink(IanaLinkRelations.SELF)
                    .orElseThrow(() -> new ComputerNotFoundException(savedComputerModel.getContent().getCptId())).toUri()).body(savedComputerModel);
    }

     /**
     * Delete computer resource at the given id
     * @param cptId
     * @return Response Entity with ok status
     */
    @DeleteMapping("/computers/{cptId}")
    public ResponseEntity<?>  deleteComputer(@PathVariable Long cptId){
        computerService.deleteComputer(cptId);
        return ResponseEntity.ok().build();
    }
}