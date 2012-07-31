package com.example.ActionBarSpike;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.debug.hv.ViewServer;

public class HelloAndroidActivity extends SherlockActivity {

    private static final String TAG = "ActionBarSpike";

    private Handler handler = new Handler();

    private CustomSuggestionsTextView searchEditText;

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
        getSupportActionBar().setTitle("Testing");

        ViewServer.get(this).addWindow(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getSupportMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        final MenuItem search = menu.findItem(R.id.menu_search);
        searchEditText = (CustomSuggestionsTextView) search.getActionView();

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

}

