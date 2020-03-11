package it.swiftelink.com.vcs_doctor.ui.activity.income.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;

import it.swiftelink.com.factory.model.income.MyIncomeRecordResModel;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

public class MyIncomeAdapter extends BaseQuickAdapter<MyIncomeRecordResModel.MyIncomeRecord, BaseViewHolder> {
    public MyIncomeAdapter() {
        super(R.layout.item_myincome_layout);
    }


    @Override
    protected void convert(BaseViewHolder helper, MyIncomeRecordResModel.MyIncomeRecord item) {
        DecimalFormat df = new DecimalFormat("#0.00");


        if (item != null) {
            if (item.getType().equals("INCOME")) {
                helper.setText(R.id.income_type, "进帐");
                helper.setText(R.id.amount, "+ ¥" + df.format(item.getAmount()));
            } else if (item.getType().equals("EXPEND")) {
                helper.setText(R.id.amount, "- ¥" + df.format(item.getAmount()));
            }
            helper.setText(R.id.order_number, item.getNo());
            String createTime = DateUtils.convertToString(item.getCreationDate(), DateUtils.TIME_FORMAT);
            helper.setText(R.id.createTime, createTime);
        }
    }
}
