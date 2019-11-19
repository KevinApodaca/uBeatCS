package cs4330.cs.utep.ubeatcs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    ClassInfo globalClassInfo;

    public SectionsPagerAdapter(Context context, FragmentManager fm, ClassInfo currentClass) {
        super(fm);
        mContext = context;
        globalClassInfo = currentClass;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("name", globalClassInfo.getClass_name());
        bundle.putString("number", globalClassInfo.getClass_number());
        bundle.putString("teacher", globalClassInfo.getClass_teacher());
        bundle.putString("url", globalClassInfo.getClass_url());
        if (position == 0) {
            PlaceholderFragment placeholderFragment = PlaceholderFragment.newInstance(position);
            placeholderFragment.setArguments(bundle);
            return placeholderFragment;
        } else {
            PlaceholderFragmentWebsite placeholderFragmentWebsite = new PlaceholderFragmentWebsite().newInstance();
            placeholderFragmentWebsite.setArguments(bundle);
            return placeholderFragmentWebsite;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}