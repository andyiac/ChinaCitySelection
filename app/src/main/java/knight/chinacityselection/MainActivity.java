package knight.chinacityselection;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import knight.chinacityselection.db.CityDB;


public class MainActivity extends Activity {


    private List<String> mContentData;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.id_grid_view);

        final CityDB cityDB = ClientApplication.getInstance().getCityDB();

        mContentData = cityDB.getAllProvince();

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.ItemBtn, mContentData);

        gridview.setAdapter(arrayAdapter);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridview.setGravity(Gravity.CENTER);

        gridview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int index = arg2 + 1;//id是从0开始的，所以需要+1
                        Toast.makeText(MainActivity.this, "你按下了选项：" + index, Toast.LENGTH_SHORT).show();
                        Log.e("test ", "==========");

                        String province = mContentData.get(arg2);
                        mContentData.clear();
                        mContentData = cityDB.getProvinceAllCity(province);
                        Log.e("citys",mContentData.toString());
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
        );


    }
}


