package rs.ac.metropolitan.cs330.znamenitosti.adapter.city;

import android.view.View;
import android.widget.RelativeLayout;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.City;

/**
 *
 * @author nikola
 */
public class CityDrawerLocked extends CityDrawer {

    public CityDrawerLocked(City city) {
        super(city);
    }

    @Override
    public void draw(View view) {
        super.draw(view);
        cityNameParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        view.setBackgroundResource(R.drawable.selector_disable);
        cityState.setImageResource(R.drawable.ic_locked);
        cityName.setLayoutParams(cityNameParams);
    }
}
