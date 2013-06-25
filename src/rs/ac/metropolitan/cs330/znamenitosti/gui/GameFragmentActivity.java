package rs.ac.metropolitan.cs330.znamenitosti.gui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.view.WindowManager;
import rs.ac.metropolitan.cs330.znamenitosti.config.ZnamenitostiApp;

/**
 *
 * @author nikola
 */
public class GameFragmentActivity extends FragmentActivity {

    protected FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public ZnamenitostiApp getApplicationContext() {
        return (ZnamenitostiApp) super.getApplicationContext();
    }
}
