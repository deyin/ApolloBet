package dylan.io.apollobet.models;

import android.support.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.Date;
import java.util.List;

public class MatchParent implements Parent<Match>, Comparable<MatchParent> {

    private String title;

    private final List<Match> matches;

    private Date date;

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

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int compareTo(@NonNull MatchParent o) {
        return this.date.compareTo(o.date);
    }
}
