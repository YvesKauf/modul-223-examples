package ch.nyp.pendenzen_app.util;

import android.content.Context;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;

import java.io.IOException;

/**
 * Diese Klasse wird gebraucht um ein Bild zudrehen
 *
 * @author Steven Ringger
 */

public class ImageRotateUtil {
    /**
     * Für die Ausgabe eines Bildes wird diese Methode aufgerufen damit Picasso weiss um wie viel das Bild gerdeht werden muss
     * @param imageFilePath Uri für das Bild
     * @param mContext
     * @return float mit Anzahl an Grad die das Bild gedreht werden muss
     */
    public static float getCameraPhotoOrientation(Uri imageFilePath, Context mContext) {
        float rotate = 0;
        try {
            ExifInterface exif;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                exif = new ExifInterface(mContext.getContentResolver().openInputStream
                        (imageFilePath));
            } else {
                exif = new ExifInterface(imageFilePath.getPath());
            }
            int exifOrientation = Integer.parseInt(exif.getAttribute(ExifInterface.TAG_ORIENTATION));
            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }
}
