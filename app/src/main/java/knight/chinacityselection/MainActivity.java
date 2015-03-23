package knight.chinacityselection;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import knight.chinacityselection.fragments.CitySelectFragment;
import knight.chinacityselection.fragments.ProvinceSelectFragment;


public class MainActivity extends FragmentActivity implements ProvinceSelectFragment.onProvinceSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(this.toString())
                .replace(R.id.activity_main, new ProvinceSelectFragment(), this.toString())
                .commit();
    }

    @Override
    public void onProvinceSelected(String province) {
        gotoCitySelectFragment(province);
    }


    private void gotoCitySelectFragment(String province) {

        CitySelectFragment citySelectFragment = new CitySelectFragment();
        Bundle args = new Bundle();
        args.putString(CitySelectFragment.PROVINCE, province);
        citySelectFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.activity_main, citySelectFragment, this.toString());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}


