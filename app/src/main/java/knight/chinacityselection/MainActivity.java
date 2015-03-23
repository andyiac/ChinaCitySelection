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

import java.util.ArrayList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.id_grid_view);

        ArrayList<String> arraylist = new ArrayList<String>();
        for (int i = 1; i < 11; i++) {
            arraylist.add(i + "btn");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.ItemBtn, arraylist);

        gridview.setAdapter(arrayAdapter);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridview.setGravity(Gravity.CENTER);

        gridview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int index = arg2 + 1;//id是从0开始的，所以需要+1
                        Toast.makeText(MainActivity.this, "你按下了选项：" + index, Toast.LENGTH_LONG).show();
                        Log.e("test ", "==========");
                    }
                }
        );

    }
}


