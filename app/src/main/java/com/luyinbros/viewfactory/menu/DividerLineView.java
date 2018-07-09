package com.luyinbros.viewfactory.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.luyinbros.viewfactory.R;


public class DividerLineView extends View {
    private Paint mPaint;
    private int orientationMode = HORIZONTAL;
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    public final int lineSize;
    private Path mPath;
    private PathEffect effects;

    public DividerLineView(Context context) {
        this(context, null);
    }

    public DividerLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        lineSize = dip2px(context, 1);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(lineSize);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DividerLineView);
        setColor(a.getColor(R.styleable.DividerLineView_dividerline_color, ContextCompat.getColor(context, R.color.divider_default)));
        setOrientationMode(a.getInt(R.styleable.DividerLineView_dividerline_orientationMode, HORIZONTAL));
        a.recycle();

        mPath = new Path();
        //数组含义：里面最少要有2个值，值的个数必须是偶数个。偶数位（包含0），表示实线长度，奇数位表示断开的长度
        effects = new DashPathEffect(new float[]{15, 3}, 0);
        mPaint.setPathEffect(effects);

    }

    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
        invalidate();
    }

    public void setOrientationMode(int orientationMode) {
        this.orientationMode = orientationMode;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (orientationMode == VERTICAL) {
            setMeasuredDimension(lineSize, MeasureSpec.getSize(heightMeasureSpec));
        } else {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), lineSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (orientationMode == HORIZONTAL) {
            mPath.reset();
            mPath.moveTo(0, getHeight() / 2);
            mPath.lineTo(getWidth(), getHeight() / 2);
            canvas.drawPath(mPath, mPaint);
        } else {
            mPath.reset();
            mPath.moveTo(getWidth() / 2, 0);
            mPath.lineTo(getWidth() / 2, getHeight());
            canvas.drawPath(mPath, mPaint);
        }
    }

    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
