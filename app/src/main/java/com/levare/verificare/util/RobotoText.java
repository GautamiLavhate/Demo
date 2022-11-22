package com.levare.verificare.util;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class RobotoText extends AppCompatTextView {
    public RobotoText(Context context) {
        super(context);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "roboto-medium.ttf");
        this.setTypeface(face);
    }

    public RobotoText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "roboto-medium.ttf");
        this.setTypeface(face);
    }

    public RobotoText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "roboto-medium.ttf");
        this.setTypeface(face);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    /*
    public RobotoText(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Helvetica_Neue.ttf");
        this.setTypeface(face);
    }

    public RobotoText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Helvetica_Neue.ttf");
        this.setTypeface(face);
    }

    public RobotoText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "Helvetica_Neue.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }*/
}
