package rs.ac.metropolitan.cs330.znamenitosti.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import rs.ac.metropolitan.cs330.znamenitosti.MainActivity;
import rs.ac.metropolitan.cs330.znamenitosti.R;

/**
 *
 * @author nikola
 */
public class Settings extends Fragment {

    private CheckBox autoUpdate;
    private CheckBox saveImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        rs.ac.metropolitan.cs330.znamenitosti.service.Settings settings = ((MainActivity) getActivity()).getApplicationContext().getSettings();
        autoUpdate = (CheckBox) view.findViewById(R.id.checkbox_auto_update);
        saveImage = (CheckBox) view.findViewById(R.id.checkbox_save_images);
        autoUpdate.setChecked(settings.getAutoUpdate());
        saveImage.setChecked(settings.getSaveImage());
        return view;
    }
}
