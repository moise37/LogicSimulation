package com.im.logicsimulator;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DisplayInfo {

    private int numberHorizontalPixels;
    private int numberVerticalPixels;

    public DisplayInfo(WindowManager window) {
        Display display = window.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
    }

    public int getScreenWidth() {
        return numberHorizontalPixels;
    }

    public int getScreenHeight() {
        return numberVerticalPixels;
    }
}
