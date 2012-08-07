package com.example.ActionBarSpike;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class EmbeddedSearchActivity extends BaseActivity {

    private static final String TAG = EmbeddedSearchActivity.class.getSimpleName();

    private EditText searchEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.embedded_search_layout);
        searchEditText = (EditText) findViewById(R.id.searchText);
    }

    @Override
    protected void addSearchResultsFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.search_results_container, new SearchResultsFragment(), null);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.removeItem(R.id.menu_search);
        return result;
    }

    @Override
    protected EditText getSearchEditText() {
        return searchEditText;
    }

}
