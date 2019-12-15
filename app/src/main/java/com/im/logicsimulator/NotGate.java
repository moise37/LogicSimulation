package com.im.logicsimulator;
import android.content.res.Resources;
import android.graphics.Canvas;

import java.util.HashSet;

public class NotGate extends Tool implements Node, RemoveConnection {
    private Node a;

    private float outputPositionX, outputPositionY;
    private float inputPositionX, inputPositionY;
    private HashSet<Wire> outputWires;
    private Wire inputWire;


    public NotGate(Resources res, int image, GridRect grid) {
        super(res, image, grid);
        outputWires = new HashSet<>();
    }

    public void drawTool(Canvas canvas) {
        super.drawTool(canvas);
        canvas.drawCircle(outputPositionX, outputPositionY, connectionRadius, paint);
        canvas.drawCircle(inputPositionX, inputPositionY, connectionRadius, paint);
    }

    public HashSet<Wire> getWires(){
        HashSet<Wire> temp = new HashSet<>();
        temp.add(inputWire);
        temp.addAll(outputWires);
        return temp;
    }

    public void removeTool(Tool toolToDelete){
        if (a == toolToDelete) {
            a = null;
            inputWire = null;
        }
        else if(!(toolToDelete instanceof Switch)) {
            outputWires.removeAll(((RemoveConnection)toolToDelete).getWires());
        }
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        updatePorts(x, y);
    }

    public void realignWires() {
        if (inputWire != null) {
            inputWire.setEnd(inputPositionX, inputPositionY);
        }
        for (Wire wire : outputWires) {
            wire.setStart(outputPositionX, outputPositionY);
        }
    }

    public void updatePorts(float centerX, float centerY) {
        outputPositionX = centerX + (float) (defaultTool.image.getWidth() / 2) - connectionRadius - 2;
        outputPositionY = centerY;

        inputPositionY = centerY;
        inputPositionX = centerX - (float) (defaultTool.image.getWidth() / 2) + connectionRadius + 2;
    }

    public boolean eval() {
        if (this.a == null) {
            return true;
        }
        else{
            return !a.eval();
        }
    }

    public float getOutputPositionX() { return this.outputPositionX; }
    public float getOutputPositionY() { return this.outputPositionY; }
    public float getInputPositionX() { return this.inputPositionX; }
    public float getInputPositionY() { return this.inputPositionY; }
    public void addOutputWire(Wire wire) { outputWires.add(wire); }
    public void setInputWire(Wire wire) { inputWire = wire; }
    public void setSource(Node a) { this.a = a; }
    public Node getSource() { return this.a; }
}
