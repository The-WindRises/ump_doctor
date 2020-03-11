package it.swiftelink.com.vcs_doctor.ui.activity.auth.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import it.swiftelink.com.factory.model.auth.DepartmentResModel;
import it.swiftelink.com.vcs_doctor.R;

public class DepartmentParentAdapter extends BaseQuickAdapter<DepartmentResModel.DataBean, BaseViewHolder> {
    public DepartmentParentAdapter() {
        super(R.layout.item_department_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DepartmentResModel.DataBean item) {
        if (item != null) {
            helper.setText(R.id.itemName, item.getName());
        }
    }
}
