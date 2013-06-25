package rs.ac.metropolitan.cs330.znamenitosti.adapter.sight;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.adapter.ListItemDrawer;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;

/**
 *
 * @author nikola
 */
public abstract class SightDrawer implements ListItemDrawer {

    private Sight sight;
    protected ImageView image;
    protected ImageView state;

    public SightDrawer(Sight sight) {
        this.sight = sight;
    }

    public void draw(View view) {
        this.image = (ImageView) view.findViewById(R.id.item_sight_image);
        this.state = (ImageView) view.findViewById(R.id.item_sight_state);
        byte[] data = sight.image.data;
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        Drawable drawable = new BitmapDrawable(view.getResources(), bmp);
        image.setImageDrawable(drawable);
    }
}
