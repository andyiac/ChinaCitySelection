package knight.chinacityselection.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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


public class MainFragment extends Fragment {

    private List<String> mContentData;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);
        init(layout);
        return layout;
    }

    public void init(View view) {

        GridView gridview = (GridView) view.findViewById(R.id.id_grid_view);
        final CityDB cityDB = ClientApplication.getInstance().getCityDB();
        mContentData = cityDB.getAllProvince();
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item, R.id.ItemBtn, mContentData);
        gridview.setAdapter(arrayAdapter);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridview.setGravity(Gravity.CENTER);

        gridview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int index = arg2 + 1;//id是从0开始的，所以需要+1
                        Toast.makeText(getActivity(), "你按下了选项：" + index, Toast.LENGTH_SHORT).show();
                        Log.e("test ", "==========");

                        String province = mContentData.get(arg2);
                        mContentData.clear();
                        mContentData = cityDB.getProvinceAllCity(province);
                        Log.e("citys", mContentData.toString());
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
        );
    }

}
