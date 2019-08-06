package com.example.explosionlib;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Author:${YAN}
 * Time:2019/8/6 0006 上午 10:15
 * Description:
 */
public class BooleanFactory extends ParticleFactory{
    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect rect) {

        int width = rect.width();
        int height = rect.height();

        //横向个数
       int partW_Count =  width / Utils.PART_WH;
       //竖向个数
       int partH_Count =  height / Utils.PART_WH;

       //每个粒子在对应的bitmap中所占的大小
        int bitmap_part_w = bitmap.getWidth() / partW_Count;
        int bitmap_part_h = bitmap.getHeight() / partH_Count;

        Particle[][] particles = new Particle[partH_Count][partW_Count];
        for (int row = 0; row < partH_Count; row++) {//行
            for (int column = 0; column < partW_Count; column++) {//列
                //获取当前粒子所在位置的颜色
                int color = bitmap.getPixel(column * bitmap_part_w,row * bitmap_part_h);

                float x = rect.left + Utils.PART_WH * column;
                float y = rect.top + Utils.PART_WH * row;

                particles[row][column] = new BooleanParticle(color,x,y,rect);
            }
        }

        return particles;
    }
}
