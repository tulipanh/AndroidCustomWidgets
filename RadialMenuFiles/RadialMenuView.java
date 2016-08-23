package com.tulipan.hunter.radialmenutest.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.tulipan.hunter.radialmenutest.R;

/**
 * Created by Hunter on 8/21/2016.
 */

public class RadialMenuView extends View {
    private Context mContext;
    private int mNumOptions;
    private String mOptionsText;
    private String[] mOptions;
    private Point mOrigin;
    private ActionExecutor mActionExecutor;

    private int mActive;
    private int innerRadius;
    private int outerRadius;
    private Paint bodyPaint = new Paint();
    private Paint framePaint = new Paint();
    private Paint textPaint = new Paint();

    public RadialMenuView(Context context) {
        super(context);
        init(context);
    }

    public RadialMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RadialMenuView, 0, 0);
        try {
            mNumOptions = a.getInteger(R.styleable.RadialMenuView_numberOfOptions, 0);
            mOptionsText = a.getString(R.styleable.RadialMenuView_optionsText);
            mOptions = mOptionsText.split("/");
            if (mOptions.length != mNumOptions) throw new IllegalArgumentException();
            if (mNumOptions < 1 || mNumOptions > 12) throw new IllegalArgumentException();
        } finally {
            a.recycle();
        }
        init(context);
    }

    public RadialMenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        // Perform normal constructor stuff here.
        // Could have option to set style with a special style in styles.xml, just doing it manually for now
        bodyPaint.setColor(Color.argb(200, 150, 150, 150));
        framePaint.setColor(Color.argb(200, 30, 30, 30));
        textPaint.setColor(Color.argb(255, 0, 0, 0));
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.setTextSize(20f);
        mActive = 0;
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        mOrigin = new Point(getWidth()/2, getHeight()/2);
        innerRadius = getWidth()/6;
        outerRadius = getWidth()/2;
    }

    @Override
    public void onDraw(Canvas canvas) {
        bodyPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(mOrigin.x, mOrigin.y, outerRadius - 4, bodyPaint);

        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setStrokeWidth(6f);
        framePaint.setStrokeJoin(Paint.Join.ROUND);

        canvas.drawCircle(mOrigin.x, mOrigin.y, outerRadius - 4, framePaint);
        canvas.drawCircle(mOrigin.x, mOrigin.y, innerRadius, framePaint);

        framePaint.setStrokeWidth(3f);
        Point startOrig = new Point(0, innerRadius);
        Point endOrig = new Point(0, outerRadius - 4);
        Point start = new Point(startOrig);
        Point end = new Point(endOrig);

        for (int i = 1; i <= mNumOptions; i++) {
            canvas.drawLine(mOrigin.x+start.x, mOrigin.y+start.y, mOrigin.x+end.x, mOrigin.y+end.y, framePaint);
            start = rotate(startOrig, i);
            end = rotate(endOrig, i);
        }

        startOrig.set((int) (startOrig.x*Math.cos(Math.PI/mNumOptions) + startOrig.y*Math.sin(Math.PI/mNumOptions))
                , (int) (-1*startOrig.x*Math.sin(Math.PI/mNumOptions) + startOrig.y*Math.cos(Math.PI/mNumOptions)));

        endOrig.set((int) (endOrig.x*Math.cos(Math.PI/mNumOptions) + endOrig.y*Math.sin(Math.PI/mNumOptions))
                , (int) (-1*endOrig.x*Math.sin(Math.PI/mNumOptions) + endOrig.y*Math.cos(Math.PI/mNumOptions)));

        start = new Point(startOrig);
        end = new Point(endOrig);

        for (int i = 1; i <= mNumOptions; i++) {
            Path textPath = new Path();
            textPath.moveTo(mOrigin.x+start.x, mOrigin.y+start.y);
            textPath.lineTo(mOrigin.x + end.x, mOrigin.y + end.y);
            canvas.drawTextOnPath(mOptions[i - 1], textPath, 10f, 0f, textPaint);
            start = rotate(startOrig, i);
            end = rotate(endOrig, i);
        }
    }

    private Point rotate(Point point, int i) {
        int oldX = point.x;
        int oldY = point.y;

        double angle = i*2*Math.PI/mNumOptions;

        int newX = (int) (oldX*Math.cos(angle) + oldY*Math.sin(angle));
        int newY = (int) (-1*oldX*Math.sin(angle) + oldY*Math.cos(angle));

        return new Point(newX, newY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        double dx = x - (mOrigin.x + getX());
        double dy = y - (mOrigin.y + getY());
        double distance = Math.sqrt(dx * dx + dy * dy);
        double angle = Math.atan2(dx, dy);
        if (angle < 0) angle += (2*Math.PI);
        boolean cont = true;

        switch(e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cont = true;
                break;

            case MotionEvent.ACTION_MOVE:
                /**
                 * This should check where the event took place,
                 * translate this position to a section of the radial menu,
                 * and highlight it somehow.
                 */
                if (distance < innerRadius) {
                    if(mActive != 0) mActive = 0;
                } else {
                    double percent = angle/(2*Math.PI);
                    int sectionNumber = (int) (percent / (1.0d/mNumOptions)) + 1;
                    if (mActive != sectionNumber) {
                        mActive = sectionNumber;
                    }
                }
                cont = true;
                break;

            case MotionEvent.ACTION_UP:
                /**
                 * This should check where the event took place,
                 * translate this position to a section of the radial menu,
                 * and perform the action related to that section.
                 */
                mActionExecutor.performMenuAction(mActive);
                cont = true;
                break;
        }

        return cont;
    }

    public int getCenterX() {
        return mOrigin.x;
    }

    public int getCenterY() {
        return mOrigin.y;
    }

    public void setActionExecutor(ActionExecutor e) {
        mActionExecutor = e;
    }

    public static class ActionExecutor {

        public void performMenuAction(int menuItem) {
            switch (menuItem) {
                default:
                    break;
            }
        }
    }
}
