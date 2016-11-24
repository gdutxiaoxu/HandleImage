package com.example.coordinatematrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/11/24.
 */

public class ImageMatrixView extends ImageView {

    private Bitmap mBitmap;
    private Paint mPaint;
    private Matrix mMatrix;

    public ImageMatrixView(Context context) {
        this(context, null);
    }

    public ImageMatrixView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageMatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMatrix=new Matrix();
    }

    public void setImageMatrix(Matrix matrix){
        mMatrix = matrix;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap,0,0,mPaint);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
    }
}
