package com.policeapp.framework.Widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class AppRegularTextView extends AppCompatTextView {
    public AppRegularTextView(Context context) {
        super(context);
        setStyle(context);
    }

    public AppRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStyle(context);
    }

    public AppRegularTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setStyle(context);
    }

    private void setStyle(Context context){
//        Typeface typeface = ResourcesCompat.getFont(context, R.font.montserrat);
//        this.setTypeface(typeface);
    }
}
