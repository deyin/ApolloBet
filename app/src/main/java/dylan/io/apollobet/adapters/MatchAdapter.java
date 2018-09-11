package dylan.io.apollobet.adapters;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import java.util.List;
import java.util.Map;

import dylan.io.apollobet.R;
import dylan.io.apollobet.listeners.OnOddsSelectedListener;
import dylan.io.apollobet.models.Match;
import dylan.io.apollobet.models.MatchParent;
import dylan.io.apollobet.models.Odds;
import dylan.io.apollobet.models.OddsType;
import dylan.io.apollobet.utils.DateUtils;

public class MatchAdapter extends ExpandableRecyclerAdapter<MatchParent, Match,
        MatchAdapter.MatchParentViewHolder, MatchAdapter.MatchChildViewHolder> {

    private final Context mContext;
    private final OnOddsSelectedListener mOnOddsSelectedListener;

    public MatchAdapter(Context context, @NonNull List<MatchParent> parentList) {
        super(parentList);
        this.mContext = context;
        if (!(context instanceof OnOddsSelectedListener)) {
            throw new IllegalArgumentException("The activity of " + context + " should implement interface of OnOddsSelectedListener");
        }
        mOnOddsSelectedListener = (OnOddsSelectedListener) context;
    }

    @NonNull
    @Override
    public MatchParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_match_parent, parentViewGroup, false);
        return new MatchParentViewHolder(view);
    }

    @NonNull
    @Override
    public MatchChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_match, childViewGroup, false);
        return new MatchChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(@NonNull MatchParentViewHolder parentViewHolder, int parentPosition, @NonNull MatchParent parent) {
        parentViewHolder.onBind(parentPosition, parent);
    }

    @Override
    public void onBindChildViewHolder(@NonNull MatchChildViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Match child) {
        childViewHolder.onBind(childPosition, child);
    }

    static class MatchParentViewHolder extends ParentViewHolder<MatchParent, Match> {

        TextView title;

        public MatchParentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }

        public void onBind(int parentPosition, MatchParent matchParent) {
            title.setText(matchParent.getTitle());
        }
    }

    class MatchChildViewHolder extends ChildViewHolder<Match> {

        TextView matchInfo;
        TextView vs;
        TextView spread;

        TextView winOdds;
        TextView drawOdds;
        TextView loseOdds;

        TextView spreadWinOdds;
        TextView spreadDrawOdds;
        TextView spreadLoseOdds;

        TextView more;
        TextView spreadMore;

        public MatchChildViewHolder(@NonNull View itemView) {
            super(itemView);
            matchInfo = itemView.findViewById(R.id.tv_match_info);
            vs = itemView.findViewById(R.id.tv_vs);
            spread = itemView.findViewById(R.id.tv_spread);

            winOdds = itemView.findViewById(R.id.tv_win_odds);
            drawOdds = itemView.findViewById(R.id.tv_draw_odds);
            loseOdds = itemView.findViewById(R.id.tv_lose_odds);

            more = itemView.findViewById(R.id.tv_none_spread_more_odds);

            spreadWinOdds = itemView.findViewById(R.id.tv_spread_win_odds);
            spreadDrawOdds = itemView.findViewById(R.id.tv_spread_draw_odds);
            spreadLoseOdds = itemView.findViewById(R.id.tv_spread_lose_odds);

            spreadMore = itemView.findViewById(R.id.tv_spread_more_odds);
        }

        private final class OnOddsClickListener implements View.OnClickListener {

            final Match mMatch;

            public OnOddsClickListener(Match match) {
                this.mMatch = match;
            }

            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                boolean selected = v.isSelected();
                OddsType oddsType = getOddsType(v.getId());
                mOnOddsSelectedListener.onOddsSelected(mMatch, oddsType, selected);
                v.setBackgroundResource(selected ? R.color.colorOddSelected : R.color.colorOddNotSelected);
            }

            @Nullable
            private OddsType getOddsType(@IdRes int id) {
                OddsType oddsType = null;
                switch (id) {
                    case R.id.tv_win_odds:
                        oddsType = OddsType.WIN;
                        break;
                    case R.id.tv_draw_odds:
                        oddsType = OddsType.DRAW;
                        break;
                    case R.id.tv_lose_odds:
                        oddsType = OddsType.LOSE;
                        break;
                    case R.id.tv_spread_win_odds:
                        oddsType = OddsType.SPREAD_WIN;
                        break;
                    case R.id.tv_spread_draw_odds:
                        oddsType = OddsType.SPREAD_DRAW;
                        break;
                    case R.id.tv_spread_lose_odds:
                        oddsType = OddsType.SPREAD_LOSE;
                        break;
                }
                return oddsType;
            }
        }

        public void onBind(int childPosition, Match match) {
            matchInfo.setText(match.getNumber()
                    + "\n" + match.getLeagueShortName()
                    + "\n" + getDisplayTime(match));
            vs.setText(match.getHostTeamShortName() + match.getHostLeagueOrder()
                    + "vs" + match.getAwayTeamShortName() + match.getAwayLeagueOrder());

            spread.setText(match.getSpread() > 0 ? String.format("+%d", match.getSpread()) : String.valueOf(match.getSpread()));
            spread.setBackgroundResource(match.getSpread() > 0 ?
                    R.color.colorPositiveSpread : R.color.colorNegativeSpread);

            setOdds(match);

        }

        private void setOdds(Match match) {
            Map<OddsType, Odds> oddsMap = match.getOddsMap();

            OnOddsClickListener onOddsClickListener = new OnOddsClickListener(match);

            winOdds.setText(String.valueOf(oddsMap.get(OddsType.WIN)));
            winOdds.setOnClickListener(onOddsClickListener);

            drawOdds.setText(String.valueOf(oddsMap.get(OddsType.DRAW)));
            drawOdds.setOnClickListener(onOddsClickListener);

            loseOdds.setText(String.valueOf(oddsMap.get(OddsType.LOSE)));
            loseOdds.setOnClickListener(onOddsClickListener);

            spreadWinOdds.setText(String.valueOf(oddsMap.get(OddsType.SPREAD_WIN)));
            spreadWinOdds.setOnClickListener(onOddsClickListener);

            spreadDrawOdds.setText(String.valueOf(oddsMap.get(OddsType.SPREAD_DRAW)));
            spreadDrawOdds.setOnClickListener(onOddsClickListener);

            spreadLoseOdds.setText(String.valueOf(oddsMap.get(OddsType.SPREAD_LOSE)));
            spreadLoseOdds.setOnClickListener(onOddsClickListener);
        }


        private String getDisplayTime(Match match) {
            boolean sameDay = DateUtils.isSameDay(match.getDeadline(), match.getMatchTime());
            return DateUtils.toString("HH:mm",
                    (sameDay ? match.getMatchTime() : match.getDeadline()));
        }
    }

    public void updateMatchParent(List<MatchParent> matchParents, boolean preserveExpansionState) {

        this.getParentList().clear();

        this.getParentList().addAll(matchParents);

        this.notifyParentDataSetChanged(preserveExpansionState);

        this.expandParent(0); // default expand the first parent
    }

}
