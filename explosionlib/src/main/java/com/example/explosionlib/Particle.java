package com.example.explosionlib;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Author:${YAN}
 * Time:2019/8/6 0006 上午 10:21
 * Description:粒子，后面需要拓展其他粒子爆炸效果可以继承这个抽象类
 */
public abstract class Particle {
    public float cx;
    public float cy;
    public int color;

    public Particle(int color,float x,float y){
        this.color = color;
        cx = x;
        cy = y;
    }


    /**
     * 步进，粒子爆炸进行的步骤路径
     * @param canvas
     * @param paint
     * @param factor 时间因子，随着时间的推移，粒子到了哪个位置
     */
    public void advance(Canvas canvas, Paint paint,float factor){
        //计算我当前所在的位置
        calculat(factor);

        //绘制
        draw(canvas,paint);
    }

    protected abstract void draw(Canvas canvas, Paint paint);

    protected abstract void calculat(float factor);
}
