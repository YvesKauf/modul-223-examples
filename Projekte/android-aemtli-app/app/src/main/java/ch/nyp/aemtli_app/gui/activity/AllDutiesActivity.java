package ch.nyp.aemtli_app.gui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.nyp.aemtli_app.R;
import ch.nyp.aemtli_app.gui.adapter.DutyAdapter;
import ch.nyp.aemtli_app.model.Duty;
import ch.nyp.aemtli_app.model.UserDuty;
import ch.nyp.aemtli_app.persistence.AppDatabase;
import ch.nyp.aemtli_app.persistence.UserDutyJoinDao;


/**
 * History:
 * 14.05.2018 1.0 Céline Späti add Activity
 * 15.05.2018 1.1 Céline Späti add functions onOptionsItemSelected and onCreateOptionsMenu
 * 15.05.2018 1.2 Severin Zahler DB
 * 15.05.2018 1.3 Sven Bänninger Passing user over bundle
 *
 * @author Céline Späti
 * @version 1.3
 */
public class AllDutiesActivity extends BaseActivity {


    /**
     * Get the date of today and load the view
     * Get a message, if it has none duties
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.showDuties_title);

        //Date of today
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();

        setContentView(R.layout.activity_all_duties);

        TextView dateOfTodayTextView = findViewById(R.id.textView_today);
        dateOfTodayTextView.setText(getWeekDayName(date) + ", " + dateFormat.format(date));


        AppDatabase db = AppDatabase.getAppDb(this);
        UserDutyJoinDao userDutyJoinDao = db.getUserDutyJoinDao();
        String currentDateString = dateFormat.format(date).toString();
        List<UserDuty> allUserDuties = userDutyJoinDao.getDutiesByDate(currentDateString);

        //Return a message, if no duties are defined
        TextView noDutyMessage = findViewById(R.id.textView_noDuties);
        if(allUserDuties.size() <= 0) {
            noDutyMessage.setVisibility(View.VISIBLE);
        } else {
            noDutyMessage.setVisibility(View.GONE);

            List<Duty> dutiesForList = prepareDutyList(allUserDuties);

            //Load the layout of a sigle item
            ListView listView = findViewById(R.id.listview_show_duties);
            //Assign the DutyAdapter
            listView.setAdapter(new DutyAdapter(this, dutiesForList));
        }

    }

    private List<Duty> prepareDutyList(List<UserDuty> userDutiesFromDb) {
        List<Duty> dutiesForListView = new ArrayList<>();
        int i = 0;
        Duty lastDuty = null;
        for(UserDuty userDuty : userDutiesFromDb) {
            if (lastDuty == null ||
                    lastDuty.getDutyId() != userDuty.getDuty().getDutyId()) {

                //Jeweils das Duty vom vorherigen Durchlauf hinzufügen zur Liste mit den Ämtlis
                if (lastDuty != null) {
                    dutiesForListView.add(lastDuty);
                }

                //1.Datensatz
                lastDuty = new Duty();
                lastDuty.setDutyId(userDuty.getDuty().getDutyId());
                lastDuty.setName(userDuty.getDuty().getName());
            }
            lastDuty.addUser(userDuty.getUser());
        }

        //letztes Duty hinzufügen zur Liste
        if (lastDuty != null) {
            dutiesForListView.add(lastDuty);
        }

        return dutiesForListView;
    }

    /**
     * Calculate the weekday form the date of today
     *
     * @param date the date of today
     * @return the weekday as string
     */
    private String getWeekDayName(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String dayName = "";

        switch (dayOfWeek) {
            case 1:
                dayName = getString(R.string.showDuties_label_sunday);
                break;
            case 2:
                dayName = getString(R.string.showDuties_label_monday);
                break;
            case 3:
                dayName = getString(R.string.showDuties_label_tuesday);
                break;
            case 4:
                dayName = getString(R.string.showDuties_label_wednesday);
                break;
            case 5:
                dayName = getString(R.string.showDuties_label_thursday);
                break;
            case 6:
                dayName = getString(R.string.showDuties_label_friday);
                break;
            case 7:
                dayName = getString(R.string.showDuties_label_saturday);
                break;
        }
        return dayName;
    }
}