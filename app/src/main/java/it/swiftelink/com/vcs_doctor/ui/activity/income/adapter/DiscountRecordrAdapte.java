package it.swiftelink.com.vcs_doctor.ui.activity.income.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.entity.DiscountRecord;

public class DiscountRecordrAdapte extends BaseQuickAdapter<DiscountRecord, BaseViewHolder> {
    public DiscountRecordrAdapte(@Nullable List<DiscountRecord> data) {
        super(R.layout.item_discountrecordr_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscountRecord item) {
        helper.setText(R.id.channel, item.getChannel());
        helper.setText(R.id.order_number, item.getOrder());
        helper.setText(R.id.discount_amount, item.getAmount());
        helper.setText(R.id.discount_time, item.getDate());
    }
}
