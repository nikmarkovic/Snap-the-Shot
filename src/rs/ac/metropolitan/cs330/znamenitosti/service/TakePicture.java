package rs.ac.metropolitan.cs330.znamenitosti.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.hardware.Camera;
import android.os.Environment;
import android.app.ProgressDialog;
import android.content.Context;
import rs.ac.metropolitan.cs330.znamenitosti.R;
import rs.ac.metropolitan.cs330.znamenitosti.model.Image;

/**
 *
 * @author nikola
 */
public abstract class TakePicture implements Camera.PictureCallback {

    private boolean savePicture;
    private Image template;
    private Context ctx;

    public TakePicture(Context ctx, boolean savePicture, Image template) {
        this.savePicture = savePicture;
        this.template = template;
        this.ctx = ctx;
    }

    public void onPictureTaken(final byte[] data, final Camera camera) {
        final ProgressDialog dialog = ProgressDialog.show(ctx, ctx.getString(R.string.please_wait), ctx.getString(R.string.comparing));
        new Thread(new Runnable() {
            public void run() {
                if (savePicture && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    savePicture(data);
                }
                Image image = new Image();
                image.data = data;
                int result = template.compareTo(image);
                dialog.dismiss();
                if (result > 70) {
                    onSolved();
                } else {
                    camera.startPreview();
                }
            }
        }).start();
    }

    public abstract void onSolved();

    private void savePicture(byte[] data) {
        File pictureFile = getOutputMediaFile();
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Znamenitosti");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }
}
