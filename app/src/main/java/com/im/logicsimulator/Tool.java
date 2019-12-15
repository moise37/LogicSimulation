package com.im.logicsimulator;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;

public class Tool implements Serializable {
    protected SerialBitmap defaultTool;
    //tools x,y position on the screen.
    protected float x, y;
    protected float connectionRadius;
    protected transient Paint paint;

    //create a bitmap with the passed image and set the spawn coordinates
    public Tool(Resources res, int image, GridRect grid) {
        defaultTool = new SerialBitmap(res, image, grid);
        x = 0;
        y = 0;
        connectionRadius = grid.getCellWidth() / 16;
        paint = new Paint();
    }

    public void drawTool(Canvas canvas) {
        if (!(x == 0 & y == 0)) {
            canvas.drawBitmap(defaultTool.image, x - (defaultTool.image.getWidth() / 2), y - (defaultTool.image.getHeight() / 2), null);
            paint.setColor(Color.RED);
        } else {
            paint.setColor(Color.TRANSPARENT);
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return this.x; }
    public float getY() { return this.y; }

    private void readObject(java.io.ObjectInputStream in){
        try{
            in.defaultReadObject();
            this.paint = new Paint();
            paint.setColor(Color.RED);
        }catch(IOException i){
            Log.d("Tool Deserialization", "Error Deserializing");
        }catch(ClassNotFoundException i){
            Log.d("Tool Deserialization", "Error Deserializing");
        }
    }
}

