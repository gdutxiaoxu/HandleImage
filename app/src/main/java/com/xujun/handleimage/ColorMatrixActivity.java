package com.xujun.handleimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

public class ColorMatrixActivity extends AppCompatActivity {


    private ImageView mIv;
    private GridLayout mGlGroup;
    private Button mBtnChange;
    private Button mBtnReset;

    private int mEtHeight;
    public static final int mRows = 5;
    public static final int mColumns = 4;
    private static final int mSum=20;
    private int mEtWidth;
    EditText[] mEditTexts = new EditText[mSum];

    float[] colorMatrixs = new float[mSum];
    private Bitmap mBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);
        initView();
        initData();
    }

    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                getMatrix();
                setImageMatrix();
                break;
            case R.id.btn_reset:
                initMatrix();
                getMatrix();
                setImageMatrix();
                break;
        }
    }

    private void setImageMatrix() {
        Bitmap bmp = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap
                .Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrixs);
        paint.setColorFilter(colorMatrixColorFilter);
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        mIv.setImageBitmap(bmp);
    }

    private void getMatrix() {

        for (int i = 0; i < mSum; i++) {
            colorMatrixs[i] = Float.parseFloat(mEditTexts[i].getText().toString());
        }
    }

    private void initData() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tangyan);
        mIv.setImageBitmap(mBitmap);
        mGlGroup.post(new Runnable() {

            @Override
            public void run() {
                mEtWidth = mGlGroup.getWidth() / mRows;
                mEtHeight = mGlGroup.getHeight() / mColumns;
            }
        });
        initMatrix();
    }

    private void initMatrix() {
        for (int i = 0; i < mSum; i++) {
            if (i % 6 == 0) {
                mEditTexts[i].setText(String.valueOf(1));
            } else {
                mEditTexts[i].setText(String.valueOf(0));
            }

        }
    }

    private void initView() {
        mIv = (ImageView) findViewById(R.id.iv);
        mGlGroup = (GridLayout) findViewById(R.id.gl_group);
        mBtnChange = (Button) findViewById(R.id.btn_change);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
        initEditTexts();
    }

    private void initEditTexts() {
        for (int i = 0; i < mSum; i++) {
            EditText editText = new EditText(ColorMatrixActivity.this);
            mEditTexts[i] = editText;
            mGlGroup.addView(editText);
        }
    }
}
