package rs.ac.metropolitan.cs330.znamenitosti.adapter.sight;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;

/**
 *
 * @author nikola
 */
public class SightAdapter extends ArrayAdapter<Sight> {

    private Context context;
    private List<Sight> list;

    public SightAdapter(Context context, List<Sight> sights) {
        super(context, R.layout.item_sight, sights);
        this.context = context;
        this.list = sights;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_sight, parent, false);
        Sight sight = list.get(position);
        sight.getListItemDrawer().draw(rowView);
        return rowView;
    }
}
