package rs.ac.metropolitan.cs330.znamenitosti.fragment.level;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;

/**
 *
 * @author nikola
 */
public class InProgress extends Fragment {

    private Sight sight;

    public InProgress(Sight sight) {
        this.sight = sight;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_progress, container, false);
        ImageView image = (ImageView) view.findViewById(R.id.in_progress_image);
        ImageButton map = (ImageButton) view.findViewById(R.id.button_map);
        if(sight.city.remainingHelps() > 0) {
            map.setBackgroundResource(R.drawable.selector_level_option);
        } else {
            map.setBackgroundResource(R.drawable.btn_disable);
        }
        byte[] data = sight.image.data;
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        Drawable drawable = new BitmapDrawable(view.getResources(), bmp);
        image.setImageDrawable(drawable);
        return view;
    }
}
