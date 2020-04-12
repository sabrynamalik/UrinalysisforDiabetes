package com.sabry.log;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proxSensor;
    private TextView textView;
    private TextView nearFar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        textView = (TextView) findViewById(R.id.proximityText);
        nearFar = (TextView) findViewById(R.id.nearFarText);
    }


    public final void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];
        textView.setText(Float.toString(distance));
        String txt = "Far";
        if (distance == 0) {
            txt = "Near";
        }
        nearFar.setText(txt);
        Log.i("distance", Float.toString(distance));
    }

    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
       // Do something here if sensor accuracy changes.
    }

    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
