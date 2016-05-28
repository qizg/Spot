package com.spot.app.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.spot.app.R;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Qi on 2016/1/21.
 */
public class ImageLoaderHelper
{


    public static void displayImage(String url, DisplayImageOptions options,
                                    ImageView imageView)
    {
        if (url == null)
            url = "";
        ImageLoader.getInstance().displayImage(url.trim(), imageView,
                options);

    }

    public static void displayImage(String url, DisplayImageOptions options,
                                    ImageView imageView, ProgressBar pb)
    {
        if (url == null)
            url = "";
        ImageLoader.getInstance().displayImage(url.trim(), imageView,
                options, new MyImageLoadingListener(pb));

    }

    public static class MyImageLoadingListener extends
            SimpleImageLoadingListener
    {
        private ProgressBar progressBar;

        public static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        public MyImageLoadingListener(ProgressBar progressBar)
        {
            this.progressBar = progressBar;
        }

        @Override
        public void onLoadingStarted(String imageUri, View view)
        {
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view,
                                    FailReason failReason)
        {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage)
        {
            progressBar.setVisibility(View.GONE);

            if (loadedImage != null)
            {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay)
                {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    public static DisplayImageOptions optionsForRounded = new DisplayImageOptions.Builder()
            // 设置图片下载期间显示的图片
             .showImageOnLoading(R.drawable.mine_call_pressed)
            // 设置图片Uri为空或是错误的时候显示的图片
            .showImageForEmptyUri(R.drawable.mine_call_pressed)
            // 设置图片加载或解码过程中发生错误显示的图片
             .showImageOnFail(R.drawable.mine_call_pressed)
            // 设置下载的图片是否缓存在内存中
            .cacheInMemory(true)
                    // 设置下载的图片是否缓存在SD卡中
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    // 默认是ARGB_8888， 使用RGB_565会比使用ARGB_8888少消耗2倍的内存
            .bitmapConfig(Bitmap.Config.RGB_565)
            .considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(120)) // 设置成圆角图片
            .build(); // 创建配置过得DisplayImageOption对象
    /**
     * displayer：
     * RoundedBitmapDisplayer（int roundPixels）设置圆角图片
     * FakeBitmapDisplayer（）这个类什么都没做
     * FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间
     * 　　　　　　　  SimpleBitmapDisplayer()正常显示一张图片
     */
    public static DisplayImageOptions optionsForCommon = new DisplayImageOptions.Builder()
            // 设置图片下载期间显示的图片
             .showImageOnLoading(R.drawable.card_imgbg)
            // 设置图片Uri为空或是错误的时候显示的图片
            .showImageForEmptyUri(R.drawable.card_imgbg)
                    // 设置图片加载或解码过程中发生错误显示的图片
            .showImageOnFail(R.drawable.card_imgbg)
            .resetViewBeforeLoading(true)
                    // 设置下载的图片是否缓存在内存中
            .cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    // 设置下载的图片是否缓存在SD卡中
            .cacheOnDisk(true)
                    // 默认是ARGB_8888， 使用RGB_565会比使用ARGB_8888少消耗2倍的内存
            .bitmapConfig(Bitmap.Config.RGB_565)
            .considerExifParams(true).displayer(new SimpleBitmapDisplayer())
            .build(); // 创建配置过得DisplayImageOption对象

    public static DisplayImageOptions optionsForRoundRect = new DisplayImageOptions.Builder()
            // 设置图片下载期间显示的图片
             .showImageOnLoading(R.drawable.card_imgbg)
            // 设置图片Uri为空或是错误的时候显示的图片
            .showImageForEmptyUri(R.drawable.card_imgbg)
            //设置图片加载或解码过程中发生错误显示的图片
            .showImageOnFail(R.drawable.card_imgbg)
            .resetViewBeforeLoading(true)
                    // 设置下载的图片是否缓存在内存中
            .cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
                    // 设置下载的图片是否缓存在SD卡中
            .cacheOnDisk(true)
                    // 默认是ARGB_8888， 使用RGB_565会比使用ARGB_8888少消耗2倍的内存
            .bitmapConfig(Bitmap.Config.RGB_565)
            .considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角矩形
//            .displayer(new SimpleBitmapDisplayer())
            .build(); // 创建配置过得DisplayImageOption对象

}
