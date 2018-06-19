package ch.nyp.aemtli_app.gui.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ch.nyp.aemtli_app.R;
import ch.nyp.aemtli_app.gui.adapter.UserAdapter;
import ch.nyp.aemtli_app.model.User;
import ch.nyp.aemtli_app.persistence.AppDatabase;
import ch.nyp.aemtli_app.helper.Helper;

/**
 * Klasse, die das GUI für die Ansicht mit allen Lernenden handelt
 *
 * @author Miriam Streit <miriam.streit@nyp.ch>
 */
public class ApprenticesActivity extends BaseActivity {

    /**
     * Methode, die beim Erstellen der Activity als Erstes aufgerufen wird
     *
     * @param savedInstanceState letzter gespeicherter Zustand
     * @author Miriam Streit <miriam.streit@nyp.ch>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apprentices);
        setTitle(R.string.allelernendenactivity_activityname);

        Boolean rechteVorhanden = Helper.canReadExtStorage(getApplicationContext(), this);
        if (rechteVorhanden) {
            setzeDatenInListView();
        }

    }

    /**
     * Wird nach Berechtigungen gefragt, wird diese Funktion automatisch aufgerufen
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @param requestCode Code des Requests
     * @param permissions Array mit Erlaubnissen
     * @param grantResults Array mit Resultaten
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 3) {
            setzeDatenInListView();
        }
    }

    /**
     * Bevor die Daten in die Liste gesetzt werden dürfen, müssen die Berechtigungen geprüft werden
     * Da dieser Code von mehreren Orten aus aufgerufen werden muss, ist er als Methode ausgelagert
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     */
    private void setzeDatenInListView() {
        List<User> benutzerliste = AppDatabase.getAppDb(getApplicationContext()).getUserDao()
                .getAllApprentices();
        if(benutzerliste.size() == 0) {
            TextView keineLernendenLabel = findViewById(R.id.layout_listview_textview_keinelernenden);
            keineLernendenLabel.setText(getResources().getString(R.string.allelernendenactivity_keinelernende));
        }
        else {
            ListView lernendenListe = findViewById(R.id.allelernendenactivity_liste);
            UserAdapter adapter = new UserAdapter(this, benutzerliste);
            lernendenListe.setAdapter(adapter);
            lernendenListe.setFastScrollEnabled(true);
        }
    }
}
