package com.smartmedia.vademecum;

import java.util.Arrays;
import java.util.Locale;

import com.smartmedia.vademecum.adapter.RowAdapter;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

public class TreatmentsFragment extends ListFragment {

    private String[] myList;
    final private String[] circulo = {"\u2022"};
    private String title;

    public TreatmentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_treatments, container,
                false);

        ActionBar bar = getActivity().getActionBar();
        bar.setDisplayHomeAsUpEnabled(false);
        bar.setTitle(getString(R.string.app_name));

        myList = getResources().getStringArray(R.array.treaments_list);
        int colors[] = new int[myList.length];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = getColor(view, myList[i]);
        }

        final RowAdapter adapter = new RowAdapter(container.getContext(),
                R.layout.row_treatments, myList, circulo, colors);

        setListAdapter(adapter);

        EditText search = (EditText) view
                .findViewById(R.id.search_text_treaments);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().toLowerCase(Locale.getDefault());
                adapter.getFilter().filter(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

        Arrays.sort(myList);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        transition(l.getItemAtPosition(position).toString());
    }

    public void transition(String name) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        Fragment fragment = new TreamentsSelectedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.TREAMENT_NAME, name);
        fragment.setArguments(bundle);

        trans.replace(R.id.root_treatments, fragment, "treaments_list");
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(null);

        trans.commit();
    }

    public int getColor(View view, String name) {

        name = name.toLowerCase(Locale.getDefault());
        name = name.replace(" ", "_");
        name = name.replace("á", "a");
        name = name.replace("é", "e");
        name = name.replace("í", "i");
        name = name.replace("ó", "o");
        name = name.replace("ú", "u");

        int colorId = getResources().getIdentifier(name, "color",
                view.getContext().getPackageName());

        return colorId;
    }

    public String getTitle() {
        return title;
    }
}
