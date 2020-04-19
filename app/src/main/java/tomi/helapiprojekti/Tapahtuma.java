package tomi.helapiprojekti;

import java.util.ArrayList;

public class Tapahtuma {

    private String nimi;
    private String short_description;
    private String long_description;
    private String start_time;
    private String info_url;
    private ArrayList<String> kuvaUrlit;


    public Tapahtuma(String nimi, String short_description, String start_time, String info_url, String long_description,ArrayList<String> kuvaUrlit){
        this.start_time = start_time;
        this.short_description = short_description;
        this.info_url = info_url;
        this.nimi = nimi;
        this.long_description = long_description;
        this.kuvaUrlit = kuvaUrlit;
    }

    public String getNimi() {
        return this.nimi;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getInfo_url() {
        return info_url;
    }

    public String getLong_description() {
        return long_description;
    }

    public ArrayList<String> getKuvaUrlit() {
        return kuvaUrlit;
    }
}
