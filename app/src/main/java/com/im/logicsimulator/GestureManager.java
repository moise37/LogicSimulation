package com.im.logicsimulator;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureManager extends GestureDetector.SimpleOnGestureListener {
    private StateManager viewStateManager;
    public GestureManager(StateManager stateManager) {
        viewStateManager = stateManager;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        viewStateManager.enableSingleTapState();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        viewStateManager.enableLongPressState();
    }

    @Override
    public boolean onDown(MotionEvent e){
        viewStateManager.enableOnDownState();
        return true;
    }
}
