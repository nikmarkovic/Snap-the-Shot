package rs.ac.metropolitan.cs330.znamenitosti.fragment.main;

import java.util.Collections;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.graphics.drawable.ColorDrawable;
import rs.ac.metropolitan.cs330.znamenitosti.MainActivity;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.city.CityAdapter;
import rs.ac.metropolitan.cs330.znamenitosti.model.City;
import rs.ac.metropolitan.cs330.znamenitosti.service.Gameplay;

/**
 *
 * @author nikola
 */
public class SelectLevel extends ListFragment {

    private List<City> list;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setSelector(R.drawable.selector_transparent);
        getListView().setDivider(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getListView().setDividerHeight(10);
        refreshList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        City city = list.get(position);
        if (!city.unlocked && Gameplay.INSTANCE.unlockPoints() > 0) {
            city.unlocked = true;
            city.save();
            refreshList();
        } else if (city.unlocked) {
            MainActivity main = (MainActivity) getActivity();
            main.setCity(city);
            main.onHome(main.findViewById(R.id.button_home));
        }
    }

    private void refreshList() {
        list = City.all();
        Collections.sort(list);
        CityAdapter adapter = new CityAdapter(getActivity(), list);
        setListAdapter(adapter);
    }
}
