package it.swiftelink.com.vcs_doctor.ui.fragment;


import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> mList = new ArrayList<Fragment>();

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;

    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);

    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

}
