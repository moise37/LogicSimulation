package com.im.logicsimulator;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.io.Serializable;
import java.util.HashSet;

public class Output extends Tool implements Serializable, RemoveConnection{
    protected float outputPositionX, outputPositionY;
    protected Wire outputWire;
    protected SerialBitmap outputOn;
    //Single input connector
    protected Node n;

    public Output(Resources res, int offImage, int onImage, GridRect grid) {
        super(res, offImage, grid);
        outputOn = new SerialBitmap(res, onImage, grid);
    }

    public void drawOnImage(Canvas canvas){
        canvas.drawBitmap(outputOn.image, x - (outputOn.image.getWidth() / 2), y - (outputOn.image.getHeight() / 2), null);
    }

    public void drawOffImage(Canvas canvas){
        super.drawTool(canvas);
    }

    public HashSet<Wire> getWires(){
        HashSet<Wire> temp = new HashSet<>();
        temp.add(outputWire);
        return temp;
    }

    public void removeTool(Tool toolToDelete){
        if (n == toolToDelete) {
            n = null;
            outputWire = null;
        }
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        updatePorts(x, y);
    }

    public void updatePorts(float centerX, float centerY) {
        outputPositionY = centerY + (float)(defaultTool.image.getHeight() / 2)-connectionRadius-2;
        outputPositionX = centerX;

    }

    public void realignWires() {
        if (outputWire != null) {
            outputWire.setEnd(outputPositionX, outputPositionY);
        }
    }

    public float getOutputPositionX() {
        return this.outputPositionX;
    }
    public float getOutputPositionY() {
        return this.outputPositionY;
    }
    public void setOutputWire(Wire wire) {
        this.outputWire = wire;
    }
    public void setSource(Node n) {
        this.n = n;
    }
    public Node getSource() {
        return n;
    }
}
