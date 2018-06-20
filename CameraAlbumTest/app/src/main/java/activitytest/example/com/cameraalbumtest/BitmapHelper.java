package activitytest.example.com.cameraalbumtest;


import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Bitmap辅助类
 */
public class BitmapHelper {

    /**
     * 获取图片的旋转角度
     *
     * @param path 图片路径
     * @return 旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 保存图片旋转角度信息到文件
     *
     * @param path   图像文件路径
     * @param degree 图像旋转角度
     */
    public static void setBitmapDegree(String path, int degree) {
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);

            switch (degree) {
                case 90:
                    exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_90));
                    break;
                case 180:
                    exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_180));
                    break;
                case 270:
                    exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_270));
                    break;
            }
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转Bitmap对象
     *
     * @param bm     Bitmap对象
     * @param degree 旋转角度
     * @return 旋转后的Bitmap对象
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        if (degree == 0)
            return bm;

        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的Bitmap对象
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 将Bitmap进行质量压缩
     *
     * @param bitmap  需要压缩的Bitmap对象
     * @param quality 质量压缩因子
     * @return 质量压缩后的Bitmap对象
     */
    public static Bitmap bitmapCompress(Bitmap bitmap, int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);

        byte[] bytes = os.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    /**
     * 对bitmap进行质量压缩并存储到指定文件
     *
     * @param path      文件路径
     * @param bitmap    位图对象
     * @param quality   质量压缩因子
     * @param isRecycle 最终是否销毁位图
     */
    public static void bitmapCompressAndStorage(String path, Bitmap bitmap, int quality, boolean isRecycle) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isRecycle)
                bitmap.recycle();
        }
    }

    /**
     * 根据需求的宽高计算位图的像素压缩比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int sampleSize = 1;

        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;

        if (srcWidth > reqWidth || srcHeight > reqHeight) {
            int widthRatio = Math.round((float) srcWidth / (float) reqWidth);
            int heightRatio = Math.round((float) srcHeight / (float) (reqHeight));
            sampleSize = widthRatio > heightRatio ? widthRatio : heightRatio;
        }
        Log.d("==>",srcWidth+"//"+srcHeight+"//"+reqWidth+"//"+reqHeight+"//"+sampleSize);
        return sampleSize;
    }

    /**
     * 根据实际需求的宽高对指定图片进行像素压缩,减少内存开销
     *
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 第一次测量时，只读取图片的像素宽高，但不将位图写入内存
        BitmapFactory.decodeFile(filePath, options);
        // 计算像素压缩的比例
        options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        // 将压缩过像素后的位图读入内存
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据指定的比例对图片进行像素压缩,减少内存开销
     *
     * @param filePath
     * @param sampleSize
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String filePath, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 对图像进行压缩后重新储存
     * @param filePath
     */
    public static void bitmapCompressAndStorage(final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取原图的旋转角度信息
                int degree = BitmapHelper.getBitmapDegree(filePath);
                // 读取裁剪后的bitmap对象
                Bitmap bitmap = BitmapHelper.decodeSampledBitmapFromFile(filePath, 2);
                // 对原图进行质量压缩后重新存储
                BitmapHelper.bitmapCompressAndStorage(filePath, bitmap, 50, false);
                // 为压缩后的新图加上旋转角度
                if (degree > 0)
                    BitmapHelper.setBitmapDegree(filePath, degree);
            }
        }).start();
    }

    /**
     * 通知媒体扫描指定文件
     *
     * @param context 上下文对象
     * @param uri     文件Uri
     */
    public static void informMediaScanner(Context context, Uri uri) {
        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(localIntent);
    }

    public static String getImagePath(Activity activity, Uri imageUri, String selection) {
        String path = null;
        // query projection
        String[] projection = {MediaStore.Images.Media.DATA};
        // 执行查询
        Cursor cursor;
        if (Build.VERSION.SDK_INT < 11) {
            cursor = activity.managedQuery(imageUri, projection, selection, null, null);
        } else {
            CursorLoader cursorLoader = new CursorLoader(activity, imageUri, projection, selection, null, null);
            cursor = cursorLoader.loadInBackground();
        }
        if (cursor != null) {
            // 从查询结果解析path
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
