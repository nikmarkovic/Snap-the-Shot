package rs.ac.metropolitan.cs330.znamenitosti.adapter.city;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.City;

/**
 *
 * @author nikola
 */
public class CityAdapter extends ArrayAdapter<City> {

    private Context context;
    private List<City> list;

    public CityAdapter(Context context, List<City> cities) {
        super(context, R.layout.item_city, cities);
        this.context = context;
        this.list = cities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_city, parent, false);
        City city = list.get(position);
        city.getListItemDrawer().draw(rowView);
        return rowView;
    }
}
