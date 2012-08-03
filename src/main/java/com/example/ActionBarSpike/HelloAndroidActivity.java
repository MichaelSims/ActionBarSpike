package com.example.ActionBarSpike;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.debug.hv.ViewServer;

public class HelloAndroidActivity extends Activity {

    private static final String TAG = HelloAndroidActivity.class.getSimpleName();

    private Handler handler = new Handler();

    private PopupWindow popupWindow;

    private EditText searchEditText;
    private TextView textView;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        getActionBar().setTitle("Testing");

        textView = new TextView(this);

        popupWindow = new PopupWindow(this);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ViewServer.get(this).addWindow(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        final MenuItem search = menu.findItem(R.id.menu_search);
        searchEditText = (EditText) search.getActionView();
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    showDropDown();
                } else {
                    dismissDropDown();
                }
            }
        });

        search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                enterSearchMode();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                exitSearchMode();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void enterSearchMode() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "EXPAND!");
                searchEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    private void exitSearchMode() {
        searchEditText.setText("");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    public void showDropDown() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!popupWindow.isShowing()) {
                    // Make sure the list does not obscure the IME when shown for the first time.
                    popupWindow.setInputMethodMode(ListPopupWindow.INPUT_METHOD_NEEDED);
                }
                popupWindow.setWidth(searchEditText.getWidth());
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                if (searchEditText.getWindowToken() != null) { // Protect against trying to show dropdown on a destroyed view
                    textView.setText("Hello, you typed, initially at least " + searchEditText.getText().toString());
                    popupWindow.setContentView(textView);
                    popupWindow.showAsDropDown(searchEditText);
                }
            }
        });
    }

    public void dismissDropDown() {
        popupWindow.dismiss();
    }

}

