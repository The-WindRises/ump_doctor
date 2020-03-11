package it.swiftelink.com.vcs_doctor.videoChat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tencent.rtmp.ui.TXCloudVideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import it.swiftelink.com.vcs_doctor.R;

public class TXVideoView extends FrameLayout {


    public TXCloudVideoView videoView;

    View headView;


    ImageView headIV;

    private Context mContext;

    public TXVideoView(@NonNull Context context) {
        super(context);

        this.mContext = context;
        initView();
    }

    public TXVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public TXVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.video_view, this);
        videoView = findViewById(R.id.cloudVideoView);
        headView = findViewById(R.id.headView);
        headIV = findViewById(R.id.headIV);

    }


    public void setUserId(String userId) {

        videoView.setUserId(userId);
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != VISIBLE) {
            headView.setVisibility(GONE);
            headIV.setImageDrawable(null);
        }
    }

    public String getUserId() {
        return videoView.getUserId();
    }


    public void updateVideoStatus( boolean hasVideo) {
        if (hasVideo) {
            videoView.setVisibility(VISIBLE);
            headView.setVisibility(GONE);
        } else {
            videoView.setVisibility(GONE);
            headView.setVisibility(VISIBLE);
        }
    }


}
