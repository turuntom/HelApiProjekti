package tomi.helapiprojekti;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {

    public ArrayList<Tapahtuma> parseJSONtoTapahtumaList(JSONObject jsonObject){
        ArrayList<Tapahtuma> tapahtumaLista = new ArrayList();
        if(jsonObject != null)
        {
            try {
                JSONArray data = jsonObject.getJSONArray("data");
                Log.d("JSONOBJ", "onPostExecute: "+data.toString());
                for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.getJSONObject(i);
                    String infoUrl = "";
                    try{
                        JSONObject info_url = obj.getJSONObject("info_url");
                        infoUrl = info_url.getString("fi");
                    }catch (Exception e){
                        infoUrl = "Ei saatavilla";
                    }

                    String name = "";
                    try{
                        JSONObject nameobj = obj.getJSONObject("name");
                        name = nameobj.getString("fi");
                        if(name == ""){
                            name = nameobj.getString("en");
                        }
                        if(name == ""){
                            name = nameobj.getString("sv");
                        }
                    }catch (Exception ignored){
                        continue;
                    }


                    String shortdesc = "";
                    try{
                        JSONObject shortdescObj = obj.getJSONObject("short_description");
                        shortdesc = shortdescObj.getString("fi");
                    }catch (Exception e){
                        shortdesc = "Ei saatavilla";
                    }
                    String longdesc = "";
                    try{
                        JSONObject longDescObj = obj.getJSONObject("description");
                        longdesc = longDescObj.getString("fi");
                    }catch (Exception ignored){}

                    String starttime = "";
                    try{
                        starttime = obj.getString("start_time");
                    }catch (Exception e){}

                    ArrayList<String>  kuvaUrlit = new ArrayList<>();
                    try{
                        JSONArray kuvaUrlJsonArr = obj.getJSONArray("images");
                        for(int j = 0; j < kuvaUrlJsonArr.length(); j++){
                            JSONObject kuvaJsonObj = kuvaUrlJsonArr.getJSONObject(j);
                            kuvaUrlit.add(kuvaJsonObj.getString("url"));
                        }
                    }catch (Exception e){}

                    Tapahtuma t = new Tapahtuma(name, shortdesc, starttime, infoUrl,longdesc, kuvaUrlit);
                    tapahtumaLista.add(t);

                }

                Log.e("App", "Parsed tapahtumat successfully!" );
            } catch (Exception ex) {
                Log.e("App", "Failure parsin' tapahtumat", ex);
            }
        }
        return tapahtumaLista;
    }

    public ArrayList<Paikka> parseJSONtoPaikkaList(JSONObject jsonObject){
        ArrayList<Paikka> paikkaLista = new ArrayList<>();
        if(jsonObject != null){
            try{
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.getJSONObject(i);
                    String tprekId = "", nimi = "";
                    ArrayList<String> divisions = new ArrayList<>();
                    try{
                        JSONArray divJsonArr = obj.getJSONArray("divisions");

                        for(int j = 0; i < divJsonArr.length();i++){
                            JSONObject arrObj = divJsonArr.getJSONObject(i);
                            JSONObject nameObj = arrObj.getJSONObject("name");
                            String alueenNimi = nameObj.getString("fi");
                            divisions.add(alueenNimi);
                        }
                    }catch (Exception e){
                        Log.e("App", "parseJSONtoPaikkaList error parsin divisions array");
                    }



                    try{
                        tprekId = obj.getString("id");
                    }catch (Exception e){}

                    try{
                        JSONObject nimiObj = obj.getJSONObject("name");
                        nimi = nimiObj.getString("fi");
                    }catch (Exception e){
                        Log.e("App", "parseJSONtoPaikkaList: ");
                    }

                    if(nimi != "" && tprekId != ""){
                        Paikka p = new Paikka(nimi,tprekId,divisions);
                        paikkaLista.add(p);
                    }

                }
            }catch(Exception e){
                Log.e("App", "Error parsin' paikkalist");

            }
        }

        return paikkaLista;
    }

}
