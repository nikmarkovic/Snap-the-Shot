package rs.ac.metropolitan.cs330.znamenitosti.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 *
 * @author nikola
 */
public class Settings {

    private SharedPreferences preferences;

    public Settings(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setAppVersion(long version) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("appVersion", version);
        editor.commit();
    }

    public long getAppVersion() {
        return preferences.getLong("appVersion", 0);
    }

    public void setAutoUpdate(boolean autoUpdate) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("autoUpdate", autoUpdate);
        editor.commit();
    }

    public boolean getAutoUpdate() {
        return preferences.getBoolean("autoUpdate", false);
    }

    public void setSaveImage(boolean saveImage) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("saveImage", saveImage);
        editor.commit();
    }

    public boolean getSaveImage() {
        return preferences.getBoolean("saveImage", false);
    }
}
