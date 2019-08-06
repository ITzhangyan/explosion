package com.example.explosionlib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

/**
 * Author:${YAN}
 * Time:2019/8/6 0006 上午 10:17
 * Description:
 */
@SuppressLint("ViewConstructor")
public class ExplosionField extends View {
    private ArrayList<ExplosionAnimator> mExplosionAnimator;
    private ParticleFactory mParticleFactory;

    private OnClickListener mOnClickListener;
    private ClickCallBack mClickCallback;

    //在初始化的时候，传入一个粒子工厂，方便拓展
    public ExplosionField(Context context, ParticleFactory mParticleFactory) {
        super(context);
        //初始化
        init(mParticleFactory);
    }

    /**
     * //初始化数据
     *
     * @param mParticleFactory
     */
    private void init(ParticleFactory mParticleFactory) {
        mExplosionAnimator = new ArrayList<>();
        this.mParticleFactory = mParticleFactory;
        //将Field添加到屏幕里去
        attachToActivity((Activity) getContext());
    }

    /**
     * 将Field添加到window的根布局里去
     *
     * @param context
     */
    private void attachToActivity(Activity context) {

        //查找到根布局id为content
        ViewGroup rootView = context.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(this, lp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //反复执行
        for (ExplosionAnimator explosionAnimator : mExplosionAnimator) {
            explosionAnimator.draw(canvas);
        }
    }

    /**
     * 给所有需要添加粒子特效的view添加点击事件
     *
     * @param view
     */
    public void addListener(View view) {

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.addListener(viewGroup.getChildAt(i));
            }
        } else {
            view.setClickable(true);
            view.setOnClickListener(getOnClickListener());
        }

    }

    private OnClickListener getOnClickListener() {
        if (null == this.mOnClickListener) {
            this.mOnClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //粒子爆炸特效
                    explode(v);
                    if (mClickCallback != null) {
                        mClickCallback.onClick(v);
                    }
                }
            };
        }
        return mOnClickListener;
    }



    /**
     * 粒子爆炸特效
     *
     * @param view 需要粒子特效的view
     */
    private void explode(final View view) {
        //获取view的位置
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);//view相对于整个屏幕的位置
        //Actionbar的高度
        int actionBarHeight = ((ViewGroup) getParent()).getTop();

        //获取状态栏高度
        Rect fram = new Rect();
        ((Activity)getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(fram);
        int statusBarHeight = fram.top;

        rect.offset(0,- actionBarHeight - statusBarHeight);//粒子生成的位置区域


        //点击控件的时候，会有一个震动效果
        ValueAnimator anim = new ValueAnimator().ofFloat(0f,1f).setDuration(150);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((Utils.RANDOM.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY((Utils.RANDOM.nextFloat() - 0.5f) * view.getHeight() * 0.05f);
            }
        });

        //动画结束后，恢复位置
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //恢复位置
                view.setTranslationX(0);
                view.setTranslationY(0);

                explode2(view,rect);
            }
        });
        //开启动画
        anim.start();
    }

    /**
     * 执行动画
     * @param view
     * @param rect
     */
    private void explode2(final View view, Rect rect) {
        final ExplosionAnimator animator = new ExplosionAnimator(this,Utils.createBitmapFromView(view),rect,mParticleFactory);
        mExplosionAnimator.add(animator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

                view.setClickable(false);
                view.animate().setDuration(150L).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).start();

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                view.setClickable(true);
                view.animate().setDuration(150L).scaleX(1.0F).scaleY(1.0F).alpha(1.0F).start();
                mExplosionAnimator.remove(animator);
            }

        });
        animator.start();
    }

    public void setClickListener(ClickCallBack clickListener){
        this.mClickCallback = clickListener;
    }
}
