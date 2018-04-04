package quiniela.repository;

public interface PlayerRepositoryCustom {

    public long updateMatch(long idPlayer, long idMatch, String homeTeam, String visitorTeam, int homeScore, int visitorScore );
}
