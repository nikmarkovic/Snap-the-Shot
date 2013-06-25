package rs.ac.metropolitan.cs330.znamenitosti;

import java.util.List;
import java.io.IOException;
import java.util.Date;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import rs.ac.metropolitan.cs330.znamenitosti.gui.GameActivity;
import rs.ac.metropolitan.cs330.znamenitosti.model.Sight;
import rs.ac.metropolitan.cs330.znamenitosti.service.TakePicture;

/**
 *
 * @author nikola
 */
public class CameraActivity extends GameActivity implements SurfaceHolder.Callback {

    private Sight sight;
    private Camera camera;
    private boolean cameraview = false;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        surfaceView = (SurfaceView) findViewById(R.id.camera_view);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long sightId = extras.getLong("sightId");
            sight = Sight.load(Sight.class, sightId);
        }

        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        View view = inflater.inflate(R.layout.camera_overlay, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.overlay_image);
        imageView.setAlpha(0.5f);
        byte[] data = sight.image.data;
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        Drawable drawable = new BitmapDrawable(view.getResources(), bmp);
        imageView.setBackgroundDrawable(drawable);
        LayoutParams layoutParamsControl = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addContentView(view, layoutParamsControl);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, LevelActivity.class);
        intent.putExtra("cityId", sight.city.getId());
        finish();
        startActivity(intent);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        camera = getCameraInstance();
        Pair<Integer, Integer> minSize = minSize(16d, 9d);
        minSize = (minSize == null) ? minSize(16d, 10d) : minSize;
        Camera.Parameters cp = camera.getParameters();
        cp.setPictureSize(minSize.first, minSize.second);
        cp.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        camera.setParameters(cp);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (cameraview) {
            camera.stopPreview();
            cameraview = false;
        }
        if (camera != null) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                cameraview = true;
            } catch (IOException e) {
            }
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        cameraview = false;
    }

    public void onTakePicture(View view) {
        boolean savePicture = getApplicationContext().getSettings().getSaveImage();
        camera.takePicture(null, null, new TakePicture(this, savePicture, sight.image) {
            public void onSolved() {
                sight.solved = true;
                sight.solvedTime = new Date();
                sight.save();
                Intent intent = new Intent(CameraActivity.this, LevelActivity.class);
                intent.putExtra("cityId", sight.city.getId());
                finish();
                startActivity(intent);
            }
        });
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    private Pair<Integer, Integer> minSize(double aspectWidth, double aspectHeight) {
        Camera.Parameters cp = camera.getParameters();
        List<Size> supportedSizes = cp.getSupportedPictureSizes();
        int w = Integer.MAX_VALUE, h = Integer.MAX_VALUE;
        for (Size size : supportedSizes) {
            if (size.width > 900 && size.width < w && Math.abs((double) size.width / (double) size.height - aspectWidth / aspectHeight) < 0.05) {
                w = size.width;
                h = size.height;
            }
        }
        return (w != Integer.MAX_VALUE && h != Integer.MAX_VALUE) ? new Pair<Integer, Integer>(w, h) : null;
    }
}
