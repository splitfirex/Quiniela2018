package quiniela.repository;

import quiniela.model.PlayerMatch;

import java.util.Date;
import java.util.List;

public interface PlayerMatchCustomRepository {
    public List<PlayerMatch> findbyBetweenDates(Date from, Date to);
}
