package it.swiftelink.com.vcs_doctor.ui.fragment.main;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseFragment;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.LanguageType;
import it.swiftelink.com.common.utils.MultiLanguageUtil;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.activity.WebViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
@BindEventBus
public class ClinicFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    private int languageType;
    public ClinicFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_clinic;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        tvTitle.setText(getString(R.string.clinic));
        btnBack.setVisibility(View.GONE);
        languageType = MultiLanguageUtil.getInstance().getLanguageType();
    }

    @Override

    protected void initData() {
        super.initData();
    }
    @OnClick({R.id.btn_back, R.id.tv_enter_hk,
            R.id.tv_enter_ch, R.id.hkTv, R.id.daluTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                break;
            case R.id.tv_enter_hk:
            case R.id.hkTv:
                if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
                    WebViewActivity.show(getActivity(),"https://www2.ump.com.hk/locations.php?id=30&lang_id=3",getString(R.string.hk));
                } else if (languageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
                    WebViewActivity.show(getActivity(),"https://www2.ump.com.hk/locations.php?id=30&lang_id=1",getString(R.string.hk));
                } else if (languageType == LanguageType.LANGUAGE_EN) {
                    WebViewActivity.show(getActivity(),"https://www2.ump.com.hk/locations.php?id=30&lang_id=2",getString(R.string.hk));
                }
                break;
            case R.id.tv_enter_ch:
            case R.id.daluTv:
                if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
                    WebViewActivity.show(getActivity(),"https://www2.ump.com.hk/locations.php?id=31&lang_id=3",getString(R.string.dalu));
                } else if (languageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
                    WebViewActivity.show(getActivity(),"https://www2.ump.com.hk/locations.php?id=31&lang_id=1",getString(R.string.dalu));
                } else if (languageType == LanguageType.LANGUAGE_EN) {
                    WebViewActivity.show(getActivity(),"https://www2.ump.com.hk/locations.php?id=310&lang_id=2",getString(R.string.dalu));
                }
                break;
        }
    }
}
