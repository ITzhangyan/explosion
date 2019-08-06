package com.example.explosionlib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * Author:${YAN}
 * Time:2019/8/6 0006 上午 11:33
 * Description:
 */
public class Utils {

    public static final Random RANDOM = new Random(System.currentTimeMillis());

    private static final Canvas CANVAS = new Canvas();

    public static final int PART_WH = 8;

    public Utils(){}

    /**
     * 生成一个和我们自己的view一样大小的bitmap
     * @param view
     * @return
     */
    public static Bitmap createBitmapFromView(View view){
        view.clearFocus();
       Bitmap bitmap =  createBitmapSafely(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888,1);
        if (bitmap != null){
            synchronized (CANVAS){
                CANVAS.setBitmap(bitmap);
                view.draw(CANVAS);
                CANVAS.setBitmap(null);
            }
        }

        return bitmap;
    }

    private static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {

        try {
            return Bitmap.createBitmap(width,height,config);
        }catch (OutOfMemoryError var5){
            var5.printStackTrace();
            if (retryCount > 0){
                System.gc();
                return createBitmapSafely(width,height,config,retryCount - 1);
            }else {
                return null;
            }
        }
    }

}
