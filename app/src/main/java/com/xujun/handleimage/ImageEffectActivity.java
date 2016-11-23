package com.xujun.handleimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ImageEffectActivity extends AppCompatActivity {

    private ImageView mIv1;
    private ImageView mIv2;
    private ImageView mIv3;
    private ImageView mIv4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_effect);
        initView();
        initData();
    }

    private void initData() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tangyan);
        mIv1.setImageBitmap(bitmap);

        Bitmap bitmapNegative = ImageHelper.handleImageNegative(bitmap);
        mIv2.setImageBitmap(bitmapNegative);

        Bitmap bitmapRelief = ImageHelper.handleImagePixelsRelief(bitmap);
        mIv3.setImageBitmap(bitmapRelief);

        Bitmap bitmapOldPhoto = ImageHelper.handleImagePixelsOldPhoto(bitmap);
        mIv4.setImageBitmap(bitmapOldPhoto);
    }

    private void initView() {
        mIv1 = (ImageView) findViewById(R.id.iv1);
        mIv2 = (ImageView) findViewById(R.id.iv2);
        mIv3 = (ImageView) findViewById(R.id.iv3);
        mIv4 = (ImageView) findViewById(R.id.iv4);

    }
}
