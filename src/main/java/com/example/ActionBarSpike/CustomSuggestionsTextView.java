package com.example.ActionBarSpike;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;

/** Shamelessly hacked up version of {@link android.widget.AutoCompleteTextView}. */
public class CustomSuggestionsTextView extends EditText {

    public interface DropDownContentViewProvider {
        View getDropDownContentView();
    }

    private DropDownContentViewProvider contentViewProvider;

    private PopupWindow popupWindow;
    private PassThroughClickListener passThroughClickListener = new PassThroughClickListener();

    @SuppressWarnings("UnusedDeclaration")
    public CustomSuggestionsTextView(Context context) {
        super(context);
        initialize(context);
    }

    @SuppressWarnings("UnusedDeclaration")
    public CustomSuggestionsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    @SuppressWarnings("UnusedDeclaration")
    public CustomSuggestionsTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        Log.e(getClass().getSimpleName(), "Initialize");
        popupWindow = new PopupWindow(context);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setFocusable(true);

        addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (getText().length() > 0) {
                    showDropDown();
                } else {
                    dismissDropDown();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        super.setOnClickListener(passThroughClickListener);
    }

    public boolean isPopupShowing() {
        return popupWindow.isShowing();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && isPopupShowing()) {
            // special case for the back key, we do not even try to send it
            // to the drop down list but instead, consume it immediately
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                KeyEvent.DispatcherState state = getKeyDispatcherState();
                if (state != null) {
                    state.startTracking(event, this);
                }
                return true;
            } else if (event.getAction() == KeyEvent.ACTION_UP) {
                KeyEvent.DispatcherState state = getKeyDispatcherState();
                if (state != null) {
                    state.handleUpEvent(event);
                }
                if (event.isTracking() && !event.isCanceled()) {
                    dismissDropDown();
                    return true;
                }
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    protected void onDisplayHint(int hint) {
        super.onDisplayHint(hint);
        switch (hint) {
            case INVISIBLE:
                dismissDropDown();
                break;
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused) {
            dismissDropDown();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus) {
            dismissDropDown();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        dismissDropDown();
        super.onDetachedFromWindow();
    }

    @Override
    protected boolean setFrame(final int l, int t, final int r, int b) {
        boolean result = super.setFrame(l, t, r, b);

        if (isPopupShowing()) {
            showDropDown();
        }

        return result;
    }

    public void showDropDown() {
        final View anchor = this;
        post(new Runnable() {
            @Override
            public void run() {
                if (!isPopupShowing()) {
                    // Make sure the list does not obscure the IME when shown for the first time.
                    popupWindow.setInputMethodMode(ListPopupWindow.INPUT_METHOD_NEEDED);
                }
                setPopupDimensions(anchor);
                if (anchor.getWindowToken() != null) { // Protect against trying to show dropdown on a destroyed view
                    popupWindow.setContentView(contentViewProvider.getDropDownContentView());
                    popupWindow.showAsDropDown(anchor);
                }
            }
        });
    }

    private void setPopupDimensions(View anchor) {
        popupWindow.setWidth(anchor.getWidth());
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void dismissDropDown() {
        popupWindow.dismiss();
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        passThroughClickListener.wrapped = listener;
    }

    /**
     * Allows us a private hook into the on click event without preventing users from setting
     * their own click listener.
     */
    private class PassThroughClickListener implements OnClickListener {

        private View.OnClickListener wrapped;

        /** {@inheritDoc} */
        public void onClick(View v) {
            // If the dropdown is showing, bring the keyboard to the front
            // when the user touches the text field.
            if (isPopupShowing()) {
                popupWindow.setInputMethodMode(ListPopupWindow.INPUT_METHOD_NEEDED);
                showDropDown();
            }

            if (wrapped != null) {
                wrapped.onClick(v);
            }
        }
    }

    public void setContentViewProvider(DropDownContentViewProvider contentViewProvider) {
        this.contentViewProvider = contentViewProvider;
    }
}
