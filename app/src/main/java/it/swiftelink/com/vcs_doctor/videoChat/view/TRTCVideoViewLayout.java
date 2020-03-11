package it.swiftelink.com.vcs_doctor.videoChat.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;

import it.swiftelink.com.vcs_doctor.R;

/**
 * Module:   TRTCVideoViewLayout
 * <p>
 * Function: 用于计算每个视频画面的位置排布和大小尺寸
 */
public class TRTCVideoViewLayout extends RelativeLayout {
    private final static String TAG = TRTCVideoViewLayout.class.getSimpleName();
    private Context mContext;
    private ArrayList<TXVideoView> mVideoViewList;

    private FrameLayout rl_remote;
    private FrameLayout rl_local;


    public TRTCVideoViewLayout(Context context,int layoutRes) {
        this(context,null,layoutRes);

    }


    public TRTCVideoViewLayout(Context context, AttributeSet attrs,int layoutRes) {
        this(context, attrs,-1,layoutRes);

    }

    public TRTCVideoViewLayout(Context context, AttributeSet attrs, int defStyleAttr,int layoutRes) {
        super(context, attrs, defStyleAttr);
        initView(context,layoutRes);
    }


    public void setUserId(String userId) {

        if (TextUtils.isEmpty(userId)) {
            return;
        }
        for (int i = 0; i < mVideoViewList.size(); i++) {
            TXVideoView renderView = mVideoViewList.get(i);
            if (renderView != null) {
                String vUserId = renderView.getUserId();
                if (userId.equalsIgnoreCase(vUserId)) {
                    return;
                }
            }
        }

        mVideoViewList.get(0).setUserId(userId);//把第一个VideoView设置为自己
    }

    public void setRemoteUserId(String remoteUserId) {

        if (TextUtils.isEmpty(remoteUserId)) {
            return;
        }
        for (int i = 0; i < mVideoViewList.size(); i++) {
            TXVideoView renderView = mVideoViewList.get(i);
            if (renderView != null) {
                String vUserId = renderView.getUserId();
                if (remoteUserId.equalsIgnoreCase(vUserId)) {
                    return;
                }
            }
        }
        mVideoViewList.get(1).setUserId(remoteUserId);

    }


    private void initView(Context context ,int layoutRes) {
        mContext = context;

        View view = LayoutInflater.from(context).inflate(layoutRes, this);
        rl_remote = view.findViewById(R.id.rl_remote);
        rl_local = view.findViewById(R.id.rl_local);


        if(mVideoViewList==null){
            mVideoViewList = new ArrayList<>();
            mVideoViewList.add(new TXVideoView(mContext));
            mVideoViewList.add(new TXVideoView(mContext));
        }

        showView();
    }


    private void showView() {
        rl_local.removeAllViews();
        rl_remote.removeAllViews();
        rl_local.addView(mVideoViewList.get(0) );
        rl_remote.addView(mVideoViewList.get(1));
    }


    public TXCloudVideoView getCloudVideoViewByUseId(String userId) {
        TXVideoView videoView = getTXVideoViewByUseId(userId);
        if (videoView != null) {
            return videoView.videoView;
        }
        return null;
    }


    public TXVideoView getTXVideoViewByUseId(String userId) {
        if (mVideoViewList != null && mVideoViewList.size() > 0) {
            for (TXVideoView videoView : mVideoViewList) {
                String tempUserID = videoView.getUserId();
                if (tempUserID != null && tempUserID.equalsIgnoreCase(userId)) {
                    return videoView;
                }
            }
        }

        return null;
    }


    public void onMemberLeave(String userId) {
        Log.e(TAG, "onMemberLeave: userId = " + userId);
        for (int i = 0; i < mVideoViewList.size(); i++) {
            TXVideoView renderView = mVideoViewList.get(i);
            if (renderView != null && null != renderView.getUserId()) {
                if (renderView.getUserId().equals(userId)) {
                    renderView.setUserId(null);
                    renderView.setVisibility(View.GONE);
                }
            }
        }


    }

    public void updateVideoStatus(String userID, boolean bHasVideo) {
        TXVideoView txVideoView = getTXVideoViewByUseId(userID);

        if (txVideoView != null) {

            txVideoView.updateVideoStatus(bHasVideo);
        }
    }
}
