package com.example.explosionlib;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Author:${YAN}
 * Time:2019/8/6 0006 上午 10:42
 * Description:粒子动画
 */
public class ExplosionAnimator extends ValueAnimator{

    public long DEFAULT_DURATION = 1500;//动画时间
    private Particle[][] mParticles;//存放粒子的二维数组
    private Paint mPaint;
    private View mContainer;
    private ParticleFactory mParticleFactory;

    public ExplosionAnimator(ExplosionField view, Bitmap bitmap, Rect rect,ParticleFactory particleFactory){
        this.mParticleFactory = particleFactory;
        this.mPaint = new Paint();
        this.mContainer = view;
        this.setFloatValues(0.0f,1.0f);
        this.setDuration(this.DEFAULT_DURATION);
        this.mParticles =  particleFactory.generateParticles(bitmap,rect);

        //释放图片资源
        bitmap.recycle();

    }

    public void draw(Canvas canvas){
        if (!isStarted()){//动画结束
            return;
        }
        //所有粒子需要开始运动
        for (Particle[] mParticle : mParticles) {
            for (Particle particle : mParticle) {
                //单个粒子
                particle.advance(canvas , mPaint , (Float) getAnimatedValue());
            }
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
