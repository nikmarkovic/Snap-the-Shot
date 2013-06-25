package rs.ac.metropolitan.cs330.znamenitosti.adapter.city;

import android.view.View;
import android.widget.RelativeLayout;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.City;

/**
 *
 * @author nikola
 */
public class CityDrawerInProgress extends CityDrawer {

    public CityDrawerInProgress(City city) {
        super(city);
    }

    @Override
    public void draw(View view) {
        super.draw(view);
        cityNameParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        view.setBackgroundResource(R.drawable.selector_item_city);
        cityState.setImageResource(R.drawable.ic_dummy);
        cityName.setLayoutParams(cityNameParams);
    }
}
