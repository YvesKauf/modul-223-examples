package ch.nyp.pendenzen_app.application;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ch.nyp.pendenzen_app.R;
import ch.nyp.pendenzen_app.connection.Database;
import ch.nyp.pendenzen_app.models.Pendenz;
import ch.nyp.pendenzen_app.util.SharedPrefUtil;

/**Die CreateActivity ist die Ansicht welche erscheint wenn man auf den FloatingActionButton auf
 * der Startansicht klickt. In dieser Ansicht erscheint ein Formular mit welchem man eine Pendenz
 * erstellen kann.
 *
 *
 * @author Dylan Schmid, Jens Scheidmann
 */

public class AddPendenzActivity extends AppCompatActivity {

    public static final String INTENT_KEY_DATE_BESTAETIGUNG = "date_pendenz";
    public static final String INTENT_KEY_TITLE_BESTAETIGUNG = "title_pendenz";
    public static final String INTENT_KEY_DESCRIPTION_BESTAETIGUNG = "description_pendenz";
    public static final String INTENT_KEY_URGENCY_BESTAETIGUNG = "urgency_pendenz";

    private EditText editTextDate;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerUrgency;


    /** In dieser onCreate werden die einzelnen objekte erstellt. Dazu werden zwei OnClickListener
     * erstellt. Ein Datepicker und die Validierung welche überprüft ob alle Pflichtfelder ausgefüllt
     * sind und wenn diese ausgefüllt sind diese an eine weitere Ansicht weitergibt, die BestaetigungActivity.
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_add_pendenz);
        setTitle(R.string.title_apprentice_add);

        editTextDate = findViewById(R.id.editText_create_date);
        editTextTitle = findViewById(R.id.editText_create_title);
        editTextDescription = findViewById(R.id.editText_create_description);
        spinnerUrgency = findViewById(R.id.spinner_create_urgency);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getApplicationContext(), R.array.create_spinner_urgency, android.R.layout.simple_spinner_item);
        spinnerUrgency.setAdapter(adapterSpinner);
        spinnerUrgency.setSelection(1);

        Button commit = findViewById(R.id.button_create_commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateTitle())
                {
                    /**Falls das Pflichtfeld nicht ausgefüllt worden ist wird eine Nachricht ausgegeben
                     * welche einem sagt dass man das Feld ausfüllen muss
                     */

                    Toast.makeText(AddPendenzActivity.this, R.string.exception_title, Toast.LENGTH_SHORT).show();
                } else if (validateDescription(editTextDescription.getText().toString())){
                    /** Falls die Beschreibung zu lang ist wird ein Toast ausgegeben
                     *
                     */

                    Toast.makeText(AddPendenzActivity.this, R.string.exception_too_long_description, Toast.LENGTH_SHORT).show();
                } else
                {
                	//Pendenz zur DB hinzufügen
					String date = editTextDate.getText().toString();
					String title = editTextTitle.getText().toString();
					String description = editTextDescription.getText().toString();
					String urgency = spinnerUrgency.getSelectedItem().toString();
					int userId = SharedPrefUtil.getLoggedInUser(getApplicationContext())
                            .getUserId();

					Pendenz pendenz = new Pendenz(title, description, date, urgency);
					pendenz.setIdUser(userId);
					Database apprenticeDb = new Database(getApplicationContext());
					apprenticeDb.insertPendenz(pendenz);

					//Activity schliessen & Pendenzen-Liste aktualisieren
                    setResult(RESULT_OK);
					finish();
                }
            }
        });

        editTextDate.setKeyListener(null);

        /** Der onClickListener welcher den Datepicker öffnet. */
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View sendEditText) {
                new DatePickerDialog(AddPendenzActivity.this,
                        date,
                        untilCalendar.get(Calendar.YEAR),
                        untilCalendar.get(Calendar.MONTH),
                        untilCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
    }


    /**Die Validierungsmethode welche überprüft ob der Titel leer ist oder nicht und ob der Titel länger ist als 50 Zeichen.
     *
     * @return der Rückgabewert ist ein boolean, welcher true ist wenn der mitgegebene String leer
     * ist und false ist wenn dieser String nicht leer ist.
     */
    public boolean validateTitle()
    {
        if (editTextTitle.getText().toString().isEmpty() || editTextTitle.getText().toString().equals("")) {
            return false;

        }else return editTextTitle.getText().toString().length() < 50;
    }

    /** Eine Methode um zzu überprüfen ob ein String länger ist als 500 Zeichen
     *
     * @param stringToCheck der String welcher überprüft werden muss
     * @return falls der String länger ist als 500 Zeichen dann gibt er true zurück, sonst false
     */
    public boolean validateDescription(String stringToCheck)
    {
        return stringToCheck.length() > 500;
    }

    /** Dieser Override sorgt dafür dass der Pfeil oben links in der Toolbar bei betätigung einen
     * auf die zuletzt geöffnete Aktivität bringt.
     *
     * @return Der Rückgabewert ist ein boolean Wert, und da es nur etwas aktivieren soll wird es
     * immer true sein
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    /** Hier wird der Datepicker erstellt
     *
     * @author Dylan Schmid
     */
    Calendar untilCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            untilCalendar.set(Calendar.YEAR, year);
            untilCalendar.set(Calendar.MONTH, month);
            untilCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd/MM/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

            String date = sdf.format(untilCalendar.getTime());

            editTextDate.setText(date);
        }
    };




}

