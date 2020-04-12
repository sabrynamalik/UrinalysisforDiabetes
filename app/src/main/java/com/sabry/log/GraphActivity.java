package com.sabry.log;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String prop = System.getProperty("user.dir");
        String csvFile = this.getFilesDir().toString() + "/graduates.csv";
        String[][] data = new String[520][2];
        String[][] means = new String[4][2];

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int i = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] ln = line.split(cvsSplitBy);

                if (ln[2] != null && ln[5] != null) {
                    data[i][0] = ln[2].replaceAll("^\"|\"$", "");
                    data[i][1] = ln[5].replaceAll("^\"|\"$", "");
                }

                Log.e("log", data[i][0] + ",  " + data[i][1]);

                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        int[] sums = new int[4];
        int[] counts = {0, 0, 0, 0};
        int[] avg = new int[4];
        for (int j=1; j<data.length; j++) {
            String major = data[j][0];
            float salary;
            if (data[j][1] != null) {
                salary = Float.valueOf(data[j][1]);

                if (major.equals("Mechanical Engineering")) {
                    if (salary != 0) {
                        ++counts[0];
                        sums[0] += salary;
                    }
                }
                if (major.equals("Economics")) {
                    if (salary != 0) {
                        ++counts[1];
                        sums[1] += salary;
                    }
                }
                if (major.equals("Biological Sciences")) {
                    if (salary != 0) {
                        ++counts[2];
                        sums[2] += salary;
                    }
                }
                if (major.equals("Psychology")) {
                    if (salary != 0) {
                        ++counts[3];
                        sums[3] += salary;
                    }
                }
            }
        }
        for (int j=0; j<4; j++) {
            avg[j] = sums[j] / counts[j];
        }

        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, avg[0] ),
                new DataPoint(1, avg[1]),
                new DataPoint(2, avg[2]),
                new DataPoint(3, avg[3])
        });
        graph.getViewport().setScalable(true);
        graph.getViewport().setMinX(-0.5);
        graph.getViewport().setMaxX(3.5);
        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    if (value == 0) {
                        return "MechE";
                    }
                    if (value == 1) {
                        return "Econ";
                    }
                    if (value == 2) {
                        return "Bio";
                    }
                    if (value == 3) {
                        return "Psych";
                    }
                }
                return super.formatLabel(value, isValueX);
            }
        });

    }
}
