package fr.takima.cdb.model.home;

// Lib : Spring
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import fr.takima.cdb.controlers.company.CompanyControler;
// Components
import fr.takima.cdb.controlers.computer.ComputerControler;
import fr.takima.cdb.controlers.home.HomeControler;

/**
 * Mapper to transform Computer data object into Computer web resource
 */
@Component
public class HomeStatsModelAssembler implements RepresentationModelAssembler<HomeStats, EntityModel<HomeStats>> {
    private static int DEFAULT_PAGE_INDEX=0;
    private static int DEFAULT_PAGE_SIZE=50;
    private static Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.DESC;
    private static String DEFAULT_SORT_BY="name";
    @Override
    public EntityModel<HomeStats> toModel(HomeStats homeStats) {
        return new EntityModel<>(homeStats,
            linkTo(methodOn(HomeControler.class).getHomeStats()).withSelfRel(),
            linkTo(methodOn(ComputerControler.class).getComputers(DEFAULT_PAGE_INDEX,DEFAULT_PAGE_SIZE,DEFAULT_SORT_DIRECTION, DEFAULT_SORT_BY)).withRel("computers"),
            linkTo(methodOn(CompanyControler.class).getCompanies(DEFAULT_PAGE_INDEX,DEFAULT_PAGE_SIZE,DEFAULT_SORT_DIRECTION, DEFAULT_SORT_BY)).withRel("companies")
            );
    }
    

}