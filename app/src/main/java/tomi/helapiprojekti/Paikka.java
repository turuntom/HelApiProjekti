package tomi.helapiprojekti;

import java.util.ArrayList;

public class Paikka {
    private String nimi;
    private String id;
    private ArrayList<String> divisions;

    public Paikka(String nimi, String id, ArrayList<String> divisions) {
        this.nimi = nimi;
        this.id = id;
        this.divisions = divisions;
    }

    public String getNimi() {
        return nimi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getDivisions() {
        return divisions;
    }

}
