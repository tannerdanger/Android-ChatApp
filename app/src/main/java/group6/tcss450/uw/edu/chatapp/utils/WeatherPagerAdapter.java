package group6.tcss450.uw.edu.chatapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


import group6.tcss450.uw.edu.chatapp.weather.WeatherFragment;

/**
 * @author Tanner Brown
 * @version 11/3/2018
 */
public class WeatherPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    int numberOfTabs;

    public WeatherPagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.numberOfTabs = tabCount;
    }

    //    @Override
//    public Fragment getItem(int thePosition) {
//        switch (thePosition){
//            case 0:
//                WeatherFragment tab1 = new WeatherFragment();
//                return tab1;
//            case 1:
//                ForecastFragment tab2 = new ForecastFragment();
//                return tab2;
//            default:
//                return null;
//        }
//    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}