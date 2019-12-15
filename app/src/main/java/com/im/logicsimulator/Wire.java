package com.im.logicsimulator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;

public class Wire implements Serializable {

    private Position wireStart, wireEnd;
    private Position bottomWireStart, bottomWireEnd;
    private Position verticalWireStart, verticalWireEnd;
    private Position topWireStart, topWireEnd;
    private transient Paint paint;

    public Wire() {
        paint = new Paint();
        paint.setAntiAlias(true);
        wireStart = new Position();
        wireEnd = new Position();
        bottomWireStart = new Position();
        bottomWireEnd = new Position();
        verticalWireStart = new Position();
        verticalWireEnd = new Position();
        topWireStart = new Position();
        topWireEnd = new Position();
    }

    public void draw(Canvas canvas) {
        paint.setStrokeWidth(6f);
        setUpTaxiCabWire();
        canvas.drawLine(bottomWireStart.x, bottomWireStart.y, bottomWireEnd.x, bottomWireEnd.y, paint);
        canvas.drawLine(verticalWireStart.x, verticalWireStart.y, verticalWireEnd.x, verticalWireEnd.y, paint);
        canvas.drawLine(topWireStart.x, topWireStart.y, topWireEnd.x, topWireEnd.y, paint);
    }

    public void setStart(float x, float y) {
        this.wireStart.x = x;
        this.wireStart.y = y;
    }

    public void setEnd(float x, float y) {
        this.wireEnd.x = x;
        this.wireEnd.y = y;
    }

    private void setUpTaxiCabWire() {
        bottomWireStart.x = wireStart.x;
        bottomWireStart.y = wireStart.y;
        bottomWireEnd.x = (bottomWireStart.x + wireEnd.x) / 2;
        bottomWireEnd.y = bottomWireStart.y;

        verticalWireStart.x = bottomWireEnd.x;
        verticalWireStart.y = bottomWireEnd.y;
        verticalWireEnd.x = bottomWireEnd.x;
        verticalWireEnd.y = wireEnd.y;

        topWireStart.x = verticalWireEnd.x;
        topWireStart.y = verticalWireEnd.y;
        topWireEnd.x = wireEnd.x;
        topWireEnd.y = wireEnd.y;
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
