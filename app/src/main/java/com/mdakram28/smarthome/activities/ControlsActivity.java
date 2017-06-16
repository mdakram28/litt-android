package com.mdakram28.smarthome.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.Toast;

import com.mdakram28.smarthome.R;
import com.mdakram28.smarthome.util.ControlsGrid;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ControlsActivity extends AppCompatActivity {

    @BindView(R.id.gridLayout_controls)
    GridLayout _gridLayoutControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controls);
        ButterKnife.bind(this);

        ControlsGrid controlsGrid = new ControlsGrid(this,_gridLayoutControls);
        controlsGrid.populate(getIntent().getStringExtra("roomid"));

        _gridLayoutControls.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int pLength;
                final int MARGIN = 5;

                int pWidth = _gridLayoutControls.getWidth();
                int pHeight = _gridLayoutControls.getHeight();
                int numOfCol = _gridLayoutControls.getColumnCount();
                int numOfRow = _gridLayoutControls.getRowCount();

                //Set myGridLayout equal width and height
                if(pWidth>=pHeight){
                    pLength = pHeight;
                }else{
                    pLength = pWidth;
                }

                int side = pLength/numOfCol;

                ViewGroup.LayoutParams pParams = _gridLayoutControls.getLayoutParams();
                pParams.width = side*numOfCol;
                pParams.height = side*numOfRow;
                _gridLayoutControls.setLayoutParams(pParams);

                for(int i=0;i<_gridLayoutControls.getChildCount();i++){
                    View child = _gridLayoutControls.getChildAt(i);
                    GridLayout.LayoutParams params = (GridLayout.LayoutParams) child.getLayoutParams();
                    params.width = side - 2*MARGIN;
                    params.height = side - 2*MARGIN;
                    params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                    child.setLayoutParams(params);
                }
                _gridLayoutControls.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
