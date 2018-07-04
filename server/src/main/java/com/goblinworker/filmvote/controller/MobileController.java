package com.goblinworker.filmvote.controller;

import com.goblinworker.filmvote.model.FilmDate;
import com.goblinworker.filmvote.model.Theater;
import com.goblinworker.filmvote.model.User;
import com.goblinworker.filmvote.model.Vote;
import com.goblinworker.filmvote.service.ClubService;
import com.goblinworker.filmvote.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for mobile app rest endpoints.
 */
@RestController
@RequestMapping("/mobile/v1/")
public class MobileController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private FilmService filmService;

    /**
     * Add a Voting Club by name.
     *
     * @param club String
     */
    @PostMapping("addClub/{club}")
    public ResponseEntity addClub(@PathVariable String club)
            throws Exception {

        clubService.addClub(club);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Remove a Voting Club by name.
     *
     * @param club String
     */
    @DeleteMapping("removeClub/{club}")
    public ResponseEntity removeClub(@PathVariable String club)
            throws Exception {

        clubService.removeClub(club);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Sign up to a Voting Club on Mobile App.
     *
     * @param club String
     * @param user String
     * @return User
     */
    @PostMapping("signUp/{club}/{user}")
    public ResponseEntity<User> signUp(@PathVariable String club, @PathVariable String user)
            throws Exception {

        User myUser = clubService.addUser(club, user);

        return new ResponseEntity<>(myUser, HttpStatus.OK);
    }

    /**
     * Sign in to a Voting Club on Mobile App.
     *
     * @param club String
     * @param user String
     * @return User
     */
    @PostMapping("signIn/{club}/{user}")
    public ResponseEntity<User> signIn(@PathVariable String club, @PathVariable String user)
            throws Exception {

        User myUser = clubService.getUser(club, user);

        return new ResponseEntity<>(myUser, HttpStatus.OK);
    }

    /**
     * Sign out of Voting Club on Mobile App.
     *
     * @param club String
     * @param user String
     */
    @PostMapping("signOut/{club}/{user}")
    public ResponseEntity signOut(@PathVariable String club, @PathVariable String user)
            throws Exception {
        // TODO: sign out process requires token authentication
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Add a Theater to a Club.
     *
     * @param club    String
     * @param theater Theater
     */
    @PostMapping("addTheater/{club}")
    public ResponseEntity addTheater(@PathVariable String club, @RequestBody Theater theater)
            throws Exception {

        clubService.addTheater(club, theater);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Remove a Theater from a Club.
     *
     * @param club    String
     * @param theater Theater
     */
    @DeleteMapping("removeTheater/{club}/{theater}")
    public ResponseEntity removeTheater(@PathVariable String club, @PathVariable String theater)
            throws Exception {

        clubService.removeTheater(club, theater);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get Theaters near a location.
     *
     * @param location String (address or zipcode)
     * @return Theater Map
     */
    @GetMapping("getTheatersForLocation/{location}")
    public ResponseEntity<Map<String, Theater>> getTheatersForLocation(@PathVariable String location)
            throws Exception {

        Map<String, Theater> theaterMap = filmService.getTheatersForLocation(location);

        return new ResponseEntity<>(theaterMap, HttpStatus.OK);
    }

    /**
     * Get your Club's Theaters for a specific date.
     *
     * @param club String
     * @param date String yyyy-MM-dd
     * @return Theater Map
     */
    @GetMapping("getTheatersForDate/{club}/{date}")
    public ResponseEntity<Map<String, Theater>> getTheatersForDate(@PathVariable String club, @PathVariable String date)
            throws Exception {

        Map<String, Theater> theaterMap = loadTheaterData(club, date);

        return new ResponseEntity<>(theaterMap, HttpStatus.OK);
    }

    /**
     * Add a vote for a film on a specific date.
     *
     * @param club String
     * @param user String
     * @param vote Vote
     * @return Vote
     */
    @PostMapping("addVote/{club}/{user}")
    public ResponseEntity<Vote> addVote(@PathVariable String club, @PathVariable String user, @RequestBody Vote vote)
            throws Exception {

        Vote myVote = clubService.addVote(club, user, vote);

        return new ResponseEntity<>(myVote, HttpStatus.OK);
    }

    /**
     * Get the current vote for the closest date with activity.
     *
     * @param club String
     * @return Vote
     */
    @GetMapping("getFilmVote/{club}")
    public ResponseEntity<Vote> getFilmVote(@PathVariable String club)
            throws Exception {

        Vote myVote = clubService.getFilmVote(club);

        return new ResponseEntity<>(myVote, HttpStatus.OK);
    }

    /**
     * Get the current vote for a specific date.
     *
     * @param club String
     * @param date String yyyy-MM-dd
     * @return Vote
     */
    @GetMapping("getFilmVote/{club}/{date}")
    public ResponseEntity<Vote> getFilmVote(@PathVariable String club, @PathVariable String date)
            throws Exception {

        Vote myVote = clubService.getFilmVote(club, date);

        return new ResponseEntity<>(myVote, HttpStatus.OK);
    }

    private Map<String, Theater> loadTheaterData(String club, String date)
            throws Exception {

        Map<String, Theater> theaterMap = clubService.getTheaterMap(club);

        for (Map.Entry<String, Theater> theaterEntry : theaterMap.entrySet()) {
            Theater theater = theaterEntry.getValue();
            FilmDate filmDate = filmService.getFilmsForDate(theater.getName(), date);
            theater.addFilmDate(filmDate);
        }

        return theaterMap;
    }

}
