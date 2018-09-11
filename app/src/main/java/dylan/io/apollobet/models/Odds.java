package dylan.io.apollobet.models;

public class Odds {

    private Double value;

    private boolean selected = false;

    public Odds(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
