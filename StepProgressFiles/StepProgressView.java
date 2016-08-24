package com.tulipan.hunter.stepprogresstest.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.tulipan.hunter.stepprogresstest.R;

import java.util.ArrayList;

/**
 * Created by Hunter on 8/5/2016.
 */

/**
 * In order to use the "progressLevel" attribute, the XML where this view is declared must have
 * an xml namespace for "http://schemas.android.com/apk/res/com.tulipan.hunter.vesselbuilder or the
 * name of whatever package this class is included in.
 */

public class StepProgressView extends View {
    private Context mContext;
    private Integer mNumOfSteps;
    private Integer mProgressLevel;
    private String mStepsText;
    private String[] mStepNames;
    private Paint comPaint = new Paint();
    private Paint uncPaint = new Paint();
    private Paint accPaint = new Paint();

    public StepProgressView(Context context) {
        super(context);
        init(context);
    }

    public StepProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StepProgressView, 0, 0);
        try {
            mNumOfSteps = a.getInteger(R.styleable.StepProgressView_numberOfSteps, 0);
            mProgressLevel = a.getInteger(R.styleable.StepProgressView_progressLevel, 0);
        } finally {
            a.recycle();
        }
        init(context);
    }

    public StepProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        if (mNumOfSteps < 1) throw new IllegalArgumentException();
        mStepNames = new String[mNumOfSteps];
        if (mProgressLevel > mNumOfSteps) mProgressLevel = mNumOfSteps;
        if (mProgressLevel < 1) mProgressLevel = 1;
        comPaint.setColor(ContextCompat.getColor(context, R.color.progressComplete));
        uncPaint.setColor(ContextCompat.getColor(context, R.color.progressIncomplete));
    }

    @Override
    public void onDraw(Canvas canvas) {
        /**
         * This should draw four solid circles connected by three thin rectangles (or thick strokes).
         * The rectangles should have arrows on them if possible pointing to the right.
         * They should be light colored and then darkened as progress is made.
         * The current step should be highlighted in some way (perhaps a white circle or something.
         * (0-4 with 0 indicating no progress and 4 indicating on the last step)
         * They should be spaced and sized using the getWidth() and getHeight() functions with some
         * reasonable minimum and maximum (or just maximum).
         */

        Point[] circleCenters = new Point[mNumOfSteps];

        int spacer = getWidth()/(2*mNumOfSteps);
        for (int i = 0; i < mNumOfSteps; i++) {
            circleCenters[i] = new Point((2*i+1)*spacer, getHeight()/2);
        }

        /* Draw the lines between the steps, darker if already completed */
        float circleRadius = (spacer/2 < 0.4*getHeight()) ? spacer/2 : (float)(0.4*getHeight());
        comPaint.setStrokeWidth(circleRadius/2);
        comPaint.setStrokeJoin(Paint.Join.ROUND);
        uncPaint.setStrokeWidth(circleRadius/2);
        uncPaint.setStrokeJoin(Paint.Join.ROUND);
        int colorSplit;
        colorSplit = mProgressLevel-1;
        canvas.drawLine(circleCenters[0].x, circleCenters[0].y, circleCenters[colorSplit].x, circleCenters[colorSplit].y, comPaint);
        canvas.drawLine(circleCenters[colorSplit].x, circleCenters[colorSplit].y, circleCenters[circleCenters.length-1].x, circleCenters[circleCenters.length-1].y, uncPaint);

        /* Draw the circles indicating the steps, darker if already completed */
        comPaint.setStrokeWidth(circleRadius/8);
        comPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        uncPaint.setStrokeWidth(circleRadius/8);
        uncPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i = 0; i <= colorSplit; i++) {
            canvas.drawCircle(circleCenters[i].x, circleCenters[i].y, circleRadius, comPaint);
        }
        for (int i = colorSplit+1; i < circleCenters.length; i++) {
            canvas.drawCircle(circleCenters[i].x, circleCenters[i].y, circleRadius, uncPaint);
        }

        /* Draw the highlight indicating which step you are on. */
        uncPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(circleCenters[colorSplit].x, circleCenters[colorSplit].y, (float)(0.75*circleRadius), uncPaint);
        uncPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(circleCenters[colorSplit].x, circleCenters[colorSplit].y, (float)(0.5*circleRadius), uncPaint);
    }
}
