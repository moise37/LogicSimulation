package com.im.logicsimulator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;

public class Wire implements Serializable {

    private Position wireStart, wireEnd;
    private transient Paint paint;

    private Path path;

    public Wire() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        wireStart = new Position();
        wireEnd = new Position();
        path = new Path();
    }

    public void draw(Canvas canvas) {
        path.reset();
        path.moveTo(wireStart.x, wireStart.y);

        Position controlPoint1 = new Position();
        Position controlPoint2 = new Position();

        controlPoint1.x = wireStart.x + 50;
        controlPoint1.y = wireStart.y + 50;

        controlPoint2.x = wireEnd.x - 50;
        controlPoint2.y = wireEnd.y - 50;

        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, wireEnd.x, wireEnd.y);

        //draw black wire outline
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(15);
        canvas.drawPath(path, paint);

        //draw white wire outline
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(6);
        canvas.drawPath(path, paint);

    }

    public void setStart(float x, float y) {
        this.wireStart.x = x;
        this.wireStart.y = y;
    }

    public void setEnd(float x, float y) {
        this.wireEnd.x = x;
        this.wireEnd.y = y;
    }


    public void setPaintWhite() {
        paint.setColor(Color.WHITE);
    }

    public void setPaintBlue() {
        paint.setColor(Color.rgb(0, 255, 255));
    }

    private void readObject(java.io.ObjectInputStream in){
        try{
            in.defaultReadObject();
            this.paint = new Paint();
            setPaintWhite();
        }catch(IOException i){
            Log.d("Wire Deserialization", "Error Deserializing");
        }catch(ClassNotFoundException i){
            Log.d("Wire Deserialization", "Error Deserializing");
        }
    }
}
