package com.im.logicsimulator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.HashSet;

public class CurrentGame {

    private HashSet<Tool> usedTools;
    private HashSet<Wire> wires;

    //toolBuffer is used to store a tool that is about to be added to the usedTools set or if a
    //specific tool is to be moved around on the screen. This allows us to be able to visually drag
    //any tool object around on the screen.
    private Tool toolBuffer;

    //Variables below are used to store temporary references to the two objects a user wants to
    //connect.
    private Tool tool1;
    private Tool tool2;


    //Used to save the position of the toolBuffer when a tool is going to be moved around by the
    //user.
    private Position toolBufferPos;

    public CurrentGame() {
        usedTools = new HashSet<>();
        wires = new HashSet<>();
        toolBufferPos = new Position();
    }

    public void drawTools(Canvas canvas) {
        for(Wire wire : wires){
            wire.draw(canvas);
        }
        drawToolBuffer(canvas);
        for (Tool tool : usedTools) {
            if(tool instanceof Output){
                checkInfiniteLoop((Output)tool, canvas);
            }
            else {
                tool.drawTool(canvas);
            }
        }
    }

    private void checkInfiniteLoop(Output tool, Canvas canvas){
        try{
            tool.drawTool(canvas);
        }catch (StackOverflowError e){
            tool.setSource(null);
            wires.remove(tool.outputWire);
            tool.setOutputWire(null);
        }
    }

    //Removes object from usedTools set and sets the toolBuffer to the deleted object. This
    //allows us to be able to drag and move the tool around the screen.
    public void setUpMove(Rect touchedCell) {
        toolBuffer = this.getTool(touchedCell);
        this.saveToolBufferPos();
    }

    public Tool getTool(Rect touchedCell) {
        if (touchedCell != null) {
            for (Tool tool : usedTools) {
                if (tool.getX() == touchedCell.centerX() && tool.getY() == touchedCell.centerY()) {
                    return tool;
                }
            }
        }
        return null;
    }

    //New Tool are added to the toolBuffer before being added to the usedTools container.
    //This is done in order to allow users to be able to drag and drop the selected tool
    //in any position on the grid. Once the tool is dropped, it is then added to the usedTools
    //set.
    public void addNewTool(Resources resources, int type, GridRect grid) {
        switch (type) {
            case R.drawable.nandgate:
                this.toolBuffer = new NANDGate(resources, type, grid);
                break;
            case R.drawable.andgate:
                this.toolBuffer = new AndGate(resources, type, grid);
                break;
            case R.drawable.offswitch:
                this.toolBuffer = new Switch(resources, type, grid);
                break;
            case R.drawable.orgate:
                this.toolBuffer = new OrGate(resources, type, grid);
                break;
            case R.drawable.notgate:
                this.toolBuffer = new NotGate(resources, type, grid);
                break;
            case R.drawable.xnorgate:
                this.toolBuffer = new XNORGate(resources, type, grid);
                break;
            case R.drawable.lightbulboff:
                this.toolBuffer = new Light(resources, type, R.drawable.lightbulbon, grid);
                break;
            case R.drawable.norgate:
                this.toolBuffer = new NORGate(resources, type, grid);
                break;
            case R.drawable.xorgate:
                this.toolBuffer = new XORGate(resources, type, grid);
                break;
        }
    }

    //Used for when a new tool is added, but has not been added to the usedTool Set yet.
    private void drawToolBuffer(Canvas canvas) {
        if (toolBuffer != null) {
            toolBuffer.drawTool(canvas);
        }
    }

    public void setToolBufferPos(float x, float y) {
        if (toolBuffer != null) {
            toolBuffer.setPosition(x, y);
            if (toolBuffer instanceof Switch) {
                ((Switch) toolBuffer).realignWires();
            } else if (toolBuffer instanceof NotGate) {
                ((NotGate) toolBuffer).realignWires();
            } else if (toolBuffer instanceof TwoInputTool) {
                ((TwoInputTool) toolBuffer).realignWires();
            } else if (toolBuffer instanceof Output) {
                ((Output) toolBuffer).realignWires();
            }
        }
    }

    public void addToolBuffer() {
        usedTools.add(toolBuffer);
        resetToolBuffer();
    }

    private void saveToolBufferPos() {
        this.toolBufferPos.x = toolBuffer.getX();
        this.toolBufferPos.y = toolBuffer.getY();
    }

    public void resetToolBuffer() {
        this.toolBuffer = null;
    }

    public void connectTools(Wire wire) {
        if (wire != null && ConnectionHandler.connect(tool1, tool2, wire)) {
            wires.add(wire);
        }
    }

    public void deleteTool(Rect touchedCell) {
        Tool toolToDelete = getTool(touchedCell);
        usedTools.remove(toolToDelete);
        if(toolToDelete instanceof Switch){
            wires.removeAll(((Switch) toolToDelete).getWires());
            for(Tool tool : usedTools){
                if(!(tool instanceof Switch)){
                    ((RemoveConnection)tool).removeTool(toolToDelete);
                }
            }
        }
        else{
            for(Tool tool : usedTools){
                if(!(tool instanceof Switch)){
                    ((RemoveConnection)tool).removeTool(toolToDelete);
                }
            }
            wires.removeAll(((RemoveConnection)toolToDelete).getWires());
        }
    }

    public void reloadToolBufferPos() {
        this.setToolBufferPos(this.toolBufferPos.x, this.toolBufferPos.y);
    }

    public void toggleSwitch(Rect touchedCell) { ((Switch) getTool(touchedCell)).toggleSwitch(); }

    public void saveConnection1(Rect touchedCell) { this.tool1 = this.getTool(touchedCell); }

    public void saveConnection2(Rect touchedCell) { this.tool2 = this.getTool(touchedCell); }

    public HashSet<Tool> getUsedTools() { return this.usedTools; }

    public void setUsedTools(HashSet<Tool> usedTools){this.usedTools = usedTools;}

    public HashSet<Wire> getWires(){return this.wires;}

    public void setWires(HashSet<Wire> wires){this.wires = wires;}
}
