package fr.takima.cdb.services.home;

import fr.takima.cdb.model.home.HomeStats;

/**
 * Interface of computer service bean
 */
public interface HomeService {
 
    /**
     * Get the full, exhaustive list of all computers
     * @return
     */
    HomeStats getHomeStats();

}