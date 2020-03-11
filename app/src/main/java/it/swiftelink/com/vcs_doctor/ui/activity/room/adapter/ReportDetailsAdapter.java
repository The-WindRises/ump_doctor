package it.swiftelink.com.vcs_doctor.ui.activity.room.adapter;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;

import it.swiftelink.com.common.utils.TxtUtils;
import it.swiftelink.com.factory.model.room.InquiryReportResModel;
import it.swiftelink.com.vcs_doctor.R;

public class ReportDetailsAdapter extends BaseQuickAdapter<InquiryReportResModel.DataBean.PrescriptionDrugsBean, BaseViewHolder> {


    public ReportDetailsAdapter() {

        super(R.layout.item_prescription_layout);
    }

    @SuppressLint("StringFormatMatches")
    @Override
    protected void convert(BaseViewHolder helper, InquiryReportResModel.DataBean.PrescriptionDrugsBean item) {
        if (item != null) {
            DecimalFormat df = new DecimalFormat("#0.00");
            String price = item.getPrice();
            String total = item.getTotalAmount();
            if (!"".equals(price) && null != item.getPrice()) {
                helper.setText(R.id.priceTv, "¥" + df.format(Float.parseFloat(item.getPrice())));
            }
            if (!"".equals(total) && null != item.getTotalAmount()) {
                helper.setText(R.id.totalTv, "¥" + df.format(Float.parseFloat(item.getTotalAmount())));
            }
            helper.setText(R.id.drugNameTv, item.getName());
            helper.setText(R.id.specificationTv, item.getSpecification());
            helper.setText(R.id.unitTv, item.getUnit());
            helper.setText(R.id.quantityTv, "x" + item.getQuantity());
            helper.setText(R.id.countTv, String.format(mContext.getString(R.string.count_tv), item.getQuantity()));
            helper.setText(R.id.usageMethodTv, item.getUsageMethod());
            helper.setText(R.id.frequencyTv, item.getFrequency());
            helper.setText(R.id.unitCiTv, item.getOnceUnit());
            helper.setText(R.id.dosageTv, item.getOnceMetering());
            helper.setText(R.id.daysTakenTv, item.getDaysTaken());
        }
    }
}
