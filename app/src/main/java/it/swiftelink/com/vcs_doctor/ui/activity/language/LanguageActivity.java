package it.swiftelink.com.vcs_doctor.ui.activity.language;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.net.LanguageEvent;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.LanguageType;
import it.swiftelink.com.common.utils.MultiLanguageUtil;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.R;

/**
 * @作者 Arvin
 * @时间 2019/7/23 15:02
 * @一句话描述 语言设置
 */
public class LanguageActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.chineseRb)
    RadioButton chineseRb;
    @BindView(R.id.tradiChineseRb)
    RadioButton tradiChineseRb;
    @BindView(R.id.englishRb)
    RadioButton englishRb;
    @BindView(R.id.languageRadioGroup)
    RadioGroup languageRadioGroup;
    @BindView(R.id.currentLanguageTitleTv)
    TextView currentLanguageTitleTv;
    @BindView(R.id.confirm_btn)
    Button confirm_btn;
    @BindView(R.id.currentLanguageTv)
    TextView currentLanguageTv;
    private int languageType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.setting_language));
        languageType = MultiLanguageUtil.getInstance().getLanguageType();
        if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            languageRadioGroup.check(R.id.chineseRb);
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
            languageRadioGroup.check(R.id.tradiChineseRb);
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            languageRadioGroup.check(R.id.englishRb);
        }
        languageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.chineseRb:
                        //中文简体
                        languageType = LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
                        break;
                    case R.id.tradiChineseRb:
                        //中文繁体
                        languageType = LanguageType.LANGUAGE_CHINESE_TRADITIONAL;
                        break;
                    case R.id.englishRb:
                        //英文
                        languageType = LanguageType.LANGUAGE_EN;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.btn_back, R.id.confirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.confirm_btn:
                MultiLanguageUtil.getInstance().updateLanguage(languageType);
                App.getSPUtils().put("refreshUI", true);
                finish();
                break;
        }
    }
}
