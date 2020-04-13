package com.sabry.log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewTest extends AppCompatActivity implements SensorEventListener {

    private Camera c;
    private boolean testStarted = false; // true if prox sensor has been triggered
    private SensorManager sensorManager;
    private Sensor proxSensor;
    private MediaPlayer beep;
    private MediaPlayer eventually;

    FrameLayout preview;
    CameraPreview mPreview;
    ImageView lastPhotoImageView;
    TextView notifyTextView;

    //variables to manage permissions
    private Activity thisActivity;

    public static final int REQUEST_CAMERA = 29181;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtest);

        beep = MediaPlayer.create(this, R.raw.beep);
        eventually = MediaPlayer.create(this, R.raw.eventually);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        preview = (FrameLayout) findViewById(R.id.previewFrameLayout);

        //RelativeLayout crossHairs = (RelativeLayout) preview.findViewById(R.id.cross);

        //Get references to View objects ----
        //FrameLayout preview = (FrameLayout) findViewById(R.id.previewFrameLayout);

        //lastPhotoImageView = (ImageView) findViewById(R.id.photoImageView);

        //lastPhotoImageView.setVisibility(View.GONE); // hide upon first use

        //Detect and Access Camera ----
        //check if camera exists - may not be needed if camera is required by app
        checkCameraHardware(this);

        //get access to the camera
        thisActivity = this;
        checkCameraPermission(this, thisActivity);
        c = getCameraInstance();

        //Create a Preview Class ----
        // --- done as a new java class named "CameraPreview.java"

        //Build a Preview Layout ----
        // --- done as part of the activity_main layout file

        // Create our Preview view and set it as the content of our activity.
        c.setDisplayOrientation(90);
        Camera.Parameters parameters = c.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();

        mPreview = new CameraPreview(this, c);
        ImageView iView = new ImageView(this);
        iView.setImageResource(R.drawable.ic_launcher_foreground);
        preview.addView(mPreview);

        ViewGroup.LayoutParams prms = (ViewGroup.LayoutParams) iView.getLayoutParams();

        //Setup Listeners for Capture ----
        // --- done through the Camera.PictureCallback method below

        //Capture and Save Files ----
        // --- done through the takePicture method implemented below

        //Release the Camera ----
        // --- done through the onPause method of the activity

    }
/*
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        preview.requestLayout();
        assert preview.getLayoutParams() != null;
        int width = preview.getLayoutParams().width;
        double he = width;
        int ht = (int) (width * 0.7);
        //preview.getLayoutParams().height = ht;

        preview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ViewGroup.LayoutParams params = preview.getLayoutParams();

        Log.i("HEIGHT#######################", "" + params.width);

    }
*/

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private boolean checkCameraPermission(Context context, Activity activity) {
        boolean hadPermission; // returns whether system HAD permission or not at install

        if (Build.VERSION.SDK_INT >= 23) { //only run for SDK 23 and above

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                hadPermission = false;
                //request permission
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
            } else {
                hadPermission = true; //
            }
            return hadPermission;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // camera-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile();
            if (pictureFile == null){
                Log.d("TAG", "Error creating media file, check storage permissions");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("TAG", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("TAG", "Error accessing file: " + e.getMessage());
            }

            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            CalcResult calcResult = new CalcResult(bitmap);
            int res = calcResult.findNearest();
            Log.i("! CALC NEAREST", "" + res);
            openResultsAct(res);

            //make preview available again
            camera.startPreview();

            /*
            //make image visible within the imageView
            if (!lastPhotoImageView.isShown()) {
                lastPhotoImageView.setVisibility(View.VISIBLE);
            }
            lastPhotoImageView.setImageURI(Uri.fromFile(pictureFile));
            notifyTextView.setText("Last picture taken:\n");
            notifyTextView.append(pictureFile.getName());

             */
        }
    };


    /** Create a File for saving an image or video */
    private File getOutputMediaFile(){

        //create an internal directory to store photos
        File mediaStorageDir = new File(getFilesDir(),"TCAImages");

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("TCAImages", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");


        return mediaFile;
    }


    public void takePicture(){
        //notifyTextView.setText("Capturing Image...");
        c.takePicture(null, null, mPicture);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        releaseCamera();    // release the camera immediately on pause event
    }

    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void releaseCamera(){
        if (c != null){
            c.release();    // release the camera for other applications
            c = null;
        }
    }

    public final void onAccuracyChanged(Sensor sensor, int accuracy) {    }

    public final void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];
        if (distance == 0 && !testStarted) {
            testStarted = true;
            startTest();
        }

        Log.i("distance", Float.toString(distance));
    }

    public void startTest() {
        beep.start();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                eventually.start();
                takePicture();

            }
        }, 40000); // 40 seconds  40000

    }

    public void openResultsAct(int result) {
        Intent intent = new Intent(this, Results.class);
        intent.putExtra("result", ""+ result);
        startActivity(intent);
    }


}
