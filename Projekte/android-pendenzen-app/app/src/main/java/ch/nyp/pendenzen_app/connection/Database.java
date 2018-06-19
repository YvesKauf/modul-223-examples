package ch.nyp.pendenzen_app.connection;

import android.content.Context;

import java.util.List;

import ch.nyp.pendenzen_app.models.Pendenz;
import ch.nyp.pendenzen_app.models.User;
import ch.nyp.pendenzen_app.persistence.AppDatabase;
import ch.nyp.pendenzen_app.persistence.PendenzDao;
import ch.nyp.pendenzen_app.persistence.UserDao;
/**
 * Created by robin on 25.01.2018.
 *
 * is the adapter from the UserDao to an developer -> easy to use
 * validation are in the function
 *
 */
public class Database {
    private UserDao mUserDao;
    private PendenzDao mPendenzDao;

    /**
     * @param context -> need for the AppDatabase
     */
    public Database(Context context){
        mUserDao = AppDatabase.getAppDb(context).getUserDao();
        mPendenzDao = AppDatabase.getAppDb(context).getPendenzDao();
    }

    /**
     * @author Robin Furrer
     *
     * creates one test user for testing
     * used for whitebox testing or debugging
     *
     */
    private void createTestUser(){
        User user = new User("test@user.ch", "pw", "Test", "User");

        registerUser(user);
    }

    /**
     * @author Robin Furrer
     *
     * test if email and password match one entry in the SQLite
     *
     * @param user -> User needs email and password
     * @return true / false -> if login is true or false
     */
    public User loginUser(User user){
        user = mUserDao.loadUser(user.getEmail(), user.getPassword());
        if(user == null){
            return null;
        } else {
            return user;
        }
    }

    /**
     * @author Robin Furrer
     *
     * @param email -> for register
     *
     * @return true / false -> if email already exists to an user
     */
    public boolean checkEmail(String email){
        User user = mUserDao.checkEmail(email);
        if(user == null){
            return true;
        } else {
            return false;
        }
    }

    /**
     * @author Robin Furrer
     *
     * @param user -> need to set email, password, firstname and lastname
     * profilpicture is optional
     *
     * @return true / false -> if register was succesfully
     */
    public boolean registerUser(User user){
        if(checkEmail(user.getEmail())){
            mUserDao.insert(user);
            System.out.println("register true");
            return true;
        } else {
            System.out.println("register false");
            return false;
        }
    }

    /**
     * Fügt einen User in die Datenbank hinzu
     *
     * @param user welcher in die Datenbank hinzugefügt wird
     */
    public void insertUser(User user){
        mUserDao.insert(user);
    }

    /**
     * Gibt alle Lernende nach Vornamen und Nachnamen alphabetisch sortiert zurück
     *
     * @return liefert alle Lernende nach Vornamen und Nachnamen alphabetisch sortiert zurück
     */
    public List<User> getAllApprentices(){
        return mUserDao.getAllApprentices();
    }

    public void insertPendenz(Pendenz pendenz) {
        mPendenzDao.insert(pendenz);
    }

    public List<Pendenz> getPendenzenByUserId(int userId) {
        return mPendenzDao.getAllByUserId(userId);
    }

    /**
     * @author Robin Furrer
     *
     * delete all user in SQLite
     */
    public void deleteAll(){
        mUserDao.deleteAll();
    }
}
