package tomi.helapiprojekti;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

import java.util.ArrayList;

public class TapahtumaViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<Tapahtuma>> tapahtumaLiveData = new MutableLiveData<>();

    public TapahtumaViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<Tapahtuma>> getTapahtumaLiveData(){
        return tapahtumaLiveData;
    }

    public void setTapahtumaLiveData(ArrayList<Tapahtuma> lista){
        tapahtumaLiveData.setValue(lista);
    }


    public void getNewTapahtumaListFromHelApi(String url){
            GetJSON getJSON = (GetJSON) new GetJSON(url, new GetJSON.ICallBack() {
            @Override
            public void getJSONResponse(JSONObject jsonObject) {
                JSONParser omaParseri = new JSONParser();
                ArrayList<Tapahtuma> tapahtumaLista = omaParseri.parseJSONtoTapahtumaList(jsonObject);
                setTapahtumaLiveData(tapahtumaLista);
                Log.d("App", "getJSONResponse: "+tapahtumaLista.toString());
            }
        }).execute();
    }

}
