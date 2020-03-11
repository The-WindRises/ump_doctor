package it.swiftelink.com.vcs_doctor.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {
    public boolean isCanScroll=false;
    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll=isCanScroll;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!isCanScroll){
            return false;
        }
        return super.onTouchEvent(ev);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(!isCanScroll){
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }
}
