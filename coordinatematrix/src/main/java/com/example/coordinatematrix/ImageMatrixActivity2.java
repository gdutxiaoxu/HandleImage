package com.example.coordinatematrix;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ImageMatrixActivity2 extends AppCompatActivity {

    private ImageMatrixView mImv;


    int count=9;
    float[] mMatrixs = new float[count];
    private Matrix mMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_matrix2);
        mImv = (ImageMatrixView) findViewById(R.id.imv);
        for(int i=0;i<count;i++){
            if(i%4==0){
                mMatrixs[i]=1;
            }else{
                mMatrixs[i]=0;
            }
        }
    }

    public void onButtonClick(View v){
        switch (v.getId()){
            case R.id.btn_translate:
                mMatrix = new Matrix();
                mMatrix.setTranslate(100,100);
                mImv.setImageMatrix(mMatrix);
                mImv.invalidate();

                break;
            case  R.id.btn_rotate:

                mMatrix = new Matrix();
                mMatrix.setTranslate(100,50);
                mMatrix.postRotate(45);
                mImv.setImageMatrix(mMatrix);
                mImv.invalidate();
                break;
            case R.id.btn_multi_effect:


                mMatrix = new Matrix();
                mMatrix.setTranslate(150,250);
                mMatrix.postScale(2,2);
                mImv.setImageMatrix(mMatrix);
                mImv.invalidate();
                break;

            case R.id.btn_reset:

                mMatrix = new Matrix();
                mImv.setImageMatrix(mMatrix);
                mImv.invalidate();
                break;
        }
    }
}
