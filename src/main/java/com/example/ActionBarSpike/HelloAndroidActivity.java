package com.example.ActionBarSpike;

import android.os.Bundle;
import android.util.Log;

public class HelloAndroidActivity extends BaseActivity {

    private static final String TAG = HelloAndroidActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
    }

}

