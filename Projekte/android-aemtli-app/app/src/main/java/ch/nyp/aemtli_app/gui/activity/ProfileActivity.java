package ch.nyp.aemtli_app.gui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import ch.nyp.aemtli_app.R;
import ch.nyp.aemtli_app.helper.Helper;
import ch.nyp.aemtli_app.model.User;
import ch.nyp.aemtli_app.persistence.AppDatabase;
import ch.nyp.aemtli_app.persistence.UserDao;


/**
 * Class for the profile-activity. In this activity the user can change his email address, password
 * and profile picture. The picture can only be an image from the gallery.
 *
 * History:
 * 08.05.2018 Sven Bänninger created Class
 * @author Sven Bänninger
 * @version 1.0
 */
public class ProfileActivity extends BaseActivity {

    private User mLoggedInUser;
    private String mErrorMessage;
    private EditText mEmail;
    private EditText mOldPassword;
    private EditText mNewPassword;
    private EditText mNewPasswordRepeat;
    private ImageView mImage;
    private Uri mSelectedImageUri;
    private final int PICK_IMAGE = 4321;
    private final int ACTION_BACK_BUTTON = 16908332;
    private final Pattern EMAIL_PATTERN = Patterns.EMAIL_ADDRESS;
    private final Pattern PATTERN_PASSWORD = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!:;?`'\"£¢¦°ç*+|/´~\\\\<>\\-_])(?=\\S+$).{8,}$");
    private UserDao mUserDao;


    /**
     * OnClickListener for Buttons in profile-activity. Checks witch button was clicked and performs
     * an action based on the result. It either opens the gallery or saves the inputs.
     *
     * @since 1.0
     */
    private View.OnClickListener profileListener = new View.OnClickListener() {

        @Override
        public void onClick(View sendButton) {
            switch (sendButton.getId()) {
                case R.id.button_change_picture:
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE);
                    break;
                case R.id.button_save_profile:
                    boolean error = validateInputs();
                    if (error) {
                        showErrorDialog(mErrorMessage);
                    } else {
                        mLoggedInUser.setEmail(mEmail.getText().toString());
                        String newPassword = mNewPassword.getText().toString();
                        String newPasswordRepeat = mNewPasswordRepeat.getText().toString();

                        if (newPassword.equals(newPasswordRepeat)) {
                            mLoggedInUser.setPassword(newPassword);
                        }
                        mUserDao.update(mLoggedInUser);
                        Toast.makeText(getApplicationContext(), getString(R.string.success_title_profile), Toast.LENGTH_LONG).show();
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.profile_title);

        mUserDao = AppDatabase.getAppDb(this.getApplicationContext()).getUserDao();

        mEmail = findViewById(R.id.input_email);
        mOldPassword = findViewById(R.id.input_password_old);
        mNewPassword = findViewById(R.id.input_password_new);
        mNewPasswordRepeat = findViewById(R.id.input_password_new_repeat);
        mImage = findViewById(R.id.profile_image);
        Button changePicture = findViewById(R.id.button_change_picture);
        Button saveProfile = findViewById(R.id.button_save_profile);

        saveProfile.setOnClickListener(profileListener);
        changePicture.setOnClickListener(profileListener);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int loggedInUserId = Helper.getLoggedInUserId(getApplicationContext());

        List<User> users = mUserDao.getAll();
        mLoggedInUser = mUserDao.getById(loggedInUserId);
        String imagePath = mLoggedInUser.getProfilePicturePath();

        if (imagePath != null) {
            Glide.with(getApplicationContext()).load(imagePath).into(mImage);
        }

        mEmail.setText(mLoggedInUser.getEmail());
    }

    /**
     * Method which is performed after an other activity is opened which returns a value. In this
     * case it is used for the gallery. The method checks then if everything went well and if the
     * request is the same as the one for the gallery (checked by requestCode). When no problems
     * occurred then the chosen image is copied to the picture folder and the then the picture is set
     * with the Glide library.
     *
     * @param requestCode the Code which identifies the request
     * @param resultCode  Code of the return value
     * @param data        Data from the chosen image
     * @since 1.0
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    mSelectedImageUri = data.getData();

                    //Ausgewähltes Foto kopieren
                    Uri imageUri = copyImage(mSelectedImageUri);
                    String imagePath = imageUri.getPath();

                    //Pfad des "neuen" Fotos in den Shared Preferences ablegen
                    mLoggedInUser.setProfilePicturePath(imagePath);

                    //"Neues" Foto in der ImageView anzeigen
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        showToast(getString(R.string.error_dialog_message_unknown));
                    }
                    Glide.with(getApplicationContext()).load(imagePath).into(mImage);
                } else {
                    showToast("Kein Bild ausgewählt");
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                showToast("Abgebrochen");
            }
        }
    }

    /**
     * This method validates the entered email address and passwords on the profile site.
     * The email address is checked for empty inputs, a specific pattern and uniqueness. The pattern is from the
     * Patterns class from Android utils. For password validation is a additional method called.
     *
     * @return errorMessage, message depending on the validation error.
     * @since 1.0
     */
    private boolean validateInputs() {
        boolean hasError = false;
        mErrorMessage = "";
        if (TextUtils.isEmpty(mEmail.getText())) {
            mErrorMessage = getString(R.string.error_dialog_message_profile_field_required);
            ;
            hasError = true;
        } else if (!EMAIL_PATTERN.matcher(mEmail.getText()).matches()) {
            mErrorMessage = getString(R.string.error_dialog_message_profile_invalid_email);
            ;
            hasError = true;
        } else if (!mEmail.getText().toString().equals(mLoggedInUser.getEmail())) {
            List<User> users = mUserDao.getAll();
            for (User user : users) {
                if (user.getEmail().equals(mEmail.getText().toString())) {
                    mErrorMessage = getString(R.string.error_dialog_message_profile_unicque_email);
                    hasError = true;
                }
            }
        } else {
            int passwordCounter = 0;
            if (!TextUtils.isEmpty(mOldPassword.getText())) {
                passwordCounter++;
            }
            if (!TextUtils.isEmpty(mNewPassword.getText())) {
                passwordCounter++;
            }
            if (!TextUtils.isEmpty(mNewPasswordRepeat.getText())) {
                passwordCounter++;
            }
            if (passwordCounter > 0) {
                if (passwordCounter < 3) {
                    mErrorMessage = getString(R.string.error_dialog_message_profile_field_required);
                    hasError = true;
                } else {
                    String passwordState = validatePasswords();
                    if (!passwordState.equals("")) {
                        mErrorMessage = passwordState;
                        hasError = true;
                    }
                }
            }
        }
        return hasError;
    }

    /**
     * Method which checks the entered passwords if any of the password fields is not empty.
     * First the old password is compared with the current password of the logged in user. Next the
     * new password is checked for a specific pattern. The pattern is a String saved as a
     * Pattern Object. In the end the new passwords are checked if they are exactly the same.
     *
     * @return passwordMessage, message depending on the password validation.
     * @since 1.0
     */
    public String validatePasswords() {
        String passwordMessage = "";

        if (!PATTERN_PASSWORD.matcher(mNewPassword.getText()).matches()) {
            passwordMessage = getString(R.string.error_dialog_message_profile_invalid_password);
        } else if (!mNewPassword.getText().toString().equals(mNewPasswordRepeat.getText().toString())) {
            passwordMessage = getString(R.string.error_dialog_message_profile_passwords_not_matching);
        }
        return passwordMessage;
    }

    /**
     * Basic method to show a toast with a message.
     *
     * @param message the message to display
     */
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
    }

    /**
     * Show a dialog with an error message and an "Okay" button. Is shown if there are any errors in
     * the input fields.
     *
     * @param message the error message to display
     */
    private void showErrorDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder
                .setMessage(message)
                .setTitle(getString(R.string.error_dialog_title_profile_wrong))
                .setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Dialog which is activated when the user clicks the back button in the action bar. The dialog
     * has two buttons. if the button "Okay" is pressed, the users is sent back to the parent activity
     * ShowDutiesActivity. If the button "Nein" is pressed, nothing happens and the user stays on the
     * profile site.
     */
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder
                .setMessage(getString(R.string.profile_dialog_message_quit))
                .setTitle(getString(R.string.profile_dialog_title_quit))
                .setPositiveButton(R.string.general_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.general_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Method which is called when an menu item in the Actionbar is clicked. Checks if the clicked
     * button was the back button and opens the dialog if it was. The item ID had to be written as a
     * Constant after several tries of using the values android provides. Non of these provided values
     * were working.
     *
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId) {
            case ACTION_BACK_BUTTON:
                showAlertDialog();
                return true;
        }
        return false;
    }

    /**
     * Origin: https://github.com/joe-nyp/modul-335-examples/blob/master/ImageGalleryExample/app/src/main/java/ch/nyp/imagegalleryexample/MainActivity.java
     * <p>
     * Copies the chosen picture to the open picture folder. The name of the chosen picture
     * consists of the time and date. The app also has to ask for the permission to write the new
     * file.
     *
     * @param imageToCopy
     * @return
     */
    private Uri copyImage(Uri imageToCopy) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "AllTodaysDuties" + timeStamp + ".jpg";

        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = new File(path, imageFileName);

        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission
                    .WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                        .WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageToCopy);
            outputStream = new FileOutputStream(image);
            copyStream(inputStream, outputStream);
        } catch (IOException exception) {
            showToast("Fehler beim kopieren des Bildes");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {

            }
        }
        return Uri.fromFile(image);
    }

    /**
     * Origin: https://github.com/joe-nyp/modul-335-examples/blob/master/ImageGalleryExample/app/src/main/java/ch/nyp/imagegalleryexample/MainActivity.java
     * <p>
     * Copies the file byte for byte.
     *
     * @param input  InputStream (source)
     * @param output OutputStream (destination)
     * @throws IOException
     */
    private void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
}
