package com.example.mapapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapPopupWindow.Builder builder =
                        new MapPopupWindow.Builder(39.914935, 116.404269, MainActivity.this);
                builder.setDestination("天安门")
                        .setMode("3").build().show(MainActivity.this.getWindow().getDecorView());
            }
        });
    }

}