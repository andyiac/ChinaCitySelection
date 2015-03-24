package knight.chinacityselection;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import knight.chinacityselection.views.GridPop;


public class MainActivity extends FragmentActivity {


    private Button btn1;
    private Button btn2;
    private GridPop gridpop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.id_btn_1);
        btn2 = (Button) findViewById(R.id.id_btn_2);

        gridpop = new GridPop(MainActivity.this, R.layout.pop_grid_view);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (gridpop != null) {
                    if (gridpop.isShowing()) {
                        gridpop.dismiss();
                    } else {
                        gridpop.showAsDropDown(v);
                    }
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (gridpop != null) {
                    if (gridpop.isShowing()) {
                        gridpop.dismiss();
                    } else {
                        gridpop.showAsDropDown(v);
                        gridpop.setOnCitySelectedListener(new GridPop.onCitySelectedListener() {
                            @Override
                            public void onCitySelected(String city) {
                                ((Button) v).setText(city);
                            }
                        });
                    }
                }
            }
        });

    }


}


