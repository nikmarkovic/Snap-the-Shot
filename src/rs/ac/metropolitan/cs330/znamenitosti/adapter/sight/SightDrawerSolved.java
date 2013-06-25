package rs.ac.metropolitan.cs330.znamenitosti.adapter.sight;

import android.view.View;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;

/**
 *
 * @author nikola
 */
public class SightDrawerSolved extends SightDrawer {

    public SightDrawerSolved(Sight sight) {
        super(sight);
    }

    @Override
    public void draw(View view) {
        super.draw(view);
        state.setBackgroundResource(R.drawable.ic_solved);
    }
}
