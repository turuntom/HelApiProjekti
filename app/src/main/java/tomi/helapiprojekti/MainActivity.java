package tomi.helapiprojekti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.renderscript.ScriptGroup;

public class MainActivity extends AppCompatActivity {


    TapahtumaListFragment tapahtumaListFragment;
    InputFragment inputFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        tapahtumaListFragment = new TapahtumaListFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.tapahtumaListFrameLayout, tapahtumaListFragment,tapahtumaListFragment.getTag())
                .commit();

        inputFragment = new InputFragment();
        manager.beginTransaction()
                .replace(R.id.inputFrameLayout, inputFragment, inputFragment.getTag())
                .commit();
    }
}
