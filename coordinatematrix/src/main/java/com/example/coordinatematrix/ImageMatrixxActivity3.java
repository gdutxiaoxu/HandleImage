package com.example.coordinatematrix;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageMatrixxActivity3 extends AppCompatActivity {

    private ImageView iv;
    private GridLayout glGroup;
    private LinearLayout ll;
    private Button btnRotate;
    private Button btnScale;
    private Matrix mMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_matrixx3);
        iv = (ImageView) findViewById(R.id.iv);
        glGroup = (GridLayout) findViewById(R.id.glGroup);
        ll = (LinearLayout) findViewById(R.id.ll);
        btnRotate = (Button) findViewById(R.id.btn_rotate);
        btnScale = (Button) findViewById(R.id.btn_scale);
    }

    public void onButtonClick(View v){
        switch (v.getId()){
            case R.id.btn_rotate:
                iv.setRotation(90);

                break;
            case R.id.btn_scale:
                Matrix matrix2=new Matrix(iv.getMatrix());
                matrix2.setRotate(45,0.5f,0.5f);
                iv.setImageMatrix(matrix2);

                break;
        }

    }
}
