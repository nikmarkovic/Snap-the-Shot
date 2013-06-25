package rs.ac.metropolitan.cs330.znamenitosti.fragment.level;

import java.util.Collections;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;
import rs.ac.metropolitan.cs330.znamenitosti.LevelActivity;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.sight.SightAdapter;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;

/**
 *
 * @author nikola
 */
public class SelectSight extends ListFragment {

    private List<Sight> list;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setSelector(R.drawable.selector_item_city);
        getListView().setDivider(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getListView().setDividerHeight(10);
        refreshList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Sight sight = list.get(position);
        LevelActivity level = (LevelActivity) getActivity();
        if (sight.solved) {
            level.onSolved(sight);
        } else {
            level.onInProgress(sight);
        }
    }

    private void refreshList() {
        list = ((LevelActivity) getActivity()).getCity().sights();
        Collections.sort(list);
        SightAdapter adapter = new SightAdapter(getActivity(), list);
        setListAdapter(adapter);
    }
}
