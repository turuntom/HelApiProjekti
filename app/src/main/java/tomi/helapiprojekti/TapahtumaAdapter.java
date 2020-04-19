package tomi.helapiprojekti;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class TapahtumaAdapter extends ArrayAdapter<Tapahtuma> {

    private Context context;
    private LayoutInflater layoutInflater;

    private ArrayList<Tapahtuma> tapahtumat;
    private Activity mActivity;

    TextView nimi;

    TextView tapahtumallaKuvia;
    Button naytaKuvatButton;
    TextView tapahtumaLabel;
    ImageView imagePopView;



    public TapahtumaAdapter(@NonNull Context context, int resource, Activity activity, @NonNull ArrayList<Tapahtuma> tapahtumat) {
        super(context, resource, tapahtumat);
        this.layoutInflater = LayoutInflater.from(context);
        this.tapahtumat = tapahtumat;
        this.context = context;
        this.mActivity = activity;
    }

    @Override
    public void addAll(@NonNull Collection<? extends Tapahtuma> collection) {
        super.addAll(collection);
        tapahtumat.clear();
        tapahtumat.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public void add(@Nullable Tapahtuma object) {
        super.add(object);
        tapahtumat.add(object);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final View view = layoutInflater.inflate(R.layout.tapahtuma_adapteri_layout, parent, false);

        nimi = (TextView)view.findViewById(R.id.nimiTextView);
        tapahtumaLabel = (TextView)view.findViewById(R.id.tapahtumaLabelTextView);
        naytaKuvatButton = (Button)view.findViewById(R.id.naytaKuvatButton);
        tapahtumallaKuvia = (TextView)view.findViewById(R.id.tapahtumallaKuviaTextView);
        naytaKuvatButton.setVisibility(View.INVISIBLE);
        tapahtumallaKuvia.setVisibility(View.INVISIBLE);
        final Tapahtuma t = tapahtumat.get(position);
        final ArrayList<String> kuvaUrlit = t.getKuvaUrlit();
        if(!kuvaUrlit.isEmpty()){
            naytaKuvatButton.setVisibility(View.VISIBLE);
            tapahtumallaKuvia.setVisibility(View.VISIBLE);
        }
        nimi.setText(t.getNimi());
        int moneskoTapahtuma = position +1;
        tapahtumaLabel.setText("Tapahtuma "+moneskoTapahtuma);

        naytaKuvatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                if(kuvaUrlit != null && !kuvaUrlit.isEmpty()){
                    url = kuvaUrlit.get(0);
                }

                Log.d("kuva", "onClick: painettu kuvaa");
                showKuvat(view,url);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Sulje tapahtumatiedot painamalla ikkunasta", Toast.LENGTH_LONG).show();

                showTapahtumaInfo(view,t);

                Log.d("App", "onClick: TAPAHTUMAA PAINETTU");

            }
        });
        return view;
    }

    //Näytetään tapahtuman kuva popuppina
    public void showKuvat(View view, String kuvaUrl){
        View kuvaView = LayoutInflater.from(mActivity).inflate(R.layout.kuva_pop,null);
        final PopupWindow tapahtumaWindow = new PopupWindow(kuvaView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Log.d("kuva", "showKuvat: KUVASSA");
        imagePopView = kuvaView.findViewById(R.id.imagePopView);
        if(!kuvaUrl.isEmpty()){
            Picasso.get().load(kuvaUrl).into(imagePopView);
            imagePopView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    tapahtumaWindow.dismiss();
                }
            });

            tapahtumaWindow.showAsDropDown(kuvaView,0,0);
        }
    }


    //Näytetään tapahtuman tiedot popuppina
    public void showTapahtumaInfo(View view, Tapahtuma tapahtuma){
        View tapahtumaView = LayoutInflater.from(mActivity).inflate(R.layout.tapahtuma_pop,null);
        final PopupWindow tapahtumaWindow = new PopupWindow(tapahtumaView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView popNimi = tapahtumaView.findViewById(R.id.nimiPopText);
        TextView popAika = tapahtumaView.findViewById(R.id.aikaPopText);
        TextView popInfo = tapahtumaView.findViewById(R.id.infoPopText);
        TextView popLyhyt = tapahtumaView.findViewById(R.id.lyhytPopText);
        TextView popPitka = tapahtumaView.findViewById(R.id.pitkaPopText);

        Log.d("niminimi", "showTapahtumaInfo: "+tapahtuma.getNimi());
        popNimi.setText(tapahtuma.getNimi());
        Log.d("niminimi", "showTapahtumaInfo: "+tapahtuma.getStart_time());
        popAika.setText(tapahtuma.getStart_time());
        popInfo.setText(tapahtuma.getInfo_url());
        popLyhyt.setText(tapahtuma.getShort_description());
        popPitka.setText(tapahtuma.getLong_description());

        TableLayout tablePop = tapahtumaView.findViewById(R.id.tablePopUp);
        tablePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapahtumaWindow.dismiss();
            }
        });

        tapahtumaWindow.showAsDropDown(tapahtumaView,0,0);
    }

}
