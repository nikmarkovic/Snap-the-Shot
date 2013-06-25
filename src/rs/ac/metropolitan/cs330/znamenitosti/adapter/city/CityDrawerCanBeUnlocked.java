package rs.ac.metropolitan.cs330.znamenitosti.adapter.city;

import android.view.View;
import android.widget.RelativeLayout;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.City;

/**
 *
 * @author nikola
 */
public class CityDrawerCanBeUnlocked extends CityDrawer {

    public CityDrawerCanBeUnlocked(City city) {
        super(city);
    }

    @Override
    public void draw(View view) {
        super.draw(view);
        cityNameParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        cityState.setImageResource(R.drawable.ic_locked);
        view.setBackgroundResource(R.drawable.selector_item_city);
        cityName.setLayoutParams(cityNameParams);
    }
}
