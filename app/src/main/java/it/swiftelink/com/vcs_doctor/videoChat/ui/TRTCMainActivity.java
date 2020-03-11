package it.swiftelink.com.vcs_doctor.videoChat.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloudDef;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;
import net.qiujuer.genius.ui.widget.Loading;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.widget.dialog.ConfirmDialog;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.presenter.videochat.TRTCMainContract;
import it.swiftelink.com.factory.presenter.videochat.TRTCMainPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.CloseMessageEvent;
import it.swiftelink.com.vcs_doctor.event.CommentEvent;
import it.swiftelink.com.vcs_doctor.util.LogUpload;
import it.swiftelink.com.vcs_doctor.util.RxKeyboardTool;
import it.swiftelink.com.vcs_doctor.videoChat.common.TrtcRoomManager;
import it.swiftelink.com.vcs_doctor.videoChat.view.TRTCVideoViewLayout;

import static com.tencent.trtc.TRTCCloudDef.TRTC_QUALITY_Bad;
import static com.tencent.trtc.TRTCCloudDef.TRTC_QUALITY_Down;
import static com.tencent.trtc.TRTCCloudDef.TRTC_QUALITY_Excellent;
import static com.tencent.trtc.TRTCCloudDef.TRTC_QUALITY_Good;
import static com.tencent.trtc.TRTCCloudDef.TRTC_QUALITY_Poor;
import static com.tencent.trtc.TRTCCloudDef.TRTC_QUALITY_Vbad;

