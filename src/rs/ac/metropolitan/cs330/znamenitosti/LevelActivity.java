package rs.ac.metropolitan.cs330.znamenitosti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import rs.ac.metropolitan.cs330.znamenitosti.fragment.level.InProgress;
import rs.ac.metropolitan.cs330.znamenitosti.fragment.level.Solved;
import rs.ac.metropolitan.cs330.znamenitosti.gui.GameFragmentActivity;
import rs.ac.metropolitan.cs330.znamenitosti.model.City;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;

/**
 *
 * @author nikola
 */
public class LevelActivity extends GameFragmentActivity {

    private City city;
    private Sight selectedSight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long cityId = extras.getLong("cityId");
            city = City.load(City.class, cityId);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public City getCity() {
        return city;
    }

    public Sight getSelectedSight() {
        return selectedSight;
    }

    public void onSolved(Sight sight) {
        selectedSight = sight;
        replaceFragment(new Solved(sight));
    }

    public void onInProgress(Sight sight) {
        selectedSight = sight;
        replaceFragment(new InProgress(sight));
    }

    public void onCamera(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra("sightId", selectedSight.getId());
        finish();
        startActivity(intent);
    }

    public void onMap(View viwe) {
        if (city.remainingHelps() > 0) {
            city.helps++;
            city.save();
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("sightId", selectedSight.getId());
            finish();
            startActivity(intent);
        }
    }

    public void onSolvedMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("sightId", selectedSight.getId());
        finish();
        startActivity(intent);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.level_fragment_content, fragment);
        transaction.commit();
    }
}
