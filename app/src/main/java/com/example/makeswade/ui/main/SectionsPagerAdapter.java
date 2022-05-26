package com.example.makeswade.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.makeswade.CreateCharacterAttributeFragment;
import com.example.makeswade.CreateCharacterInfoFragment;
import com.example.makeswade.CreateCharacterOtherFragment;
import com.example.makeswade.CreateCharacterSkillFragment;
import com.example.makeswade.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment = new CreateCharacterInfoFragment();
                break;
            case 1:
                fragment = new CreateCharacterAttributeFragment();
                break;
            case 2:
                fragment = new CreateCharacterSkillFragment();
                break;
            case 3:
                fragment = new CreateCharacterOtherFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }
}