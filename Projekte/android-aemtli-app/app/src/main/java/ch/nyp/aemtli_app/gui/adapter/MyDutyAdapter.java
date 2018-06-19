package ch.nyp.aemtli_app.gui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.nyp.aemtli_app.R;
import ch.nyp.aemtli_app.model.Duty;
import ch.nyp.aemtli_app.model.User;

/**
 * AemtliAdapter-Klasse, welche dafür verantwortlich ist, dass in der Listview, welche
 * im AemtliFragment angezeigt wird die aktuellsten Ämtli geladen werden.
 *
 * History:
 * <PRE>
 * 1.0	14.05.2018	Mathias Blaser File erstellt
 * 1.1  16.05.2018  David von Mühlenen File an Datenbank angepasst
 * </PRE>
 *
 * @author David von Mühlenen
 * @version 1.0
 */
public class MyDutyAdapter extends ArrayAdapter<Duty> {

    private LayoutInflater mLayoutInflater;


    /**
     * Standard-Konstruktor
     *
     * @param context Der aktuelle Kontext der App
     * @param duties Liste mit allen AemtliObjekten
     *
     * @since 1.0
     */

    public MyDutyAdapter(Context context, List<Duty> duties) {
        super(context, R.layout.layout_listview_myduty);
        addAll(duties);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Gibt die View eines einzelnen Eintrags in der Liste zurück.
     *
     * @param position Position des anzuzeigenden Eintrags.
     * @param convertView Das alte View, welches wiederbenutzt werden soll. Null wenn kein altes
     * View vorhanden ist.
     * @param parent Das Eltern-View.
     * @return View eines spezifischen ListView-Eintrags
     * @since 1.0
     */

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_listview_myduty, null);

            viewHolder = new ViewHolder();
            viewHolder.aemtliName = convertView.findViewById(R.id.aemtchen_name);
            viewHolder.verantwortlicherName = convertView.findViewById(R.id.verantwortlicher_name);
            viewHolder.verantwortlicherBild = convertView.findViewById(R.id.verantwortlicher_bild);
            viewHolder.stellvertreterName = convertView.findViewById(R.id.stellvertreter_name);
            viewHolder.stellvertreterBild = convertView.findViewById(R.id.stellvertreter_bild);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        List<User> myUsers = getItem(position).getUsers();
        String aemtliName = getItem(position).getName();
        viewHolder.aemtliName.setText(aemtliName);
        String verantwortlicherName = myUsers.get(0).getFirstName() + " " + myUsers.get(0).getLastName();
        viewHolder.verantwortlicherName.setText(verantwortlicherName);

        if(myUsers.get(0).getProfilePicturePath() == null) {
            viewHolder.verantwortlicherBild.setImageResource(R.mipmap.ic_launcher);
        } else {
            Uri verantwortlicherBild = Uri.parse(myUsers.get(0).getProfilePicturePath());
            viewHolder.verantwortlicherBild.setImageURI(verantwortlicherBild);
        }

        if (myUsers.size() > 1) {
            String stellvertreterName = myUsers.get(1).getFirstName() + " " + myUsers.get(1)
                    .getLastName();
            viewHolder.stellvertreterName.setText(stellvertreterName);
            if(myUsers.get(1).getProfilePicturePath() == null) {
                viewHolder.stellvertreterBild.setImageResource(R.mipmap.ic_launcher);
            } else {
                Uri stellvertreterBild = Uri.parse(myUsers.get(1).getProfilePicturePath());
                viewHolder.stellvertreterBild.setImageURI(stellvertreterBild);
            }
        } else {
            viewHolder.stellvertreterName.setText(R.string.myDuties_no_stellvertreter);
			viewHolder.stellvertreterBild.setImageURI(null);
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView aemtliName;
        TextView verantwortlicherName;
        ImageView verantwortlicherBild;
        TextView stellvertreterName;
        ImageView stellvertreterBild;
    }
}
