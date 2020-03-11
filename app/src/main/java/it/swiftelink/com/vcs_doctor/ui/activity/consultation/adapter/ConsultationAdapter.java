package it.swiftelink.com.vcs_doctor.ui.activity.consultation.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import it.swiftelink.com.factory.model.consultation.ConsultationResModel;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

/**
 * 问诊记录adapter
 */

public class ConsultationAdapter extends BaseQuickAdapter<ConsultationResModel.ConsultationModel, BaseViewHolder> {
    public ConsultationAdapter() {
        super(R.layout.item_consultation_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ConsultationResModel.ConsultationModel item) {
        if (item != null) {
            helper.setText(R.id.inquiryNumber, item.getNo());
            if ("FINISH".equals(item.getStatus())) {
                helper.setText(R.id.reason, mContext.getString(R.string.completed));
            } else if ("SINGULAR".equals(item.getStatus())) {
                helper.setText(R.id.reason, mContext.getString(R.string.abnormal_interruption));
            }
            RelativeLayout reportWap = helper.getView(R.id.reportWap);
            if (item.getDisgnosisReportId() != null && !"".equals(item.getDisgnosisReportId())) {
                reportWap.setVisibility(View.VISIBLE);
            } else {
                reportWap.setVisibility(View.GONE);
            }
            ImageView imageView = helper.getView(R.id.icon);
            String createTime = DateUtils.convertToString(item.getDiagnosisStartTime(), DateUtils.TIME_FORMAT);
            helper.setText(R.id.tiem, createTime);
            helper.setText(R.id.useName, item.getPatientName());
            String disgonsisDuration = DateUtils.getDurationInString(item.getDisgonsisDuration() * 1000);
            helper.setText(R.id.timeuse, disgonsisDuration);
            helper.addOnClickListener(R.id.reportWap);
        }

    }
}
