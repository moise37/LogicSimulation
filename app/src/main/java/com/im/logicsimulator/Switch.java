package com.im.logicsimulator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.HashSet;

public class Switch extends Tool implements Node {
    private float outputPositionX, outputPositionY;
    private HashSet<Wire> outputWires;
    private boolean toggleState;
    private SerialBitmap onSwitch;

    public Switch(Resources res, int image, GridRect grid) {
        super(res, image, grid);
        onSwitch = new SerialBitmap(res, R.drawable.onswitch, grid);
        outputWires = new HashSet<>();
    }

    @Override
    public void drawTool(Canvas canvas) {
        if (this.toggleState) {
            canvas.drawBitmap(onSwitch.image, x-(onSwitch.image.getWidth()/2), y-(onSwitch.image.getHeight()/2), null);
        } else {
            //draw default off state.
            super.drawTool(canvas);
        }
        canvas.drawCircle(outputPositionX, outputPositionY, connectionRadius, paint);
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);

        updatePorts(x, y);
    }

    public void updatePorts(float centerX, float centerY) {

        outputPositionX = centerX + (float) (defaultTool.image.getHeight() / 4) * 3;
        outputPositionY = centerY;

    }

    public void realignWires() {
        for (Wire wire : outputWires) {
            wire.setStart(outputPositionX, outputPositionY);
        }
    }

    public HashSet<Wire> getWires(){
        return outputWires;
    }

    public float getOutputPositionX() {
        return this.outputPositionX;
    }

    public float getOutputPositionY() {
        return this.outputPositionY;
    }

    public void addOutputWire(Wire wire) {
        outputWires.add(wire);
    }

    public void toggleSwitch() {
        this.toggleState = !this.toggleState;
    }

    public boolean eval() {
        return this.toggleState;
    }
}