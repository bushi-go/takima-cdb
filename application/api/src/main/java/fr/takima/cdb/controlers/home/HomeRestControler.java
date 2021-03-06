package fr.takima.cdb.controlers.home;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.takima.cdb.model.home.HomeStats;
import fr.takima.cdb.model.home.HomeStatsModelAssembler;
import fr.takima.cdb.services.home.HomeService;

@RestController
@RequestMapping("/api/")
public class HomeRestControler {

    private HomeService homeService;
    private HomeStatsModelAssembler homeStatsModelAssembler;

    HomeRestControler(HomeService homeService,HomeStatsModelAssembler homeStatsModelAssembler){
        this.homeService = homeService;
        this.homeStatsModelAssembler = homeStatsModelAssembler;
    }

    @GetMapping("/")
    public EntityModel<HomeStats> getHomeStats(){
        return homeStatsModelAssembler.toModel(homeService.getHomeStats());
    }
}