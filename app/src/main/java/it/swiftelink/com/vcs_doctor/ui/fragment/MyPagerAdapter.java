package it.swiftelink.com.vcs_doctor.ui.fragment;

import android.content.Context;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragmentList;
    private Integer[] list_Title;

    public MyPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList, Integer[] list_Title) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.list_Title = list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.length;
    }

    /**
     * //此方法用来显示tab上的名字
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(list_Title[position]);
    }
}
