package tomi.helapiprojekti;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class TapahtumaListFragment extends Fragment {

    TapahtumaAdapter tapahtumaAdapter;
    ArrayList<Tapahtuma> tapahtumaLista;
    ListView listView;
    Context mContext;
    TapahtumaViewModel tapahtumaViewModel;

    MainActivity mainActivity;

    public TapahtumaListFragment() {
        // Required empty public constructor
    }


    public static TapahtumaListFragment newInstance() {
        TapahtumaListFragment fragment = new TapahtumaListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tapahtumaLista = new ArrayList<>();
        mainActivity = (MainActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tapahtuma_list, container, false);
        listView = view.findViewById(R.id.tapahtumaListView);
        mContext = getActivity().getApplicationContext();

        tapahtumaViewModel = ViewModelProviders.of(mainActivity).get(TapahtumaViewModel.class);

        MutableLiveData<ArrayList<Tapahtuma>> tapahtumaLiveDataList =  tapahtumaViewModel.tapahtumaLiveData;

        tapahtumaAdapter = new TapahtumaAdapter(mContext, R.layout.tapahtuma_adapteri_layout,getActivity(),tapahtumaLista);
        listView.setAdapter(tapahtumaAdapter);

        tapahtumaViewModel.getTapahtumaLiveData().observe(this, new Observer<ArrayList<Tapahtuma>>() {
            @Override
            public void onChanged(ArrayList<Tapahtuma> list) {
                Log.d("App", "onChanged: TAPAHTUMA LISTA ON MUUTTUNUT");
                tapahtumaAdapter.addAll(list);
                if(list.isEmpty()){
                    Log.d("tapahtumat", "onChanged: LISTA OLI TYHJÄ");
                    Toast.makeText(mContext, "Hakuehdoilla ei löytynyt mitään, haetaan laajemmin", Toast.LENGTH_LONG).show();
                    tapahtumaViewModel.getNewTapahtumaListFromHelApi("https://api.hel.fi/linkedevents/v1/event/?format=json");
                }
            }
        });

        return view;
    }

}
