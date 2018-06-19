package ch.nyp.pendenzen_app.application;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.nyp.pendenzen_app.R;
import ch.nyp.pendenzen_app.util.ImageRotateUtil;

/**
 * Klasse, um einen Apprentice hinzuzufügen.
 *
 *
 * @author Timo Baumberger, Steven Ringger, Nadine Moser
 */
public class AddApprenticeActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText mVorname;
    private EditText mNachname;

    private FloatingActionButton mFloatingActionButton;

    private Uri mCurrentPhotoUri;
    private ImageView mImageView;

    /**
     * Entry point Methode der Activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apprentice);
        setTitle(R.string.title_apprentice_add);

        mVorname = findViewById(R.id.edittext_addapprentice_firstname);
        mNachname = findViewById(R.id.edittext_addapprentice_lastname);

        Button btnSave = findViewById(R.id.button_addapprentice_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validateFirstName = validate(mVorname);
                boolean validateLastname = validate(mNachname);

                if (validateFirstName && validateLastname) {
                    Intent intent = new Intent();

                    intent.putExtra("firstName", mVorname.getText().toString());
                    intent.putExtra("lastName", mNachname.getText().toString());

                    if (mCurrentPhotoUri != null) {
                        intent.putExtra("photoUri", mCurrentPhotoUri.toString());
                    }

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        mFloatingActionButton = findViewById(R.id.floatactionbutton_addapprentice_caputre);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }

        });

    }

    /**
     * Startet die Aktivity der Kamera und gibt Speicherobjekt mit.
     *
     */
    private void dispatchTakePictureIntent() {
        mImageView = findViewById(R.id.imageview_addapprentice_portrait);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (imageFile != null) {
                String providerPath = "ch.nyp.pendenzen_app.application.GenericFileProvider";
                Context context = getApplicationContext();
                mCurrentPhotoUri = FileProvider.getUriForFile(
                        context, providerPath, imageFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /**
     * Wird aufgerufen, sobald die Kamera das Bild aufgenommen hat.
     *
     * @param requestCode code mit dem die Activity für die Kamera gestartet wurde.
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Picasso.with(this).load(mCurrentPhotoUri)
                    .rotate(ImageRotateUtil.getCameraPhotoOrientation(mCurrentPhotoUri,this))
                    .resize(300, 300)
                    .centerCrop()
                    .into(mImageView);
        }
    }

    /**
     * speichert das Bild ab.
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix (type)
                storageDir      // directory
        );
        return image;
    }

    /**
     * Validiert den mitgegebenen Text, ob min 1 und max 50 Zeichen eingegeben wurden.
     *
     * @param editText eingegebener Text, welcher zu validieren ist.
     * @return
     */
    public boolean validate(EditText editText){
        String name = editText.getText().toString();
        if(!name.trim().isEmpty()) {
            if (name.length() <= 50) {
                return true;
            } else {
                editText.setError(getString(R.string.edittext_addapprentice_errortext_maxlaenge));
            }
        } else {
            editText.setError(getString(R.string.edittext_addapprentice_errortext_required));
        }
        return false;
    }

    /**
     * Wird aufgerufen, sobald man über die action bar eine Activity zurück möchte.
     * Diese Methode schliesst die aktuelle Activity.
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
