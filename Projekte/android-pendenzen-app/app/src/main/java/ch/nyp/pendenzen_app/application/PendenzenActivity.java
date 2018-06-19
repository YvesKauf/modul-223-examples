package ch.nyp.pendenzen_app.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ch.nyp.pendenzen_app.R;
import ch.nyp.pendenzen_app.connection.Database;
import ch.nyp.pendenzen_app.models.Pendenz;
import ch.nyp.pendenzen_app.models.User;
import ch.nyp.pendenzen_app.util.SharedPrefUtil;


/** Die MainActivity ist die Startansicht unserer App. Die Ansicht beinhaltet eine Listview aller
 * Pendenzen und einen FloatingActionButton. Wenn man auf eine Pendenz drückt geschieht bisher noch nichts.
 * Der FloatingActionButton öffnet die Erstellungsansicht.
 *
 *
 * @author Claudia Carvalho, Jens Scheidmann
 */

public class PendenzenActivity extends BaseActivity {

    public static final int ADD_PENDENZ = 2;

    /** In dieser onCreate werden nur die einzelnen Objekte erzeugt.
     *
     * @param savedInstanceState ist standardmässig drin
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendenzen);
        setTitle(R.string.menu_pendenzen);

        /**
         * Hier wird die Startseite festgelegt.
         * Der Button welcher die Erstellungsansicht öffnet. Es wird ein neuer Intent erstellt.
         *
         */
        FloatingActionButton addButton = findViewById(R.id.add_main_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPendenzActivity.class);
                startActivityForResult(intent, ADD_PENDENZ);
            }
        });

        /** Hier wird die Liste mit Daten aus der Datenbank gefüllt
         *
         */
        prepareListView();
    }

    private void prepareListView() {
        User loggedInUser = SharedPrefUtil.getLoggedInUser(getApplicationContext());
        Database apprenticeDb = new Database(getApplicationContext());
        List<Pendenz> myPendenzen = apprenticeDb.getPendenzenByUserId(loggedInUser.getUserId());

        /**
         * Mit findViewById kann man die view vom layout folder finden
         */
        ListView listView = findViewById(R.id.activity_main_listView);
        listView.setAdapter(new PendenzAdapter(getApplicationContext(), myPendenzen));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_PENDENZ && resultCode == RESULT_OK) {
            //ListView aktualisieren
            prepareListView();
            //Toast ausgeben
            Toast.makeText(getApplicationContext(), getString(R.string.toast_main_gespeichert), Toast.LENGTH_SHORT).show();
        }
    }
}