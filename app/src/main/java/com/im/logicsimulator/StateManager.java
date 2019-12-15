package com.im.logicsimulator;

public class StateManager {

    private boolean drawingState;
    private boolean deleteState;
    private boolean moveState;
    private boolean toolBufferSavedState;
    private boolean passiveTouchState;
    private boolean wiringState;
    private boolean longPress;
    private boolean singleTapState;

    public StateManager() {
        disableAllStates();
    }

    public void disableAllStates() {
        this.disableDrawingState();
        this.disableDeleteState();
        this.disableMoveState();
        this.disableToolBufferSavedState();
        this.disablePassiveTouchState();
        this.disableWiringState();
        this.disableLongPressState();
        this.disableSingleTapState();
    }

    public void enableDrawingState() {
        this.drawingState = true;
    }

    public void enableDeleteState() {
        this.deleteState = true;
    }

    public void enableMoveState() {
        this.moveState = true;
    }

    public void enableToolBufferSavedState() {
        this.toolBufferSavedState = true;
    }

    public void enableWiringState() {
        this.wiringState = true;
    }

    public void enableLongPressState() {
        this.longPress = true;
    }

    public void enableSingleTapState() {
        this.singleTapState = true;
    }

    public void disableDrawingState() {
        this.drawingState = false;
    }

    public void disableDeleteState() {
        this.deleteState = false;
    }

    public void disableMoveState() {
        this.moveState = false;
    }

    public void disableToolBufferSavedState() {
        this.toolBufferSavedState = false;
    }

    public void disablePassiveTouchState() {
        this.passiveTouchState = false;
    }

    public void disableWiringState() {
        this.wiringState = false;
    }

    public void disableLongPressState() {
        this.longPress = false;
    }

    public void disableSingleTapState() {
        this.singleTapState = false;
    }

    public boolean getDrawingState() {
        return this.drawingState;
    }

    public boolean getDeleteState() {
        return this.deleteState;
    }

    public boolean getMoveState() {
        return this.moveState;
    }

    public boolean getToolBufferSavedState() {
        return this.toolBufferSavedState;
    }

    public boolean getWiringState() {
        return this.wiringState;
    }

    public boolean getLongPress() {
        return longPress;
    }

    public boolean getSingleTapState() {
        return this.singleTapState;
    }

    public boolean getPassiveTouchState() {
        evaluatePassiveTouchState();
        return this.passiveTouchState;
    }

    public void evaluatePassiveTouchState() {

        passiveTouchState = !drawingState && !moveState && !deleteState;

    }

    public void setLongPress(boolean state) {
        longPress = state;
    }
}
