package com.spot.app.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.spot.app.R;
import com.spot.app.activity.HomeActivity;
import com.spot.app.customview.SelectPicPopupWindow;
import com.spot.app.utils.FetchImageUtilsCrop;
import com.spot.app.utils.ToastUtil;

import java.io.File;

/**
 * 取证.
 */
public class ProofFragment extends Fragment implements FetchImageUtilsCrop.OnPickFinishedCallback {

    @ViewInject(R.id.proof_iv_photo)
    private ImageView ivPhoto;


    private FetchImageUtilsCrop imageUtils;

    public ProofFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proof, container, false);
        ViewUtils.inject(this, view);

        HomeActivity homeActivity = (HomeActivity) getActivity();
        imageUtils = homeActivity.getImageUtils();

        return view;
    }

    @OnClick({R.id.proof_check_photo, R.id.proof_btn_save})
    private void viewOnClick(View v)
    {
        switch (v.getId())
        {
            case R.id.proof_check_photo:
                //上传图片
                showWindow = new SelectPicPopupWindow(getActivity(),
                        new SelectPicPopupWindow.OnClickListenerWithPosition() {
                            @Override
                            public void onClick(View v, int actionId) {
                                showWindow.dismiss();
                                switch (v.getId()) {
                                    case R.id.btn_take_photo:
                                        imageUtils.doTakePhoto(ProofFragment.this);
                                        break;
                                    case R.id.btn_pick_photo:
                                        imageUtils.doPickPhotoFromGallery(ProofFragment.this);

                                        break;
                                }
                            }
                        });
                showWindow.showAtLocation(getActivity().findViewById(R.id.layout_main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.proof_btn_save:
                //保存
                ToastUtil.showShort(getContext(),"保存");
                break;
        }

    }

    private SelectPicPopupWindow showWindow;

    @Override
    public void onPickSuccessed(Bitmap bm, String path) {
        if (path != null && path.startsWith("file://"))
            path = path.substring(7);

        File file = new File(path);
        if (!file.exists())
        {
            ToastUtil.showShort(getContext(),"路径错误，请重新选取！");
            return;
        }

        ivPhoto.setImageBitmap(bm);
    }

    @Override
    public void onPickFailed() {

    }
}
