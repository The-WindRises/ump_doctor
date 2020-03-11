package it.swiftelink.com.vcs_doctor.ui.activity.videochat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.presenter.videochat.MessageChatContract;
import it.swiftelink.com.factory.presenter.videochat.MessageChatPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.CommentEvent;
import it.swiftelink.com.vcs_doctor.event.MsgEvent;
import it.swiftelink.com.vcs_doctor.videoChat.common.TrtcRoomManager;

/**
 * @作者 Arvin
 * @时间 2019/8/1 10:23
 * @一句话描述 Im聊天
 */

public class MessageChatActivity extends BasePresenterActivity<MessageChatContract.Presenter> implements MessageChatContract.View {
    @BindView(R.id.chat_layout)
    ChatLayout chatLayout;
    @BindView(R.id.stateView)
    StateView stateView;
    private TitleBarLayout mTitleBar;
    private InputLayout inputLayout;

    private String diagnosisId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_chat;
    }

    public static void show(Activity activity, String diagnosisId) {

        Intent intent = new Intent(activity, MessageChatActivity.class);
        intent.putExtra("diagnosisId", diagnosisId);
        activity.startActivity(intent);
    }

    @SuppressLint({"ResourceAsColor", "InvalidWakeLockTag"})
    @Override
    protected void initWidget() {
        super.initWidget();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        chatLayout.initDefault();
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setChatName(App.getSPUtils().getString("patientName"));
        chatInfo.setId(App.getSPUtils().getString("patientId"));
        chatInfo.setType(TIMConversationType.C2C);
        mTitleBar = chatLayout.getTitleBar();
        inputLayout = chatLayout.getInputLayout();
        // 隐藏拍照并发送
        inputLayout.disableCaptureAction(true);
        // 隐藏摄像并发送
        inputLayout.disableVideoRecordAction(true);
        chatLayout.setChatInfo(chatInfo);
        mTitleBar.getRightIcon().setVisibility(View.GONE);
        chatLayout.getMessageLayout().setOnItemClickListener(null);
        //获取单聊面板的标题栏
        //单聊面板标记栏返回按钮点击事件，这里需要开发者自行控制
        mTitleBar.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrtcRoomManager.getInstance().enterRoom();
                finish();
            }
        });
    }

    @Override
    protected MessageChatContract.Presenter initPresenter() {
        return new MessageChatPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();

        Intent intent = getIntent();
        if (intent != null) {
            diagnosisId = intent.getStringExtra("diagnosisId");
        }
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }


    @Override
    public void onBackPressed() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFloatWindowClose(MsgEvent msgEvent) {
        switch (msgEvent.getType()) {
            case Common.EventbusType.VIDEO_FLOAT_DISMISS:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void endVideoChatSuccess() {
        EventBus.getDefault().postSticky(new CommentEvent());
        finish();
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.out_toptobottom);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
