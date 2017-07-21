package lijianchnag.luckypan;

import android.animation.Animator;
import android.animation.ValueAnimator;
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
import android.renderscript.Script;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 13155 on 2017/7/12:16:30.
 * Des :
 */

public class LuckyPan extends View {

    private static final String TAG = "LuckyPan";
    int left;

    int top;

    RectF mRecctF;

    RectF mRecctFText;

    RectF mRectCircle;

    Paint mPaint;

    Paint mPaint1;

    Paint mPaintText;

    int widthPixel;

    DisplayMetrics displayMetrics;

    Context mContext;

    float randius;

    int InitAngle = 0;

    private int[] images = new int[]{R.mipmap.huawei,R.mipmap.image_one,R.mipmap.iphone,R.mipmap.macbook,R.mipmap.meizu,R.mipmap.xiaomi};

    private String[] strs = {"华为手机","谢谢惠顾","iPhone 6s","mac book","魅族手机","小米手机"};

    List<Bitmap>  mListBitmap;

    Bitmap mBitmapCircle;

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
        mListBitmap = new ArrayList<>();
        mBitmapCircle = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.node);
        for(int i = 0;i< images.length;i++){
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),images[i]);
            mListBitmap.add(bitmap);
        }
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        float x = event.getX();
                        float y = event.getY();
                        Log.d(TAG,"x"+x+"y"+y);
                        Log.d(TAG,"mRectCircle.left"+mRectCircle.left+"mRectCircle.right"+mRectCircle.right+"mRectCircle.top"+mRectCircle.top+"mRectCircle.bottom"+mRectCircle.bottom);
                        if(x>mRectCircle.left && x <mRectCircle.right && y>mRectCircle.top && y<mRectCircle.bottom){
                            double v1 = Math.random() * 10 + 0.5;
                            if(v1 <10 ) {
                                startRotatePosition(3);
                            }else {
                                startRotatePosition(1);
                            }

                        }
                        break;
                }
                return true;
            }
        });
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

        int centerX = left +getWidth()/2;
        int centerY = top +getHeight()/2;
        for(int i =0 ;i<6;i++){
            if(i%2 == 0){
            canvas.drawArc(mRecctF,InitAngle+i*60,60,true,mPaint);
            }else{
             canvas.drawArc(mRecctF,InitAngle+i*60,60,true,mPaint1);
            }
            Path mPath = new Path();
            mPath.addArc(mRecctFText, InitAngle+i*60, 60);
            float width = mPaintText.measureText(strs[i]);
            float hOffset = (float) (randius*Math.PI*2/6/3 -width/2);
            float vOffset =- randius/12;
            canvas.drawTextOnPath(strs[i], mPath, hOffset, vOffset, mPaintText);
            mPaintText.setStrokeWidth(3*displayMetrics.density);
            canvas.drawPoint((float) (randius+Math.cos(Math.PI/180*InitAngle+Math.PI/6+Math.PI/3*i)*randius/2), (float) (randius+Math.sin(Math.PI/180*InitAngle+Math.PI/6+Math.PI/3*i)*randius/2),mPaintText);
            Bitmap btp = mListBitmap.get(i);
            float imageWidth = randius/3;
            float btpleft = (float) (randius+Math.cos(Math.PI/180*InitAngle+Math.PI/6+Math.PI/3*i)*randius/2 - imageWidth/2);
            float btpright = btpleft +imageWidth;
            float btpTop = (float) (randius+Math.sin(Math.PI/180*InitAngle+Math.PI/6+Math.PI/3*i)*randius/2 -imageWidth/2);
            float btpBottom = btpTop+imageWidth;
            RectF rectF = new RectF(btpleft,btpTop,btpright,btpBottom);
            canvas.drawBitmap(btp,null,rectF,null);
        }
        mRectCircle = new RectF(centerX - mBitmapCircle.getWidth()/2,centerY - mBitmapCircle.getHeight()/2,
                centerX + mBitmapCircle.getWidth()/2,centerY + mBitmapCircle.getHeight()/2
        );
        canvas.drawBitmap(mBitmapCircle,null,mRectCircle,null);



    }

    public void startRotate() {

        int lap = (int) (Math.random()*12 +4);
//        InitAngle = 0;
        ValueAnimator animator = ValueAnimator.ofInt(InitAngle,360*lap);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(lap*500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               int updateValue = (int)animation.getAnimatedValue();
//                Log.d(TAG,"updateValue"+updateValue);
                InitAngle = updateValue;
//                InitAngle = (updateValue % 360 + 360) % 360;
                ViewCompat.postInvalidateOnAnimation(LuckyPan.this);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                InitAngle = 0;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();
    }
    ValueAnimator animator;
    int mSelectedPostion;
    public void startRotatePosition(final int position) {
        if(animator != null && animator.isRunning()){
            return;
        }
        int lap;
        if(position == 3) {
            lap = (int) (Math.random() * 12 + 4);
            animator = ValueAnimator.ofInt(InitAngle, 360 * lap + 60 * position);
            mSelectedPostion = position;
        }else{
            lap = (int) (Math.random() * 12 + 4);
            Random random = new Random();
            int realposition = random.nextInt(6);
            animator = ValueAnimator.ofInt(InitAngle, 360 * lap + 60 * realposition);
            mSelectedPostion = realposition;
        }

        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(lap*500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int updateValue = (int)animation.getAnimatedValue();
                InitAngle = (updateValue % 360 + 360) % 360;
                ViewCompat.postInvalidateOnAnimation(LuckyPan.this);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

//                Toast.makeText(mContext,strs[mSelectedPostion],Toast.LENGTH_SHORT).show();
//                InitAngle = 0;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }



}
