package ch.nyp.pendenzen_app.application;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import ch.nyp.pendenzen_app.R;
import ch.nyp.pendenzen_app.connection.Database;
import ch.nyp.pendenzen_app.models.User;

/**
 * Created by Denis Rykart on 01.02.2018
 *
 * Manages the register page.
 * Extends AppCompatActivity to take a photo with the camera on the device.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText editText_firstname;
    private EditText editText_lastname;
    private EditText editText_email;
    private EditText editText_password;
    private EditText editText_confirmPassword;
    private Button button_register;

    private static int MIN_PASSWORD_LENGTH = 6;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    String mCurrentPhotoPath;
    Uri photoURI;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.title_register);

        editText_firstname = (EditText) findViewById(R.id.editText_firstName);
        editText_lastname = (EditText) findViewById(R.id.editText_lastName);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_confirmPassword = (EditText) findViewById(R.id.editText_confirmPassword);

        button_register = findViewById(R.id.button_registrieren);
        //ImageButton photo = findViewById(R.id.imageButton_photo);
        imageView = findViewById(R.id.imageview_bildaufnehmen);

        database = new Database(this.getApplicationContext());

        button_register.setOnClickListener(new View.OnClickListener() {

            /**
             * Creates new User if the validate-Method returns true.
             * After it creates a new Intent to start the new Activity (WelcomeActivity)
             * The input of the email gets compared with all other users email. If the email exists it prints out a warning.
             *
             * @author Denis Rykart
             * @param view
             */
            @Override
            public void onClick(View view) {

                if(validate()){
                    String email = editText_email.getText().toString();
                    String password = editText_password.getText().toString();
                    String firstname = editText_firstname.getText().toString();
                    String lastname = editText_lastname.getText().toString();

                    User user = new User(email, password, firstname, lastname);
                    user.setApprentice(true);

                    if(photoURI != null){
                        user.setProfilpicture(photoURI.getPath());
                    }

                    if(database.registerUser(user)){
                        Toast.makeText(getApplicationContext(), "Registriert", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), PendenzenActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        printEmailErrorMessage();
                    }
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            /**
             * This method opens the camera so the user can take a photo. The photo will be saved local on the device.
             *
             * @author Denis Rykart
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(getApplicationContext(), "ch.nyp.pendenzen_app.application.GenericFileProvider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                    }
                }
            }
        });
    }

    /**
     * Sets the path of the photo so it can be shown in the app
     *
     * @author Denis Rykart
     * @param requestCode Have to be the same as CAMERA_REQUEST
     * @param resultCode
     * @param data Explicit Intent
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            imageView.setImageURI(photoURI);
        }
    }


    /**
     * Create a new File and the name of it that every name is unique.
     *
     * @author Denis Rykart
     * @return image (profile picture)
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$"
    );

    /**
     * Checks if the parameter email corresponds to the EMAIL_ADRESS_PATTERN.
     *
     * @param email
     * @return boolean if the email is valid
     */
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    /**
     * Prints Error message if the email already exists
     *
     * @author Denis Rykart
     */
    public void printEmailErrorMessage(){
        editText_email.setError("Es gibt bereits einen User mit dieser Email.");
    }

    /**
     * Validates, as the name of the method says, the inputs of the user.
     * All fields accept the profile picture have to be filled in.
     * The method must be private because it only can be used in this class.
     *
     * @author Denis Rykart
     * @return boolean if the inputs corresponds to the requirements
     */
    public boolean validate(){
        boolean validate = true;

        if(!editText_confirmPassword.getText().toString().equals(editText_password.getText().toString())){
            editText_confirmPassword.setError("Passwörter müssen übereinstimmen");
            editText_password.setError("Passwörter müssen übereinstimmen");

            validate = false;
        } else if(editText_password.getText().length() < MIN_PASSWORD_LENGTH){
            editText_password.setError("Passwort muss mindestens aus 6 Zeichen bestehen");

            validate = false;
        }

        if(editText_firstname.getText().toString().trim().equals("")){
            editText_firstname.setError("Geben sie einen Vornamen ein");

            validate = false;
        }

        if(editText_lastname.getText().toString().trim().equals("")){
            editText_lastname.setError("Geben sie einen Nachnamen ein");

            validate = false;
        }

        if(!checkEmail(editText_email.getText().toString())) {
            editText_email.setError("Ungültige E-Mail");

            validate = false;
        }

        return validate;
    }
}
