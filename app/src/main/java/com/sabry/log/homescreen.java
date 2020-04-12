package com.sabry.log;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homescreen extends AppCompatActivity {

    private Button emailButt;
    private Button proximityButt;
    private Button lightButt;
    private Button graphButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        emailButt = (Button) findViewById(R.id.emailButt);
        emailButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmailAct();
            }
        });

        proximityButt = (Button) findViewById(R.id.proximityButt);
        proximityButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProximityAct();
            }
        });

        lightButt = (Button) findViewById(R.id.lightButt);
        lightButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLightAct();
            }
        });

        graphButt = (Button) findViewById(R.id.graphButt);
        graphButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGraphAct();
            }
        });

    }

    public void openEmailAct() {
        Intent intent = new Intent(this, EmailActivity.class);
        startActivity(intent);
    }
    public void openProximityAct() {
        Intent intent = new Intent(this, ProximityActivity.class);
        startActivity(intent);
    }

    public void openLightAct() {
        Intent intent = new Intent(this, LightActivity.class);
        startActivity(intent);
    }

    public void openGraphAct() {
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }
}
