package rs.ac.metropolitan.cs330.znamenitosti;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import rs.ac.metropolitan.cs330.znamenitosti.gui.GameFragmentActivity;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;

/**
 *
 * @author nikola
 */
public class MapActivity extends GameFragmentActivity {

    private Sight sight;
    private GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long sightId = extras.getLong("sightId");
            sight = Sight.load(Sight.class, sightId);
        }
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode == ConnectionResult.SERVICE_MISSING
                || resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
                || resultCode == ConnectionResult.SERVICE_DISABLED) {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1).show();
        } else {
            setUpMapIfNeeded();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LevelActivity.class);
        intent.putExtra("cityId", sight.city.getId());
        finish();
        startActivity(intent);
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) fragmentManager.findFragmentById(R.id.map)).getMap();
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        map.clear();
        LatLng latLng = new LatLng(sight.latitude, sight.longitude);
        map.addMarker(new MarkerOptions().position(latLng).title(sight.name));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
