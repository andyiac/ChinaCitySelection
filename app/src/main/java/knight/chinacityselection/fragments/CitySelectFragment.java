package knight.chinacityselection.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import knight.chinacityselection.ClientApplication;
import knight.chinacityselection.R;
import knight.chinacityselection.db.CityDB;


public class CitySelectFragment extends Fragment {

    public final static String PROVINCE = "province";
    private List<String> mContentData;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        Toast.makeText(getActivity(), "CitySelectFragment on create view", Toast.LENGTH_SHORT).show();
        String mProvince = getArguments().getString(PROVINCE);
        init(layout, mProvince);

        return layout;
    }


    public void init(View view, String province) {

        GridView gridview = (GridView) view.findViewById(R.id.id_grid_view);

        final CityDB cityDB = ClientApplication.getInstance().getCityDB();

        mContentData = cityDB.getProvinceAllCity(province);

        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item, R.id.ItemBtn, mContentData);
        gridview.setAdapter(arrayAdapter);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridview.setGravity(Gravity.CENTER);

        gridview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        Toast.makeText(getActivity(), "you click：" + arg2, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
