package com.weboniselab.meghana.android.app.imagezoomexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    /*ImageView mImageView;
    ScaleGestureDetector mScaleGestureDetector;
    android.graphics.Matrix mMatrix=new android.graphics.Matrix();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView=(ImageView) findViewById(R.id.imageView);
        mScaleGestureDetector = new ScaleGestureDetector(this,new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleGestureDetector.onTouchEvent(ev);
        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.
            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            mMatrix.setScale(scaleFactor, scaleFactor);
            mImageView.setImageMatrix(mMatrix);
            return true;
        }
    }*/

    ImageView mImageView;
    TextView mTextView;
    Bitmap bitmap;
    int bmpWidth, bmpHeight;
    int touchState;
    final int IDLE = 0;
    final int TOUCH = 1;
    final int PINCH = 2;
    float dist0, distCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView=(ImageView) findViewById(R.id.imageView);
        mTextView = (TextView)findViewById(R.id.touchevent);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bmpWidth = bitmap.getWidth();
        bmpHeight = bitmap.getHeight();
        distCurrent = 1;
        dist0 = 1;
        drawMatrix();
        mImageView.setOnTouchListener(MyOnTouchListener);
        touchState = IDLE;

    }

    private void drawMatrix(){
        float curScale = distCurrent/dist0;
        if (curScale < 0.1){
            curScale = 0.1f;
        }


        Bitmap resizedBitmap;
        int newHeight = (int) (bmpHeight * curScale);
        int newWidth = (int) (bmpWidth * curScale);
        resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
        mImageView.setImageBitmap(resizedBitmap);
    }

    View.OnTouchListener MyOnTouchListener
            = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            float distx, disty;

            switch(event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    mTextView.setText("ACTION_DOWN");
                    touchState = TOUCH;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    mTextView.setText("ACTION_POINTER_DOWN");
                    touchState = PINCH;
                    distx = event.getX(0) - event.getX(1);
                    disty = event.getY(0) - event.getY(1);
                    dist0 = (float)Math.sqrt(distx * distx + disty * disty);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mTextView.setText("ACTION_MOVE");
                    if(touchState == PINCH){
                        distx = event.getX(0) - event.getX(1);
                        disty = event.getY(0) - event.getY(1);
                        distCurrent = (float)Math.sqrt(distx * distx + disty * disty);
                        drawMatrix();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mTextView.setText("ACTION_UP");
                    touchState = IDLE;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    mTextView.setText("ACTION_POINTER_UP");
                    touchState = TOUCH;
                    break;
            }

            return true;
        }

    };
}
