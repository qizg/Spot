package com.spot.app.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.spot.app.R;


/**
 * Created by Qi on 2016/2/23.
 */
public class SelectPicPopupWindow extends PopupWindow
{

    public interface OnClickListenerWithPosition {
        void onClick(View v, int actionId);
    }

    private OnClickListenerWithPosition listener;
    private int DEFAULT_ACTION_ID = 0;
    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private View mMenuView;
    private int actionId = DEFAULT_ACTION_ID;

    public SelectPicPopupWindow(Activity context, OnClickListenerWithPosition itemsOnClick)
    {
        super(context);
        showView(context, itemsOnClick, DEFAULT_ACTION_ID);
    }

    public SelectPicPopupWindow(Activity context,
                                OnClickListenerWithPosition itemsOnClick, int actionId)
    {
        super(context);
        showView(context, itemsOnClick, actionId);
    }

    public SelectPicPopupWindow(Activity context, OnClickListenerWithPosition itemsOnClick,
                                String FirBtnName, String SecBtnName)
    {
        super(context);
        showView(context, itemsOnClick, DEFAULT_ACTION_ID);
        btn_take_photo.setText(FirBtnName);
        btn_pick_photo.setText(SecBtnName);
    }

    public SelectPicPopupWindow(Activity context, OnClickListenerWithPosition itemsOnClick,
                                int actionId, String FirBtnName, String SecBtnName)
    {
        super(context);
        showView(context, itemsOnClick, actionId);
        btn_take_photo.setText(FirBtnName);
        btn_pick_photo.setText(SecBtnName);
    }

    private void showView(Activity context, final OnClickListenerWithPosition itemsOnClick,
                          final int actionId)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.select_pic_poputwindow, null);
        btn_take_photo = (Button) mMenuView.findViewById(R.id.btn_take_photo);
        btn_pick_photo = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                //销毁弹出框
                dismiss();
            }
        });
        //设置按钮监听
        btn_pick_photo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                itemsOnClick.onClick(v, actionId);
            }
        });
        btn_take_photo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                itemsOnClick.onClick(v, actionId);
            }
        });
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener()
        {

            public boolean onTouch(View v, MotionEvent event)
            {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (y < height)
                    {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
