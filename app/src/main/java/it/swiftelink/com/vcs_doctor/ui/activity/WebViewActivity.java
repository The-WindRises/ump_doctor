package it.swiftelink.com.vcs_doctor.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.vcs_doctor.R;

@BindEventBus
public class WebViewActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    private AgentWeb mAgentWeb;

    public static void show(Activity activity, String url, String title) {

        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initData() {
        super.initData();

        Intent intent = getIntent();

        if(intent!=null){
            String url = intent.getStringExtra("url");
            String title = intent.getStringExtra("title");
            tvTitle.setText(title);
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(llContent, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .createAgentWeb()
                    .ready()
                    .go(url);
        }
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }


    @Override
    protected void onPause() {

        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }

        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }
}
