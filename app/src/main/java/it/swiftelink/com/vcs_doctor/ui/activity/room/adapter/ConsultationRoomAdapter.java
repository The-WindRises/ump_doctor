package it.swiftelink.com.vcs_doctor.ui.activity.room.adapter;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.factory.model.room.DiagnosisOrderResModel;
import it.swiftelink.com.vcs_doctor.R;

public class ConsultationRoomAdapter extends BaseQuickAdapter<DiagnosisOrderResModel, BaseViewHolder> {

    public ConsultationRoomAdapter() {
        super(R.layout.item_consultationroom_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiagnosisOrderResModel item) {
        if (item != null) {
            ImageView iconIv = helper.getView(R.id.iconIv);
            GlideLoadUtils.getInstance().glideLoad(mContext, item.getPatientHeadImg(), iconIv, R.mipmap.icon_dc_man);
            helper.setText(R.id.nameTv, item.getPatientName());
            helper.setText(R.id.descriptionTv, item.getSymptomDescription());
            String language = item.getDiagnosisLanguage();
            if (!TextUtils.isEmpty(language)) {
                String[] languages = language.split(",");
                StringBuilder languageSb = new StringBuilder();
                for (int i = 0; i < languages.length; i++) {
                    if (languages[i].equals("zh_CN")) {
                        languageSb.append(mContext.getString(R.string.mandarin));
                        languageSb.append(" ");
                    } else if (languages[i].equals("zh_TW")) {
                        languageSb.append(mContext.getString(R.string.Cantonese));
                        languageSb.append(" ");
                    } else if (languages[i].equals("en_US")) {
                        languageSb.append("English");
                    }
                }
                helper.setText(R.id.languageTv, languageSb.toString());
            }

        }
    }
}
