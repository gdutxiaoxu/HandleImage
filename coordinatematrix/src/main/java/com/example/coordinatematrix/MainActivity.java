package com.example.coordinatematrix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View v){
        switch (v.getId()){
            case R.id.btn_first:
                jump(ImageMatrixActivity.class);
                break;

            case R.id.btn_second:
                jump(ImageMatrixActivity2.class);
                break;

        }
    }

    public void jump(Class clz){
        Intent intent = new Intent(this,clz);
        startActivity(intent);
    }
}
