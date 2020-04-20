package com.sabry.log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import static com.sabry.log.Reg.PREFER_NAME;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin;
    Button redcapButt;

    EditText txtUsername, txtPassword;

    // User Session Manager Class
    UserSession session;
    private SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button switchButton = (Button)findViewById(R.id.buttonReg);
        switchButton.setOnClickListener (new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Reg.class);
                startActivity(intent);

            }
        });

        // User Session Manager
        session = new UserSession(getApplicationContext());

        // get Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        // User Login button
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        File loginCreds = new File(getFilesDir().getPath() + File.separator + "login.txt");
        try {
            loginCreds.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois;
        HashMap<String, String> hash = new HashMap<>();
        try {
            FileInputStream fis = new FileInputStream(loginCreds);
            ois = new ObjectInputStream(fis);
            hash = (HashMap<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Login button click event
        final HashMap<String, String> finalHash = hash;
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Get username, password from EditText
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                // Validate if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0){
                    String uName = null;
                    String uPassword =null;

                    if (sharedPreferences.contains("Name"))
                    {
                        uName = sharedPreferences.getString("Email", "");
                    }

                    if (sharedPreferences.contains("txtPassword"))
                    {
                        uPassword = sharedPreferences.getString("txtPassword", "");
                    }

                    Log.e("! USERNAME", uName);
                    Log.e("! PASSWORD", uPassword);
                    //if(username.equals(uName) && password.equals(uPassword)){
                    String pWord = finalHash.get(username);
                    if (pWord != null && pWord.equals(password)) {
                        session.createUserLoginSession(uName,
                                uPassword);
                        // Starting MainActivity
                        Intent i = new  Intent(getApplicationContext(), LogHome.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                        finish();

                    }else{
                        // username / password doesn't match&
                    }
                }else{
                    // user didn't entered username or password

                }

            }
        });
    }





    public void openLogHome(View view) {
        Intent intent = new Intent(this, LogHome.class);
        startActivity(intent);
    }

    public void openForgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }
}