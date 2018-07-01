package com.goblinworker.filmvote.service;

import com.goblinworker.filmvote.model.Theater;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service that manages information regarding films and theaters.
 */
@Component
public class FilmService {

    private final Logger LOGGER = Logger.getLogger(FilmService.class.getSimpleName());

    /**
     * Returns a list of theaters close to a location.
     *
     * @param address String
     * @param zipcode String
     * @return List<Theater>
     */
    public List<Theater> getTheaterList(String address, String zipcode) {
        // TODO: finish him!!!
        return new ArrayList<>();
    }

}
