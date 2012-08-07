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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/** Inspired by {@link android.support.v4.app.DialogFragment} */
public class SearchResultsFragment extends Fragment {

    private TextView textView;
    private EditText searchEditText;

    private ListView searchResultsList;

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
        searchResultsList = (ListView) v.findViewById(R.id.search_results_list);
        searchResultsList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, LIST_DATA));
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

    private static final String[] LIST_DATA = new String[]{
            "Philip J. Fry",
            "Bender",
            "Leela",
            "Professor Farnsworth",
            "Doctor Zoidberg",
            "Amy Wong",
            "Hermes Conrad",
            "Nibbler",
            "Brannigan, Zapp",
            "Kroker, Kif",
            "Mom",
            "Agnew, Spiro",
            "Boxy",
            "Brain Slugs",
            "Brain Spawn",
            "Calculon",
            "Crushinator, The",
            "Changstein-El-Gamal, Father",
            "Chanukah Zombie",
            "Clamps",
            "Conrad, Dwight",
            "Conrad, LeBarbara",
            "Donbot",
            "Elzar",
            "Farnsworth, Cubert",
            "Flexo",
            "Gore, Al",
            "Gypsy-bot",
            "Hawking, Stephen",
            "Hedonism Bot",
            "Horrible Gelatinous Blob",
            "Hyperchicken",
            "Hypnotoad",
            "Kwanzaabot",
            "Lrrr",
            "Linda",
            "McDoogal, Hattie",
            "Michelle",
            "Morbo",
            "Mousepad, Joey",
            "Munchnik Randy",
            "Nixon, Richard",
            "Panucci, Mr.",
            "Pazuzu",
            "Petunia",
            "Preacherbot, Reverend",
            "Roberto",
            "Robot Devil",
            "Robot Santa",
            "Sal",
            "Scruffy",
            "Slim, Barbados",
            "Smitty",
            "Tate, Ethan 'Bubblegum'",
            "Tim, Tinny",
            "Turanga, Morris",
            "Turanga, Munda",
            "URL",
            "Vogel, Warden",
            "Wernstrom, Ogden",
            "Wong, Inez",
            "Wong, Leo", "Philip J. Fry",
            "Bender",
            "Leela",
            "Professor Farnsworth",
            "Doctor Zoidberg",
            "Amy Wong",
            "Hermes Conrad",
            "Nibbler",
            "Brannigan, Zapp",
            "Kroker, Kif",
            "Mom",
            "Agnew, Spiro",
            "Boxy",
            "Brain Slugs",
            "Brain Spawn",
            "Calculon",
            "Crushinator, The",
            "Changstein-El-Gamal, Father",
            "Chanukah Zombie",
            "Clamps",
            "Conrad, Dwight",
            "Conrad, LeBarbara",
            "Donbot",
            "Elzar",
            "Farnsworth, Cubert",
            "Flexo",
            "Gore, Al",
            "Gypsy-bot",
            "Hawking, Stephen",
            "Hedonism Bot",
            "Horrible Gelatinous Blob",
            "Hyperchicken",
            "Hypnotoad",
            "Kwanzaabot",
            "Lrrr",
            "Linda",
            "McDoogal, Hattie",
            "Michelle",
            "Morbo",
            "Mousepad, Joey",
            "Munchnik Randy",
            "Nixon, Richard",
            "Panucci, Mr.",
            "Pazuzu",
            "Petunia",
            "Preacherbot, Reverend",
            "Roberto",
            "Robot Devil",
            "Robot Santa",
            "Sal",
            "Scruffy",
            "Slim, Barbados",
            "Smitty",
            "Tate, Ethan 'Bubblegum'",
            "Tim, Tinny",
            "Turanga, Morris",
            "Turanga, Munda",
            "URL",
            "Vogel, Warden",
            "Wernstrom, Ogden",
            "Wong, Inez",
            "Wong, Leo", "Philip J. Fry",
            "Bender",
            "Leela",
            "Professor Farnsworth",
            "Doctor Zoidberg",
            "Amy Wong",
            "Hermes Conrad",
            "Nibbler",
            "Brannigan, Zapp",
            "Kroker, Kif",
            "Mom",
            "Agnew, Spiro",
            "Boxy",
            "Brain Slugs",
            "Brain Spawn",
            "Calculon",
            "Crushinator, The",
            "Changstein-El-Gamal, Father",
            "Chanukah Zombie",
            "Clamps",
            "Conrad, Dwight",
            "Conrad, LeBarbara",
            "Donbot",
            "Elzar",
            "Farnsworth, Cubert",
            "Flexo",
            "Gore, Al",
            "Gypsy-bot",
            "Hawking, Stephen",
            "Hedonism Bot",
            "Horrible Gelatinous Blob",
            "Hyperchicken",
            "Hypnotoad",
            "Kwanzaabot",
            "Lrrr",
            "Linda",
            "McDoogal, Hattie",
            "Michelle",
            "Morbo",
            "Mousepad, Joey",
            "Munchnik Randy",
            "Nixon, Richard",
            "Panucci, Mr.",
            "Pazuzu",
            "Petunia",
            "Preacherbot, Reverend",
            "Roberto",
            "Robot Devil",
            "Robot Santa",
            "Sal",
            "Scruffy",
            "Slim, Barbados",
            "Smitty",
            "Tate, Ethan 'Bubblegum'",
            "Tim, Tinny",
            "Turanga, Morris",
            "Turanga, Munda",
            "URL",
            "Vogel, Warden",
            "Wernstrom, Ogden",
            "Wong, Inez",
            "Wong, Leo", "Philip J. Fry",
            "Bender",
            "Leela",
            "Professor Farnsworth",
            "Doctor Zoidberg",
            "Amy Wong",
            "Hermes Conrad",
            "Nibbler",
            "Brannigan, Zapp",
            "Kroker, Kif",
            "Mom",
            "Agnew, Spiro",
            "Boxy",
            "Brain Slugs",
            "Brain Spawn",
            "Calculon",
            "Crushinator, The",
            "Changstein-El-Gamal, Father",
            "Chanukah Zombie",
            "Clamps",
            "Conrad, Dwight",
            "Conrad, LeBarbara",
            "Donbot",
            "Elzar",
            "Farnsworth, Cubert",
            "Flexo",
            "Gore, Al",
            "Gypsy-bot",
            "Hawking, Stephen",
            "Hedonism Bot",
            "Horrible Gelatinous Blob",
            "Hyperchicken",
            "Hypnotoad",
            "Kwanzaabot",
            "Lrrr",
            "Linda",
            "McDoogal, Hattie",
            "Michelle",
            "Morbo",
            "Mousepad, Joey",
            "Munchnik Randy",
            "Nixon, Richard",
            "Panucci, Mr.",
            "Pazuzu",
            "Petunia",
            "Preacherbot, Reverend",
            "Roberto",
            "Robot Devil",
            "Robot Santa",
            "Sal",
            "Scruffy",
            "Slim, Barbados",
            "Smitty",
            "Tate, Ethan 'Bubblegum'",
            "Tim, Tinny",
            "Turanga, Morris",
            "Turanga, Munda",
            "URL",
            "Vogel, Warden",
            "Wernstrom, Ogden",
            "Wong, Inez",
            "Wong, Leo"
    };

}
