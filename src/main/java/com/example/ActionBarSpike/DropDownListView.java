package com.example.ActionBarSpike;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class DropDownListView extends ListView {

    public DropDownListView(Context context) {
        super(context);
    }

    public DropDownListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DropDownListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean hasWindowFocus() {
        return true;
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    public boolean hasFocus() {
        return true;
    }
}
