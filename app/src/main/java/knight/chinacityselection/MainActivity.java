package knight.chinacityselection;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import knight.chinacityselection.views.GridPop;


public class MainActivity extends FragmentActivity {


    private GridPop gridpop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {

        Button btn1 = (Button) findViewById(R.id.id_btn_1);
        Button btn2 = (Button) findViewById(R.id.id_btn_2);

        shiftBtnRightDrawableUp(btn1);
        shiftBtnRightDrawableUp(btn2);

        gridpop = new GridPop(MainActivity.this, R.layout.pop_grid_view);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridpop != null) {
                    gridpop.toggle(v);
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (gridpop != null) {
                    gridpop.toggle(v);
                }
            }
        });

    }

    //设置默认btn的状态
    private void shiftBtnRightDrawableUp(Button btn) {
        Drawable mDrawableUp = getResources().getDrawable(R.drawable.arrow_up);
        mDrawableUp.setBounds(1, 1, 50, 50);
        btn.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawableUp, null);
    }

}


