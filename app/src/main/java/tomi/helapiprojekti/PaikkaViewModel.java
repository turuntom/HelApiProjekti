package tomi.helapiprojekti;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.util.ArrayList;

public class PaikkaViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<Paikka>> paikkaLiveData = new MutableLiveData<>();

    public PaikkaViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Paikka>> getPaikkaLiveData(){
        return paikkaLiveData;
    }

    public void setPaikkaLiveData(ArrayList<Paikka> lista){
        paikkaLiveData.setValue(lista);
    }

    public void getNewPaikkaListFromHelApi(String url){
        GetJSON getJSON = (GetJSON) new GetJSON(url, new GetJSON.ICallBack() {
            @Override
            public void getJSONResponse(JSONObject jsonObject) {
                JSONParser omaParser = new JSONParser();
                ArrayList<Paikka> paikkaLista = omaParser.parseJSONtoPaikkaList(jsonObject);
                setPaikkaLiveData(paikkaLista);
            }
        }).execute();
    }

}
