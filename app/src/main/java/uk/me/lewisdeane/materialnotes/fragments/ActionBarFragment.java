package uk.me.lewisdeane.materialnotes.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import uk.me.lewisdeane.materialnotes.R;
import uk.me.lewisdeane.materialnotes.activities.MainActivity;
import uk.me.lewisdeane.materialnotes.customviews.CustomTextView;
import uk.me.lewisdeane.materialnotes.utils.Animations;
import uk.me.lewisdeane.materialnotes.utils.DatabaseHelper;

/**
 * Created by Lewis on 05/08/2014.
 */
public class ActionBarFragment extends Fragment {

    private View mRootView;
    public static LinearLayout mContainer, mActionBar1;
    public static ImageButton mMenu, mSearch;
    public static CustomTextView mHeader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_action_bar, container, false);
        init();
        setListeners();
        return mRootView;
    }

    private void init(){
        mContainer = (LinearLayout) mRootView.findViewById(R.id.fragment_action_bar_container);
        mActionBar1 = (LinearLayout) mRootView.findViewById(R.id.fragment_action_bar_1);

        mMenu = (ImageButton) mRootView.findViewById(R.id.fragment_action_bar_1_toggle);
        mSearch = (ImageButton) mRootView.findViewById(R.id.fragment_action_bar_1_search);

        mHeader = (CustomTextView) mRootView.findViewById(R.id.fragment_action_bar_1_subheader);
    }

    private void setListeners(){
        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack(false);
            }
        });
    }

    public void goBack(boolean _backKey){
        MainActivity.PATH = getNewPath(_backKey);
        MainActivity.loadNotes();
        setUp(null);
    }

    public String getNewPath(boolean _backKey){
        if(MainActivity.isInAdd == 0 && MainActivity.PATH.length() > 1){
            return DatabaseHelper.getPrevPath(MainActivity.PATH);
        } else if(MainActivity.isInAdd == 0 && MainActivity.PATH.equals("/")){
            if(_backKey)
                getActivity().finish();
            else
                MainActivity.mNavigationDrawerFragment.mDrawerLayout.openDrawer(Gravity.LEFT);
            return "";
        } else{
            Animations.setAddAnimation(true, MainActivity.mFABFragment.mRootView);
            Animations.setListAnimation(true, MainActivity.mMainFragment.mList);

            MainActivity.isInAdd = 0;

            return MainActivity.PATH;
        }
    }

    public void setUp(String _text){
        String[] split = MainActivity.PATH.split("/");
        mHeader.setText(MainActivity.PATH.equals("/") ? getString(R.string.app_name) : split[split.length-1]);

        if(MainActivity.PATH.equals("/") && MainActivity.isInAdd == 0)
            mMenu.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_menu_white));
        else {
            mMenu.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_arrow_back_white));
        }

        if(_text != null)
            mHeader.setText(_text);
    }

    public void onDrawerOpened(){
        showSearchIcon(false);
    }

    public void onDrawerClosed(){
        showSearchIcon(true);
    }

    public void showSearchIcon(boolean _shouldShow){
        MainActivity.mActionBarFragment.mSearch.setClickable(_shouldShow);
    }
}
