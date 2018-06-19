package ch.nyp.pendenzen_app.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import ch.nyp.pendenzen_app.R;
import ch.nyp.pendenzen_app.models.User;
import ch.nyp.pendenzen_app.util.SharedPrefUtil;

/**
 * Basisklasse der App. Erstellt je nach eingeloggter Rolle das entsprechende ActionBar-Menu.
 * Berufsbildner und Lernender haben weisen ein unterschiedliches Menü auf.
 */
public class BaseActivity extends AppCompatActivity {

	private boolean mIsApprenticeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		User loggedInUser = SharedPrefUtil.getLoggedInUser(getApplicationContext());
		mIsApprenticeView = loggedInUser.isApprentice();
	}

	/**
	 * Create the ActionBar-Menu from the XML-File menu/menu_apprentice.xmlce.xml
	 *
	 * @param menu the menu in which the menu items are
	 * @return true, because the menu should be visible
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mIsApprenticeView) {
			getMenuInflater().inflate(R.menu.menu_apprentice, menu);
		} else {
			getMenuInflater().inflate(R.menu.menu_teacher, menu);
		}
		return true;
	}

	/**
	 * Here the functions are executed, which were selected depending on the menu item
	 *
	 * @param item Clicked item in the menu
	 * @return the functions which were selected with the item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int menuItemId = item.getItemId();
		if (mIsApprenticeView) {
			switch(menuItemId) {
				case R.id.action_start_pendenzen:
					Intent intentMyPendenzen = new Intent();
					intentMyPendenzen.setClass(getApplicationContext(), PendenzenActivity.class);
					startActivity(intentMyPendenzen);
					finish();
					break;
				//The Login Page will open, if "Logout" is clicked
				case R.id.action_logout:
					Intent intentLogin = new Intent();
					intentLogin.setClass(getApplicationContext(), LoginActivity.class);
					startActivity(intentLogin);
					finish();
					break;
			}
		} else {
			switch(menuItemId) {
				case R.id.action_start_pendenzen:
					Intent intentMyPendenzen = new Intent();
					intentMyPendenzen.setClass(getApplicationContext(), PendenzenActivity.class);
					startActivity(intentMyPendenzen);
					finish();
					break;
				case R.id.action_start_apprentices:
					Intent intentApprentices = new Intent();
					intentApprentices.setClass(getApplicationContext(), ApprenticesActivity.class);
					startActivity(intentApprentices);
					finish();
					break;
				//The Login Page will open, if "Logout" is clicked
				case R.id.action_logout:
					Intent intentLogin = new Intent();
					intentLogin.setClass(getApplicationContext(), LoginActivity.class);
					startActivity(intentLogin);
					finish();
					break;
			}
		}

		return super.onOptionsItemSelected(item);
	}
}
