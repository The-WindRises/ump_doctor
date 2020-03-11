package it.swiftelink.com.vcs_doctor.ui.activity.income.adapter;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.factory.model.income.BackResModel;
import it.swiftelink.com.vcs_doctor.R;

public class BackAdapter extends BaseQuickAdapter<BackResModel.Back, BaseViewHolder> {
    public BackAdapter() {
        super(R.layout.item_bank_layout);
    }
    @Override
    protected void convert(BaseViewHolder helper, BackResModel.Back item) {
        ImageView bank_iv = helper.getView(R.id.bank_iv);
        GlideLoadUtils.getInstance().glideLoad(mContext,R.mipmap.zhaoshang,bank_iv,R.mipmap.zhaoshang);
        helper.setText(R.id.bank_title, item.getBank());
        helper.setText(R.id.bank_number, "尾号为" + item.getCardNo() + "的储蓄卡");
    }
}
