package it.swiftelink.com.vcs_doctor.videoChat.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.tencent.liteav.TXLiteAVCode;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;


import java.util.ArrayList;

import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.videoChat.floatview.DraggableFloatWindow;
import it.swiftelink.com.vcs_doctor.videoChat.view.TRTCVideoViewLayout;
import it.swiftelink.com.vcs_doctor.videoChat.view.TXVideoView;

//房间信息的管理
public class TrtcRoomManager {
    private static final String TAG = "TrtcRoomManager";
    private final static TrtcRoomManager confereesManager = new TrtcRoomManager();

    private TrtcRoomManager() {
    }

    private RoomListener roomListener;
    private TRTCCloudDef.TRTCParams trtcParams; /// TRTC SDK 视频通话房间进入所必须的参数
    private TRTCCloud trtcCloud;                /// TRTC SDK 实例对象
    private TrtcConfig trtcConfig = new TrtcConfig();
    private TRTCCloudListener trtcListener;
    private TRTCVideoViewLayout mVideoViewLayout;
    private DraggableFloatWindow mFloatWindow;
    private boolean bAudioEnable = true, bCameraEnable = true;
    private boolean bIsFrontCamera = true; //是否是前置摄像头
    private Context application;
    private long startTime;
    public String mConnectUserId;
    public void setRoomListener(RoomListener roomListener) {
        this.roomListener = roomListener;
    }


    public static TrtcRoomManager getInstance() {
        return confereesManager;
    }

    //初始化trtc
    private void initTrtc(Context context, int sdkAppId, int roomId, String userId, String userSig, long startTime) {
        //获取前一个页面得到的进房参数
        application = context.getApplicationContext();
        this.startTime = startTime;
        trtcParams = new TRTCCloudDef.TRTCParams(sdkAppId, userId, userSig, roomId, "", "");
        //获取 TRTC SDK 单例
        trtcListener = new TRTCCloudListenerImpl();
        trtcCloud = TRTCCloud.sharedInstance(context);
        trtcCloud.setListener(trtcListener);
        mVideoViewLayout = new TRTCVideoViewLayout(application, R.layout.room_show_view);
        mVideoViewLayout.setUserId(trtcParams.userId);
    }


    /**
     * 点击切换前后置摄像头
     */
    public void onSwitchCamera() {
        if (trtcCloud != null) {
            bIsFrontCamera = !bIsFrontCamera;
            trtcCloud.switchCamera();
        }
    }

    public TRTCCloud getTrtcCloud() {
        return trtcCloud;
    }

    public void sendCmdMsg(byte[] data) {
        int type = 0x1;
        trtcCloud.sendCustomCmdMsg(type, data, false, false);
    }

    /**
     * 设置视频通话的视频参数：需要 TRTCSettingDialog 提供的分辨率、帧率和流畅模式等参数
     */
    private void setTRTCCloudParam() {
        // 大画面的编码器参数设置
        // 设置视频编码参数，包括分辨率、帧率、码率等等，这些编码参数来自于 TRTCSettingDialog 的设置
        // 注意（1）：不要在码率很低的情况下设置很高的分辨率，会出现较大的马赛克
        // 注意（2）：不要设置超过25FPS以上的帧率，因为电影才使用24FPS，我们一般推荐15FPS，这样能将更多的码率分配给画质

        TRTCCloudDef.TRTCVideoEncParam encParam = new TRTCCloudDef.TRTCVideoEncParam();
        encParam.videoResolution = trtcConfig.getVideoResolution();
        encParam.videoFps = trtcConfig.getVideoFps();
        encParam.videoBitrate = trtcConfig.getVideoBitrate();
        encParam.videoResolutionMode = trtcConfig.getVideoResolutionMode();

        trtcCloud.setVideoEncoderParam(encParam);
        TRTCCloudDef.TRTCNetworkQosParam qosParam = new TRTCCloudDef.TRTCNetworkQosParam();
        qosParam.preference = trtcConfig.getQosPreference();
        trtcCloud.setNetworkQosParam(qosParam);
        trtcCloud.muteLocalAudio(true);
        trtcCloud.muteLocalAudio(false);
        trtcCloud.setAudioRoute(TRTCCloudDef.TRTC_AUDIO_ROUTE_SPEAKER);
        //设置默认滤镜
        trtcCloud.setBeautyStyle(TRTCCloudDef.TRTC_BEAUTY_STYLE_SMOOTH, 0, 0, 0);
    }


    @SuppressLint("HandlerLeak")
    public void prepareRoom(Context context, int sdkAppId, int roomId, String userId, String userSig, final long startTime) {



        if (TextUtils.isEmpty(userId)) {
            userId = App.getSPUtils().getString("userId");
        }
        initTrtc(context, sdkAppId, roomId, userId, userSig, startTime);
        // 预览前配置默认参数
        setTRTCCloudParam();
        enterRoom();
    }


