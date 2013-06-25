package rs.ac.metropolitan.cs330.znamenitosti.gui;

import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import rs.ac.metropolitan.cs330.znamenitosti.config.ZnamenitostiApp;

/**
 *
 * @author nikola
 */
public class GameActivity extends android.app.Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.UNKNOWN);
    }

    @Override
    public ZnamenitostiApp getApplicationContext() {
        return (ZnamenitostiApp) super.getApplicationContext();
    }
}
