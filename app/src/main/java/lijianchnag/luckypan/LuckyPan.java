package lijianchnag.luckypan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by 13155 on 2017/7/12:16:30.
 * Des :
 */

public class LuckyPan extends View {

    int left;

    int top;

    RectF mRecctF;

    RectF mRecctFText;

    Paint mPaint;

    Paint mPaint1;

    Paint mPaintText;

    int widthPixel;

    DisplayMetrics displayMetrics;

    Context mContext;

    float randius;

    private String[] strs = {"华为手机","谢谢惠顾","iPhone 6s","mac book","魅族手机","小米手机"};

    public LuckyPan(Context context) {
        super(context);
    }

    public LuckyPan(Context context, AttributeSet set){
        super(context,set);
         displayMetrics = new DisplayMetrics();
        ((WindowManager)(context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getMetrics(displayMetrics);
        mContext = context;
        mPaint = new Paint();
        mPaint.setStrokeWidth(1*displayMetrics.density);
        mPaint.setColor(Color.rgb(255,133,132));
        mPaint1 = new Paint();
        mPaint1.setColor(Color.rgb(254,104,105));
        mPaintText = new Paint();
        mPaintText.setStrokeWidth(1*displayMetrics.density);
        mPaintText.setColor(Color.YELLOW);
        mPaintText.setTextSize(15*displayMetrics.density);

//        mPaint.setStrokeWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        left = getPaddingLeft();
        top = getPaddingTop();
        randius = Math.min(getWidth(),getHeight())/2;
        mRecctF = new RectF(left,top,getWidth(),getHeight());
        mRecctFText = new RectF(left+getWidth()*1/6,top+getHeight()*1/6,getWidth()*5/6,getHeight()*5/6);
//        Rect mRect = new Rect();
//        mPaint.setTextSize(50*displayMetrics.density);
//        mPaint.getTextBounds(str,0,str.length(),mRect);
//
//
//        canvas.drawText(str,left+getWidth()/2-mRect.width()/2,top+getHeight()/2+mRect.height()/2,mPaint);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(mRecctF,mPaint1);
//        mPaint.setColor(Color.BLUE);
        for(int i =0 ;i<6;i++){
            if(i%2 == 0){
            canvas.drawArc(mRecctF,i*60,60,true,mPaint);
            }else{
             canvas.drawArc(mRecctF,i*60,60,true,mPaint1);
            }
            Path mPath = new Path();
            mPath.addArc(mRecctFText, i*60, 60);
            float width = mPaintText.measureText(strs[i]);
            float hOffset = (float) (randius*Math.PI*2/6/3 -width/2);
            float vOffset =- randius/12;
            canvas.drawTextOnPath(strs[i], mPath, hOffset, vOffset, mPaintText);
            mPaintText.setStrokeWidth(3*displayMetrics.density);
//            canvas.drawPoint((float) (randius+Math.cos(30)*randius/3), (float) (randius-Math.sin(30)*randius/3),mPaintText);
            canvas.drawPoint((float) (randius+Math.cos(Math.PI/6)*randius/2),(randius+randius/4),mPaintText);

        }



        for(int i= 0;i<6;i++) {
//            Path mPath = new Path();
//            mPath.addArc(mRecctFText, i*60, 60);
//            float width = mPaintText.measureText(strs[i]);
//            float hOffset = (float) (randius*Math.PI*2/6/3 -width/2);
//            float vOffset =- randius/12;
//            canvas.drawTextOnPath(strs[i], mPath, hOffset, vOffset, mPaintText);

        }


//        canvas.drawArc(mRecctF,60,60,true,mPaint);
//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.xiaomi);
//        Matrix matrix = new Matrix();
//        matrix.postScale(0.5f,0.5f);
//        canvas.drawBitmap(bitmap,matrix,mPaint);

    }
}
