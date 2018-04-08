package quiniela.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quiniela.model.Player;
import quiniela.model.Tournament;
import quiniela.repository.TournamentRepository;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TournamentServiceImpl implements TournamentService {

    static private AtomicLong counter = new AtomicLong();

    @Autowired
    private TournamentRepository tournamentRepository;

    @PostConstruct
    private void init() {
        tournamentRepository.deleteAll();
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    @Override
    public List<String> getPlayersByIdTournament(long idTournament) {
        return tournamentRepository.findById(idTournament).getListPlayers();
    }

    @Override
    public Tournament getTournamentByName(String name) {
        return tournamentRepository.findByName(name);
    }

    @Override
    public Tournament createTournament(String name, String usernameAdmin) {
        Tournament t =  new Tournament();
        t.setName(name);
        t.setId(counter.incrementAndGet());
        t.getListPlayers().add(usernameAdmin);
        t.setIdAdmin(usernameAdmin);
        tournamentRepository.save(t);
        return t;
    }

    @Override
    public Tournament getTournamentByUsername(String username) {
        return tournamentRepository.findByUsername(username);
    }

    @Override
    public Tournament addPlayer(long idTournament, Player player) {
        Tournament t =  tournamentRepository.findById(idTournament);
        t.getListPlayers().add(player.getUsername());
        tournamentRepository.save(t);
        return t;
    }
}
