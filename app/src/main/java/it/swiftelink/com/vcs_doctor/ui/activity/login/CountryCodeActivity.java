package it.swiftelink.com.vcs_doctor.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.factory.model.CountryCodeModel;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.activity.income.adapter.DiscountRecordrAdapte;
import it.swiftelink.com.vcs_doctor.ui.activity.login.adapter.CountryCodeAdapter;

/**
 * @作者 Arvin
 * @时间 2019/7/26 10:14
 * @一句话描述 国家编码
 */

@BindEventBus
public class CountryCodeActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    CountryCodeAdapter countryCodeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_country_code;
    }

    @Override
    protected void initData() {
        super.initData();
        List<CountryCodeModel> countryCodeModels = new ArrayList<>();
        CountryCodeModel countryCodeModel1 = new CountryCodeModel(getString(R.string.China), "+86");
        CountryCodeModel countryCodeModel2 = new CountryCodeModel(getString(R.string.hong_kong), "+852");
        CountryCodeModel countryCodeModel3 = new CountryCodeModel(getString(R.string.lebmacao), "+853");
        countryCodeModels.add(countryCodeModel1);
        countryCodeModels.add(countryCodeModel2);
        countryCodeModels.add(countryCodeModel3);
        countryCodeAdapter.setNewData(countryCodeModels);
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        out();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        out();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.national_code));
        countryCodeAdapter = new CountryCodeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(countryCodeAdapter);
        countryCodeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CountryCodeModel countryCodeModel = (CountryCodeModel) adapter.getData().get(position);
                String countryCode = countryCodeModel.getCountryCode();
                Intent i = new Intent();
                i.putExtra("countryCode", countryCode);
                setResult(101, i);
                finish();
            }
        });
    }
}
