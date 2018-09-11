package dylan.io.apollobet.listeners;

import android.support.annotation.NonNull;

import dylan.io.apollobet.models.Match;
import dylan.io.apollobet.models.OddsType;

public interface OnOddsSelectedListener {

    void onOddsSelected(@NonNull Match match, @NonNull OddsType oddsType, boolean selected);
}
