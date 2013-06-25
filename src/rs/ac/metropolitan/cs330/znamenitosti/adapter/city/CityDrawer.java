package rs.ac.metropolitan.cs330.znamenitosti.adapter.city;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.ListItemDrawer;
import rs.ac.metropolitan.cs330.znamenitosti.model.City;

/**
 *
 * @author nikola
 */
public abstract class CityDrawer implements ListItemDrawer {

    private City city;
    protected TextView cityName;
    protected ImageView cityState;
    protected RelativeLayout.LayoutParams cityNameParams;
    protected RelativeLayout.LayoutParams cityStateParams;

    public CityDrawer(City city) {
        this.city = city;
    }

    public void draw(View view) {
        this.cityName = (TextView) view.findViewById(R.id.item_city_name);
        this.cityState = (ImageView) view.findViewById(R.id.item_city_icon);
        this.cityNameParams = (RelativeLayout.LayoutParams) cityName.getLayoutParams();
        this.cityStateParams = (RelativeLayout.LayoutParams) cityState.getLayoutParams();
        cityNameParams.addRule(RelativeLayout.CENTER_VERTICAL);
        cityStateParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        cityState.setLayoutParams(cityStateParams);
        cityName.setText(city.name);
    }
}
