package quiniela.model.views;

import quiniela.model.PlayerMatch;

import java.util.ArrayList;
import java.util.List;

public class ViewResumeMatches extends TokenAbleView {

    List<PlayerMatch> nextMatches = new ArrayList<PlayerMatch>();
    List<PlayerMatch> prevMatches = new ArrayList<PlayerMatch>();

    public List<PlayerMatch> getPrevMatches() {
        return prevMatches;
    }

    public void setPrevMatches(List<PlayerMatch> prevMatches) {
        this.prevMatches = prevMatches;
    }

    public List<PlayerMatch> getNextMatches() {
        return nextMatches;
    }

    public void setNextMatches(List<PlayerMatch> nextMatches) {
        this.nextMatches = nextMatches;
    }
}
