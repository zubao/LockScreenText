package com.fanle.lockscreentext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/25.
 * Email:   lipeil4195@gmail.com
 * Author:  lipeilong
 */

public class LockScreenTextView extends TextView {

    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;//渐变矩阵
    private Paint mPaint;//画笔
    private int mViewWidth = 0;//textView的宽
    private int mTranslateX = 0;//平移量
    private int delta = 15;//移动增量

    private final long TIME_INTERNAL = 100;

    public LockScreenTextView(Context ctx) {
        this(ctx, null);
    }

    public LockScreenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth != getMeasuredWidth() && getMeasuredWidth() > 0 && getText().length() > 0) {

            mViewWidth  = getMeasuredWidth();
            int size    = mViewWidth / 2;

            mLinearGradient = new LinearGradient(-size, 0, 0, 0,
                    new int[]{0x3fffffff, 0x7fffffff, 0x3fffffff},
                    new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP); //边缘融合

            mPaint = getPaint();
            mPaint.setShader(mLinearGradient);//设置渐变
            mGradientMatrix = new Matrix();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null) {
            float width = getMeasuredWidth();

            mGradientMatrix.setTranslate(mTranslateX, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            mTranslateX += delta;//默认向右移动
            mTranslateX = (int) (mTranslateX % width);

            postInvalidateDelayed(TIME_INTERNAL);//刷新
        }
    }
}

