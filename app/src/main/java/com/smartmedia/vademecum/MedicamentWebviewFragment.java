package com.smartmedia.vademecum;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.smartmedia.vademecum.listener.OnItemSelectedListener;

public class MedicamentWebviewFragment extends Fragment implements OnItemSelectedListener {

    private String medicament;
    private WebView web;
    int position;
    Menu menu;

    public MedicamentWebviewFragment() {
        //Bundle bundle = getArguments();

        //medicament = bundle.getString(MainActivity.MEDICAMENT_NAME);
        //position = bundle.getInt(MainActivity.TAB_POSITION, -1);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle= getArguments();

        medicament = bundle.getString(MainActivity.MEDICAMENT_NAME);
        position = bundle.getInt(MainActivity.TAB_POSITION, -1);

        View view = inflater.inflate(R.layout.fragment_medicament_webview,
                container, false);

        ActionBar bar = getActivity().getActionBar();
        if (bar != null)
            bar.setTitle(medicament);

        setHasOptionsMenu(true);

        int index = findResource(view);

        int resource = view.getResources().getIdentifier("html_text" + index,
                "string", view.getContext().getPackageName());

        String style = "<style>h1{color: " + getColor(view, index)
                + ";}</style>";
        String data = style + getString(resource);

        web = (WebView) view.findViewById(R.id.webview_info);
        web.getSettings().setJavaScriptEnabled(true);

        web.loadDataWithBaseURL("file:///android_asset/", data, "text/html",
                "UTF-8", null);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int size = menu.size();
        if (size == 0) {
            inflater.inflate(R.menu.main, menu);
            this.menu = menu;
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void CreateIndex(MenuItem item, int tabPos) {
        if (tabPos == position) {
            IndexDialogFragment index = new IndexDialogFragment();
            index.setOnItemSelectedListener(this);
            index.show(getFragmentManager(), null);
        }
    }

    @Override
    public void onItemSelected(String item) {
        web.loadUrl("javascript:findH1('" + item + "');");
    }

    public int findResource(View view) {
        medicament = medicament.toLowerCase(Locale.getDefault());
        int id = 0;

        int index = 1;
        while (index != 0) {
            int source = view.getResources().getIdentifier(
                    "medicament_name" + index, "string",
                    view.getContext().getPackageName());

            if (source == 0) {
                index = 0;
                break;
            } else {
                String comparable = getString(source);
                comparable = comparable.toLowerCase(Locale.getDefault());

                if (medicament.equals(comparable)) {
                    id = index;
                    index = 0;
                    break;
                }
                index++;
            }
        }
        return id;
    }

    @SuppressLint("NewApi")
    public String getColor(View view, int id) {
        int nameId = getResources().getIdentifier("treament" + id, "string",
                view.getContext().getPackageName());

        if (nameId != 0) {
            String name = getString(nameId);

            name = name.toLowerCase(Locale.getDefault());
            name = name.replace(" ", "_");
            name = name.replace("á", "a");
            name = name.replace("é", "e");
            name = name.replace("í", "i");
            name = name.replace("ó", "o");
            name = name.replace("ú", "u");

            int colorId = getResources().getIdentifier(name, "color",
                    view.getContext().getPackageName());

            if (colorId != 0) {
                int color = getResources().getColor(colorId);
                //String myColor = "#" + toHex(color);

                return "#" + toHex(color);
            } else
                return null;
        } else {
            return null;
        }
    }

    @SuppressLint("NewApi")
    void hola() {
        // getActivity().getActionBar().getTabAt(0).setIcon(icon)
    }

    public String toHex(int value) {
        String color = Integer.toHexString(value);
        color = color.substring(2, color.length());
        return color;
    }

    public String getTitle() {
        return medicament;
    }
}
