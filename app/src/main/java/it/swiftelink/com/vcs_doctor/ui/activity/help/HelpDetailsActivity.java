package it.swiftelink.com.vcs_doctor.ui.activity.help;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.vcs_doctor.R;

/**
 * @作者 Arvin
 * @时间 2019/8/1 16:08
 * @一句话描述 帮助详情
 */

@BindEventBus
public class HelpDetailsActivity extends BaseActivity {
    @BindView(R.id.helpContenTv)
    AppCompatTextView helpContenTv;
    @BindView(R.id.tv_title)
    TextView titleTv;
    private Drawable drawable;
    String content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        content = getIntent().getStringExtra("content");
        String title = getIntent().getStringExtra("title");
        titleTv.setText(title);
        helpContenTv.setText(Html.fromHtml(content, imageGetter, null));
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        out();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private Html.ImageGetter imageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String s) {
            //多张图片情况根据drawableMap.get(s)获取drawable
            if (drawable != null)
                return drawable;
            else
                initDrawable(s);
            return null;
        }
    };

    /**
     * 加载网络图片
     *
     * @param s
     */
    private void initDrawable(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Drawable drawable = Glide.with(HelpDetailsActivity.this).load(s).submit().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            WindowManager manager = HelpDetailsActivity.this.getWindowManager();
                            DisplayMetrics outMetrics = new DisplayMetrics();
                            manager.getDefaultDisplay().getMetrics(outMetrics);
                            int width = outMetrics.widthPixels;
                            int height = outMetrics.heightPixels;
                            if (drawable != null) {
                                drawable.setBounds(0, 0, width, drawable.getIntrinsicHeight());
                                //多张图片情况下进行存储：drawableMap.put(s,drawable);
                                HelpDetailsActivity.this.drawable = drawable;
                                if (Build.VERSION.SDK_INT >= 24)
                                    helpContenTv.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT, imageGetter, null));
                                else
                                    helpContenTv.setText(Html.fromHtml(content, imageGetter, null));
                            }
                        }
                    });
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
