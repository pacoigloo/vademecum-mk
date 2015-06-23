package com.smartmedia.vademecum.adapter;

import com.smartmedia.vademecum.MainActivity;
import com.smartmedia.vademecum.RootFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public FragmentManager fmManager;
    //public Fragment fragmentAtPos[] = new Fragment[2];

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
        fmManager = fm;
    }

    @Override
    public Fragment getItem(int index) {
        Fragment fragment= new RootFragment();
        Bundle bundle= new Bundle();
        bundle.putInt(MainActivity.POS, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return MainActivity.NUM_ITEMS;
    }
}
