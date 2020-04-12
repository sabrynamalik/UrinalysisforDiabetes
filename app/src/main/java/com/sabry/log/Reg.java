package com.sabry.log;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Reg extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private Editor editor;
    private Button buttonReg2;
    private EditText txtUsername, txtPassword, txtEmail;
    UserSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        buttonReg2 = (Button) findViewById(R.id.buttonReg2);

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
                    Toast.makeText(Reg.this, "Enter name", Toast.LENGTH_SHORT).show();
                }
                else if( txtEmail.getText().length()<=0){
                    Toast.makeText(Reg.this, "Enter email", Toast.LENGTH_SHORT).show();
                }
                else if( txtPassword.getText().length()<=0){
                    Toast.makeText(Reg.this, "Enter password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.e("COMMIT", "commiting new account");
                    // as now we have information in string. Lets stored them with the help of editor
                    editor.putString("Name", name);
                    editor.putString("Email",email);
                    editor.putString("txtPassword",pass);
                    editor.commit();}   // commit the values

                // after saving the value open next activity
                startMain();

            }
        });
    }

    public void startMain() {
        Intent ob = new Intent(this, MainActivity.class);
        startActivity(ob);
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
}