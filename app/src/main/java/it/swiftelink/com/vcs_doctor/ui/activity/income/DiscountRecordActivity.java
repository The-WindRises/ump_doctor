package it.swiftelink.com.vcs_doctor.ui.activity.income;

import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.entity.DiscountRecord;
import it.swiftelink.com.vcs_doctor.ui.activity.income.adapter.DiscountRecordrAdapte;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

/**
 * 提现记录Activity
 */
@BindEventBus
public class DiscountRecordActivity extends BaseActivity {
    @BindView(R.id.sum_amount)
    TextView sum_amount;

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.dateTv)
    TextView dateTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discount_record;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_title.setText(getString(R.string.discountrecord));
        dateTv.setText(DateUtils.getDateYM());
        List<DiscountRecord> discountRecords = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            discountRecords.add(new DiscountRecord("2019-07-18  12:30:25", "招商银行", "998865868865" + i, "2000" + i));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new DiscountRecordrAdapte(discountRecords));
    }

    @OnClick({R.id.btn_back, R.id.dateTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.dateTv:
                showTimePickerView();
                break;
        }
    }

    /**
     * 显示时间选择器
     */
    private void showTimePickerView() {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this,
                new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        long dateTime = date.getTime();
                        String dateStr = DateUtils.convertToStringYM(dateTime);
                        dateTv.setText(dateStr);
                    }
                }).setType(new boolean[]{true, true, false, false, false, false})
                .build();
        pvTime.show();
    }

}

