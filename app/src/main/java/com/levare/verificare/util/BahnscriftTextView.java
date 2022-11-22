package com.levare.verificare.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class BahnscriftTextView extends TextView {

    public BahnscriftTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public BahnscriftTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BahnscriftTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "BAHNSCHRIFT.TTF");
            setTypeface(tf);
        }
    }
}
