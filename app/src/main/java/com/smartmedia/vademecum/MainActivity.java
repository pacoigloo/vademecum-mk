package com.smartmedia.vademecum;

import com.smartmedia.vademecum.adapter.TabsPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	public static final int NUM_ITEMS = 2;
    public static final String TAB_POSITION= "tab position";
    public static final String MEDICAMENT_NAME= "medicamnet name";
    public static final String TREAMENT_NAME= "treament name";
    public static final String POS= "position";

	private ViewPager vPager;
	private TabsPagerAdapter tpAdapter;
	private ActionBar actionBar;
	private MenuItem menuItem;
	private static int actionBarPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

    //Alerta de certificación y validación.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Certifico que:\nSoy médico u odontólogo profesional.")
                .setTitle("Vademecum MK")
                .setCancelable(false)
                .setNeutralButton("Acepto",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.show();
        TextView messageText = (TextView)alert.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        alert.show();

        tpAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		vPager = (ViewPager) findViewById(R.id.content_view);
		vPager.setAdapter(tpAdapter);

		final String[] tabs = new String[NUM_ITEMS];
		tabs[0] = this.getResources().getString(R.string.medicaments);
		tabs[1] = this.getResources().getString(R.string.treatments);

		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tabName : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tabName)
					.setTabListener(this));
		}

		vPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// change the tab with a swipe
				actionBar.setSelectedNavigationItem(position);
				SetTab(position);
			}
		});
	}

	// ----------------------------------------------------------

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		actionBarPosition = tab.getPosition();
		vPager.setCurrentItem(actionBarPosition);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	// ----------------------------------------------------------

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() != R.id.action_index)
			onBackPressed();
		else {
			Fragment medInfo = getSupportFragmentManager().findFragmentByTag(
					"medicaments_info");
			if (medInfo != null)
				((MedicamentWebviewFragment) medInfo).CreateIndex(item,
						actionBarPosition);

			Fragment treatInfo = getSupportFragmentManager().findFragmentByTag(
					"treatments_info");
			if (treatInfo != null)
				((MedicamentWebviewFragment) treatInfo).CreateIndex(item,
						actionBarPosition);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menuItem = menu.findItem(R.id.action_index);
		return super.onPrepareOptionsMenu(menu);
	}

	void SetTab(int position) {

		FragmentManager manager = getSupportFragmentManager();
		Fragment frag;

		String title = getString(R.string.app_name);
		String tags[] = new String[3];

		if (position == 0) {
			tags[0] = "medicaments";
			tags[1] = "medicaments_info";
		} else {
			tags[0] = "treaments";
			tags[1] = "treaments_list";
			tags[2] = "treatments_info";
		}

		for (String myTag : tags) {
			frag = manager.findFragmentByTag(myTag);

			if (frag != null) {
				if (frag instanceof TreamentsSelectedFragment) {
					title = ((TreamentsSelectedFragment) frag).getTitle();
					actionBar.setDisplayHomeAsUpEnabled(true);

				} else if (frag instanceof MedicamentWebviewFragment) {
					title = ((MedicamentWebviewFragment) frag).getTitle();
					showIndexButton();
					actionBar.setDisplayHomeAsUpEnabled(true);

				} else {
					title = getString(R.string.app_name);
					actionBar.setDisplayHomeAsUpEnabled(false);
					hideIndexButton();
				}

			} else
				getString(R.string.app_name);
		}

		actionBar.setTitle(title);
	}

	public void hideIndexButton() {
		invalidateOptionsMenu();
		if (menuItem != null) {
			menuItem.setEnabled(false);
			menuItem.setVisible(false);
		}
	}

	public void showIndexButton() {
		openOptionsMenu();
		if (menuItem != null) {
			menuItem.setEnabled(true);
			menuItem.setVisible(true);
		}
	}
}
