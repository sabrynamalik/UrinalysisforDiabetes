package com.sabry.log;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String PREFER_NAME = "Reg";
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
        redcapButt = (Button) findViewById(R.id.redcapButt);

        redcapButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRedCap();
            }
        });

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();


        // User Login button
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);


        // Login button click event
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

                    // Object uName = null;
                    // Object uEmail = null;
                    Log.e("! USERNAMEEEEEE", uName);
                    Log.e("! PASSWORDDDDDDDDDDDDD", uPassword);
                    if(username.equals(uName) && password.equals(uPassword)){
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
                        Toast.makeText(getApplicationContext(),
                                "Username/Password is incorrect",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(),
                            "Please enter username and password",
                            Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu1:
                Intent intent1 = new Intent(this, Login.class);
                this.startActivity(intent1);
                break;
            case R.id.menu2:
                Intent intent2 = new Intent(this, MainActivity.class);
                this.startActivity(intent2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
    */



    public void openRedCap() {
        Uri uri = Uri.parse("https://redcap.vanderbilt.edu/surveys/?s=YN4MR3AR7L");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

  /*
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                User user = new User(null, null);
                userLocalStore.storeUserData(user);
                userLocalStore.setUserLoggedIn(true);


                break;

            case R.id.tvRegisterlink:
                startActivity(new Intent(this, CreateAccount.class));
                break;

        }

    }

   */


    public void openLogHome(View view) {
        Intent intent = new Intent(this, LogHome.class);
        startActivity(intent);
    }

    public void openForgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }
}