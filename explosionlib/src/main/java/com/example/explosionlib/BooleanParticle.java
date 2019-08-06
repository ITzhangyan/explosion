package com.example.explosionlib;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Author:${YAN}
 * Time:2019/8/6 0006 下午 4:43
 * Description:
 */
public class BooleanParticle extends Particle {
    private float radius = Utils.PART_WH;
    private float alpha;
    private float mFactor = 0.5f;//x轴和y轴移动路径
    private Rect mBound;

    public BooleanParticle(int color, float x, float y, Rect bound) {
        super(color, x, y);
        mBound = bound;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha));//这样透明颜色就不是黑色了
        canvas.drawCircle(cx, cy, radius, paint);

    }

    @Override
    protected void calculat(float factor) {
        cx = cx + factor * Utils.RANDOM.nextInt(mBound.width()) * (Utils.RANDOM.nextFloat() - mFactor);

        cy = cy + factor * Utils.RANDOM.nextInt(mBound.height()) * (Utils.RANDOM.nextFloat() - mFactor);

        radius = radius - factor * Utils.RANDOM.nextInt(2);

        alpha = (1f - factor) * (1 + Utils.RANDOM.nextFloat());
    }
}
