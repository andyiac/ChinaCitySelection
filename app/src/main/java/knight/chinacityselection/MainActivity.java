package knight.chinacityselection;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import knight.chinacityselection.fragments.MainFragment;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(this.toString())
                .replace(R.id.activity_main, new MainFragment(), this.toString())
                .commit();
    }

}


