package com.smartmedia.vademecum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TreamentsSelectedFragment extends ListFragment {

    private String[] myList;
    private String treament;

    public TreamentsSelectedFragment() {
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        savedInstanceState = savedInstanceState == null ? getArguments() : savedInstanceState;
        treament = savedInstanceState.getString(MainActivity.TREAMENT_NAME);

        View view = inflater.inflate(R.layout.fragment_treatments_selected,
                container, false);

        ActionBar bar = getActivity().getActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(treament);

        myList = fillList(view, treament);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, myList);

        setListAdapter(adapter);

        Arrays.sort(myList);

        return view;
    }

    public String[] fillList(View view, String treament) {
        ArrayList<String> list = new ArrayList<String>();

        //TODO en vademecum mk no es necesario cambiar dermatologicos por dermatologia
        /*
		if (treament.equals("Dermatológicos"))
			treament = "Dermatogía";
		*/
        treament.toLowerCase(Locale.getDefault());

        int index = 1;
        while (index != 0) {
            int source = view.getResources().getIdentifier("treament" + index,
                    "string", view.getContext().getPackageName());

            if (source == 0) {
                index = 0;
                break;
            } else {
                String comparable = getString(source);
                comparable.toLowerCase(Locale.getDefault());

                if (treament.equals(comparable)) {
                    int nameId = view.getResources().getIdentifier(
                            "medicament_name" + index, "string",
                            view.getContext().getPackageName());
                    list.add(getString(nameId));
                }

                index++;
            }
        }

        String[] listArray = new String[list.size()];
        listArray = list.toArray(listArray);

        return listArray;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        transition(l.getItemAtPosition(position).toString());
    }

    public void transition(String name) {
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        Fragment fragment = new MedicamentWebviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.MEDICAMENT_NAME, name);
        bundle.putInt(MainActivity.TAB_POSITION, 1);
        fragment.setArguments(bundle);

        trans.replace(R.id.root_treatments, fragment, "treatments_info");
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(null);

        trans.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(MainActivity.MEDICAMENT_NAME, treament);
    }

    public String getTitle() {
        return treament;
    }
}
