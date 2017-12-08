package net.splitfire.quiniela.controller;

import net.splitfire.quiniela.model.Match;
import net.splitfire.quiniela.service.MatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MatchesController {

    @Autowired
    MatchesService matchesService;

    @RequestMapping(value = "/matches/", method = RequestMethod.GET)
    public ResponseEntity<List<Match>> listAvaliableMatches() {
        List<Match> users = matchesService.getListMatches();
        if(users.isEmpty()){
            return new ResponseEntity<List<Match>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Match>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/matches/all", method = RequestMethod.GET)
    public ResponseEntity<List<Match>> listAllMatchByAllUsers() {
        List<Match> users = matchesService.getListMatches();
        if(users.isEmpty()){
            return new ResponseEntity<List<Match>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Match>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/matches/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Match>> getUserMatches(@PathVariable("name") String username) {
        List<Match> matches = matchesService.getListMatchesByUser(username);
        if(matches.isEmpty()){
            return new ResponseEntity<List<Match>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Match>>(matches, HttpStatus.OK);
    }

}
