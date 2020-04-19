package tomi.helapiprojekti;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class InputFragment extends Fragment {

    Button haePaikatButton, haeTapahtumatButton;
    TextView valittuPaikkaTextView;
    EditText haettavaPaikkaEditText;
    TapahtumaViewModel tapahtumaViewModel;
    PaikkaViewModel paikkaViewModel;
    Paikka valittuPaikkaObj;
    PaikkaAdapter paikkaAdapter;
    Context mContext;

    public InputFragment() {
        // Required empty public constructor
    }


    public static InputFragment newInstance() {
        InputFragment fragment = new InputFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_input, container, false);
        mContext = getActivity().getApplicationContext();
        haePaikatButton = view.findViewById(R.id.haePaikkaButton);
        haeTapahtumatButton = view.findViewById(R.id.haeTapahtumatButton);
        haettavaPaikkaEditText = view.findViewById(R.id.haettavaPaikkaEditText);

        valittuPaikkaTextView = view.findViewById(R.id.valittuPaikkaTextView);
        paikkaViewModel = ViewModelProviders.of((MainActivity)getActivity()).get(PaikkaViewModel.class);
        haePaikatButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                String haettavaPaikka = haettavaPaikkaEditText.getText().toString();
                String paikkaHakuUrl = "https://api.hel.fi/linkedevents/v1/search/?type=place&input="+haettavaPaikka;
                paikkaViewModel.getNewPaikkaListFromHelApi(paikkaHakuUrl);
            }
        });


        //SPINNER
        final Spinner paikkaSpinner = (Spinner)view.findViewById(R.id.paikkaSpinner);

        paikkaViewModel.getPaikkaLiveData().observe(this, new Observer<ArrayList<Paikka>>() {
            @Override
            public void onChanged(ArrayList<Paikka> list) {
                paikkaAdapter = new PaikkaAdapter(mContext, R.layout.my_spinner_item,list);
                paikkaAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown);
                paikkaSpinner.setAdapter(paikkaAdapter);
            }
        });

        paikkaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Paikka p = paikkaAdapter.getPaikka(position);
                valittuPaikkaTextView.setText(paikkaAdapter.getPaikka(position).getNimi());
                valittuPaikkaObj = p;
                Log.d("tapahtumat", "onItemSelected: NIMI ON"+p.getNimi());
                for (String divisioona: p.getDivisions())
                {
                    Log.d("tapahtumat", "onItemSelected: DIVISIOONAT ON "+divisioona);
                }
                Log.d("tapahtumat", "onItemSelected: ID ON"+p.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                valittuPaikkaTextView.setText("Kaikki alueet");
            }
        });


        //SPINNER LOPPUU

        tapahtumaViewModel = ViewModelProviders.of((MainActivity)getActivity()).get(TapahtumaViewModel.class);


        haeTapahtumatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Haetaan tapahtumia odota hetki", Toast.LENGTH_SHORT).show();
                String tapahtumaHakuUrl = "";
                if(valittuPaikkaObj != null){
                    String paikkaId = valittuPaikkaObj.getId();
                    ArrayList<String> paikanDivisioonat = valittuPaikkaObj.getDivisions();
                    if(paikkaId.contains("tprek")){
                        tapahtumaHakuUrl = "https://api.hel.fi/linkedevents/v1/event/?location="+paikkaId;
                        Log.d("tapahtumat", "onClick: oli tprek");
                        tapahtumaViewModel.getNewTapahtumaListFromHelApi(tapahtumaHakuUrl);
                    }else if(paikanDivisioonat.size() > 0){
                        tapahtumaHakuUrl = "https://api.hel.fi/linkedevents/v1/event/?division="+paikanDivisioonat.get(paikanDivisioonat.size()-1);
                        Log.d("tapahtumat", "onClick: oli divisioonia");
                        tapahtumaViewModel.getNewTapahtumaListFromHelApi(tapahtumaHakuUrl);
                    }else{
                        tapahtumaViewModel.getNewTapahtumaListFromHelApi("https://api.hel.fi/linkedevents/v1/event/?format=json");
                    }
                }else{
                    Toast.makeText(mContext, "Osoitteelle ei löydy kartta ID:tä tai aluetietoja, haetaan kaikki tapahtumat", Toast.LENGTH_LONG).show();
                    tapahtumaViewModel.getNewTapahtumaListFromHelApi("https://api.hel.fi/linkedevents/v1/event/?format=json");
                    Log.d("tapahtumat", "onClick: Haetaan kaikki");
                }
            }
        });

        return view;
    }
}
