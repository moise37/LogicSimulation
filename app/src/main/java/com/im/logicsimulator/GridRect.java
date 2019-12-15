package com.im.logicsimulator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

// a Grid class with all the information needed to create a 2D array of individual cells
// The grid is created of a 2D array of rectangles
public class GridRect {
    private int numColumns, numRows;
    private int screenWidth, screenHeight;
    private int gridWidth, gridHeight;
    private int cellWidth, cellHeight;
    private Paint paint = new Paint();
    private Rect[][] cells;
    private Rect touchedCell;


    public GridRect(int numColumns, int numRows, int screenWidth, int screenHeight) {
        this.numColumns = numColumns;
        this.numRows = numRows;
        cells = new Rect[numColumns][numRows];
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        gridHeight = screenHeight / numRows;
        gridWidth = screenWidth / numColumns;

        createGrid();
        cellWidth = cells[0][0].width();
        cellHeight = cells[0][0].width();

    }

    //This function draws all the grid cells in the 2D array
    //it traverses each cell 1 by 1 and draws its borders individually
    //It also checks if there is a touched cell that needs to be drawn and if so takes appropriate action
    public void drawGrid(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);
        if (touchedCell != null) {
            createHitBox(touchedCell, canvas);
        }

    }

    public void createGrid() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < numColumns; i++) {
            for (int k = 0; k < numRows; k++) {
                cells[i][k] = new Rect(x, y, x + gridWidth, y + gridHeight);
                x = gridWidth + x + 1;
                if (k == numRows - 1) {
                    x = 0;
                }
            }
            y = gridHeight + y + 1;
        }
    }

    //this function checks whether two touched points are inside any of the rectangles in the 2D Matrix
    //if it finds a match it sets the found cell as the touched cell in the grid
    public Rect checkGrid(float touchedX, float touchedY) {
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                if (cells[i][k].contains((int) touchedX, (int) touchedY)) {
                    touchedCell = cells[i][k];
                    //System.out.println("cell " +  "[ "  + i + ", " + k +  " ]" + "has been touched");
                    break;
                }
            }
        }
        return touchedCell;
    }

    //draws a hitbox around the passed cell (Rectangle)
    public void createHitBox(Rect rect, Canvas canvas) {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3f);
        canvas.drawRect(rect, paint);
    }

    //sets the touched cell to null so that the hitboxes are not drawn on a draw grid call
    public void invalidateTouchedCell() {
        touchedCell = null;
    }

    public int getGridWidth() {
        return this.gridWidth;
    }

    public int getGridHeight() {
        return this.gridHeight;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getCellWidth() {
        return cellWidth;
    }


}
