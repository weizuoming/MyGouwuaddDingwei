package com.dash.a16_gaode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void map(View view) {
        Intent intent = new Intent(this,MapActivity.class);
        startActivity(intent);

    }

    public void poi(View view) {
        Intent intent = new Intent(this,PoiActivity.class);
        startActivity(intent);
    }

    public void locate(View view) {
        Intent intent = new Intent(this,LocateActivity.class);
        startActivity(intent);
    }
}
