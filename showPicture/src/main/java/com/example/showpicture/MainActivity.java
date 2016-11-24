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
	private Bitmap bmp; //ԭʼͼƬ
	
	//ͼƬ�任����
	private float smallbig=1.0f;   //���ű���
	private int turnRotate=0;       //��ת����
	private float saturation=0f;    //���Ͷ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    selectBn = (Button) findViewById(R.id.button1);
	    imageShow = (ImageView) findViewById(R.id.imageView1);
	    imageCreate = (ImageView) findViewById(R.id.imageView2);
	    textview1 = (TextView) findViewById(R.id.textView1);
	    textview2 = (TextView) findViewById(R.id.textView2);
	    
	    //ѡ��ͼƬ
	    selectBn.setOnClickListener(new OnClickListener() {
	    	@Override
	    	public void onClick(View v) {
	    		Intent intent = new Intent(Intent.ACTION_PICK, 
	    				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	    		startActivityForResult(intent, 0 );
	    	}
	    });
    
	 //��СͼƬ
	Button button2=(Button)findViewById(R.id.button2);
	button2.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			SmallPicture();
		}
	});
	//�Ŵ�ͼƬ
	    Button button3=(Button)findViewById(R.id.button3);
	    button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BigPicture();
			}
		});
	   //��תͼƬ
	Button button4=(Button)findViewById(R.id.button4);
	button4.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			TurnPicture();
		}
	});
	//ͼƬ���Ͷȸı�
	Button button5=(Button)findViewById(R.id.button5);
	button5.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			SaturationPicture();
		}
	});
	//ͼƬ�Աȶȸı�
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

	//��ʾ����ͼƬ
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK) {
			ShowPhotoByImageView(data);     //��ʾ��Ƭ
			CreatePhotoByImageView();          //����ͼƬ
		}
	}
	//�Զ��庯�� ��ʾ�򿪵���Ƭ��ImageView1��
	public void ShowPhotoByImageView(Intent data) {
		Uri imageFileUri = data.getData();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;    //�ֻ���Ļˮƽ�ֱ���
		int height = dm.heightPixels;  //�ֻ���Ļ��ֱ�ֱ���
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
			 //ͼ����������   
		    bmpFactoryOptions.inJustDecodeBounds = false;      		    
		    bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);  
		    imageShow.setImageBitmap(bmp); //�����ú���Ƭ��ʾ����  
		    textview1.setVisibility(View.VISIBLE);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	//�����ڶ���ͼƬ����ʾ
	public void CreatePhotoByImageView() {
		try {
		    Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
	    	Canvas canvas = new Canvas(createBmp); //���� ����λͼ���ڻ���
	    	Paint paint = new Paint(); //��ˢ �ı���ɫ �Աȶȵ�����
	    	canvas.drawBitmap(bmp, 0, 0, paint);    //����:û��ͼƬ ��Ϊ����bmpд��createBmp
	    	imageCreate.setImageBitmap(createBmp);
	    	textview2.setVisibility(View.VISIBLE);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//��СͼƬ
	private void SmallPicture() {
		Matrix matrix = new Matrix();
		//�������� 0.5-1.0
		if(smallbig>0.5f)
			smallbig=smallbig-0.1f;
		else
			smallbig=0.5f;
		//x y����ͬʱ����
		matrix.setScale(smallbig,smallbig,bmp.getWidth()/2,bmp.getHeight()/2); 
		Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
		Canvas canvas = new Canvas(createBmp); //���� ����λͼ���ڻ���
		Paint paint = new Paint(); //��ˢ �ı���ɫ �Աȶȵ�����
	    	canvas.drawBitmap(bmp, matrix, paint);
	    	imageCreate.setBackgroundColor(Color.RED);
	    	imageCreate.setImageBitmap(createBmp);
	    	textview2.setVisibility(View.VISIBLE);
	    }
	  //�Ŵ�ͼƬ
	private void BigPicture() {
		Matrix matrix = new Matrix();
		//�������� 0.5-1.0
		if(smallbig<1.5f)
			smallbig=smallbig+0.1f;
		else
			smallbig=1.5f;
		//x y����ͬʱ����
		matrix.setScale(smallbig,smallbig,bmp.getWidth()/2,bmp.getHeight()/2); 
		Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
		Canvas canvas = new Canvas(createBmp); 
		Paint paint = new Paint(); 
		canvas.drawBitmap(bmp, matrix, paint);
		imageCreate.setBackgroundColor(Color.RED);
		imageCreate.setImageBitmap(createBmp);
		textview2.setVisibility(View.VISIBLE);
	}
	//��תͼƬ
	private void TurnPicture() {
		Matrix matrix = new Matrix();
		turnRotate=turnRotate+15;
		//ѡ��Ƕ� ��(0,0)��ѡ�� ����˳ʱ�� ������ʱ�� ������ת
		matrix.setRotate(turnRotate,bmp.getWidth()/2,bmp.getHeight()/2); 
		Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
		Canvas canvas = new Canvas(createBmp); 
		Paint paint = new Paint(); 
		canvas.drawBitmap(bmp, matrix, paint);
		imageCreate.setBackgroundColor(Color.RED);
		imageCreate.setImageBitmap(createBmp);
		textview2.setVisibility(View.VISIBLE);
	}
	//�ı�ͼ�񱥺Ͷ�
	private void SaturationPicture() {
		//���ñ��Ͷ� 0��ʾ�Ҷ�ͼ�� ����1���Ͷ����� 0-1���Ͷȼ�С
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(saturation);
		Paint paint = new Paint(); 
		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		//��ʾͼƬ
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
	//����ͼƬ�Աȶ�
	private void ContrastPicture() {
		ColorMatrix cm = new ColorMatrix();
		float brightness = -25;  //����
		float contrast = 2;        //�Աȶ�
		cm.set(new float[] {
			contrast, 0, 0, 0, brightness,
			0, contrast, 0, 0, brightness,
			0, 0, contrast, 0, brightness,
			0, 0, 0, contrast, 0
		});
		Paint paint = new Paint(); 
		paint.setColorFilter(new ColorMatrixColorFilter(cm));
		//��ʾͼƬ
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
