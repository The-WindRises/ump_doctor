package it.swiftelink.com.vcs_doctor.ui.activity.room.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import it.swiftelink.com.vcs_doctor.R;

public class PrescriptionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PrescriptionAdapter(@Nullable List<String> data) {
        super(R.layout.item_prescription_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.drugNameTv, item);
        helper.setText(R.id.priceTv, "￥55");
        helper.setText(R.id.specificationTv, "6粒*2");
        helper.setText(R.id.unitTv, "盒");
        helper.setText(R.id.quantityTv, "x1");
        helper.setText(R.id.countTv, "共1件");
        helper.setText(R.id.totalTv, "￥55");
        helper.setText(R.id.frequencyTv, "一日一次");
        helper.setText(R.id.usageMethodTv, "口服");
        helper.setText(R.id.unitCiTv, "粒");
        helper.setText(R.id.dosageTv, "剂量");
        helper.setText(R.id.daysTakenTv, "两粒");
        helper.setText(R.id.descriptionTv, "无");
    }
}
