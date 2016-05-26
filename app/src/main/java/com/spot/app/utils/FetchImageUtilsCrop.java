package com.spot.app.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FetchImageUtilsCrop
{

    /** The launch code when taking a picture */
    private static final int CAMERA_WITH_DATA = 3023;

    /** The launch code when picking a photo and the raw data is returned */
    private static final int PHOTO_PICKED_WITH_DATA = 3021;

    private static int DEFAULT_IMAGE_WIDTH_SIZE = 350;

    private static int DEFAULT_IMAGE_HEIGHT_SIZE = 350;

    private int photox = DEFAULT_IMAGE_WIDTH_SIZE;

    private int photoy = DEFAULT_IMAGE_HEIGHT_SIZE;

    private OnPickFinishedCallback callback;

    private File mCurrentPhotoFile;

    private Activity mActivity;

    String path = Environment.getExternalStorageDirectory() + File.separator
            + "SPOT/uploadImage";

    private Uri url;

    private String selectPicPath;

    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + File.separator
                    + "SPOT/uploadImage");

    private static final int PHOTO_FromGallery = 3022;

    public FetchImageUtilsCrop(Activity activity)
    {
        mActivity = activity;
    }



    public static interface OnPickFinishedCallback
    {
        public void onPickSuccessed(Bitmap bm, String filePath);

        public void onPickFailed();
    }

    public static String getRealFilePath(final Context context, final Uri uri)
    {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme))
        {
            data = uri.getPath();
        }
        else if (ContentResolver.SCHEME_CONTENT.equals(scheme))
        {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[] { ImageColumns.DATA }, null, null, null);
            if (null != cursor)
            {
                if (cursor.moveToFirst())
                {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1)
                    {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
        case PHOTO_PICKED_WITH_DATA:
            String path = getRealFilePath(mActivity, url);
            Bitmap bm = getSmallBitmap(path);
            if (bm != null)
            {
                callback.onPickSuccessed(bm, path);
                return;
            }
            else
            {
                if (data != null && data.getExtras() != null)
                {
                    Bitmap img = (Bitmap) data.getExtras().get("data");

                    if (img != null)
                    {
                        callback.onPickSuccessed(img, path);
                        return;
                    }
                }
                else
                {
                    callback.onPickFailed();
                    return;
                }
            }
            break;
        case CAMERA_WITH_DATA:
            doCropPhoto();
            break;
        case PHOTO_FromGallery:
            if (mCurrentPhotoFile == null)
            {
                return;
            }
            Bitmap bmm = getSmallBitmap(mCurrentPhotoFile
                    .getAbsolutePath());
            if (bmm != null)
            {
                callback.onPickSuccessed(bmm,
                        mCurrentPhotoFile.getAbsolutePath());
                return;
            }
            else
            {
                if (data != null && data.getExtras() != null)
                {
                    Bitmap img = (Bitmap) data.getExtras().get("data");

                    if (img != null)
                    {
                        callback.onPickSuccessed(img,
                                mCurrentPhotoFile.getAbsolutePath());
                        return;
                    }
                }
                else
                {
                    callback.onPickFailed();
                    return;
                }
            }

            break;
        }
    }

    /**
     * Sends a newly acquired photo to Gallery for cropping
     */
    private void doCropPhoto()
    {
        try
        {
            // Launch gallery to crop the photo
            final Intent intent = getCropImageIntent(url);
            mActivity.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Constructs an intent for image cropping.
     */
    private Intent getCropImageIntent(Uri photoUri)
    {
        url = createImagePathUri(mActivity);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", photox);
        intent.putExtra("aspectY", photoy);
        intent.putExtra("outputX", photox);
        intent.putExtra("outputY", photoy);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());
        if (mCurrentPhotoFile.exists())
        {
            mCurrentPhotoFile.delete();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, url);
        return intent;
    }

    public void doTakePhoto(OnPickFinishedCallback callback)
    {
        this.callback = callback;
        doTakePhoto(DEFAULT_IMAGE_WIDTH_SIZE, DEFAULT_IMAGE_HEIGHT_SIZE);
    }

    public void doPickPhotoFromGallery(OnPickFinishedCallback callback)
    {
        this.callback = callback;
        doPickPhotoFromGallery(DEFAULT_IMAGE_WIDTH_SIZE,
                DEFAULT_IMAGE_HEIGHT_SIZE);

    }

    /**
     * Launches Camera to take a picture and store it in a file.
     */
    public void doTakePhoto(int width, int height)
    {
        this.photox = width;
        this.photoy = height;
        try
        {
            // Launch camera to take photo for selected contact
            final Intent intent = getTakePickIntent();
            mActivity.startActivityForResult(intent, CAMERA_WITH_DATA);

        }
        catch(ActivityNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Launches Gallery to pick a photo.
     */
    public void doPickPhotoFromGallery(int width, int height)
    {
        this.photox = width;
        this.photoy = height;
        try
        {
            // Launch picker to choose photo for selected contact
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());
            if (mCurrentPhotoFile.exists())
            {
                mCurrentPhotoFile.delete();
            }
            File f = new File(path + "/");
            if (!f.exists())
            {
                f.mkdirs();
            }
            try
            {
                mCurrentPhotoFile.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            final Intent intent = getPhotoPickIntent();
            mActivity.startActivityForResult(intent, PHOTO_FromGallery);
        }
        catch(ActivityNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Constructs an intent for picking a photo from Gallery, cropping it and
     * returning the bitmap.
     */
    private final Intent getPhotoPickIntent()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", photox);
        intent.putExtra("aspectY", photoy);
        intent.putExtra("outputX", photox);
        intent.putExtra("outputY", photoy);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(mCurrentPhotoFile));
        return intent;
    }

    private Intent getTakePickIntent()
    {
        url = createImagePathUri(mActivity);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, url);
        return intent;
    }

    /**
     * Create a file name for the icon photo using current time.
     */
    private String getPhotoFileName()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss", Locale.getDefault());
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * Constructs an intent for capturing a photo and storing it in a temporary
     * file.
     */
    private static Uri createImagePathUri(Context context)
    {
        Uri imageFilePath = null;
        String status = Environment.getExternalStorageState();
        SimpleDateFormat timeFormatter = new SimpleDateFormat(
                "yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));
        // ContentValues是我们希望这条记录被创建时包含的数据信息
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        values.put(MediaStore.Images.Media.DATE_TAKEN, time);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        if (status.equals(Environment.MEDIA_MOUNTED))
        {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        else
        {
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        return imageFilePath;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
