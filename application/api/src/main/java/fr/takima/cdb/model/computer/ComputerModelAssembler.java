package fr.takima.cdb.model.computer;

// Lib : Spring
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

// Components
import fr.takima.cdb.controlers.computer.ComputerControler;

/**
 * Mapper to transform Computer data object into Computer web resource
 */
@Component
public class ComputerModelAssembler implements RepresentationModelAssembler<Computer, EntityModel<Computer>> {
    private static int DEFAULT_PAGE_INDEX=0;
    private static int DEFAULT_PAGE_SIZE=50;
    private static Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;
    private static String DEFAULT_SORT_BY="name";
    @Override
    public EntityModel<Computer> toModel(Computer computer) {
        return new EntityModel<>(computer,
            linkTo(methodOn(ComputerControler.class).getComputerById(computer.getCptId())).withSelfRel()
                
                .andAffordance(afford(methodOn(ComputerControler.class).updateComputer(null, computer.getCptId())))
                .andAffordance(afford(methodOn(ComputerControler.class).replaceComputer(null, computer.getCptId())))
                .andAffordance(afford(methodOn(ComputerControler.class).deleteComputer(computer.getCptId()))),
            linkTo(methodOn(ComputerControler.class).getComputers(DEFAULT_PAGE_INDEX,DEFAULT_PAGE_SIZE,DEFAULT_SORT_DIRECTION,DEFAULT_SORT_BY)).withRel("computers")
            );
    }

}