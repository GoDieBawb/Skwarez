package com.bigbawb.squaregame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bob on 5/12/17.
 */

public class PalletteView extends View {

    MainActivity main;
    Paint paint;
    ArrayList<Rectangle> recs;
    String[] colorOrder = new String[MainActivity.colors.size()];
    int width;
    int height;
    int butCount;

    public PalletteView(Context context) {
        super(context);
        main     = (MainActivity) context;
        recs     = new ArrayList<>();
        height   = MainActivity.height;
        width    = MainActivity.width;
        butCount = MainActivity.colors.size();
        initRectangles();
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        invalidate(); //causes a canvas draw
    }

    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.rgb(47,79,79));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, width, height, paint);
        paint.setTextSize(100);
        paint.setColor(Color.rgb(0,0,0));
        drawRectangles(canvas);
    }

    private void drawRectangles(Canvas canvas) {
        for (int i = 0; i < recs.size(); i++) {
            Rectangle r = recs.get(i);
            paint.setColor(Color.rgb(47,79,79));
            canvas.drawRect(r.x-r.width/2, r.y-r.height/2, r.x+r.width/2, r.y+r.height/2, paint);
            int[] color = MainActivity.toRGB(r.color);
            paint.setColor(Color.rgb(color[0], color[1], color[2]));
            canvas.drawRect(r.x-r.width/2+r.width/10, r.y-r.height/2+r.height/10, r.x+r.width/2-r.width/10, r.y+r.height/2-r.height/10, paint);
        }
    }

    private void initRectangles() {

        int cur = 0;
        Iterator it = MainActivity.colors.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            Rectangle r = new Rectangle();
            r.width  = width;
            r.height = height*.87f/butCount;
            r.x      = width/2;
            r.y      = (r.height/2)+r.height*cur;
            r.color  = (String) pair.getKey();
            colorOrder[cur] = r.color;
            recs.add(r);
            cur++;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        if (e.getAction() == MotionEvent.ACTION_DOWN) {

            float bSize = height*.87f / butCount;
            float spot  = e.getY();
            boolean got = false;

            for (int i = 0; i < butCount; i++) {


                if (spot < bSize*i) {
                    got = true;
                    MainActivity.color = colorOrder[i-1];
                    break;
                }

            }

            if (!got) MainActivity.color = colorOrder[butCount-1];

            /*
            if (e.getY()> bSize * 5) {
                MainActivity.color = "Purple";
            }
            else if (e.getY() > bSize * 4) {
                MainActivity.color = "Cyan";
            }
            else if (e.getY() > bSize * 3) {
                MainActivity.color = "Yellow";
            }
            else if (e.getY() > bSize * 2) {
                MainActivity.color = "Blue";
            }
            else if (e.getY() > bSize * 1) {
                MainActivity.color = "Green";
            }
            else if (e.getY() > 0) {
                MainActivity.color = "Red";
            }
            */
            main.showDrawView();

        }

        return false;

    }

}
