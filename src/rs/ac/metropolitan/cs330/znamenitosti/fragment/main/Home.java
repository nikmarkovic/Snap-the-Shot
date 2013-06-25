package rs.ac.metropolitan.cs330.znamenitosti.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.City;

/**
 *
 * @author nikola
 */
public class Home extends Fragment {

    private City city;

    public Home() {
    }

    public Home(City city) {
        this.city = city;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setStatistic(view);
        return view;
    }

    private void setStatistic(View view) {
        if (city != null && city.unlocked) {
            view.setVisibility(View.VISIBLE);

            setText(view, R.id.home_city_name, city.name);

            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.progress)).append(": ")
                    .append(city.countSolvedSights()).append("/").append(city.countSights());
            setText(view, R.id.city_progress, sb.toString());

            sb = new StringBuilder();
            sb.append(getString(R.string.helps)).append(": ").append(city.remainingHelps());
            setText(view, R.id.city_helps, sb.toString());
        }
    }

    private void setText(View view, int textViewId, String text) {
        TextView tv = (TextView) view.findViewById(textViewId);
        tv.setText(text);
    }
}
