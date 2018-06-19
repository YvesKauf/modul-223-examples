package ch.nyp.pendenzen_app.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.nyp.pendenzen_app.R;
import ch.nyp.pendenzen_app.models.Pendenz;

/** Die PendenzAdapter wird genutzt um Daten in die ListView in der MainActivity zu füllen.
 *
 * @author Claudia Carvalho
 */
class PendenzAdapter extends ArrayAdapter<Pendenz> {

    private final LayoutInflater mLayoutInflater;


    /**
     * Der Pendenzadapter
     * @param context
     * @param items hier werden die items (hier der Pendenzen) mitgegeben
     */
    public PendenzAdapter(Context context, List<Pendenz> items) {
        super(context, R.layout.layout_pendenzen_item);
        addAll(items);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    /**
     * Viewholder ist dafür da, das es schneller scrollt
     * getView ist der Unterschied der eine Listview() zum Spinner hat, Spinner hat getDropdownView()
     * Hier wird genau ein Element erzeugt. getView() wird vom Framework selber aufgerufen.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_pendenzen_item, null);

            viewHolder = new ViewHolder();
            viewHolder.itemTitleTextView = convertView.findViewById(R.id.textview_itemtitle_listview);
            viewHolder.itemDateTextView = convertView.findViewById(R.id.textview_itemdate_listview);
            viewHolder.itemUrgencyDropdown = convertView.findViewById(R.id.textview_itemurgency_listview);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String name = getItem(position).getPendenzTitle();
        viewHolder.itemTitleTextView.setText(name);

        String date = getItem(position).getPendenzDate();
        viewHolder.itemDateTextView.setText(date);

        String urgency = getItem(position).getPendenzUrgency();
        viewHolder.itemUrgencyDropdown.setText(urgency);

        return convertView;
    }

    public static class ViewHolder {
        TextView itemTitleTextView;
        TextView itemUrgencyDropdown;
        TextView itemDateTextView;
    }
}
