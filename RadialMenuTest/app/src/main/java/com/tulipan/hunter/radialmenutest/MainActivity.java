package com.tulipan.hunter.radialmenutest;

import android.graphics.Color;
import android.support.annotation.MainThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tulipan.hunter.radialmenutest.views.RadialMenuView;

public class MainActivity extends AppCompatActivity {
    private RadialMenuView mRadialMenu;
    private View mColorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRadialMenu = (RadialMenuView) findViewById(R.id.radial_menu);
        mColorView = findViewById(R.id.colorbox);


        mRadialMenu.setActionExecutor(new RadialMenuView.ActionExecutor() {
            @Override
            public void performMenuAction(int menuItem) {
                switch (menuItem) {
                    case 1:
                        mColorView.setBackgroundColor(Color.RED);
                        break;

                    case 2:
                        mColorView.setBackgroundColor(Color.argb(255, 255, 140, 0));
                        break;

                    case 3:
                        mColorView.setBackgroundColor(Color.YELLOW);
                        break;

                    case 4:
                        mColorView.setBackgroundColor(Color.GREEN);
                        break;

                    case 5:
                        mColorView.setBackgroundColor(Color.BLUE);
                        break;

                    case 6:
                        mColorView.setBackgroundColor(Color.argb(255, 148, 0, 211));
                        break;

                    case 7:
                        mColorView.setBackgroundColor(Color.BLACK);
                        break;

                    default:
                        mColorView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                        break;
                }
            }
        });

    }
}
