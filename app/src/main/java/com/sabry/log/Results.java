package com.sabry.log;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.Date;

public class Results extends AppCompatActivity {
    int result;
    TextView ketoneTxt;
    Button emailButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ketoneTxt = (TextView) findViewById(R.id.ketoneTxt);
        emailButt = (Button) findViewById(R.id.emailButt);
        emailButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmailAct();
            }
        });


        Intent intent = getIntent();
        String res = intent.getStringExtra("result");
        this.result = Integer.parseInt(res);
        String txt = Integer.toString(result);
        ketoneTxt.setText(txt);

    }

    public void openEmailAct() {
        Intent intent = new Intent(this, EmailActivity.class);
        Date today = new Date();
        String bodyTxt = "The patient's ketone level was tested at "+ result +
                " mg/dL on  " + today.toString();

        intent.putExtra("body", bodyTxt);
        startActivity(intent);
    }
}
