package dylan.io.apollobet.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import java.util.List;

import dylan.io.apollobet.R;
import dylan.io.apollobet.models.Match;
import dylan.io.apollobet.models.MatchParent;

public class MatchAdapter extends ExpandableRecyclerAdapter<MatchParent, Match,
        MatchAdapter.MatchParentViewHolder, MatchAdapter.MatchChildViewHolder> {

    private final Context mContext;

    public MatchAdapter(Context context, @NonNull List<MatchParent> parentList) {
        super(parentList);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MatchParentViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_match_group, parentViewGroup, false);
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

    public void updateMatchGroups(List<MatchParent> matchParents) {

        this.getParentList().clear();

        this.getParentList().addAll(matchParents);

        this.notifyParentDataSetChanged(true);
    }

    static class MatchParentViewHolder extends ParentViewHolder<MatchParent, Match> {

        TextView title;

        public MatchParentViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
        }

        public void onBind(int parentPosition, MatchParent parent) {
            title.setText(parent.getTitle());
        }
    }

    static class MatchChildViewHolder extends ChildViewHolder<Match> {

        TextView matchInfo;

        public MatchChildViewHolder(@NonNull View itemView) {
            super(itemView);

            matchInfo = itemView.findViewById(R.id.tv_match_info);
        }

        public void onBind(int childPosition, Match child) {
            matchInfo.setText(child.getNumber()
                    + "\n" + child.getLeagueName()
                    + "\n" + child.getDisplayDeadline());
        }
    }
}
