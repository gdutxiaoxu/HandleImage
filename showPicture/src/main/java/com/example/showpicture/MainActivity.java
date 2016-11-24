package com.example.showpicture;

import java.io.FileNotFoundException;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	
	private Button selectBn;
	private ImageView imageShow;
	private ImageView imageCreate;
	private TextView textview1;
	private TextView textview2;
	private Bitmap bmp; //原始图片
	
	//图片变换参数
	private float smallbig=1.0f;   //缩放比例
	private int turnRotate=0;       //旋转度数
	private float saturation=0f;    //饱和度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    selectBn = (Button) findViewById(R.id.button1);
	    imageShow = (ImageView) findViewById(R.id.imageView1);
	    imageCreate = (ImageView) findViewById(R.id.imageView2);
	    textview1 = (TextView) findViewById(R.id.textView1);
	    textview2 = (TextView) findViewById(R.id.textView2);
	    
	    //选择图片
	    selectBn.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		Intent intent = new Intent(Intent.ACTION_PICK, 
	    				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	    		startActivityForResult(intent, 0 );
	    	}
	    });
    
	 //缩小图片
	Button button2=(Button)findViewById(R.id.button2);
	button2.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			SmallPicture();
		}
	});
	//放大图片
	    Button button3=(Button)findViewById(R.id.button3);
	    button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BigPicture();
			}
		});
	   //旋转图片
	Button button4=(Button)findViewById(R.id.button4);
	button4.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			TurnPicture();
		}
	});
	//图片饱和度改变
	Button button5=(Button)findViewById(R.id.button5);
	button5.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			SaturationPicture();
		}
	});
	//图片对比度改变
	Button button6=(Button)findViewById(R.id.button6);
	button6.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			ContrastPicture();
		}
	});
	
    if (savedInstanceState == null) {
        getFragmentManager().beginTransaction()
                .add(R.id.container, new PlaceholderFragment())
                .commit();
    }
}

	//显示两张图片
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK) {
			ShowPhotoByImageView(data);     //显示照片
			CreatePhotoByImageView();          //创建图片
		}
	}
	//自定义函数 显示打开的照片在ImageView1中
	public void ShowPhotoByImageView(Intent data) {
		Uri imageFileUri = data.getData();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;    //手机屏幕水平分辨率
		int height = dm.heightPixels;  //手机屏幕垂直分辨率
		Log.v("height", ""+height );
		Log.v("width", ""+width);
		try {
			// Load up the image's dimensions not the image itself
			BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
			bmpFactoryOptions.inJustDecodeBounds = true;
			bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);
			int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
			int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);
			Log.v("bmpheight", ""+bmpFactoryOptions.outHeight);
			Log.v("bmpheight", ""+bmpFactoryOptions.outWidth);
			if(heightRatio>1&&widthRatio>1) {
				if(heightRatio>widthRatio) {
					bmpFactoryOptions.inSampleSize = heightRatio*2;
				}
				else {
					bmpFactoryOptions.inSampleSize = widthRatio*2;
				}
			}
			 //图像真正解码   
		    bmpFactoryOptions.inJustDecodeBounds = false;      		    
		    bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);  
		    imageShow.setImageBitmap(bmp); //将剪裁后照片显示出来  
		    textview1.setVisibility(View.VISIBLE);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	//创建第二张图片并显示
	public void CreatePhotoByImageView() {
		try {
		    Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
	    	Canvas canvas = new Canvas(createBmp); //画布 传入位图用于绘制
	    	Paint paint = new Paint(); //画刷 改变颜色 对比度等属性
	    	canvas.drawBitmap(bmp, 0, 0, paint);    //错误:没有图片 因为参数bmp写成createBmp
	    	imageCreate.setImageBitmap(createBmp);
	    	textview2.setVisibility(View.VISIBLE);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//缩小图片
	private void SmallPicture() {
		Matrix matrix = new Matrix();
		//缩放区间 0.5-1.0
		if(smallbig>0.5f)
			smallbig=smallbig-0.1f;
		else
			smallbig=0.5f;
		//x y坐标同时缩放
		matrix.setScale(smallbig,smallbig,bmp.getWidth()/2,bmp.getHeight()/2); 
		Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
		Canvas canvas = new Canvas(createBmp); //画布 传入位图用于绘制
		Paint paint = new Paint(); //画刷 改变颜色 对比度等属性
	    	canvas.drawBitmap(bmp, matrix, paint);
	    	imageCreate.setBackgroundColor(Color.RED);
	    	imageCreate.setImageBitmap(createBmp);
	    	textview2.setVisibility(View.VISIBLE);
	    }
	  //放大图片
	private void BigPicture() {
		Matrix matrix = new Matrix();
		//缩放区间 0.5-1.0
		if(smallbig<1.5f)
			smallbig=smallbig+0.1f;
		else
			smallbig=1.5f;
		//x y坐标同时缩放
		matrix.setScale(smallbig,smallbig,bmp.getWidth()/2,bmp.getHeight()/2); 
		Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
		Canvas canvas = new Canvas(createBmp); 
		Paint paint = new Paint(); 
		canvas.drawBitmap(bmp, matrix, paint);
		imageCreate.setBackgroundColor(Color.RED);
		imageCreate.setImageBitmap(createBmp);
		textview2.setVisibility(View.VISIBLE);
	}
	//旋转图片
	private void TurnPicture() {
		Matrix matrix = new Matrix();
		turnRotate=turnRotate+15;
		//选择角度 饶(0,0)点选择 正数顺时针 负数逆时针 中心旋转
		matrix.setRotate(turnRotate,bmp.getWidth()/2,bmp.getHeight()/2); 
		Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
		Canvas canvas = new Canvas(createBmp); 
		Paint paint = new Paint(); 
		canvas.drawBitmap(bmp, matrix, paint);
		imageCreate.setBackgroundColor(Color.RED);
		imageCreate.setImageBitmap(createBmp);
		textview2.setVisibility(View.VISIBLE);
	}
	//改变图像饱和度
	private void SaturationPicture() {
		//设置饱和度 0表示灰度图像 大于1饱和度增加 0-1饱和度减小
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(saturation);
		Paint paint = new Paint(); 
		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		//显示图片
		Matrix matrix = new Matrix();
		Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
		Canvas canvas = new Canvas(createBmp); 
		canvas.drawBitmap(bmp, matrix, paint);
		imageCreate.setImageBitmap(createBmp);
		textview2.setVisibility(View.VISIBLE);
		saturation=saturation+0.1f;
		if(saturation>=1.5f) {
			saturation=0f;
		}
	}
	//设置图片对比度
	private void ContrastPicture() {
		ColorMatrix cm = new ColorMatrix();
		float brightness = -25;  //亮度
		float contrast = 2;        //对比度
		cm.set(new float[] {
			contrast, 0, 0, 0, brightness,
			0, contrast, 0, 0, brightness,
			0, 0, contrast, 0, brightness,
			0, 0, 0, contrast, 0
		});
		Paint paint = new Paint(); 
		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		//显示图片
		Matrix matrix = new Matrix();
		Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
		Canvas canvas = new Canvas(createBmp); 
		canvas.drawBitmap(bmp, matrix, paint);
		imageCreate.setImageBitmap(createBmp);
		textview2.setVisibility(View.VISIBLE);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
