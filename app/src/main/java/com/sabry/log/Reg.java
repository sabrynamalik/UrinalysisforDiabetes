package com.sabry.log;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

public class Reg extends AppCompatActivity {
    public static final String PREFER_NAME = "Reg";

    SharedPreferences sharedPreferences;
    private Editor editor;
    private EditText txtUsername, txtPassword, txtEmail;
    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        Button buttonReg2 = (Button) findViewById(R.id.buttonReg2);

        // creating an shared Preference file for the information to be stored
        // first argument is the name of file and second is the mode, 0 is private mode

        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);


        // get editor to edit in file
        editor = sharedPreferences.edit();

        buttonReg2.setOnClickListener(new View.OnClickListener() {

            public void onClick (View v) {
                String name = txtUsername.getText().toString();
                String email = txtEmail.getText().toString();
                String pass = txtPassword.getText().toString();

                if(txtUsername.getText().length()<=0){
                }
                else if( txtEmail.getText().length()<=0){
                }
                else if( txtPassword.getText().length()<=0){
                }
                else{
                    //Set<String> set = myScores.getStringSet("key", null);

                    Log.e("COMMIT", "commiting new account");
                    // as now we have information in string. Lets stored them with the help of editor
                    editor.putString("Name", name);
                    editor.putString("Email",email);
                    editor.putString("txtPassword",pass);
                    editor.apply();
                }   // commit the values

                // after saving the value open next activity
                startMain();

            }
        });
    }

    public void startMain() {
        Intent ob = new Intent(this, MainActivity.class);
        startActivity(ob);
    }

}