package ch.nyp.aemtli_app.helper;

import java.util.Comparator;

import ch.nyp.aemtli_app.model.User;

/**
 * Klasse, um Benutzer alphabetisch zu sortieren
 *
 * @author Miriam Streit <miriam.streit@nyp.ch>
 */
public class UserSort implements Comparator<User> {

    /**
     * Compare-Funktion, die zwei ganze Namen vergleicht
     *
     * @author Miriam Streit <miriam.streit@nyp.ch>
     * @param o1 erstes Vergleichsobjekt
     * @param o2 zweites Vergleichsobjekt
     * @return int, -1, 0 oder 1 je nach Fall
     */
    @Override
    public int compare(User o1, User o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
