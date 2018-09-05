package dylan.io.apollobet.models;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

public class MatchParent implements Parent<Match> {

    private String title = "";

    private final List<Match> matches;

    public MatchParent(String title, List<Match> matches) {
        this.title = title;
        this.matches = matches;
    }

    @Override
    public List<Match> getChildList() {
        return matches;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
