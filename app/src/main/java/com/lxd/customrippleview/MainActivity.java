package com.lxd.customrippleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lxd.rippleview.RippleView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RippleView rippleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rippleView = (RippleView) findViewById(R.id.ripple_view);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.start:
                 rippleView.startRipple();
                break;
            case R.id.stop:
                rippleView.stopRipple();
                break;
            }
    }
}
