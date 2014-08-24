package uk.me.lewisdeane.materialnotes.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.me.lewisdeane.lnavigationdrawer.NavigationItem;
import uk.me.lewisdeane.lnavigationdrawer.NavigationListView;
import uk.me.lewisdeane.materialnotes.R;
import uk.me.lewisdeane.materialnotes.activities.MainActivity;
import uk.me.lewisdeane.materialnotes.adapters.DrawerAdapter;
import uk.me.lewisdeane.materialnotes.objects.DrawerItem;

/**
 * Created by Lewis on 19/08/2014.
 */
public class NavigationDrawerFragment extends Fragment {

    private View mRootView;
    private NavigationListView mListView;
    private NavigationDrawerCallbacks mCallbacks;
    public static ActionBarDrawerToggle mDrawerToggle;
    public static DrawerLayout mDrawerLayout;
    public static ArrayList<DrawerItem> mDrawerItems = new ArrayList<DrawerItem>();
    public static DrawerAdapter mDrawerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        init();
        setListeners();

        return mRootView;
    }

    private void init() {
        mDrawerItems.clear();
        mDrawerItems.add(new DrawerItem(R.drawable.ic_mask_folder, getString(R.string.navigation_item_1), getIsSelected(MainActivity.NoteMode.EVERYTHING)));
        mDrawerItems.add(new DrawerItem(R.drawable.ic_mask_upcoming, getString(R.string.navigation_item_2), getIsSelected(MainActivity.NoteMode.UPCOMING)));
        mDrawerItems.add(new DrawerItem(R.drawable.ic_mask_delete, getString(R.string.navigation_item_3), getIsSelected(MainActivity.NoteMode.ARCHIVE)));
        mDrawerItems.add(new DrawerItem(R.drawable.ic_action_settings_grey, getString(R.string.navigation_item_4), false));
        mDrawerItems.add(new DrawerItem(R.drawable.ic_action_info_outline_grey, getString(R.string.navigation_item_5), false));

        mListView = (NavigationListView) mRootView.findViewById(R.id.fragment_navigation_drawer_list);

        mListView.addNavigationItem(new NavigationItem("Everything", R.drawable.ic_mask_folder, true));
        mListView.addNavigationItem(new NavigationItem("Upcoming", R.drawable.ic_mask_upcoming));
        mListView.addNavigationItem(new NavigationItem("Archived", R.drawable.ic_mask_delete));
        mListView.addNavigationItem(new NavigationItem("Settings", R.drawable.ic_action_settings_grey));
        mListView.addNavigationItem(new NavigationItem("Information", R.drawable.ic_action_info_outline_grey));

        mListView.setSelectedColor("#FF9900");

        mListView.setNavigationItemClickListener(new NavigationListView.NavigationItemClickListener() {
            @Override
            public void onNavigationItemSelected(String s, ArrayList<NavigationItem> navigationItems, int i) {
            }
        });

        /*
        mDrawerAdapter = new DrawerAdapter(getActivity(), R.layout.item_drawer, mDrawerItems);
        mListView.setAdapter(mDrawerAdapter);
        */
    }

    private void setListeners(){
        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallbacks.onNavigationItemSelected(MainActivity.getNoteMode(i), mDrawerItems, i);
            }
        });*/
    }

    public void setUp(int _res, DrawerLayout _drawerLayout){

        mDrawerLayout = _drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.drawable.background_circle, R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //mCallbacks.onDrawerClosed();
                MainActivity.mFABFragment.mRootView.setClickable(true);
                MainActivity.mActionBarFragment.mSearch.setClickable(true);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //mCallbacks.onDrawerOpened();
                MainActivity.mFABFragment.mRootView.setClickable(false);
                MainActivity.mActionBarFragment.mSearch.setClickable(false);
            }

            @Override
            public void onDrawerSlide(View drawerView, float offSet){
                MainActivity.mFABFragment.mRootView.setAlpha(1-offSet);
                MainActivity.mActionBarFragment.mSearch.setAlpha(1-offSet);
            }
        };

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private boolean getIsSelected(MainActivity.NoteMode _toCompare){
        return MainActivity.NOTE_MODE.equals(_toCompare);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            //throw new ClassCastException(getActivity().getClass() + " must implement NavigationDrawerCallbacks.");
        }
    }

    public interface NavigationDrawerCallbacks{
        public void onNavigationItemSelected(MainActivity.NoteMode item, ArrayList<DrawerItem> items, int position);
        public void onDrawerOpened();
        public void onDrawerClosed();
    }
}
