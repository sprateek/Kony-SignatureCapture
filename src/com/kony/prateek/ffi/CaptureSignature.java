package com.kony.prateek.ffi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.konylabs.vm.Function;

public class CaptureSignature extends Activity
{
	private final static String TAG = "SignatureActivity"; 
  public static String tempDir;
  protected Function callback;
  Button cancelButton;
  Button clearButton;
  public int count = 1;
  public String current = null;
  protected Bundle extras;
  LinearLayout linear;
  LinearLayout linearButtons;
  LinearLayout linearInside;
  private Bitmap mBitmap;
  Button mCancel;
  Button mClear;
  LinearLayout mContent;
  Button mGetSign;
  signature mSignature;
  View mView;
  File mypath;
  Button signButton;
  protected String signatureByteArray;
  TableLayout table;
  TableRow tableRow;
  TextView text;

  public void finish()
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("returnKey1", "Swinging on a star. ");
    setResult(-1, localIntent);
    super.finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    LinearLayout localLinearLayout1 = new LinearLayout(this);
    localLinearLayout1.setOrientation(1);
    this.mContent = new LinearLayout(this);
    this.mContent.setOrientation(1);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(900, 500);
    LinearLayout localLinearLayout2 = new LinearLayout(this);
    localLinearLayout2.setOrientation(0);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-2, -1);
    localLayoutParams2.setMargins(200, 0, 200, 0);
    localLinearLayout2.setLayoutParams(localLayoutParams2);
    this.mCancel = new Button(this);
    this.mCancel.setText("CANCEL");
    this.mCancel.setTextColor(-1);
    this.mCancel.setPadding(35, 15, 35, 15);
    this.mClear = new Button(this);
    this.mClear.setText("CLEAR");
    this.mClear.setTextColor(-1);
    this.mClear.setPadding(35, 15, 35, 15);
    this.mGetSign = new Button(this);
    this.mGetSign.setText("SAVE");
    this.mGetSign.setTextColor(-1);
    this.mGetSign.setPadding(35, 15, 35, 15);
    localLinearLayout2.addView(this.mCancel);
    localLinearLayout2.addView(this.mClear);
    localLinearLayout2.addView(this.mGetSign);
    localLinearLayout1.addView(this.mContent, localLayoutParams1);
    localLinearLayout1.addView(localLinearLayout2, localLayoutParams2);
    setContentView(localLinearLayout1);
    new ContextWrapper(getApplicationContext());
    this.mSignature = new signature(this, null);
    this.mSignature.setBackgroundColor(-1);
    this.mContent.addView(this.mSignature, -1, -1);
    this.mView = this.mContent;
    this.mClear.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Log.v(TAG, "Panel Cleared");
        CaptureSignature.this.mSignature.clear();
        CaptureSignature.this.mGetSign.setEnabled(false);
      }
    });
    this.mGetSign.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Log.v(TAG, "Panel Saved");
        CaptureSignature.this.mView.setDrawingCacheEnabled(true);
        CaptureSignature.this.mSignature.save(CaptureSignature.this.mView);
        new Bundle().putString("status", "done");
        CaptureSignature.this.extras = CaptureSignature.this.getIntent().getExtras();
        if (CaptureSignature.this.extras != null)
          CaptureSignature.this.callback = ((Function)CaptureSignature.this.extras.get("callback"));
        try
        {
          System.out.println("***Passing the byteArray back to Kony");
          Hashtable localHashtable = new Hashtable();
          localHashtable.put("signatureByteArray", CaptureSignature.this.signatureByteArray);
          CaptureSignature.this.callback.execute(new Object[] { localHashtable });
          CaptureSignature.this.finish();
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
    this.mCancel.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Log.v(TAG, "Panel Canceled");
        Bundle localBundle = new Bundle();
        localBundle.putString("status", "cancel");
        Intent localIntent = new Intent();
        localIntent.putExtras(localBundle);
        CaptureSignature.this.setResult(-1, localIntent);
        CaptureSignature.this.finish();
      }
    });
  }

  protected void onDestroy()
  {
    Log.w(TAG, "onDestory");
    super.onDestroy();
  }

  public class signature extends View
  {
    private static final float HALF_STROKE_WIDTH = 2.5F;
    private static final float STROKE_WIDTH = 5.0F;
    private final RectF dirtyRect = new RectF();
    private float lastTouchX;
    private float lastTouchY;
    private Paint paint = new Paint();
    private Path path = new Path();

    public signature(Context paramAttributeSet, AttributeSet arg3)
    {
      super(paramAttributeSet, arg3);
      this.paint.setAntiAlias(true);
      this.paint.setColor(-16777216);
      this.paint.setStyle(Paint.Style.STROKE);
      this.paint.setStrokeJoin(Paint.Join.ROUND);
      this.paint.setStrokeWidth(5.0F);
    }

    private void debug(String paramString)
    {
    }

	private void expandDirtyRect(float historicalX, float historicalY){
		if (historicalX < dirtyRect.left){
			dirtyRect.left = historicalX;
		} else if (historicalX > dirtyRect.right){
			dirtyRect.right = historicalX;
		}
		
		if (historicalY < dirtyRect.top){
			dirtyRect.top = historicalY;
		} else if (historicalY > dirtyRect.bottom){
			dirtyRect.bottom = historicalY;
		}
	}

    private void resetDirtyRect(float paramFloat1, float paramFloat2)
    {
      this.dirtyRect.left = Math.min(this.lastTouchX, paramFloat1);
      this.dirtyRect.right = Math.max(this.lastTouchX, paramFloat1);
      this.dirtyRect.top = Math.min(this.lastTouchY, paramFloat2);
      this.dirtyRect.bottom = Math.max(this.lastTouchY, paramFloat2);
    }

    public void clear()
    {
      this.path.reset();
      invalidate();
    }

    public byte[] convertBitmapToByteArray(Context paramContext, Bitmap paramBitmap)
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(paramBitmap.getWidth() * paramBitmap.getHeight());
      paramBitmap.compress(Bitmap.CompressFormat.PNG, 90, localByteArrayOutputStream);
      return localByteArrayOutputStream.toByteArray();
    }

    protected void onDraw(Canvas paramCanvas)
    {
      paramCanvas.drawPath(this.path, this.paint);
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      CaptureSignature.this.mGetSign.setEnabled(true);
      switch (paramMotionEvent.getAction())
      {
      default:
        debug("Ignored touch event: " + paramMotionEvent.toString());
        return false;
      case 0:
        this.path.moveTo(f1, f2);
        this.lastTouchX = f1;
        this.lastTouchY = f2;
        return true;
      case 1:
      case 2:
      }
      resetDirtyRect(f1, f2);
      int i = paramMotionEvent.getHistorySize();
      for (int j = 0; ; ++j)
      {
        if (j >= i)
        {
          this.path.lineTo(f1, f2);
          invalidate((int)(this.dirtyRect.left - 2.5F), (int)(this.dirtyRect.top - 2.5F), (int)(2.5F + this.dirtyRect.right), (int)(2.5F + this.dirtyRect.bottom));
          this.lastTouchX = f1;
          this.lastTouchY = f2;
          return true;
        }
        float f3 = paramMotionEvent.getHistoricalX(j);
        float f4 = paramMotionEvent.getHistoricalY(j);
        expandDirtyRect(f3, f4);
        this.path.lineTo(f3, f4);
      }
    }

    public void save(View paramView)
    {
      if (CaptureSignature.this.mBitmap == null)
        CaptureSignature.this.mBitmap = Bitmap.createBitmap(CaptureSignature.this.mContent.getWidth(), CaptureSignature.this.mContent.getHeight(), Bitmap.Config.RGB_565);
      Canvas localCanvas = new Canvas(CaptureSignature.this.mBitmap);
      try
      {
        paramView.draw(localCanvas);
        CaptureSignature.this.signatureByteArray = Base64.encodeToString(convertBitmapToByteArray(getContext(), CaptureSignature.this.mBitmap), 0);
        return;
      }
      catch (Exception localException)
      {
        Log.v(TAG, localException.toString());
      }
    }
  }
}