package ch.nyp.aemtli_app.gui.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.nyp.aemtli_app.R;
import ch.nyp.aemtli_app.gui.adapter.MyDutyAdapter;
import ch.nyp.aemtli_app.helper.Helper;
import ch.nyp.aemtli_app.model.Duty;
import ch.nyp.aemtli_app.model.User;
import ch.nyp.aemtli_app.model.UserDuty;
import ch.nyp.aemtli_app.persistence.AppDatabase;
import ch.nyp.aemtli_app.persistence.UserDutyJoinDao;

public class MyDutiesActivity extends BaseActivity {

	private TextView mDateTextView;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_duties);
		setTitle(R.string.myDuties_title);

		mDateTextView = findViewById(R.id.heutigesDatum);
		mListView = findViewById(R.id.listview_aetmli);

		initializeGui();
	}

	private void initializeGui() {
		//Aktuelles Datum über Date auslesen
		DateFormat dateFormat = new SimpleDateFormat("dd'.'MM'.'yyyy");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		mDateTextView.setText(dateFormat.format(date));

		UserDutyJoinDao userDutyJoinDao = AppDatabase.getAppDb(getApplicationContext())
				.getUserDutyJoinDao();
		//Liste aller Userämtli für den heutigen Tag wird geholt

		String currentDateString = dateFormat.format(date).toString();
		List<UserDuty> allUserDuties = userDutyJoinDao.getDutiesByDate(currentDateString);

		//Integer zur Speicherung der letzten AemtliID
		int lastDutyId = 0;
		int loggedInUserId = Helper.getLoggedInUserId(getApplicationContext());
		List<Duty> dutiesForListView = new ArrayList<>();
		int i = 0;
		for(UserDuty userDuty : allUserDuties) {

			int userDutyUserId = userDuty.getUser().getUserId();
			if(userDutyUserId == loggedInUserId) {
				Duty dutyForUser = userDuty.getDuty();

				if(lastDutyId != dutyForUser.getDutyId()) {
					// 1.Datensatz
					//Hauptuser - sich selber
					dutyForUser.addUser(userDuty.getUser());

					//Zweiter User
					UserDuty nextUserDuty = allUserDuties.get(i + 1);
					if (nextUserDuty.getDuty().getDutyId() == dutyForUser.getDutyId()) {
						User secondUser = nextUserDuty.getUser();
						dutyForUser.addUser(secondUser);
					}
				} else {
					// 2.Datensatz
					User firstUser = allUserDuties.get(i - 1).getUser();
					dutyForUser.addUser(firstUser);
					dutyForUser.addUser(userDuty.getUser());
				}
				dutiesForListView.add(dutyForUser);
			}

			lastDutyId = userDuty.getDuty().getDutyId();
			i++;
		}

		//Adapter zur Listview hinzufügen
		mListView.setAdapter(new MyDutyAdapter(getApplicationContext(), dutiesForListView));
	}
}
