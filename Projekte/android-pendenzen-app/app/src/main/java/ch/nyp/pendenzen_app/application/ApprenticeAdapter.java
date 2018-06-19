package ch.nyp.pendenzen_app.application;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ch.nyp.pendenzen_app.R;
import ch.nyp.pendenzen_app.models.User;
import ch.nyp.pendenzen_app.util.ImageRotateUtil;

/**
 * Diese Klasse lädt die Daten ins GUI.
 *
 * @author Timo Baumberger
 */
public class ApprenticeAdapter extends RecyclerView.Adapter<ApprenticeAdapter.ViewHolder> {

    private List<User> mApprentices;
    private Context mContext;

    public ApprenticeAdapter(Context context, List<User> apprentices) {
        mContext = context;
        mApprentices = apprentices;
    }

    /**
     * Definiert ein Feld in der RecyclerView.
     *
     * @param parent
     * @param viewType
     * @return Gibt den ViewHolder für das layout_apprentice zurück
     */
    @Override
    public ApprenticeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View apprenticeView = LayoutInflater.from(mContext).inflate(R.layout.layout_apprentice, parent, false);
        ViewHolder viewHolder = new ViewHolder(apprenticeView);

        return viewHolder;
    }

    /**
     * Setzt den Vornamen, Nachnamen und das Bild eines Lernenden
     *
     * @param holder
     * @param position die aktuelle Position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User apprentice = mApprentices.get(position);

        TextView firstNameField = holder.firstNameTextView;
        firstNameField.setText(apprentice.getFirstname());

        TextView lastNameField = holder.lastNameTextView;
        lastNameField.setText(apprentice.getLastname());

        ImageView imageView = holder.imageView;
        String photoString = apprentice.getProfilpicture();
        if (photoString != null) {
            Uri uri = Uri.parse(photoString);
            Picasso.with(mContext).load(uri)
                    .rotate(ImageRotateUtil.getCameraPhotoOrientation(uri, mContext))
                    .resize(300, 300)
                    .centerCrop()
                    .into(imageView);
        } else {
            Picasso.with(mContext).load(R.drawable.avatar)
                    .resize(300, 300)
                    .centerCrop()
                    .into(imageView);
        }
    }

    /**
     * Gibt die Anzahl lernende zurück
     *
     * @return Anzahl Lernende
     */
    @Override
    public int getItemCount() {
        return mApprentices.size();
    }

    /**
     * Fügt einen neuen Lernenden hinzu
     *
     * @param apprentice welcher hinzugefügt wird
     */
    public void addApprentice(User apprentice) {
        mApprentices.add(apprentice);
        notifyDataSetChanged();
    }

    /**
     * Bessere Performance beim Scrollen (bei vielen Elementen)
     *
     * @author Timo Baumberger
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView firstNameTextView;
        public TextView lastNameTextView;

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            firstNameTextView = itemView.findViewById(R.id.textview_apprentice_firstname);
            lastNameTextView = itemView.findViewById(R.id.textview_apprentice_lastname);
            imageView = itemView.findViewById(R.id.imageview_apprentice_portrait);
        }
    }
}
