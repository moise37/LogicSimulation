package com.im.logicsimulator;

import android.content.res.Resources;
import android.graphics.Canvas;

public class Light extends Output {

    public Light(Resources res, int imageOff, int imageOn, GridRect grid) {
        super(res, imageOff, imageOn, grid);
    }

    @Override
    public void drawTool(Canvas canvas) {
        if(n != null && n.eval()){
            super.drawOnImage(canvas);
            //if its a different output then create method here and call it.
        }
        else{
            super.drawOffImage(canvas);
            //if its a different output then create method here and call it.
        }
        canvas.drawCircle(outputPositionX, outputPositionY, connectionRadius, paint);
    }
}
