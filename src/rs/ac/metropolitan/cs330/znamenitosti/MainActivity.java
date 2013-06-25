package rs.ac.metropolitan.cs330.znamenitosti;

import android.app.ProgressDialog;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CheckBox;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.IOUtils;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import rs.ac.metropolitan.cs330.znamenitosti.gui.GameFragmentActivity;
import rs.ac.metropolitan.cs330.znamenitosti.model.City;
import rs.ac.metropolitan.cs330.znamenitosti.fragment.main.Home;
import rs.ac.metropolitan.cs330.znamenitosti.fragment.main.Settings;
import rs.ac.metropolitan.cs330.znamenitosti.fragment.main.Info;
import rs.ac.metropolitan.cs330.znamenitosti.fragment.main.Help;
import rs.ac.metropolitan.cs330.znamenitosti.model.Image;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;
import rs.ac.metropolitan.cs330.znamenitosti.model.dto.CityDto;
import rs.ac.metropolitan.cs330.znamenitosti.model.dto.Log;
import rs.ac.metropolitan.cs330.znamenitosti.model.dto.SightDto;

/**
 *
 * @author nikola
 */
public class MainActivity extends GameFragmentActivity {

    private City selectedCity;
    private List<View> optionButtons;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        optionButtons = new ArrayList<View>();
        optionButtons.add(findViewById(R.id.button_home));
        optionButtons.add(findViewById(R.id.button_settings));
        optionButtons.add(findViewById(R.id.button_help));
        optionButtons.add(findViewById(R.id.button_info));
        onHome(findViewById(R.id.button_home));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getApplicationContext().getSettings().getAutoUpdate() || City.count() == 0) {
            new Update().execute();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }

    public void setCity(City city) {
        this.selectedCity = city;
    }

    public City getCity() {
        return selectedCity;
    }

    public void onHome(View view) {
        replaceFragment(new Home(selectedCity), view);
    }

    public void onSettings(View view) {
        replaceFragment(new Settings(), view);
    }

    public void onHelp(View view) {
        replaceFragment(new Help(), view);
    }

    public void onInfo(View view) {
        replaceFragment(new Info(), view);
    }

    public void onPlay(View view) {
        Intent intent = new Intent(this, LevelActivity.class);
        intent.putExtra("cityId", selectedCity.getId());
        finish();
        startActivity(intent);
    }

    public void onAutoUpdate(View view) {
        boolean checked = ((CheckBox) findViewById(R.id.checkbox_auto_update)).isChecked();
        getApplicationContext().getSettings().setAutoUpdate(checked);
    }

    public void onSaveImages(View view) {
        boolean checked = ((CheckBox) findViewById(R.id.checkbox_save_images)).isChecked();
        getApplicationContext().getSettings().setSaveImage(checked);
    }

    public void onUpdate(View view) {
        new Update().execute();
    }

    private void replaceFragment(Fragment fragment, View view) {
        setButtonBackground(view);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.meun_fragment_content, fragment);
        transaction.commit();
    }

    private void setButtonBackground(View view) {
        for (View button : optionButtons) {
            button.setBackgroundResource(R.drawable.btn_disable);
        }
        view.setBackgroundResource(R.drawable.btn_pressed);
    }

    private class Update extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, getString(R.string.please_wait), getString(R.string.updating), true, false);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String serverAddress = getString(R.string.server_address);
                String serverPort = getString(R.string.server_port);
                String server = serverAddress + ":" + serverPort;
                long versionNumber = getApplicationContext().getSettings().getAppVersion();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                Log[] logs = restTemplate.getForObject(server + "/logs/" + versionNumber, Log[].class);
                if (logs.length == 0) {
                    return "not updated";
                }
                for (Log log : logs) {
                    if (log.getEntityType().equals("city")) {
                        CityDto cityDto = restTemplate.getForObject(server + "/cities/" + log.getEntityId(), CityDto.class);
                        City.create(cityDto.getName());
                    } else if (log.getEntityType().equals("sight")) {
                        SightDto sightDto = restTemplate.getForObject(server + "/sights/" + log.getEntityId(), SightDto.class);
                        City city = City.byName(sightDto.getCity().getName());
                        URL url = new URL(server + "/images/" + sightDto.getId());
                        URLConnection conn = url.openConnection();
                        Image image = Image.create(IOUtils.toByteArray(conn.getInputStream()));
                        new Sight.Creator()
                                .setName(sightDto.getName())
                                .setDescription(sightDto.getDescription())
                                .setLatitude(sightDto.getLatitude())
                                .setLongitude(sightDto.getLongitude())
                                .setCity(city)
                                .setImage(image)
                                .create();
                    }
                }
                getApplicationContext().getSettings().setAppVersion(logs[logs.length - 1].getLogTime());
            } catch (Exception ex) {
                return "not updated";
            }
            return "updated";
        }

        @Override
        protected void onPostExecute(String result) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (result.equals("updated")) {
                finish();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        }
    }
}
