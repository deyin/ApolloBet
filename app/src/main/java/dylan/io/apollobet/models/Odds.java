package dylan.io.apollobet.models;

public class Odds {

    private OddsType type;

    private Double value;

    private boolean selected = false;

    public Odds(OddsType type, Double value) {
        this.type = type;
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

    public OddsType getType() {
        return type;
    }

    public void setType(OddsType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