public class TRTCMainActivity extends
        BasePresenterActivity<TRTCMainContract.Presenter>
        implements TRTCMainContract.View {
    @BindView(R.id.timeTV)
    TextView timeTV;
    @BindView(R.id.cloudVideoViewContainer)
    FrameLayout cloudVideoViewContainer;
    @BindView(R.id.audioIV)
    ImageView audioIV;
    @BindView(R.id.loading_wait)
    Loading loadingWait;
    @BindView(R.id.ll_wait_enter)
    LinearLayout llWaitEnter;
    @BindView(R.id.networkStateTV)
    TextView networkStateTV;
    private int sdkAppId;
    private String doctorId;
    private String patientId;
    private String patientName;
    private String patientHeadImg;
    private String doctorName;
    private String doctorHeadImg;
    private String userId;
    private String conferees;
    private String userSig;
    private int roomId;
    private long startTime;
    private int patientScore = 5;
    private TXCloudVideoView cvvFloatRemote;//浮窗上的远程view
    private TXCloudVideoView cvvFloatLocal; //浮窗上的本地view
    private TXCloudVideoView videoViewRenderView; //获取的 主视频页面的远程view
    private TXCloudVideoView localTXCloudVideoView;//获取的 主视频页面的本地view
    private String mConnectUserId;
    List<String> tags = new ArrayList<>();
    private Handler timeHandler = null;
    CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            countDownTimer.cancel();
            LogUpload.upload(false);
            ConfirmDialog.newInstance(getString(R.string.no_enter),
                    getString(R.string.no), getString(R.string.yes), false)
                    .setConfirmSelect(new ConfirmDialog.ConfirmSelect() {
                        @Override
                        public void onClickCancel() {

                            countDownTimer.start();
                        }

                        @Override
                        public void onClickQuery() {
                            restartApplication();
                        }
                    }).setMargin(50)
                    .setOutCancel(false)
                    .show(getSupportFragmentManager());
        }
    };
    //===============================聊天页面View====================================================
    @BindView(R.id.chat_layout)
    ChatLayout chatLayout;
    private TitleBarLayout mTitleBar;
    private MessageLayout messageLayout;
    private InputLayout inputLayout;
    @BindView(R.id.chartLaytou)
    LinearLayout chartLaytou;
    @BindView(R.id.voidoLayout)
    RelativeLayout voidoLayout;
    View videoCloseView;

    private int Type = 1; //1为视频界面 2为图文界面

    @Override
    protected int getLayoutId() {
        return R.layout.vc_activity;
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void initWindows() {
        super.initWindows();
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //没有标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStatusBarFullTransparent();
    }

    private String caulateTime(int time) {
        if (time < 10) {
            return "0:0" + time;
        } else if (time < 60) {
            return "0:" + time;
        } else {
            String timeS = "";
            int minuts = time / 60;
            if (minuts > 60) {
                int hours = minuts / 60;
                timeS = hours + ":" + minuts % 60 + ":";
            } else {
                timeS = minuts + ":";
            }
            int second = time % 60;
            if (second < 10) {
                timeS += "0" + second;
            } else {
                timeS += second;
            }
            return timeS;
        }
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        userId = App.getSPUtils().getString("userId");
        userSig = App.getSPUtils().getString("userSig");
        patientId = App.getSPUtils().getString("patientId");
        patientName = App.getSPUtils().getString("patientName");
        sdkAppId = App.getSPUtils().getInt("sdkAppId");
        patientHeadImg = App.getSPUtils().getString("patientHeadImg");
        doctorName = App.getSPUtils().getString("doctorName");
        doctorHeadImg = App.getSPUtils().getString("doctorHeadImg");
        roomId = App.getSPUtils().getInt("roomId");
        startTime = App.getSPUtils().getLong("startTime");
        TrtcRoomManager.getInstance().prepareRoom(this, sdkAppId, roomId, userId, userSig, startTime);
        setAudioStatus(TrtcRoomManager.getInstance().isAudioEnable());
        TRTCVideoViewLayout view = TrtcRoomManager.getInstance().getVideoViewLayout();
        videoCloseView = LayoutInflater.from(this).inflate(R.layout.video_close_layout, null);
        if (view.getParent() != null) {
            ViewGroup vp = (ViewGroup) view.getParent();
            vp.removeView(view);
        }
        cloudVideoViewContainer.addView(view);
        if (App.getSPUtils().getBoolean("imIsLogin")) {
            sendMesg();
        }
        timeHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                long current = System.currentTimeMillis();
                int time = (int) ((current - startTime) / 1000);
                String showTime = caulateTime(time);
                timeTV.setText(showTime);
                sendEmptyMessageDelayed(1, 1000);

            }
        };

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingWait.start();
        llWaitEnter.setVisibility(View.VISIBLE);
        countDownTimer.start();
        TrtcRoomManager.getInstance().setRoomListener(new TrtcRoomManager.RoomListener() {
            @Override
            public void onTimeChange(String time) {

            }

            @Override
            public void onCameraChange(boolean isOpen) {
            }

            @Override
            public void onAudioChange(boolean isOpen) {
                setAudioStatus(isOpen);
            }

            @Override
            public void received(int cmdID, byte[] message) {

            }

            @Override
            public void onUserEnter(String userId) {
                countDownTimer.cancel();
                if (timeHandler != null)
                    timeHandler.sendEmptyMessageDelayed(1, 1000);
                llWaitEnter.setVisibility(View.GONE);
            }

            @Override
            public void onNetworkQuality(TRTCCloudDef.TRTCQuality localQuality, TRTCCloudDef.TRTCQuality remoteQuality) {
                if (localQuality.quality == TRTC_QUALITY_Bad || localQuality.quality == TRTC_QUALITY_Vbad || localQuality.quality == TRTC_QUALITY_Down) {
                    //当前网络差
                    networkStateTV.setVisibility(View.VISIBLE);
                    networkStateTV.setText(R.string.msg_network_is_poor);
                }
                if (localQuality.quality == TRTC_QUALITY_Poor || localQuality.quality == TRTC_QUALITY_Good || localQuality.quality == TRTC_QUALITY_Excellent) {
                    //当前网络恢复可用
                    networkStateTV.setVisibility(View.GONE);
                }
                if (remoteQuality.quality == TRTC_QUALITY_Bad || remoteQuality.quality == TRTC_QUALITY_Vbad || remoteQuality.quality == TRTC_QUALITY_Down) {
                    //对方网络差
                    networkStateTV.setVisibility(View.VISIBLE);
                    networkStateTV.setText(R.string.msg_opposite_network_poor);
                }
                if (remoteQuality.quality == TRTC_QUALITY_Poor || remoteQuality.quality == TRTC_QUALITY_Good || remoteQuality.quality == TRTC_QUALITY_Excellent) {
                    //对方网恢复可用
                    networkStateTV.setVisibility(View.GONE);
                }
            }

            @Override
            public void onUserVideoAvailable(String userid) {
//                TRTCVideoViewLayout mVideoViewLayout = TrtcRoomManager.getInstance().getmVideoViewLayout();
//                TXVideoView txVideoView = mVideoViewLayout.getTXVideoViewByUseId(userid);
//                txVideoView.removeAllViews();
//                txVideoView.addView(videoCloseView);
            }

            @Override
            public void onExitRoom() {
                out();
                TrtcRoomManager.getInstance().exitRoom();
                App.getSPUtils().remove("isSendsynopsis");
                if (timeHandler != null) {
                    timeHandler.removeCallbacksAndMessages(null);
                    timeHandler = null;
                }
            }

            @Override
            public void onUserEixt() {
                Run.onUiSync(new Action() {
                    @Override
                    public void call() {
                        if (mPresenter != null) {
                            mPresenter.endTRTC(App.getSPUtils().getString("diagnosisId"), true);
                        }
                    }
                }, 2000, true);
                EventBus.getDefault().postSticky(new CommentEvent());
                EventBus.getDefault().postSticky(new CloseMessageEvent());
                App.getSPUtils().remove("isSendsynopsis");
                out();

                if (timeHandler != null) {
                    timeHandler.removeCallbacksAndMessages(null);
                    timeHandler = null;
                }
            }
        });
    }

    @Override
    protected TRTCMainContract.Presenter initPresenter() {
        return new TRTCMainPresenter(this);
    }

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.out_toptobottom);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 退出视频房间
     */
    private void exitRoom() {
        if (mPresenter != null) {
            mPresenter.endTRTC(App.getSPUtils().getString("diagnosisId"), false);
        }
        TrtcRoomManager.getInstance().exitRoom();
        finish();
        EventBus.getDefault().postSticky(new CommentEvent());
        EventBus.getDefault().postSticky(new CloseMessageEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        TrtcRoomManager.getInstance().exitRoom();
        if (timeHandler != null) {
            timeHandler.removeCallbacksAndMessages(null);
            timeHandler = null;
        }
        super.onDestroy();
    }

    @Override
    protected StateView setStateView() {
        return null;
    }

    /**
     * 点击切换前后置摄像头
     */
    private void onSwitchCamera() {
        TrtcRoomManager.getInstance().onSwitchCamera();
    }

    /**
     * 点击关闭或者打开本地的麦克风采集
     */
    private void onMuteAudio() {

        TrtcRoomManager.getInstance().startLocalAudio();
        TrtcRoomManager.getInstance().muteLocalAudio();
        setAudioStatus(TrtcRoomManager.getInstance().isAudioEnable());
    }

    private void setAudioStatus(boolean isOpen) {
        TrtcRoomManager.getInstance().setAudioEnable(isOpen);
        if (!isFinishing()) {
            if (isOpen) {
                audioIV.setImageResource(R.mipmap.audio_open);
            } else {
                audioIV.setImageResource(R.mipmap.audio_close);
            }
        }
    }

    @OnClick({R.id.cameraIV, R.id.exitIV, R.id.audioIV, R.id.minIV, R.id.tv_to_message_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cameraIV:
                onSwitchCamera();
                break;
            case R.id.exitIV:
                finishInquiry();
                break;
            case R.id.audioIV:
                onMuteAudio();
                break;
            case R.id.minIV:
                showMin();
                break;
            case R.id.tv_to_message_chat:
                showMin();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * 发送消息
     */
    private void sendMesg() {

        String synopsis = App.getSPUtils().getString("Synopsis", "");
        boolean isSendsynopsis = App.getSPUtils().getBoolean("isSendsynopsis", false);
        if (!TextUtils.isEmpty(synopsis) && isSendsynopsis == false) {
            TIMConversation timConversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, App.getSPUtils().getString("patientId"));
            //构造一条消息
            TIMMessage msg = new TIMMessage();
            //构造一条消息
            TIMTextElem elem = new TIMTextElem();
            elem.setText(synopsis);
            //将elem添加到消息
            if (msg.addElement(elem) != 0) {
                return;
            }
            //发送消息
            timConversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                @Override
                public void onError(int code, String desc) {//发送消息失败
                    //错误码 code 和错误描述 desc，可用于定位请求失败原因
                    //错误码 code 含义请参见错误码表
                    Log.d("imLogin", "send message failed. code: " + code + " errmsg: " + desc);
                    App.getSPUtils().put("isSendsynopsis", false);
                }

                @Override
                public void onSuccess(TIMMessage msg) {//发送消息成功
                    App.getSPUtils().put("isSendsynopsis", true);
                    Log.e("imLogin", "SendMsg ok");
                    initChatView();
                }
            });
        }
    }

    /**
     * 显示小窗
     */

    private void showMin() {
        EasyFloat.dismiss(TRTCMainActivity.this);
        mConnectUserId = TrtcRoomManager.getInstance().mConnectUserId;
        videoViewRenderView = TrtcRoomManager.getInstance().getVideoViewLayout().getCloudVideoViewByUseId(mConnectUserId);
        localTXCloudVideoView = TrtcRoomManager.getInstance().getVideoViewLayout().getCloudVideoViewByUseId(userId);
        videoViewRenderView.removeVideoView();
        TrtcRoomManager.getInstance().getTrtcCloud().stopRemoteView(mConnectUserId);
        TrtcRoomManager.getInstance().getTrtcCloud().stopLocalPreview();
        TrtcRoomManager.getInstance().getTrtcCloud().setLocalViewFillMode(TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL);
        EasyFloat.with(TRTCMainActivity.this).setLayout(R.layout.float_voide, new OnInvokeView() {
            @Override
            public void invoke(final View view) {
                cvvFloatRemote = view.findViewById(R.id.cvv_float_remote);
                cvvFloatLocal = view.findViewById(R.id.cvv_float_local);
                TrtcRoomManager.getInstance().getTrtcCloud().setRemoteViewFillMode(mConnectUserId, TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL);
                TrtcRoomManager.getInstance().getTrtcCloud().startRemoteView(mConnectUserId, cvvFloatRemote);
                TrtcRoomManager.getInstance().getTrtcCloud().startLocalPreview(true, cvvFloatLocal);
                view.findViewById(R.id.cvv_float_remote).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //处理浮窗事件
                        if (type == 2) {
                            type = 1;
                            EasyFloat.dismiss(TRTCMainActivity.this);
                            chartLaytou.setVisibility(View.GONE);
                            voidoLayout.setVisibility(View.VISIBLE);
                            TrtcRoomManager.getInstance().getTrtcCloud().stopRemoteView(mConnectUserId);
                            TrtcRoomManager.getInstance().getTrtcCloud().stopLocalPreview();
                            cvvFloatRemote.removeVideoView();
                            cvvFloatLocal.removeVideoView();
                            TrtcRoomManager.getInstance().getVideoViewLayout().setRemoteUserId(mConnectUserId);
                            if (videoViewRenderView != null) {
                                TrtcRoomManager.getInstance().getTrtcCloud().setRemoteViewFillMode(mConnectUserId, TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL);
                                TrtcRoomManager.getInstance().getTrtcCloud().startRemoteView(mConnectUserId, videoViewRenderView);
                                TrtcRoomManager.getInstance().getTrtcCloud().setLocalViewFillMode(TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL);
                                TrtcRoomManager.getInstance().getTrtcCloud().startLocalPreview(true, localTXCloudVideoView);
                            }
                            RxKeyboardTool.hideSoftInput(TRTCMainActivity.this);
                        }
                    }
                });
            }
        }).setLocation(100, 200).show();
        chartLaytou.setVisibility(View.VISIBLE);
        voidoLayout.setVisibility(View.GONE);
        type = 2;

    }

    private void finishInquiry() {
        ConfirmDialog.newInstance(getString(R.string.end_chat),
                getString(R.string.no), getString(R.string.yes), true)
                .setConfirmSelect(new ConfirmDialog.ConfirmSelect() {
                    @Override
                    public void onClickCancel() {
                    }

                    @Override
                    public void onClickQuery() {
                        exitRoom();

                    }
                }).setMargin(50)
                .setOutCancel(true)
                .show(getSupportFragmentManager());
    }

    @Override
    public void endTRTCSuccess() {

    }


    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {

    }

    private void restartApplication() {
        Intent intent = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, restartIntent); // 100毫秒秒钟后重启应用
        System.exit(0);
    }

    public void initChatView() {
        chatLayout.initDefault();
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setChatName(App.getSPUtils().getString("patientName"));
        chatInfo.setId(App.getSPUtils().getString("patientId"));
        chatInfo.setType(TIMConversationType.C2C);
        messageLayout = chatLayout.getMessageLayout();
        mTitleBar = chatLayout.getTitleBar();
        inputLayout = chatLayout.getInputLayout();
        // 隐藏拍照并发送
        inputLayout.disableCaptureAction(true);
        // 隐藏摄像并发送
        inputLayout.disableVideoRecordAction(true);
        //隐藏发送文件
        inputLayout.disableSendFileAction(true);
        chatLayout.setChatInfo(chatInfo);
        mTitleBar.getRightIcon().setVisibility(View.GONE);
        chatLayout.getMessageLayout().setOnItemClickListener(null);
        //获取单聊面板的标题栏
        //单聊面板标记栏返回按钮点击事件，这里需要开发者自行控制
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 2) {
                    type = 1;
                    chartLaytou.setVisibility(View.GONE);
                    voidoLayout.setVisibility(View.VISIBLE);
                    TrtcRoomManager.getInstance().getTrtcCloud().stopRemoteView(mConnectUserId);
                    TrtcRoomManager.getInstance().getTrtcCloud().stopLocalPreview();
                    cvvFloatRemote.removeVideoView();
                    cvvFloatLocal.removeVideoView();
                    TrtcRoomManager.getInstance().getVideoViewLayout().setRemoteUserId(mConnectUserId);
                    if (videoViewRenderView != null) {
                        TrtcRoomManager.getInstance().getTrtcCloud().setRemoteViewFillMode(mConnectUserId, TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL);
                        TrtcRoomManager.getInstance().getTrtcCloud().startRemoteView(mConnectUserId, videoViewRenderView);
                        TrtcRoomManager.getInstance().getTrtcCloud().setLocalViewFillMode(TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL);
                        TrtcRoomManager.getInstance().getTrtcCloud().startLocalPreview(true, localTXCloudVideoView);
                    }
                    EasyFloat.dismiss(TRTCMainActivity.this);
                    RxKeyboardTool.hideSoftInput(TRTCMainActivity.this);
                }
            }
        });
    }

}
