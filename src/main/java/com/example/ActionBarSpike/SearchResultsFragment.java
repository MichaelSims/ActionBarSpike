package com.example.ActionBarSpike;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;

/** Inspired by {@link android.support.v4.app.DialogFragment} */
public class SearchResultsFragment extends Fragment {

    private TextView textView;
    private EditText searchEditText;

    private boolean showDropDown;

    private BaseActivity activity;

    private PopupWindow popupWindow;

    private Handler handler = new Handler();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_results, container, false);
        textView = (TextView) v.findViewById(R.id.search_results_text_view);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if (view.getParent() == null) {
            showDropDown = true; // We were added without being attached to a container, so assume that we need to show the dropdown
        }

        if (showDropDown) {
            popupWindow = new PopupWindow(getActivity());
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popupWindow.setContentView(view);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        searchEditText = activity.getSearchEditText();
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText("Hello, you typed " + s.toString());
                if (showDropDown) {
                    if (s.length() > 0) {
                        showDropDown();
                    } else {
                        dismissDropDown();
                    }
                }
            }
        });
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
                    popupWindow.showAsDropDown(searchEditText);
                }
            }
        });
    }

    public void dismissDropDown() {
        if (!showDropDown) {
            throw new IllegalStateException("No dropdown to dismiss");
        }
        popupWindow.dismiss();
    }

}
