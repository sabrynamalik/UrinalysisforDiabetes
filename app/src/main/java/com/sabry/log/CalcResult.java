package com.sabry.log;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import javax.vecmath.Vector3d;

class CalcResult {
    private Bitmap bm;
    private mColor col;

    static class mColor {
        int red, green, blue;

        mColor(int r, int g, int b) {
            this.red = r;
            this.green = g;
            this.blue = b;
        }
    }

    private mColor col1 = new mColor(92, 60, 58);
    private mColor col2 = new mColor(85, 53, 50);
    private mColor col3 = new mColor(80, 45, 42);
    private mColor col4 = new mColor(58, 31, 41);
    private mColor col5 = new mColor(43, 20, 33);

    private mColor[] guideColors = { col1, col2, col3, col4, col5 };
    private int[] guideValues = { 5, 15, 40, 80, 160 }; //mg/dL

    CalcResult(Bitmap bm) {
        this.bm = bm;
        getColor();
    }

    private Bitmap extractCropArea(){
        int x1, y1, x2, y2;
        int height = bm.getHeight();
        int width = bm.getWidth();

        x1 = (width/2) - 5;
        x2 = (width/2) + 5;
        y1 = (height/2) -5;
        y2 = (height/2) +5;

        //show region of interest in crop window
        Bitmap croppedImageBitmap = Bitmap.createBitmap(bm,x1, y1, x2-x1, y2-y1);
        return croppedImageBitmap;
    }

    private void getColor() {
        Bitmap cropped = extractCropArea();
        int i = 0, h = cropped.getHeight(), w = cropped.getWidth();

        int redTotal = 0, blueTotal = 0, greenTotal = 0;
        int total = h * w;

        //extract color values into appropriate datapoint arrays
        for (int x = 0; x < w; x++){
            for (int y = 0; y < h; y++){
                int pixel = cropped.getPixel(x,y);
                redTotal += Color.red(pixel);
                greenTotal += Color.green(pixel);
                blueTotal += Color.blue(pixel);
            }
        }
        col = new mColor(redTotal / total, blueTotal / total, greenTotal / total);

    }

    private double calcAngle(mColor one, mColor two) {
        // dot product
        int x1 = one.red;
        int y1 = one.green;
        int z1 = one.blue;
        int x2 = two.red;
        int y2 = two.green;
        int z2 = two.blue;

        Vector3d vec1 = new Vector3d(x1,y1,z1);
        Vector3d vec2 = new Vector3d(x2,y2,z2);
        return vec1.angle(vec2);
    }

    int findNearest() {

        double minAngle = 9999;
        int minIndex = 0;
        for (int i=0; i<guideColors.length; i++) {
            double angle = Math.abs(calcAngle(col, guideColors[i]));
            Log.i("! ANGLE", "angle:  "+ angle+"  ind:  "+i);
            if (angle < minAngle) {
                minAngle = angle;
                minIndex = i;
            }
        }
        return guideValues[minIndex];
    }
}

