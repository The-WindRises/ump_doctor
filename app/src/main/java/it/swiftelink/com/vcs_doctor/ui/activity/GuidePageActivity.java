package it.swiftelink.com.vcs_doctor.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.LanguageType;
import it.swiftelink.com.common.utils.MultiLanguageUtil;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.entity.Banner;
import it.swiftelink.com.vcs_doctor.entity.GuidePage;

/**
 * @作者 Arvin
 * @时间 2019/8/30 21:06
 * @一句话描述 引导页
 */
public class GuidePageActivity extends BaseActivity {
    @BindView(R.id.xbanner)
    XBanner xbanner;
    int languageType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        boolean firstStartTag = App.getSPUtils().getBoolean("firstStartTag", true);
        if (firstStartTag) {
            xbanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    GuidePage bannerBen = (GuidePage) model;
                    Glide.with(GuidePageActivity.this).load(bannerBen.getResId()).into((ImageView) view);
                }
            });
            xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    if (position == 2) {
                        toActivity(MainActivity.class);
                    }
                }
            });
            List<GuidePage> resCnList = new ArrayList<>();
            List<GuidePage> resHkList = new ArrayList<>();
            List<GuidePage> resEnList = new ArrayList<>();

            resCnList.add(new GuidePage(R.mipmap.set1_cn));
            resCnList.add(new GuidePage(R.mipmap.set2_cn));
            resCnList.add(new GuidePage(R.mipmap.set3_cn));

            resHkList.add(new GuidePage(R.mipmap.set1_tw));
            resHkList.add(new GuidePage(R.mipmap.set2_tw));
            resHkList.add(new GuidePage(R.mipmap.set3_tw));

            resEnList.add(new GuidePage(R.mipmap.set1_en));
            resEnList.add(new GuidePage(R.mipmap.set2_en));
            resEnList.add(new GuidePage(R.mipmap.set3_en));

            Locale locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = getResources().getConfiguration().getLocales().get(0);
            } else {
                locale = getResources().getConfiguration().locale;
            }

            String language = locale.getLanguage();
            String languageAndArea = locale.getLanguage() + "-" + locale.getCountry();

            switch (language){
                case "zh":
                    if(languageAndArea.equals("zh-HK") || languageAndArea.equals("zh-TW") || locale.toString().equals("zh_CN_#Hant")){
                        xbanner.setBannerData(resHkList);
                        languageType = LanguageType.LANGUAGE_CHINESE_TRADITIONAL;
                    }else {
                        languageType = LanguageType.LANGUAGE_CHINESE_SIMPLIFIED;
                        xbanner.setBannerData(resCnList);
                    }
                    break;
                default:
                    xbanner.setBannerData(resEnList);
                    languageType = LanguageType.LANGUAGE_EN;
                    break;
            }
            MultiLanguageUtil.getInstance().updateLanguage(languageType);
        } else {
            toActivity(SplashActivity.class);
            out();
        }
        App.getSPUtils().put("firstStartTag", false);
    }

    private String getLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }
        String lang = locale.getLanguage() + "_" + locale.getCountry();
        return lang;
    }
}