    public void enterRoom() {
        // 开启视频采集预览
        if (mVideoViewLayout != null) {
            TXVideoView localVideoView = mVideoViewLayout.getTXVideoViewByUseId(trtcParams.userId);
            if (localVideoView != null) {
                localVideoView.setVisibility(View.VISIBLE);
                trtcCloud.stopLocalPreview();
                trtcCloud.setLocalViewFillMode(trtcConfig.getVideoRenderMode());
                trtcCloud.startLocalPreview(bIsFrontCamera, localVideoView.videoView);
                //进房
                trtcCloud.startLocalAudio();
                trtcCloud.enterRoom(trtcParams, TRTCCloudDef.TRTC_APP_SCENE_VIDEOCALL);
            }
//            if (!TextUtils.isEmpty(mConnectUserId)) {
//                trtcCloud.stopAllRemoteView();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mVideoViewLayout.setRemoteUserId(mConnectUserId);
//                        TXCloudVideoView videoViewRenderView = mVideoViewLayout.getCloudVideoViewByUseId(mConnectUserId);
//                        if (videoViewRenderView != null) {
//                            trtcCloud.setRemoteViewFillMode(mConnectUserId, TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL);
//                            trtcCloud.startRemoteView(mConnectUserId, videoViewRenderView);
//                        }
//                    }
//                }, 100);
//            }
        }

    }


    public TRTCVideoViewLayout getVideoViewLayout() {
        return mVideoViewLayout;
    }

    public void exitRoom() {
        if (trtcCloud != null) {
            trtcCloud.exitRoom();
        }
    }

    public void startLocalAudio() {
        if (trtcCloud != null) {
            trtcCloud.startLocalAudio();
        }
    }


    public boolean isAudioEnable() {
        return bAudioEnable;
    }

    public void setAudioEnable(boolean bAudioEnable) {
        this.bAudioEnable = bAudioEnable;
    }

    public boolean isCameraEnable() {
        return bCameraEnable;
    }

    public void setCameraEnable(boolean bCameraEnable) {
        this.bCameraEnable = bCameraEnable;
    }

    public void updateVideoStatus() {
        if (trtcCloud != null) {
            mVideoViewLayout.updateVideoStatus(trtcParams.userId, bCameraEnable);
        }
    }

    public void muteCamera() {
        if (trtcCloud != null) {
            if (!bCameraEnable) {
                TXCloudVideoView localVideoView = mVideoViewLayout.getCloudVideoViewByUseId(trtcParams.userId);
                trtcCloud.startLocalPreview(bIsFrontCamera, localVideoView);
            } else {
                trtcCloud.stopLocalPreview();
            }
            trtcCloud.muteLocalVideo(bCameraEnable);
            bCameraEnable = !bCameraEnable;
        }
    }

    public void stopLocalPreview() {
        if (trtcCloud != null) {
            trtcCloud.stopLocalPreview();
        }
    }

    public void startLocalPreview() {
        if (trtcCloud != null) {
            TXCloudVideoView localVideoView = mVideoViewLayout.getCloudVideoViewByUseId(trtcParams.userId);
            trtcCloud.startLocalPreview(bIsFrontCamera, localVideoView);
        }
    }

    public void muteLocalAudio() {
        if (trtcCloud != null) {
            trtcCloud.muteLocalAudio(bAudioEnable);
            bAudioEnable = !bAudioEnable;
        }
    }

    public TRTCVideoViewLayout getmVideoViewLayout() {
        return mVideoViewLayout;
    }

    //当主动退出房间
    private void onExitRoom() {



        if (trtcCloud != null) {
            Log.e("Video", "关闭本地视频音频等");
            trtcCloud.stopLocalPreview();
            trtcCloud.stopLocalAudio();
            trtcCloud.setAudioRoute(TRTCCloudDef.TRTC_AUDIO_ROUTE_EARPIECE);
            trtcCloud.muteLocalAudio(true);
            trtcCloud.muteLocalVideo(true);
            //取消SDK回调
            trtcCloud.setListener(null);
        }
        TRTCCloud.destroySharedInstance();


        if (mVideoViewLayout != null) {
            mVideoViewLayout.removeAllViews();
            mVideoViewLayout = null;
        }

        trtcParams = null;
        mFloatWindow = null;
        trtcListener = null;
        roomListener = null;
        bAudioEnable = true;
        bCameraEnable = true;
        bIsFrontCamera = true;
        trtcCloud = null;

    }


    /**
     * SDK内部状态回调
     */
    class TRTCCloudListenerImpl extends TRTCCloudListener {

        public TRTCCloudListenerImpl() {
            super();
        }

        /**
         * 加入房间
         */
        @Override
        public void onEnterRoom(long elapsed) {

//            App.showToast("加入房间成功发" + "耗时" + elapsed);
        }


        /**
         * 离开房间
         */
        @Override
        public void onExitRoom(int reason) {
            if (roomListener != null) {
                roomListener.onExitRoom();
            }
            TrtcRoomManager.getInstance().onExitRoom();

        }

