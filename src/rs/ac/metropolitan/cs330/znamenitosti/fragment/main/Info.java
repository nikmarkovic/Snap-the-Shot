package rs.ac.metropolitan.cs330.znamenitosti.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import rs.ac.metropolitan.cs330.znamenitosti.MainActivity;
import rs.ac.metropolitan.cs330.znamenitosti.R;

/**
 *
 * @author nikola
 */
public class Info extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView text = (TextView) view.findViewById(R.id.info_version);
        long version = ((MainActivity) getActivity()).getApplicationContext().getSettings().getAppVersion();
        text.setText(getString(R.string.version) + " " + version);
        return view;
    }
}
