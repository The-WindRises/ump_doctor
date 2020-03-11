package it.swiftelink.com.vcs_doctor.videoChat.common;

import com.tencent.trtc.TRTCCloudDef;

public class TrtcConfig {

    //视频会议小画面
    private int videoResolution = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_640_360;
    //竖屏
    private int videoResolutionMode = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_MODE_PORTRAIT;
    //推荐帧率
    private int videoFps = 15;
    //推荐码率
    private int videoBitrate = 600;
    //流畅优先
    private int qosPreference = TRTCCloudDef.TRTC_VIDEO_QOS_PREFERENCE_CLEAR;

    private int videoRenderMode = TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL;

    public int getVideoResolution() {
        return videoResolution;
    }

    public int getVideoRenderMode() {
        return videoRenderMode;
    }

    public void setVideoResolution(int videoResolution) {
        this.videoResolution = videoResolution;
    }

    public int getVideoResolutionMode() {
        return videoResolutionMode;
    }

    public void setVideoResolutionMode(int videoResolutionMode) {
        this.videoResolutionMode = videoResolutionMode;
    }

    public int getVideoFps() {
        return videoFps;
    }

    public void setVideoFps(int videoFps) {
        this.videoFps = videoFps;
    }

    public int getVideoBitrate() {
        return videoBitrate;
    }

    public void setVideoBitrate(int videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public int getQosPreference() {
        return qosPreference;
    }

    public void setQosPreference(int qosPreference) {
        this.qosPreference = qosPreference;
    }
}
