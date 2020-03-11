package it.swiftelink.com.vcs_doctor.ui.fragment.main;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import it.swiftelink.com.common.app.BaseFragment;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.vcs_doctor.R;

/**
 * @作者 Arvin
 * @时间 2019/9/17 16:53
 * @一句话描述 发现
 */

@BindEventBus
public class FindFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    private AgentWeb mAgentWeb;

    public FindFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        tvTitle.setText(getString(R.string.find));
        btnBack.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(llContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(Constants.FIND_URL);
    }

    @Override
    public void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }

        super.onResume();
    }

    @Override
    public void onPause() {

        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }
}
