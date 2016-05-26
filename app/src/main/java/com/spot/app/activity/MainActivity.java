package com.spot.app.activity;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.spot.app.R;
import com.spot.app.fragment.AccountFragment;
import com.spot.app.fragment.ApprovalFragment;
import com.spot.app.fragment.ProofFragment;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {

    public static final String ACCOUNT_TAG = "account";

    public static final String PROOF_TAG = "proof";

    public static final String APPROVAL_TAG = "approval";

    public static FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }
}
