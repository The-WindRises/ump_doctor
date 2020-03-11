package it.swiftelink.com.vcs_doctor.ui.activity.login.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import it.swiftelink.com.factory.model.CountryCodeModel;
import it.swiftelink.com.vcs_doctor.R;

public class CountryCodeAdapter extends BaseQuickAdapter<CountryCodeModel, BaseViewHolder> {



    public CountryCodeAdapter() {
        super(R.layout.item_country_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, CountryCodeModel item) {
        helper.setText(R.id.countryNameTv, item.getCountryName());
        helper.setText(R.id.countryCodeTv, item.getCountryCode());
    }
}
