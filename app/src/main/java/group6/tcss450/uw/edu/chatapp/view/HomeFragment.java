package group6.tcss450.uw.edu.chatapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;

import group6.tcss450.uw.edu.chatapp.R;
import group6.tcss450.uw.edu.chatapp.utils.Credentials;
import group6.tcss450.uw.edu.chatapp.utils.JsonHelper;
import group6.tcss450.uw.edu.chatapp.utils.WeatherPagerAdapter;
import group6.tcss450.uw.edu.chatapp.weather.Forecast;
import group6.tcss450.uw.edu.chatapp.weather.WeatherFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 * @Author Tanner Brown
 * @Version 5 dec 2018
 */
public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Credentials mCredentials;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private WeatherPagerAdapter mAdapter;
    private ArrayList<WeatherFragment> mWeatherFrags;
    private Forecast[] mForecast;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        System.out.print("BREAKPOINT");


        //get credentials and forecast
        if(getArguments() != null && (null == mCredentials || null == mForecast)){
            mCredentials = (Credentials)getArguments().getSerializable(getString(R.string.ARGS_CREDENTIALS));
            Forecast[] f = JsonHelper.parse_Forecast(getArguments().getString(getString(R.string.ARGS_FORECAST_DATA)));
            reloadmForecast(f);
        }
        if (null != mForecast) {
            int i = 0;
            for(WeatherFragment f: mWeatherFrags){
                f.setmForecast(mForecast[i]);
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        System.out.print("BREAKPOINT");

        if(getArguments() != null ){
            if(null == mCredentials)
                mCredentials = (Credentials)getArguments().getSerializable(getString(R.string.ARGS_CREDENTIALS));

            if(null == mForecast)
                mForecast = JsonHelper.parse_Forecast(getArguments().getString(getString(R.string.ARGS_FORECAST_DATA)));
        }

        if(null == mWeatherFrags && null != mCredentials && null != mForecast){
            buildWeatherFrags();
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        //get credentials and forecast
        if(getArguments() != null){
            mCredentials = (Credentials)getArguments().getSerializable(getString(R.string.ARGS_CREDENTIALS));
            Forecast[] f = JsonHelper.parse_Forecast(getArguments().getString(getString(R.string.ARGS_FORECAST_DATA)));
            reloadmForecast(f);
        }

        ViewPager viewPager = (ViewPager)view.findViewById(R.id.viewpager);

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.weather_tabs);

        WeatherPagerAdapter adapter = setupViewPager(viewPager, getChildFragmentManager());
        viewPager.setAdapter(adapter);



        tabLayout.setupWithViewPager(viewPager, true);

        for(int i = 0; i < tabLayout.getTabCount(); i++ ){
            tabLayout.getTabAt(i).setIcon(mForecast[i].getIconResID());
            mWeatherFrags.get(i).setmForecast(mForecast[i]);
        }

        return view;
    }

    private void reloadmForecast(Forecast[] forecasts){

        if(forecasts[0].getForecast().compareTo(mForecast[0].getForecast()) == 0
                && forecasts[0].getPercipChance() == mForecast[0].getPercipChance()){
            System.out.print("THEY ARE THE SAME, DON'T LOAD");
        }else{
            mForecast = forecasts;
            int i = 0;
            for (Forecast f : mForecast) {
                int icon = getResources().getIdentifier(f.getIconCode(), "drawable", getContext().getPackageName());
                f.setIcon( icon );
                if(null != mWeatherFrags){
                    mWeatherFrags.get(i).setmForecast(f);
                }
                i++;
            }
        }
    }


    private void buildWeatherFrags(){

        mWeatherFrags = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            WeatherFragment frag = new WeatherFragment();
            Bundle args = new Bundle();
            args.putSerializable(getString(R.string.ARGS_FORECAST_DATA) ,mForecast[i]);
            frag.setArguments(args);
            mWeatherFrags.add(i, frag);
        }

    }

    private WeatherPagerAdapter setupViewPager(ViewPager mViewPager, FragmentManager manager) {

        if(null == manager){ manager = getActivity().getSupportFragmentManager(); }

        WeatherPagerAdapter adapter = new WeatherPagerAdapter(manager, 10);
        Boolean recreate = mWeatherFrags.size() != 10;

        if(recreate)
            mWeatherFrags = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            WeatherFragment frag;
            if(recreate) {
                frag = new WeatherFragment();
                Bundle args = new Bundle();
                args.putSerializable(getString(R.string.ARGS_FORECAST_DATA), mForecast[i]);
                frag.setArguments(args);

            } else {
                frag = mWeatherFrags.get(i);

                if(i > 6){
                    String date = mForecast[i].getShortDate().toString().toUpperCase();
                    adapter.addFragment(frag, date);
                } else {

                    adapter.addFragment(frag, mForecast[i].getDay());
                }
            }
        }

        mViewPager.setAdapter(adapter);
        return adapter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //get credentials and forecast
        if(getArguments() != null && (null == mCredentials || null == mForecast)){
            mCredentials = (Credentials)getArguments().getSerializable(getString(R.string.ARGS_CREDENTIALS));
            mForecast = JsonHelper.parse_Forecast(getArguments().getString(getString(R.string.ARGS_FORECAST_DATA)));

            String pkg = context.getPackageName();

            //   mTabLayout.getTabAt(i).setIcon(res.getIdentifier(mForecast[i].iconCode, "drawable", getContext().getPackageName()));
            for (Forecast f : mForecast) {
                int icon = getResources().getIdentifier(f.getIconCode(), "drawable", pkg);

                f.setIcon( icon );
            }

        }

        System.out.print("BREAKPOINT");

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void updateContent(JSONObject jsonObject){

        mForecast = JsonHelper.parse_Forecast(jsonObject.toString());
        System.out.print("BREAKPOINT");
        updateWeather(mForecast);
       // buildWeatherFrags();

    }



    private void updateWeather(Forecast[] forecast){

        if(null != forecast && forecast.length > 9){
            mForecast = forecast;
        }
        int i = 0;
        if(0 == mForecast[0].getIconResID()){
            String pkg =  getContext().getPackageName();
            for (Forecast f : mForecast) {
                int icon = getResources().getIdentifier(f.getIconCode(), "drawable", pkg);

                f.setIcon( icon );
            }
        }
        for(WeatherFragment frag : mWeatherFrags){
            //frag.setArguments();
            frag.setmForecast(mForecast[i]);
            i++;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        System.out.print("BREAKPOINT");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
    }
}
