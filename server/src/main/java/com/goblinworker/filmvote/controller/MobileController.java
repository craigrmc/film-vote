package com.goblinworker.filmvote.controller;

import com.goblinworker.filmvote.model.*;
import com.goblinworker.filmvote.service.ClubService;
import com.goblinworker.filmvote.service.FilmService;
import com.goblinworker.filmvote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
     * @return Club
     */
    @PostMapping("addClub/{club}")
    public ResponseEntity<Club> addClub(@PathVariable String club) throws Exception {

        Club myClub = clubService.addClub(club);

        return new ResponseEntity<>(myClub, HttpStatus.OK);
    }

    /**
     * Get a Voting Club by name.
     *
     * @param club String
     * @return Club
     */
    @GetMapping("getClub/{club}")
    public ResponseEntity<Club> getClub(@PathVariable String club) throws Exception {

        Club myClub = clubService.getClub(club);

        return new ResponseEntity<>(myClub, HttpStatus.OK);
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
     * @param theater String
     * @return Theater
     */
    @PostMapping("addTheater/{theater}")
    public Theater addTheater(@RequestBody User user, @PathVariable String theater) {
        return new Theater("test");
    }

    /**
     * Remove a Theater from a Club.
     *
     * @param club    String
     * @param theater String
     */
    @DeleteMapping("removeTheater/{club}/{theater}")
    public Boolean removeTheater(@PathVariable String club, @PathVariable String theater) {
        return true;
    }

    /**
     * Get theaters near a location.
     *
     * @param location String (address or zipcode)
     * @return List<String>
     */
    @GetMapping("getTheaterList/{location}")
    public List<Theater> getTheaterList(@PathVariable String location) {
        return new ArrayList<>();
    }

    @GetMapping("getFilmDate/{date}")
    public FilmDate getFilmDate(@RequestBody User user, @PathVariable String date) {
        return new FilmDate("test");
    }

    /**
     * Add a vote for a film on a specific date.
     *
     * @param user User
     * @param vote Vote
     * @return Vote
     */
    @PostMapping("addFilmVote")
    public Vote addFilmVote(@RequestBody User user, @RequestBody Vote vote) {
        // NOTE: probably should not have film as string path variable
        return new Vote();
    }

    /**
     * Get the current vote for the closest date with activity.
     *
     * @param user String
     * @return Vote
     */
    @GetMapping("getFilmVote")
    public Vote getFilmVote(@RequestBody User user) {
        // TODO: return a film / date
        return new Vote();
    }

    /**
     * Get the current vote for a specific date.
     *
     * @param user User
     * @param date String
     * @return Vote
     */
    @GetMapping("getFilmVote/{date}")
    public Vote getFilmVote(@RequestBody User user, @PathVariable String date) {
        // TODO: return a film / date
        return new Vote();
    }

}
