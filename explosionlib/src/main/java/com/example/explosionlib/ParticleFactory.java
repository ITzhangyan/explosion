package com.example.explosionlib;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Author:${YAN}
 * Time:2019/8/6 0006 上午 10:34
 * Description:粒子工厂
 */
public abstract class ParticleFactory {

    public ParticleFactory(){}

    /**
     * 生成粒子
     * @param bitmap 图片
     * @param rect 粒子区域
     * @return
     */
    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect rect);

}
