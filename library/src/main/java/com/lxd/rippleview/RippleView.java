package com.lxd.rippleview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：LiuXiaoDong on 2017/7/11 10:59.
 */

public class RippleView extends View implements Animator.AnimatorListener {

    /** 初始化半径 */
    private int mRadius;
    /** 变化的半径 */
    private int mChangeRadius;
    /** 园边线宽度 */
    private float mStrokeWidth;
    /** 动画时间 */
    private int mDuration;
    /** 动画次数 */
    private int mRepeatCount;
    /** 圆心x */
    private float mCx;
    /** 圆心y */
    private float mCy;
    /**
     * 圆环颜色
     */
    private int color;

    /**
     * 相邻圆环间隔时间
     */
    private int circleNum;

    /**
     * 最大圆环半径倍数
     */
    private float maxRadiusMultiple;
    private ValueAnimator mValueAnimator;


    private float[] lastRadius ;
    private List<Paint> paints = new ArrayList<>();
    public RippleView(Context context) {
        this(context,null);

    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        initPaint();

    }

    public void startRipple() {
        if (mValueAnimator == null) {
            mValueAnimator = new ValueAnimator();
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.setIntValues(mRadius,(int)(mRadius * maxRadiusMultiple));
            mValueAnimator.setDuration(mDuration);
            mValueAnimator.setRepeatCount(mRepeatCount);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mChangeRadius = (int) animation.getAnimatedValue();
                    invalidate();

                }
            });
            mValueAnimator.addListener(this);
            isPlaying = true;
            mValueAnimator.start();
        } else {
            if (!mValueAnimator.isRunning()) {
                mValueAnimator.start();
                isPlaying = true;
            }
        }
    }

    private boolean isPlaying;
    /** 关闭水波纹 */
    public void stopRipple() {
        if (mValueAnimator != null) {
            mValueAnimator.end();
            mChangeRadius = mRadius;
            isPlaying = false;
        }
    }


    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RippleView, 0, 0);
        mStrokeWidth = a.getDimension(R.styleable.RippleView_stroke_width, 1);
        mDuration = a.getInteger(R.styleable.RippleView_ripple_duration, 4000);
        mRepeatCount = a.getInteger(R.styleable.RippleView_repeat_count, -1);
        circleNum = a.getInteger(R.styleable.RippleView_circle_num, 3);
        maxRadiusMultiple = a.getFloat(R.styleable.RippleView_max_radius_multiple, 1.4f);
        color = a.getColor(R.styleable.RippleView_circle_color, Color.WHITE);
        a.recycle();
    }

    private void initPaint() {
        mChangeRadius = mRadius;
        lastRadius = new float[circleNum];
        for (int i = 0; i < circleNum; i++) {
            lastRadius[i] = 0f;
            paints.add(getPaint());
        }
    }

    private Paint getPaint(){
        Paint paint = new Paint();
        //初始化实心内圆画笔
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidth);
        return paint;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCx = w/2;
        mCy = h/2;
        mRadius = (int) (mCx);
    }

    public int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //用于循环的计数
    private int repeatCount;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isPlaying){
            paints.get(0).setAlpha(100 - (int) ((mChangeRadius - mRadius) * 1.0f /(mRadius * maxRadiusMultiple -mRadius) * 100));
            canvas.drawCircle(mCx, mCy, mChangeRadius, paints.get(0));
            float baseRadius = (maxRadiusMultiple -1f) / circleNum  * mRadius;
            for (int i = 1; i < circleNum; i++) {
                if(mChangeRadius - mRadius > baseRadius * i || repeatCount > 0){
                    float radius;
                    if(mChangeRadius - mRadius > baseRadius * i){
                        radius = mChangeRadius - baseRadius * i;
                        lastRadius[i] = radius;
                    }else{
                        radius = lastRadius[i] + mChangeRadius - mRadius;
                    }
                    paints.get(i).setAlpha( 100 - (int)((radius - mRadius)/((maxRadiusMultiple -1f) * mRadius) * 100));
                    canvas.drawCircle(mCx, mCy, radius , paints.get(i));
                }
            }

        }
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        repeatCount = 0;
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        repeatCount++;
    }
}
