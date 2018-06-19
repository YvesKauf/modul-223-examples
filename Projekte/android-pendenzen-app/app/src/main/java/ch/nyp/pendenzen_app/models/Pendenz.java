package ch.nyp.pendenzen_app.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


/**Die Modelklasse für eine Pendenz. Diese Klasse ist auch als Tabelle gekennzeichnet.
 * Hier werden die einzelnen Daten von der Pendenz eingespeichert.
 *
 *
 * @author Claudia Carvalho, Jens Scheidmann
 */
@Entity
public class Pendenz {

    @PrimaryKey(autoGenerate = true)
    private int pendenzId;

    private String pendenzTitle;

    private String pendenzDescription;

    private String pendenzDate;

    private String pendenzUrgency;

    private int idUser;

    public Pendenz() {}

    @Ignore
    public Pendenz(String pendenzTitle, String pendenzDescription, String pendenzDate, String
            pendenzUrgency) {
        this.pendenzTitle = pendenzTitle;
        this.pendenzDescription = pendenzDescription;
        this.pendenzDate = pendenzDate;
        this.pendenzUrgency = pendenzUrgency;
    }


    public void setPendenzId(int pendenzId) { this.pendenzId = pendenzId; }

    public void setPendenzTitle(String pendenzTitle) {
        this.pendenzTitle = pendenzTitle;
    }

    public void setPendenzUrgency(String pendenzUrgency) { this.pendenzUrgency = pendenzUrgency; }

    public void setPendenzDescription(String pendenzDescription) { this.pendenzDescription = pendenzDescription; }

    public void setPendenzDate(String pendenzDate) { this.pendenzDate = pendenzDate; }

    public int getPendenzId() { return pendenzId; }

    public String getPendenzDescription() { return pendenzDescription; }

    public String getPendenzTitle() {
        return pendenzTitle;
    }

    public String getPendenzUrgency() {
        return pendenzUrgency;
    }

    public String getPendenzDate() { return pendenzDate; }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
