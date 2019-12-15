package com.im.logicsimulator;

import android.content.res.Resources;
import android.graphics.Canvas;
import java.io.Serializable;
import java.util.HashSet;

public class TwoInputTool extends Tool implements Serializable, RemoveConnection {
    private float outputPositionX, outputPositionY;
    private float bInputPositionX, bInputPositionY;
    private float aInputPositionX, aInputPositionY;
    private Wire inputWire1, inputWire2;

    //all child classes need access to this set, however all outside classes do not, so the hashset
    //is protected.
    protected HashSet<Wire> outputWires;
    protected Node a, b;

    public TwoInputTool(Resources res, int image, GridRect grid) {
        super(res, image, grid);
        outputWires = new HashSet<>();
    }

    public void drawTool(Canvas canvas) {
        super.drawTool(canvas);
        canvas.drawCircle(outputPositionX, outputPositionY, connectionRadius, paint);
        canvas.drawCircle(aInputPositionX, aInputPositionY, connectionRadius, paint);
        canvas.drawCircle(bInputPositionX, bInputPositionY, connectionRadius, paint);
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        updatePorts(x, y);
    }

    public void updatePorts(float centerX, float centerY) {
        outputPositionX = centerX + (float) (defaultTool.image.getWidth() / 2) - connectionRadius - 2;
        outputPositionY = centerY;

        aInputPositionX = centerX - (float) (defaultTool.image.getWidth() / 2) + connectionRadius + 2;
        aInputPositionY = centerY - (float) (defaultTool.image.getHeight() / 4);

        bInputPositionX = centerX - (float) (defaultTool.image.getWidth() / 2) + connectionRadius + 2;
        bInputPositionY = centerY + (float) (defaultTool.image.getHeight() / 4);
    }

    public void realignWires() {
        if (inputWire1 != null) {
            inputWire1.setEnd(aInputPositionX, aInputPositionY);
        }
        if (inputWire2 != null) {
            inputWire2.setEnd(bInputPositionX, bInputPositionY);
        }
        for (Wire wire : outputWires) {
            wire.setStart(outputPositionX, outputPositionY);
        }
    }

    public HashSet<Wire> getWires(){
        HashSet<Wire> temp = new HashSet<>();
        temp.add(inputWire1);
        temp.add(inputWire2);
        temp.addAll(outputWires);
        return temp;
    }

    public void removeTool(Tool toolToDelete){
        if (a == toolToDelete) {
            a = null;
            inputWire1 = null;
        }
        if (b == toolToDelete) {
            b = null;
            inputWire2 = null;
        }
        if(!(toolToDelete instanceof Switch)) {
            outputWires.removeAll(((RemoveConnection)toolToDelete).getWires());
        }
    }

    public float getOutputPositionX() { return this.outputPositionX; }
    public float getOutputPositionY() { return this.outputPositionY; }
    public float getAInputPositionX() { return this.aInputPositionX; }
    public float getAInputPositionY() { return this.aInputPositionY; }
    public float getBInputPositionX() { return this.bInputPositionX; }
    public float getBInputPositionY() { return this.bInputPositionY; }
    public void addOutputWire(Wire wire) { outputWires.add(wire); }
    public void setInputWire1(Wire wire) { inputWire1 = wire; }
    public void setInputWire2(Wire wire) { inputWire2 = wire; }
    public void setSourceA(Node a) { this.a = a; }
    public void setSourceB(Node b) { this.b = b; }
    public Node getSourceA() { return this.a; }
    public Node getSourceB() { return this.b; }
}
