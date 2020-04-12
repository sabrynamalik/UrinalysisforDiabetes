package com.sabry.log;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LightActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor mLight;
    private String data[] = new String[50];
    private String dataStr;
    private int counter = 0;
    private TextView luxText;
    private Button emailButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        luxText = (TextView) findViewById(R.id.luxText);

        emailButt = (Button) findViewById(R.id.emailDataButt);
        emailButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmailAct();
            }
        });
    }

    public void openEmailAct() {
        Intent intent = new Intent(this, EmailActivity.class);
        String body = "Light sensor data: \n\n" + dataStr;
        intent.putExtra("body", body);
        startActivity(intent);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float lux = event.values[0];
        luxText.setText(Float.toString(lux));
        if (counter < 50) {
            data[counter] = Float.toString(lux);
            counter++;
        } else if (counter == 50) {
            dataStr = String.join(", ", data);
        }
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
