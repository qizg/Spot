package com.spot.app.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spot.app.R;
import com.spot.app.fragment.ApprovalItemFragment.OnListFragmentInteractionListener;
import com.spot.app.fragment.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    private View footerView;
    private static final int BOTTOM_TYPE = 1, ITEM_TYPE = 2;

    public MyItemRecyclerViewAdapter(List<DummyItem> items,
                                     OnListFragmentInteractionListener listener,
                                     View footerView) {
        mValues = items;
        mListener = listener;
        this.footerView = footerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == BOTTOM_TYPE)
        {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(lp);
            return new FooterViewHolder(footerView);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.mItem = mValues.get(position);
            itemViewHolder.mTitle.setText(mValues.get(position).content);
            itemViewHolder.mContentView.setText(mValues.get(position).details);
            itemViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onListFragmentInteraction(itemViewHolder.mItem);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mValues.size())
            return BOTTOM_TYPE;
        else
            return ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public final ImageView photo;
        public final View mView;
        public final TextView mTitle;
        public final TextView mContentView;
        public DummyItem mItem;

        public ItemViewHolder(View view) {
            super(view);
            mView = view;
            photo = (ImageView) view.findViewById(R.id.item_iv_photo);
            mTitle = (TextView) view.findViewById(R.id.item_iv_title);
            mContentView = (TextView) view.findViewById(R.id.item_iv_content);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder
    {
        public FooterViewHolder(View itemView)
        {
            super(itemView);
        }
    }
}
