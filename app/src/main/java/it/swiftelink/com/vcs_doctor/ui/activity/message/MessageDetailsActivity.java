package it.swiftelink.com.vcs_doctor.ui.activity.message;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.CloseMessageEvent;

/**
 * @作者 Arvin
 * @时间 2019/8/2 11:51
 * @一句话描述 消息详情
 */

@BindEventBus

public class MessageDetailsActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.messageTv)
    TextView messageTv;
    @BindView(R.id.dateTV)
    TextView dateTV;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString("title");
            tvTitle.setText(title);
            String content = bundle.getString("content").replace("\\n", "\n");
            messageTv.setText(content);
            String date = bundle.getString("date");
            dateTV.setText(date);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseMessageEvent closeMessageEvent) {
        this.finish();
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        out();
    }
}
