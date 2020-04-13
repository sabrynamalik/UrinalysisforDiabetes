package com.sabry.log;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogHome extends AppCompatActivity {
    private Button redcapButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_home);

        redcapButt = (Button) findViewById(R.id.redCapButt);

        redcapButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRedCap();
            }
        });

        findViewById(R.id.learnMoreButt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.diabetes.org";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    public void openNewTestAct(View v) {
        Intent intent = new Intent(this, NewTest.class);
        startActivity(intent);
    }

    public void openMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAboutUs(View view) {
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }

    public void openRedCap() {
        Uri uri = Uri.parse("https://redcap.vanderbilt.edu/surveys/?s=YN4MR3AR7L");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}