package com.smartmedia.vademecum;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RootFragment extends Fragment {


    private int position;

    public RootFragment() {
        //Bundle bundle= getArguments();
        //position= bundle.getInt(MainActivity.POS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        savedInstanceState = savedInstanceState == null ? getArguments() : savedInstanceState;
        position= savedInstanceState.getInt(MainActivity.POS);

        View view = null;
        FragmentTransaction trans = getFragmentManager().beginTransaction();

        if (position == 0) {
            view = inflater
                    .inflate(R.layout.root_medicaments, container, false);
            trans.replace(R.id.root_medicaments, new MedicamentsFragment(),
                    "medicaments");
        } else {
            view = inflater.inflate(R.layout.root_treatments, container, false);
            trans.replace(R.id.root_treatments, new TreatmentsFragment(),
                    "treaments");
        }

        trans.commit();

        return view;
    }
}
