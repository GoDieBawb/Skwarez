package com.bigbawb.squaregame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by bob on 5/12/17.
 */

class DrawView extends View {

    static ArrayList<Rectangle> recs;
    static boolean              init;
    MainActivity main;
    private Paint paint;
    int height;
    int width;
    int columnCount;

    public DrawView(Context context) {
        super(context);
        main    = (MainActivity) context;
        height  = MainActivity.height;
        width   = MainActivity.width;
        columnCount = 15;
        if (!init)
            createRectangles();
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.rgb(255,255,0));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, MainActivity.width, MainActivity.height, paint);
        paint.setTextSize(100);
        paint.setColor(Color.rgb(0,0,0));
        drawRectangles(canvas);
    }

    private void drawRectangles(Canvas canvas) {
        for (int i = 0; i < recs.size(); i++) {
            Rectangle r = recs.get(i);
            paint.setColor(Color.rgb(125, 125, 125));
            canvas.drawRect(r.x-r.width/2, r.y-r.height/2, r.x+r.width/2, r.y+r.height/2, paint);
            int[] color = MainActivity.toRGB(r.color);
            paint.setColor(Color.rgb(color[0], color[1], color[2]));
            canvas.drawRect(r.x-r.width/2+r.width/30, r.y-r.height/2+r.height/30, r.x+r.width/2-r.width/30, r.y+r.height/2-r.height/30, paint);
        }
    }

    private void createRectangles() {

        init = true;
        recs = new ArrayList<>();
        int row       = 0;
        int col       = 0;
        float columns = height / (width/columnCount);
        int c = Math.round(columns);

        for (int i = 0; i < c*columnCount; i++) {

            Rectangle r = new Rectangle();
            r.width     = width/columnCount;
            r.height    = height/columns;

            r.y         = (r.height*row)+r.height/2;
            r.x         = r.width/2 + (r.width * col);
            r.color     = "White";

            recs.add(r);
            col++;
            if (i%columnCount == 0 && i > 1) {
                row++;
                col=0;
            }

        }

    }

    float yStart;
    float yEnd;
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            yStart= e.getY();
            return true;
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {

            yEnd = e.getY();
            float dist = Math.abs(yStart-yEnd);

            if (dist > height/3) {
                main.showPalletteView();
                return false;
            }

            Rectangle r = getRectangleAt(e.getX(),e.getY());
            if (r.color.equals(MainActivity.color))
                r.color = "White";
            else
                r.color=MainActivity.color;
            invalidate();
            return false;

        }

        return false;

    }

    private Rectangle getRectangleAt(float x, float y) {

        float rows = height / (width/columnCount);
        float w    = width/columnCount;
        float h    = height/rows;

        int column=0;

        for (int i = 0; i < columnCount; i++) {
            if (x < w * i) {
                column = i;
                break;
            }
        }

        int row=0;

        for (int i = 0; i < rows; i++) {
            if (y < (h * i)+h) {
                row = i;
                break;
            }
        }

        int index = row*columnCount+column;

        if (index%columnCount==0) {
            index+=columnCount;
        }

        if (index<0) {
            index=columnCount;
        }

        if (index <= columnCount) {
            index--;
        }

        return recs.get(index);

    }

}
