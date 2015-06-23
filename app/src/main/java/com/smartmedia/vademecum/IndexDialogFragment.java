package com.smartmedia.vademecum;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.webkit.WebView;

import com.smartmedia.vademecum.listener.OnItemSelectedListener;

public class IndexDialogFragment extends DialogFragment {

    OnItemSelectedListener listener;

    public IndexDialogFragment(){
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener){
        this.listener= listener;
    }

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(R.string.index_tittle);

		builder.setItems(R.array.index_tittles,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String list[] = getResources().getStringArray(
								R.array.index_tittles);
						//web.loadUrl("javascript:findH1('" + list[which] + "');");
                        //TODO
                        listener.onItemSelected(list[which]);
					}
				});


		builder.setNegativeButton(R.string.index_button,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		return builder.create();
	}
}
