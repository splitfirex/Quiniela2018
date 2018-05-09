package quinielas.model.views;

import quinielas.utils.dom.DOMMatch;

import java.util.ArrayList;
import java.util.List;

public class ViewResumeMatches extends TokenAbleView {

    List<DOMMatch> nextMatches = new ArrayList<DOMMatch>();
    List<DOMMatch> prevMatches = new ArrayList<DOMMatch>();

    public List<DOMMatch> getPrevMatches() {
        return prevMatches;
    }

    public void setPrevMatches(List<DOMMatch> prevMatches) {
        this.prevMatches = prevMatches;
    }

    public List<DOMMatch> getNextMatches() {
        return nextMatches;
    }

    public void setNextMatches(List<DOMMatch> nextMatches) {
        this.nextMatches = nextMatches;
    }
}
