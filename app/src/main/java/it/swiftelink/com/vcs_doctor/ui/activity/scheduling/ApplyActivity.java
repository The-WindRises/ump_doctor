package it.swiftelink.com.vcs_doctor.ui.activity.scheduling;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import androidx.appcompat.widget.AppCompatSpinner;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.scheduling.ApplyResModel;
import it.swiftelink.com.factory.model.scheduling.DaySchedulingdResModel;
import it.swiftelink.com.factory.presenter.scheduling.SchedulingApplyContract;
import it.swiftelink.com.factory.presenter.scheduling.SchedulingApplyPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

/**
 * 申请调班Activity
 */
@BindEventBus
public class ApplyActivity extends
        BasePresenterActivity<SchedulingApplyContract.Presenter>
        implements SchedulingApplyContract.View {
    @BindView(R.id.selectDate)
    ImageView selectDate;
    @BindView(R.id.reason_et)
    EditText reason_et;
    @BindView(R.id.adjusDate_et)
    EditText adjusDateEt;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.typeSpinner)
    AppCompatSpinner typeSpinner;
    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.shiftTagFlowLayout)
    TagFlowLayout shiftTagFlowLayout;
    @BindView(R.id.schedulingTagFlowLayout)
    TagFlowLayout schedulingTagFlowLayout;
    @BindView(R.id.adjustmentTimeLay)
    LinearLayout adjustmentTimeLay;

    private String schedulingDate;

    LayoutInflater mInflater;
    private List<DaySchedulingdResModel.DaySchedulingd> selectTime = new ArrayList<>();
    private List<ApplyResModel.Apply> applies = new ArrayList<>();
    private String type;
    //原来班次id
    private String originalShiftId;
    //  申请班次id
    private String applyShiftId;
    //申请原因
    private String applyReason;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_title.setText(getString(R.string.apply_scheduling));
        mInflater = LayoutInflater.from(this);
        shiftTagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (!selectPosSet.isEmpty()) {
                    for (Integer integer : selectPosSet) {
                        ApplyResModel.Apply apply = (ApplyResModel.Apply) shiftTagFlowLayout.getAdapter().getItem(integer);
                        applyShiftId = apply.getId();
                    }
                }
            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    type = "Shift";
                    adjustmentTimeLay.setVisibility(View.VISIBLE);
                    shiftTagFlowLayout.setVisibility(View.VISIBLE);
                } else {
                    type = "Leave";
                    adjustmentTimeLay.setVisibility(View.GONE);
                    shiftTagFlowLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectTime = (List<DaySchedulingdResModel.DaySchedulingd>) bundle.getSerializable("selectTime");
            schedulingDate = bundle.getString("schedulingDate");
            originalShiftId = bundle.getString("originalShiftId");
        }
        dateTv.setText(schedulingDate);
        schedulingTagFlowLayout.setAdapter(new TagAdapter(selectTime) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                DaySchedulingdResModel.DaySchedulingd daySchedulingd = (DaySchedulingdResModel.DaySchedulingd) o;
                TextView tv = (TextView) mInflater.inflate(R.layout.item_paiban, schedulingTagFlowLayout, false);
                tv.setText(daySchedulingd.getStartTime() + "-" + daySchedulingd.getEndTime());
                return tv;
            }
        });
        mPresenter.getToApplyList();
    }

    @Override
    protected SchedulingApplyContract.Presenter initPresenter() {
        return new SchedulingApplyPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick({R.id.btn_back, R.id.submit, R.id.selectDate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.selectDate:
                showTimePickerView();
                break;
            case R.id.submit:
                submit();

                break;

        }
    }


    void submit() {

        if (!"Leave".equals(type)) {
            if (TextUtils.isEmpty(applyShiftId)) {
                App.showToast(R.string.please_choose_typesetting_shift);
                return;
            }
        }
        applyReason = reason_et.getText().toString().trim();
        if (TextUtils.isEmpty(applyReason)) {
            App.showToast(R.string.please_enter_the_reason);
            return;
        }
        mPresenter.toApplySuccess(originalShiftId, applyShiftId, type, applyReason);
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
                        String dateStr = DateUtils.convertToString(dateTime, DateUtils.DATE_FORMAT);
                        adjusDateEt.setText(dateStr);
                        if (applies != null && applies.size() > 0) {
                            List<ApplyResModel.Apply> applies1 = new ArrayList<>();
                            for (ApplyResModel.Apply apply : applies) {
                                if (apply.getDate().equals(dateStr)) {
                                    applies1.add(apply);
                                }
                            }
                            if (applies1.size() <= 0) {
                                App.showToast(R.string.no_adjustableshifts);
                            }
                            shiftTagFlowLayout.setAdapter(new TagAdapter(applies1) {
                                @Override
                                public View getView(FlowLayout parent, int position, Object o) {
                                    ApplyResModel.Apply apply = (ApplyResModel.Apply) o;
                                    TextView tv = (TextView) mInflater.inflate(R.layout.item_paiban, shiftTagFlowLayout, false);
                                    tv.setText(apply.getStartTime() + "-" + apply.getEndTime());
                                    return tv;
                                }
                            });
                        } else {
                            App.showToast(R.string.no_adjustableshifts);
                        }

                    }
                }).setType(new boolean[]{true, true, true, false, false, false})
                .build();
        pvTime.show();
    }

    @Override
    public void toApplySuccess() {
        showContent();
        App.showToast(R.string.submint_suess);
        finish();
    }

    @Override
    public void getToApplyListSuccess(ApplyResModel applyResModel) {
        showContent();
        applies.clear();
        if (applyResModel != null) {
            applies.addAll(applyResModel.getData());
        }
//        if (applies.size() > 0) {
//            List<ApplyResModel.Apply> applyList = new ArrayList<>();
//            for (ApplyResModel.Apply apply : applies) {
//                if (apply.getDate().equals(applies.get(0).getDate())) {
//                    applyList.add(apply);
//                }
//            }
//            shiftTagFlowLayout.setAdapter(new TagAdapter(applyList) {
//                @Override
//                public View getView(FlowLayout parent, int position, Object o) {
//                    ApplyResModel.Apply apply = (ApplyResModel.Apply) o;
//                    TextView tv = (TextView) mInflater.inflate(R.layout.item_paiban, shiftTagFlowLayout, false);
//                    tv.setText(apply.getStartTime() + "-" + apply.getEndTime());
//                    return tv;
//                }
//            });
//        }
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
        if (type == Common.UrlApi.GET_TOAPPLY) {
            App.showToast(errorMsg);
        }
    }
}
