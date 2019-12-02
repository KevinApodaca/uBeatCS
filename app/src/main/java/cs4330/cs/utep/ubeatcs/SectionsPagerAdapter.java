package cs4330.cs.utep.ubeatcs;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * A FragmentPagerAdapter that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 *
 * @author Isaias Leos
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private StudyClass globalStudyClass;

    SectionsPagerAdapter(Context context, FragmentManager fm, StudyClass currentClass) {
        super(fm);
        mContext = context;
        globalStudyClass = currentClass;
    }

    /**
     * Create a fragment that will return each tab will do, from the simple study material
     * fragment to the webview fragment within the tabbed view.
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("name", globalStudyClass.getClass_name());
        bundle.putString("number", globalStudyClass.getClass_number());
        bundle.putString("teacher", globalStudyClass.getClass_teacher());
        bundle.putString("url", globalStudyClass.getClass_url());
        bundle.putString("email", globalStudyClass.getClass_email());
        bundle.putStringArrayList("youtubeList", (ArrayList<String>) globalStudyClass.getYoutubePlaylist());
        bundle.putString("crn", globalStudyClass.getClass_crn());
        if (position == 0) {
            PlaceholderFragment placeholderFragment = PlaceholderFragment.newInstance(position);
            placeholderFragment.setArguments(bundle);
            return placeholderFragment;
        } else {
            new PlaceholderFragmentWebsite();
            PlaceholderFragmentWebsite placeholderFragmentWebsite = PlaceholderFragmentWebsite.newInstance();
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