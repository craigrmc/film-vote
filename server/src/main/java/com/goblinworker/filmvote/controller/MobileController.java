package com.goblinworker.filmvote.controller;

import com.goblinworker.filmvote.model.Theater;
import com.goblinworker.filmvote.model.User;
import com.goblinworker.filmvote.model.Vote;
import com.goblinworker.filmvote.service.ClubService;
import com.goblinworker.filmvote.service.FilmService;
import com.goblinworker.filmvote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for mobile app rest endpoints.
 */
@RestController
@RequestMapping("/mobile/v1/")
public class MobileController {

    // TODO: just club / imdb service

    @Autowired
    private ClubService clubService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private VoteService voteService;

    /**
     * Add a Voting Club by name.
     *
     * @param club String
     */
    @PostMapping("addClub/{club}")
    public ResponseEntity addClub(@PathVariable String club) throws Exception {

        clubService.addClub(club);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Remove a Voting Club by name.
     *
     * @param club String
     */
    @DeleteMapping("removeClub/{club}")
    public ResponseEntity removeClub(@PathVariable String club) throws Exception {

        clubService.removeClub(club);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Sign up to a Voting Club on Mobile App.
     *
     * @param club String
     * @param user String
     */
    @PostMapping("signUp/{club}/{user}")
    public ResponseEntity<User> signUp(@PathVariable String club, @PathVariable String user) throws Exception {

        User myUser = clubService.addUser(club, user);

        return new ResponseEntity<>(myUser, HttpStatus.OK);
    }

    /**
     * Sign in to a Voting Club on Mobile App.
     *
     * @param club String
     * @param user String
     */
    @PostMapping("signIn/{club}/{user}")
    public ResponseEntity<User> signIn(@PathVariable String club, @PathVariable String user) throws Exception {

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
    public ResponseEntity signOut(@PathVariable String club, @PathVariable String user) throws Exception {
        // TODO: sign out process requires token authentication
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Add a Theater to a Club.
     *
     * @param user    User
     * @param theater Theater
     * @return Theater
     */
    @PostMapping("addTheater")
    public ResponseEntity<Theater> addTheater(@RequestBody User user, @RequestBody Theater theater) throws Exception {
        // TODO: finish him!!!
        return new ResponseEntity<>(new Theater("test"), HttpStatus.OK);
    }

    /**
     * Remove a Theater from a Club.
     *
     * @param user    User
     * @param theater Theater
     */
    @DeleteMapping("removeTheater")
    public ResponseEntity removeTheater(@RequestBody User user, @RequestBody Theater theater) throws Exception {
        // TODO: finish him!!!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get Theaters near a location.
     *
     * @param location String (address or zipcode)
     * @return Theater Map
     */
    @GetMapping("getTheatersForLocation/{location}")
    public Map<String, Theater> getTheatersForLocation(@PathVariable String location) throws Exception {
        // TODO: finish him!!
        return new HashMap<>();
    }

    /**
     * Get your Club's Theaters for a specific date.
     *
     * @param date String yyyy/MM/dd
     * @return Theater Map
     */
    @GetMapping("getTheatersForDate/{date}")
    public Map<String, Theater> getTheatersForDate(@PathVariable String date) throws Exception {
        // TODO: finish him!!!
        return new HashMap<>();
    }

    /**
     * Add a vote for a film on a specific date.
     *
     * @param user User
     * @param vote Vote
     * @return Vote
     */
    @PostMapping("addFilmVote")
    public ResponseEntity<Vote> addFilmVote(@RequestBody User user, @RequestBody Vote vote) throws Exception {
        // TODO: finish him!!!
        return new ResponseEntity<>(new Vote(), HttpStatus.OK);
    }

    /**
     * Get the current vote for the closest date with activity.
     *
     * @param user String
     * @return Vote
     */
    @GetMapping("getFilmVote")
    public ResponseEntity<Vote> getFilmVote(@RequestBody User user) throws Exception {
        // TODO: finish him!!!
        return new ResponseEntity<>(new Vote(), HttpStatus.OK);
    }

    /**
     * Get the current vote for a specific date.
     *
     * @param user User
     * @param date String yyyy/MM/dd
     * @return Vote
     */
    @GetMapping("getFilmVote/{date}")
    public ResponseEntity<Vote> getFilmVote(@RequestBody User user, @PathVariable String date) throws Exception {
        // TODO: finish him!!!
        return new ResponseEntity<>(new Vote(), HttpStatus.OK);
    }

}
