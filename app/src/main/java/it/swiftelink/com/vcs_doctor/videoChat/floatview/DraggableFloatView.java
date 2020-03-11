package it.swiftelink.com.vcs_doctor.videoChat.floatview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import it.swiftelink.com.vcs_doctor.R;


/**
 * Created by chentao on 2018/1/14.
 * 浮动按钮视图
 */

@SuppressLint("ViewConstructor")
public class DraggableFloatView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = DraggableFloatView.class.getSimpleName();

    private Context mContext;
    private View mTouchBt;

    private OnFlingListener mOnFlingListener;
    private OnTouchButtonClickListener mTouchButtonClickListener;
    private final float MINIMUM_DISTANCE = 4.0f;// 手指移动的最小距离
    private int distance;// 根据屏幕密度计算出来的，手指移动的最小距离
    private final FrameLayout cloudVideoViewContainer;


    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public DraggableFloatView(Context context, OnFlingListener flingListener) {
        super(context);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_floatview_freeposition, this);
        mTouchBt = findViewById(R.id.touchBt);
        cloudVideoViewContainer = findViewById(R.id.cloudVideoViewContainer);
        mOnFlingListener = flingListener;
        distance = dip2px(MINIMUM_DISTANCE);
        distance = ViewConfiguration.get(context).getScaledPagingTouchSlop();
        mTouchBt.setOnTouchListener(new OnTouchListener() {

            //刚按下是起始位置的坐标
            float startDownX, startDownY;
            float downX, downY;
            float moveX, moveY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "ACTION_DOWN");
                        startDownX = downX = motionEvent.getRawX();
                        startDownY = downY = motionEvent.getRawY();
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "ACTION_MOVE");
                        moveX = motionEvent.getRawX();
                        moveY = motionEvent.getRawY();
                        if (mOnFlingListener != null)
                            mOnFlingListener.onMove(moveX - downX, moveY - downY);
                        downX = moveX;
                        downY = moveY;
                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "ACTION_UP");
                        float upX = motionEvent.getRawX();
                        float upY = motionEvent.getRawY();
                        double uX = Math.abs(upX - startDownX);
                        double uY = Math.abs(upY - startDownY);
                        double uL = Math.sqrt(uX * uX + uY * uY);
                        if (uL < distance) {
                            Log.e("Video", "移动距离" + uL);

                            return false;
                        } else {
                            return true;
                        }
                }
                return true;
            }
        });
        mTouchBt.setOnClickListener(this);
    }

    public void setTouchButtonClickListener(OnTouchButtonClickListener touchButtonClickListener) {
        mTouchButtonClickListener = touchButtonClickListener;
    }

    public void addView(View view) {
        if (cloudVideoViewContainer != null) {

            if (view.getParent() != null) {
                ViewGroup vp = (ViewGroup) view.getParent();
                vp.removeView(view);
            }

            cloudVideoViewContainer.addView(view);
        }
    }

    @Override
    public void onClick(View v) {

        if (mTouchButtonClickListener != null) {
            mTouchButtonClickListener.onClick(v);
        }
    }

    public interface OnTouchButtonClickListener {
        void onClick(View view);
    }
}
