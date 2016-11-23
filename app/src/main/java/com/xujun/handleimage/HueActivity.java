package com.xujun.handleimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

public class HueActivity extends AppCompatActivity {

    private ImageView mIv;
    private SeekBar mSbHue;
    private SeekBar mSbSaturation;
    private SeekBar mSbLum;

    private  static  int MAX_VALUE=255;
    private  static  int MID_VALUE =127;
    private float mHue,mStauration, mLum;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hue);

        initView();
        initListerer();
        initData();
    }

    private void initData() {

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test3);
        mSbHue.setMax(MAX_VALUE);
        mSbLum.setMax(MAX_VALUE);
        mSbSaturation.setMax(MAX_VALUE);

        mSbHue.setProgress(MID_VALUE);
        mSbLum.setProgress(MID_VALUE);
        mSbSaturation.setProgress(MID_VALUE);
        mIv.setImageBitmap(mBitmap);
    }

    private void initListerer() {
        SeekBarListener seekBarListener = new SeekBarListener();
        mSbHue.setOnSeekBarChangeListener(seekBarListener);
        mSbSaturation.setOnSeekBarChangeListener(seekBarListener);
        mSbLum.setOnSeekBarChangeListener(seekBarListener);
    }

    private void initView() {
        mIv = (ImageView) findViewById(R.id.iv);
        mSbHue = (SeekBar) findViewById(R.id.sb_hue);
        mSbSaturation = (SeekBar) findViewById(R.id.sb_saturation);
        mSbLum = (SeekBar) findViewById(R.id.sb_lum);
    }

    private class  SeekBarListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            switch (seekBar.getId()){
                case R.id.sb_hue:
                    mHue = (i - MID_VALUE) * 1.0F / MID_VALUE * 180;
                    break;
                case R.id.sb_saturation:
                    mStauration = i * 1.0F / MID_VALUE;
                    break;
                case R.id.sb_lum:
                    mLum = i * 1.0F / MID_VALUE;
            }
            Bitmap bitmap = ImageHelper.handleImage(mBitmap, mHue, mStauration, mLum);
            mIv.setImageBitmap(bitmap);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
