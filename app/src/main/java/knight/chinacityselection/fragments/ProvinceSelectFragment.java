package knight.chinacityselection.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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


public class ProvinceSelectFragment extends Fragment {

    private List<String> mContentData;
    private ArrayAdapter<String> arrayAdapter;

    onProvinceSelectedListener mCallback;

    public interface onProvinceSelectedListener {
        public void onProvinceSelected(String province);
    }

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
                        Toast.makeText(getActivity(), "you click" + arg2, Toast.LENGTH_SHORT).show();
                        mCallback.onProvinceSelected(mContentData.get(arg2));
                    }
                }
        );
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (onProvinceSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


}
