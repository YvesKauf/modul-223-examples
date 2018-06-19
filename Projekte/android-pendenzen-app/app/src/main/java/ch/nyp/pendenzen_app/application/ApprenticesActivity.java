package ch.nyp.pendenzen_app.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import ch.nyp.pendenzen_app.R;
import ch.nyp.pendenzen_app.connection.Database;
import ch.nyp.pendenzen_app.models.User;

/**
 * Entry point für die App
 *
 * @author Timo Baumberger, Nadine Moser
 */
public class ApprenticesActivity extends BaseActivity {

    public static final int ADD_APPRENTICE = 1;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButtonAdd;
    private ApprenticeAdapter mAdapter;

    private Database apprenticeDb;

    /**
     * Entry point Methode der App
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apprentices);
        setTitle(R.string.menu_apprentices);

        mFloatingActionButtonAdd = findViewById(R.id.floatactionbutton_main_add);
        mFloatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddApprenticeActivity.class);
                startActivityForResult(intent, ADD_APPRENTICE);
            }
        });

        apprenticeDb = new Database(getApplicationContext());

        mRecyclerView = findViewById(R.id.recyclerview_main_apprentices);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ApprenticeAdapter(getApplicationContext(), apprenticeDb.getAllApprentices());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Wenn ein Lernender hinzugefügt wird, wird diese Methode aufgerufen.
     * Diese Methode fügt den lernenden in die Datenbank hinzu und be-
     * nachrichtigt den ApprenticeAdapter.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_APPRENTICE && resultCode == RESULT_OK) {
            String firstName = data.getStringExtra("firstName");
            String lastName = data.getStringExtra("lastName");
            String photoUri = data.getStringExtra("photoUri");

            User newApprentice = new User();
            newApprentice.setFirstname(firstName);
            newApprentice.setLastname(lastName);
            newApprentice.setApprentice(true);
            if (photoUri != null) {
                newApprentice.setProfilpicture(photoUri);
            }

            apprenticeDb.insertUser(newApprentice);
            mAdapter.addApprentice(newApprentice);

            Toast.makeText(getApplicationContext(), getString(R.string.toast_main_gespeichert), Toast.LENGTH_SHORT).show();
        }
    }
}
