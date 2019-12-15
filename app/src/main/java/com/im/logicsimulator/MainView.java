package com.im.logicsimulator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainView extends View {

    Canvas backgroundCanvas;
    Context context;
    GridRect grid;
    CurrentGame currentGame;

    StateManager stateManager;
    GestureDetector motionDetector;

    Wire wire;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        backgroundCanvas = new Canvas();
        stateManager = new StateManager();
        currentGame = new CurrentGame();
        GestureManager gestureHandler = new GestureManager(stateManager);
        motionDetector = new GestureDetector(context, gestureHandler);
        wire = new Wire();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        grid.drawGrid(canvas);
        if (stateManager.getWiringState()) {
            wire.draw(canvas);
        }
        currentGame.drawTools(canvas);
        invalidate();
    }

    public void setDisplay(DisplayInfo display) {
        Bitmap myBitmap = Bitmap.createBitmap(display.getScreenWidth(), display.getScreenHeight(), Bitmap.Config.ARGB_8888);
        backgroundCanvas = new Canvas(myBitmap);

        int width = backgroundCanvas.getWidth();
        int height = backgroundCanvas.getHeight();
        grid = new GridRect(10, 10, width, height);
        draw(backgroundCanvas);
    }

    public CurrentGame getCurrentGame() {
        return this.currentGame;
    }

    public void addTool(Resources resources, int pictureID) {
        stateManager.disableAllStates();
        stateManager.enableDrawingState();
        currentGame.addNewTool(resources, pictureID, grid);
    }

    public void moveTool() {
        stateManager.disableAllStates();
        stateManager.enableMoveState();
    }

    public void deletePressed() {
        stateManager.disableAllStates();
        stateManager.enableDeleteState();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect touchedCell = grid.checkGrid(event.getX(), event.getY());

        //When all states are turned off, passiveTouchAction will be executed.
        if (stateManager.getPassiveTouchState()) {
            grid.invalidateTouchedCell();
            return passiveTouchAction(event, touchedCell);
        }

        if (stateManager.getDeleteState() && currentGame.getTool(touchedCell) != null) {
            stateManager.disableDeleteState();
            deleteObject(touchedCell);
        }

        if (stateManager.getMoveState() && currentGame.getTool(touchedCell) != null) {
            currentGame.setUpMove(touchedCell);
            stateManager.enableToolBufferSavedState();
            stateManager.disableMoveState();
            stateManager.enableDrawingState();
            grid.invalidateTouchedCell();
        }

        if (stateManager.getDrawingState()) {
            drawDraggingObject(event, touchedCell);
            return true;
        }

        grid.invalidateTouchedCell();
        return false;
    }

    private boolean passiveTouchAction(MotionEvent event, Rect touchedCell) {
        if (currentGame.getTool(touchedCell) != null || stateManager.getLongPress()) {
            motionDetector.onTouchEvent(event);

            if (stateManager.getSingleTapState()) {
                Log.d("###", "Single tap registered");
                if (currentGame.getTool(touchedCell) instanceof Switch) {
                    currentGame.toggleSwitch(touchedCell);
                }
                stateManager.disableSingleTapState();
            } else if (stateManager.getLongPress()) {
                //This if statement will only be entered if the selected cell is not empty and
                //when the wiring state is disabled. This is to ensure that when a user is dragging
                //a wire and the drag over a different object, the wire start position does not update
                //to the object that is dragged over.
                if (currentGame.getTool(touchedCell) != null && !stateManager.getWiringState()) {
                    currentGame.saveConnection1(touchedCell);
                    setupDraggingWire(touchedCell);
                }
                drawDraggingWire(event, touchedCell);
            }
        }
        return true;
    }

    private void setupDraggingWire(Rect touchedCell) {
        Tool temp = currentGame.getTool(touchedCell);
        //You cannot start a wire connection at an output tool.
        if (!(temp instanceof Output)) {
            //This wire will be passed to the connection handler once two objects are selected to
            //connect.
            vibratePhone();
            wire = new Wire();
            wire.setPaintWhite();
            wire.setStart(((Node) temp).getOutputPositionX(), ((Node) temp).getOutputPositionY());
            stateManager.enableWiringState();
        }
    }

    //Issue when you long press but immediately move finger.
    private void drawDraggingWire(MotionEvent event, Rect touchedCell) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (wire != null) {
                    wire.setEnd(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                stateManager.disableLongPressState();
                stateManager.disableWiringState();
                currentGame.saveConnection2(touchedCell);
                currentGame.connectTools(wire);
                wire = null;
                vibratePhone();
                break;
        }
    }

    private void drawDraggingObject(MotionEvent event, Rect touchedCell) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                currentGame.setToolBufferPos(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_UP:
                if (uniqueGridSquareSelected(touchedCell)) {
                    currentGame.setToolBufferPos(touchedCell.centerX(), touchedCell.centerY());
                    currentGame.addToolBuffer();
                    stateManager.disableToolBufferSavedState();
                } else if (stateManager.getToolBufferSavedState()) {
                    currentGame.reloadToolBufferPos();
                    currentGame.addToolBuffer();
                    stateManager.disableToolBufferSavedState();
                } else {
                    Toast.makeText(context, "Cannot add a gates on top of each other", Toast.LENGTH_SHORT).show();
                    currentGame.resetToolBuffer();
                }
                stateManager.disableDrawingState();
                grid.invalidateTouchedCell();
                break;
        }
    }

    private void deleteObject(Rect touchedCell) {
        currentGame.deleteTool(touchedCell);
        grid.invalidateTouchedCell();
    }

    private boolean uniqueGridSquareSelected(Rect touchedCell) {
        return currentGame.getTool(touchedCell) == null;
    }

    private void vibratePhone() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(10);
        }
    }
}
