package com.spot.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.spot.app.R;
import com.spot.app.activity.ApprovalDetailsActivity;
import com.spot.app.fragment.dummy.DummyContent;

/**
 * 审批
 */
public class ApprovalFragment extends Fragment implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, ApprovalItemFragment.OnListFragmentInteractionListener {


    @ViewInject(R.id.approval_viewpager)
    private ViewPager mViewPager;

    @ViewInject(R.id.approval_rg)
    private RadioGroup radioGroup;

    @ViewInject(R.id.approval_true_rb)
    private RadioButton true_rb;

    @ViewInject(R.id.approval_no_rb)
    private RadioButton no_rb;

    private Fragment[] mFragments = new Fragment[2];

    public ApprovalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approval, container, false);
        ViewUtils.inject(this,view);

        loadData();
        initView();
        return view;
    }

    private void initView() {

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getChildFragmentManager())
        {
            @Override
            public int getCount()
            {
                return 2;
            }

            @Override
            public Fragment getItem(int position)
            {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

        radioGroup.setOnCheckedChangeListener(this);
    }

    private void loadData() {

        mFragments[0] = ApprovalItemFragment.newInstance();
        mFragments[1] = ApprovalItemFragment.newInstance();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

        startActivity(new Intent(getContext(), ApprovalDetailsActivity.class));
    }
}
