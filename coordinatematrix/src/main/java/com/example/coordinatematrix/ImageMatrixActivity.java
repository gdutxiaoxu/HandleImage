package com.example.coordinatematrix;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

public class ImageMatrixActivity extends AppCompatActivity {

    private ImageMatrixView iv;

    private GridLayout mGlGroup;
    private Button mBtnChange;
    private Button mBtnReset;

    private static final int rows = 3;
    private static final int columns = 3;
    private static final int count = rows * columns;

    EditText[] mEditTexts = new EditText[count];

    float[] mMatrixs = new float[count];
    private int mWidth;

    private int mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_matrix);
        initView();
        initData();
    }

    private void initData() {


        mGlGroup.post(new Runnable() {

            @Override
            public void run() {
                mWidth = mGlGroup.getWidth();
                mHeight = mGlGroup.getHeight();
                initEditTexts();
                initMatrixs();
            }
        });


    }

    private void initMatrixs() {
        for (int i = 0; i < count; i++) {
            if (i % 4 == 0) {
                mEditTexts[i].setText(String.valueOf(1));
            } else {
                mEditTexts[i].setText(String.valueOf(0));
            }
        }
    }

    private void initEditTexts() {
        for (int i = 0; i < count; i++) {
            EditText editText = new EditText(this);
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams
                    (mWidth / 3, mHeight / 3);
            editText.setLayoutParams(marginLayoutParams);
            editText.setGravity(Gravity.CENTER);
            mGlGroup.addView(editText);
            mEditTexts[i] = editText;
        }
    }

    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                getMatrixs();
                setImageMatrix();
                break;
            case R.id.btn_reset:
                initMatrixs();
                getMatrixs();
                setImageMatrix();
                break;
            default:
                break;
        }
    }

    private void setImageMatrix() {
        Matrix matrix = new Matrix();
        matrix.setValues(mMatrixs);
        iv.setImageMatrix(matrix);
        iv.invalidate();
    }

    private void getMatrixs() {
        for (int i = 0; i < count; i++) {
            mMatrixs[i] = Float.parseFloat(mEditTexts[i].getText().toString());
        }
    }

    private void initView() {

        iv = (ImageMatrixView) findViewById(R.id.iv);
        mGlGroup = (GridLayout) findViewById(R.id.glGroup);
        mBtnChange = (Button) findViewById(R.id.btn_change);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
    }
}
