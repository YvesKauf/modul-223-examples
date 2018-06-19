package ch.nyp.aemtli_app.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.nyp.aemtli_app.R;
import ch.nyp.aemtli_app.model.Duty;
import ch.nyp.aemtli_app.model.User;
import ch.nyp.aemtli_app.persistence.AppDatabase;
import ch.nyp.aemtli_app.persistence.DutyDao;
import ch.nyp.aemtli_app.persistence.UserDutyJoinDao;

/**
 * Adapter of Listview
 *
 * History:
 * 14.05.2018 1.0 Céline Späti add class
 * 22.05.2018 1.1 Severin Zahler added date filter to duties.
 *
 * @author Céline Späti
 * @version 1.0
 */
public class DutyAdapter extends ArrayAdapter<Duty> {

    private LayoutInflater mLayoutInflater;

    /**
     * Constructor. Initialize member variable.
     *
     * @param context Context of the application.
     *
     * @author Céline Späti, Severin Zahler
     * @version 1.0
     */
    public DutyAdapter(Context context, List<Duty> duties) {
        super(context, R.layout.layout_listview_duty);
        addAll(duties);

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     *  Returns the view of a single listitem
     *  Always open for every listitem which is show
     *  Not for the ones which are only see, if scroll
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     *
     * @author Céline Späti
     * @version 1.0
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        //For a flow scroll, it just load it at the first time
        ViewHolder viewHolder;
        if (convertView ==  null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_listview_duty,null);

            viewHolder = new ViewHolder();
            viewHolder.dutyNameTextView = convertView.findViewById(R.id.textView_dutyName);
            viewHolder.apprenticeNameTextView = convertView.findViewById(R.id.textView_apprenticename);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Assemble data to display
        //Name of duty
        Duty currentDuty = getItem(position);
        String dutyName = currentDuty.getName();

        //Apprentice names for duty
        String apprenticeNames = "";
        for (User apprentice : currentDuty.getUsers()) {
            apprenticeNames += apprentice.getFirstName() + " " + apprentice.getLastName() + "\n";
        }
        apprenticeNames = apprenticeNames.trim(); //Remove excessive line breaks;

        //Add data to view
        viewHolder.dutyNameTextView.setText(dutyName);
        viewHolder.apprenticeNameTextView.setText(apprenticeNames);

        return  convertView;
    }

    /**
     *
     * @author Céline Späti
     * @version 1.0
     */
    public static class ViewHolder {
        TextView dutyNameTextView;
        TextView apprenticeNameTextView;
    }
}
