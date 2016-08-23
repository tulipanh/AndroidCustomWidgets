package com.tulipan.hunter.radialmenutest.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by Hunter on 8/21/2016.
 */
public class RadialMenuSurface extends FrameLayout {
    private Context mContext;
    private RadialMenuView mRadialMenu;
    /**
     * This view should exist in the center of the screen, no closer the edge than
     * the radius of the menu it serves.
     *
     * It should simply await touch events, move the radial menu to the location of the event
     * and make it visible.
     *
     * When the touch event is released, it should make the radial menu disappear again.
     */

    public RadialMenuSurface(Context context) {
        super(context);
    }

    public RadialMenuSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadialMenuSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(Context context) {
        mContext = context;
        int n = getChildCount();
        for (int i = 0; i < n; i++) {
            mRadialMenu = (RadialMenuView) getChildAt(i);
        }
        if (mRadialMenu != null) {
            mRadialMenu.setVisibility(View.INVISIBLE);
        } else {
            Toast.makeText(mContext, "Radial Menu Is Null", Toast.LENGTH_SHORT).show();
        }
        // Perform normal constructor stuff here.
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init(getContext());
    }

    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        boolean cont = true;

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mRadialMenu.setX(x - mRadialMenu.getCenterX());
                mRadialMenu.setY(y - mRadialMenu.getCenterY());
                mRadialMenu.setVisibility(View.VISIBLE);
                break;

            case MotionEvent.ACTION_MOVE:
                mRadialMenu.onTouchEvent(e);
                break;

            case MotionEvent.ACTION_UP:
                mRadialMenu.setVisibility(View.INVISIBLE);
                mRadialMenu.onTouchEvent(e);
                break;
        }
        return cont;
    }
}
