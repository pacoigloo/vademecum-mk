package com.smartmedia.vademecum;

import java.util.ArrayList;
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

public class MedicamentsFragment extends ListFragment {

    public MedicamentsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicaments, container,
                false);

        ActionBar bar = getActivity().getActionBar();
        if (bar != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
            bar.setTitle(getString(R.string.app_name));
        }

        String[] medicamentNames = fillList(view, "medicament_name");
        String[] medicamentTypes = fillList(view, "treament");

        final RowAdapter adapter = new RowAdapter(container.getContext(),
                R.layout.row_medicament, medicamentNames, medicamentTypes);

        setListAdapter(adapter);

        EditText search = (EditText) view
                .findViewById(R.id.medicament_searcher);
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

        Arrays.sort(medicamentNames);
        Arrays.sort(medicamentTypes);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //TODO
        //Toast.makeText(getActivity(), "Item "+(position+1), Toast.LENGTH_LONG).show();

        transition(l.getItemAtPosition(position).toString());

        ActionBar bar = getActivity().getActionBar();
        if (bar != null)
            bar.setDisplayHomeAsUpEnabled(true);

        super.onListItemClick(l, v, position, id);
    }

    public void transition(String name) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        Fragment fragment= new MedicamentWebviewFragment();
        Bundle bundle= new Bundle();
        bundle.putString(MainActivity.MEDICAMENT_NAME, name);
        bundle.putInt(MainActivity.TAB_POSITION, 0);
        fragment.setArguments(bundle);

        trans.replace(R.id.root_medicaments, fragment, "medicaments_info");
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(null);

        trans.commit();
    }

    public String[] fillList(View view, String resource) {
        ArrayList<String> list = new ArrayList<String>();

        int index = 1;
        while (index != 0) {
            int source = view.getResources().getIdentifier(resource + index,
                    "string", view.getContext().getPackageName());

            if (source == 0) {
                index = 0;
                break;
            } else {
                list.add(getString(source));
                index++;
            }
        }

        String[] listArray = new String[list.size()];
        listArray = list.toArray(listArray);

        return listArray;
    }

}
