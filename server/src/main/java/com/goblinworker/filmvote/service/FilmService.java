package com.goblinworker.filmvote.service;

import com.goblinworker.filmvote.model.Film;
import com.goblinworker.filmvote.model.FilmDate;
import com.goblinworker.filmvote.model.Theater;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service that manages information regarding films and theaters.
 */
@Component
public class FilmService {

    private final Logger LOGGER = Logger.getLogger(FilmService.class.getSimpleName());

    /**
     * Get a map of theaters close to a location.
     *
     * @param location String city / address / zipcode
     * @return Theater Map
     */
    public Map<String, Theater> getTheatersForLocation(String location) throws Exception {

        String foundLocation = findLocation(location);
        if (foundLocation == null) {
            throw new Exception("location invalid");
        }

        // TODO: replace fake data with external API, Fandango perhaps?
        Theater theater1 = makeTheaterAMC();
        Theater theater2 = makeTheaterMartin();
        Theater theater3 = makeTheaterTheGrand();

        Map<String, Theater> theaterMap = new HashMap<>();

        theaterMap.put(theater1.getName(), theater1);
        theaterMap.put(theater2.getName(), theater2);
        theaterMap.put(theater3.getName(), theater3);

        return theaterMap;
    }

    /**
     * Get films for a specific date and theater.
     *
     * @param theater String
     * @param date    String
     * @return FilmDate
     */
    public FilmDate getFilmsForDate(String theater, String date) throws Exception {

        String foundTheater = findTheater(theater);
        if (foundTheater == null) {
            throw new Exception("theater invalid");
        }

        // TODO: replace fake data with external API, Fandango perhaps?
        Film film1 = makeFilmBladeRunner();
        Film film2 = makeFilmFightClub();
        Film film3 = makeFilmTheMatrix();

        FilmDate filmDate = new FilmDate(date);
        filmDate.addFilm(film1);
        filmDate.addFilm(film2);
        filmDate.addFilm(film3);

        return filmDate;
    }

    /**
     * Find a location.
     *
     * @param location String city / address / zipcode
     * @return String
     */
    String findLocation(String location) {

        if (location != null && !location.isEmpty()) {
            // TODO: replace fake data with external API
            return "Dummy Location";
        }

        return null;
    }

    /**
     * Find a theater by name.
     *
     * @param name String
     * @return String
     */
    String findTheater(String name) {

        if (name != null && !name.isEmpty()) {
            return "Dummy Theater";
        }

        return null;
    }

    /**
     * Make dummy theater.
     *
     * @return Theater
     */
    Theater makeTheaterAMC() {

        Theater theater = new Theater("AMC Panama City 10");
        theater.setLocation("Downtown");
        theater.setAddress("4049 W 23rd St");
        theater.setCity("Panama City");
        theater.setState("FL");
        theater.setZipcode("32405");
        theater.setPhone("(850) 913-9292");

        return theater;
    }

    /**
     * Make dummy theater.
     *
     * @return Theater
     */
    Theater makeTheaterMartin() {

        Theater theater = new Theater("Martin Theatre");
        theater.setLocation("Downtown");
        theater.setAddress("409 Harrison Ave");
        theater.setCity("Panama City");
        theater.setState("FL");
        theater.setZipcode("32401");
        theater.setPhone("(850) 763-8080");

        return theater;
    }

    /**
     * Make dummy theater.
     *
     * @return Theater
     */
    Theater makeTheaterTheGrand() {

        Theater theater = new Theater("The Grand 16 - Pier Park");
        theater.setLocation("Pier Park");
        theater.setAddress("500 Pier Park Drive");
        theater.setCity("Panama City Beach");
        theater.setState("FL");
        theater.setZipcode("32413");
        theater.setPhone("(850) 233-4835");

        return theater;
    }

    /**
     * Make dummy film.
     *
     * @return Film
     */
    Film makeFilmBladeRunner() {

        Film film = new Film("Blade Runner");
        film.setDescription("A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator.");
        film.setLength("1h 57min");
        film.setGenre("Sci-Fi, Thriller");

        film.getDirectorList().add("Ridley Scott");
        film.getWriterList().add("Hampton Fancher");
        film.getWriterList().add("David Webb Peoples");
        film.getActorList().add("Harrison Ford");
        film.getActorList().add("Rutger Hauer");
        film.getActorList().add("Sean Young");

        film.addShowTime("13:10:00");
        film.addShowTime("17:00:00");
        film.addShowTime("19:30:00");
        film.addShowTime("22:05:00");

        return film;
    }

    /**
     * Make dummy film.
     *
     * @return Film
     */
    Film makeFilmFightClub() {

        Film film = new Film("Fight Club");
        film.setDescription("An insomniac office worker, looking for a way to change his life, crosses paths with a devil-may-care soapmaker, forming an underground fight club that evolves into something much, much more.");
        film.setLength("2h 19min");
        film.setGenre("Drama");

        film.getDirectorList().add("David Fincher");
        film.getWriterList().add("Chuck Palahniuk");
        film.getWriterList().add("Jim Uhls");
        film.getActorList().add("Brad Pitt");
        film.getActorList().add("Edward Norton");
        film.getActorList().add("Meat Loaf");

        film.addShowTime("13:05:00");
        film.addShowTime("17:10:00");
        film.addShowTime("19:30:00");
        film.addShowTime("22:00:00");

        return film;
    }

    /**
     * Make dummy film.
     *
     * @return Film
     */
    Film makeFilmTheMatrix() {

        Film film = new Film("The Matrix");
        film.setDescription("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.");
        film.setLength("2h 16min");
        film.setGenre("Action, Sci-Fi");

        film.getDirectorList().add("Lana Wachowski");
        film.getDirectorList().add("Lilly Wachowski");
        film.getWriterList().add("Lana Wachowski");
        film.getWriterList().add("Lilly Wachowski");
        film.getActorList().add("Keanu Reeves");
        film.getActorList().add("Laurence Fishburne");
        film.getActorList().add("Carrie-Anne Moss");

        film.addShowTime("13:00:00");
        film.addShowTime("17:15:00");
        film.addShowTime("19:00:00");
        film.addShowTime("22:30:00");

        return film;
    }

}
