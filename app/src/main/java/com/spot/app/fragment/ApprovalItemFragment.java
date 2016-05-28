package com.spot.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bis.android.plug.refresh_recycler.layoutmanager.ABaseLinearLayoutManager;
import com.bis.android.plug.refresh_recycler.listener.OnRecyclerViewScrollLocationListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.spot.app.R;
import com.spot.app.fragment.dummy.DummyContent;
import com.spot.app.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ApprovalItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(R.id.approval_list)
    private RecyclerView approvalRv;

    @ViewInject(R.id.approval_sr)
    private SwipeRefreshLayout sr;

    private View footerView;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ApprovalItemFragment() {
    }

    @SuppressWarnings("unused")
    public static ApprovalItemFragment newInstance() {
//        ApprovalItemFragment fragment = new ApprovalItemFragment();
        return new ApprovalItemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ViewUtils.inject(this, view);

        initView();

        return view;
    }

    private void initView() {

        footerView = LayoutInflater.from(getContext())
                .inflate(R.layout.recycleview_footer, null, false);

        approvalRv.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener, footerView));

        approvalRv.setLayoutManager(getLayoutManager());

        sr.setOnRefreshListener(this);
//        sr.setColorSchemeResources(R.color.refresh_purple,
//                R.color.refresh_yellow, R.color.refresh_orange,
//                R.color.refresh_green);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Fragment fragment = getParentFragment();

        if (fragment instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) fragment;
        } else {
            throw new RuntimeException(fragment.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {

    }

    private boolean isTop;

    public RecyclerView.LayoutManager getLayoutManager()
    {
        ABaseLinearLayoutManager layoutManager = new ABaseLinearLayoutManager(getContext());
        layoutManager.setOnRecyclerViewScrollLocationListener(approvalRv,
                new OnRecyclerViewScrollLocationListener()
                {
                    @Override
                    public void onTopWhenScrollIdle(RecyclerView recyclerView)
                    {
                        isTop = true;
                    }

                    @Override
                    public void onBottomWhenScrollIdle(RecyclerView recyclerView)
                    {
                        if(!isTop) {
                            footerView.setVisibility(View.VISIBLE);
                            //loadData();
                        }

                        isTop = false;
                    }
                });



        return layoutManager;
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
