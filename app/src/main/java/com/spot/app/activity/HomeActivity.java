package com.spot.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.spot.app.R;
import com.spot.app.fragment.AccountFragment;
import com.spot.app.fragment.ApprovalFragment;
import com.spot.app.fragment.ProofFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabHost.OnTabChangeListener {

    public static final String ACCOUNT_TAG = "account";

    public static final String PROOF_TAG = "proof";

    public static final String APPROVAL_TAG = "approval";

    public static FragmentTabHost mTabHost;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.approval);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView();
    }

    private void initView() {

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(
                mTabHost.newTabSpec(APPROVAL_TAG).setIndicator(
                        getTabItem(R.drawable.approval_tab_selector,
                                getString(R.string.approval))),
                ApprovalFragment.class, null);

        mTabHost.addTab(
                mTabHost.newTabSpec(PROOF_TAG).setIndicator(
                        getTabItem(R.drawable.proof_tab_selector,
                                getString(R.string.proof))),
                ProofFragment.class, null);

        mTabHost.addTab(
                mTabHost.newTabSpec(ACCOUNT_TAG).setIndicator(
                        getTabItem(R.drawable.account_tab_selector,
                                getString(R.string.account))),
                AccountFragment.class, null);

        mTabHost.setOnTabChangedListener(this);
    }

    private View getTabItem(int imgID, String textID) {
        LinearLayout tabView = (LinearLayout) LayoutInflater.from(this)
                .inflate(R.layout.tab_item, null);
        TextView tabTxt = (TextView) tabView.findViewById(R.id.tab_text);
        ImageView tabImg = (ImageView) tabView.findViewById(R.id.tab_img);
        tabImg.setBackgroundResource(imgID);
        tabTxt.setText(textID);
        return tabView;
    }

    @Override
    public void onTabChanged(String tabId) {

        int titleId = R.string.approval;

        switch (tabId){
            case ACCOUNT_TAG:
                titleId = R.string.account;
                break;
            case PROOF_TAG:
                titleId = R.string.proof;
                break;
            case APPROVAL_TAG:
                titleId = R.string.approval;
                break;
        }

        toolbar.setTitle(titleId);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
