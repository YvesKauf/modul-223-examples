package ch.nyp.aemtli_app.gui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Collections;
import java.util.List;

import ch.nyp.aemtli_app.R;
import ch.nyp.aemtli_app.model.User;
import ch.nyp.aemtli_app.helper.UserSort;

/**
 * Klasse, um Benutzer alphabetisch in ListItems abzufüllen
 *
 * @author Miriam Streit <miriam.streit@nyp.ch>
 */
public class UserAdapter extends ArrayAdapter<User> {
    private LayoutInflater layoutinflater;
    /**
     * Konstruktor für den Benutzeradapter
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @param context Kontext der Applikation
     * @param users Liste mit Benutzern
     */
    public UserAdapter(Context context, List<User> users) {
        super(context, R.layout.layout_listview_user);
        List<User> sortedUsers = sortListAlphabetically(users);

        addAll(sortedUsers);
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * erzeugt für jedes Benutzerobjekt ein ListItem
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @param position Position des Eintrags, beginnt bei 0
     * @param convertView Viewvorlage. Null, wenn getView() zum ersten Mal aufgerufen wird
     * @param parent Elternview
     * @return convertView Viewvorlage mit gesetzten Attributen
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Bitmap profilePicture;
        if (convertView == null) {
            convertView = layoutinflater.inflate(R.layout.layout_listview_user, null);

            viewHolder = new ViewHolder();
            viewHolder.userNameTextView = convertView.findViewById(R.id.layout_listview_benutzername);
            viewHolder.userPictureImageView = convertView.findViewById(R.id.layout_listview_benutzerbild);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // setzt Daten in zugehöriges Feld
        // Problematik bei Bildern:
        // exisitert das Bild und der Zugriff ist erlaubt, wird dieses Bild gesetzt
        // fehlt der Zugriff auf das Pictures-Verzeichnis, wird das Default-Bild verwendet
        // exisitiert das Bild trotz Zugriff nicht, wird das Default-Bild verwendet
        User user = getItem(position);
        String name = user.getName();
        viewHolder.userNameTextView.setText(name);
        String bildname = "nypAemtli/" + user.getProfilePicturePath();
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File existenzTestBild = new File(path, bildname);
        if (existenzTestBild.exists()) {
            profilePicture = BitmapFactory.decodeFile(existenzTestBild.getPath());
            viewHolder.userPictureImageView.setImageBitmap(profilePicture);
        }
        else {
            viewHolder.userPictureImageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.no_image));
        }


        return convertView;
    }

    /**
     * Methode, um die Benutzer alphabetisch zu sortieren
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @param users Liste von den zu sortierenden Benutzern
     * @return sortierte Benutzerliste
     */
    private List<User> sortListAlphabetically(List<User> users) {
        UserSort comparator = new UserSort();
        Collections.sort(users, comparator);
        return users;
    }

    /**
     * Innere Klasse, um den ViewHolder zu definieren
     * Dieser dient dazu, das Scrollen flüssiger werden zu lassen
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     */
    public static class ViewHolder {
        TextView userNameTextView;
        ImageView userPictureImageView;
    }
}
