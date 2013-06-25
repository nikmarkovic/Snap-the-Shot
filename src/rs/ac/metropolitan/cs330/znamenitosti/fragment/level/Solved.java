package rs.ac.metropolitan.cs330.znamenitosti.fragment.level;

import java.text.Format;
import java.text.SimpleDateFormat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;

/**
 *
 * @author nikola
 */
public class Solved extends Fragment {

    private Sight sight;

    public Solved(Sight sight) {
        this.sight = sight;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solved, container, false);
        TextView name = (TextView) view.findViewById(R.id.solved_name);
        TextView time = (TextView) view.findViewById(R.id.solved_time);
        ImageView image = (ImageView) view.findViewById(R.id.solved_image);
        TextView description = (TextView) view.findViewById(R.id.solved_description);

        name.setText(sight.name);
        Format formatter = new SimpleDateFormat(view.getContext().getString(R.string.date_format));
        time.setText(formatter.format(sight.solvedTime));
        byte[] data = sight.image.data;
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        Drawable drawable = new BitmapDrawable(view.getResources(), bmp);
        image.setImageDrawable(drawable);
        description.setText(sight.description);
        return view;
    }
}