        /**
         * ERROR 大多是不可恢复的错误，需要通过 UI 提示用户
         */
        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            Log.e("TRTCCloudImpl: ", "onError    errCode------" + errCode + "-----errMsg-----" + errMsg);
//            if (trtcCloud == null) {
//                return;
//            }
//            if (errCode == TXLiteAVCode.ERR_CAMERA_START_FAIL || errCode == TXLiteAVCode.ERR_CAMERA_NOT_AUTHORIZED || errCode == TXLiteAVCode.ERR_CAMERA_OCCUPY) {
//                trtcCloud.stopLocalPreview();
//                trtcCloud.muteLocalVideo(true);
//                setCameraEnable(false);
//                if (roomListener != null) {
//                    roomListener.onCameraChange(false);
//                }
//            }
//
//            if (errCode == TXLiteAVCode.ERR_MIC_START_FAIL || errCode == TXLiteAVCode.ERR_MIC_NOT_AUTHORIZED || errCode == TXLiteAVCode.ERR_MIC_OCCUPY) {
//
//                trtcCloud.stopLocalAudio();
//                trtcCloud.muteLocalAudio(true);
//                setAudioEnable(false);
//                if (roomListener != null) {
//                    roomListener.onAudioChange(false);
//                }
//            }
//            if (errCode == TXLiteAVCode.ERR_ROOM_ENTER_FAIL) {
//                exitRoom();
//            }
        }

        /**
         * WARNING 大多是一些可以忽略的事件通知，SDK内部会启动一定的补救机制
         */
        @Override
        public void onWarning(int warningCode, String warningMsg, Bundle extraInfo) {

            Log.e("Video", "发生警告:" + warningMsg + ":" + warningMsg);
        }


        /**
         * 有新的用户加入了当前视频房间
         */
        @Override
        public void onUserEnter(String userId) {

            if (trtcCloud == null) {
                Log.i(TAG, "onUserEnter: trtcCloud == null");
                return;
            }
            roomListener.onUserEnter(userId);
            mConnectUserId = userId;
            Log.i(TAG, "onUserEnter: userId" + userId);
            mVideoViewLayout.setRemoteUserId(userId);
            TXCloudVideoView videoViewRenderView = mVideoViewLayout.getCloudVideoViewByUseId(userId);
            if (videoViewRenderView != null) {
                trtcCloud.stopAllRemoteView();
                trtcCloud.setRemoteViewFillMode(userId, TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL);
                trtcCloud.startRemoteView(userId, videoViewRenderView);
            }

        }


        /**
         * 有用户离开了当前视频房间
         */
        @Override
        public void onUserExit(String userId, int reason) {
            if (trtcCloud == null) {
                return;
            }
            trtcCloud.stopRemoteView(userId);
            mVideoViewLayout.onMemberLeave(userId);

            if (roomListener != null) {
                roomListener.onUserEixt();
            }
        }

        /**
         * 有用户屏蔽了画面
         */
        @Override
        public void onUserVideoAvailable(final String userId, boolean available) {
            if (trtcCloud == null) {
                return;
            }
            if (available) {
                final TXCloudVideoView renderView = mVideoViewLayout.getCloudVideoViewByUseId(userId);
                if (renderView != null) {
                    trtcCloud.startRemoteView(userId, renderView);
                }
            } else {
                trtcCloud.stopRemoteView(userId);
            }
            mVideoViewLayout.updateVideoStatus(userId, available);
        }

        /**
         * 有用户屏蔽了声音
         */
        @Override
        public void onUserAudioAvailable(String userId, boolean available) {

        }

        public void onUserSubStreamAvailable(final String userId, boolean available) {
            Log.e("Video", "用户子画面");
        }

        @Override
        public void onCameraDidReady() {
            Log.e("Video", "摄像头准备就绪");
            //  TRTCMainActivity activity = mContext.get();
            //  activity.setCameraStatus(true);
        }

        public void onMicDidReady() {
            Log.e("Video", "麦克风准备就绪");
            // TRTCMainActivity activity = mContext.get();
            // activity.setAudioStatus(true);
        }

        @Override
        public void onRecvCustomCmdMsg(String userId, int cmdID, int seq, byte[] message) {
            roomListener.received(cmdID, message);
        }

        @Override
        public void onNetworkQuality(TRTCCloudDef.TRTCQuality trtcQuality, ArrayList<TRTCCloudDef.TRTCQuality> arrayList) {
            super.onNetworkQuality(trtcQuality, arrayList);
            if (arrayList != null && arrayList.size() > 0) {
                roomListener.onNetworkQuality(trtcQuality, arrayList.get(0));
            }
        }
    }

    public interface RoomListener {
        void onTimeChange(String time);

        void onCameraChange(boolean isOpen);

        void onAudioChange(boolean isOpen);

        void onExitRoom();

        void onUserEixt();

        void received(int cmdID, byte[] message);

        void onUserEnter(String userId);

        void onNetworkQuality(TRTCCloudDef.TRTCQuality localQuality, TRTCCloudDef.TRTCQuality remoteQuality);

        void onUserVideoAvailable(String userid);
    }
}
