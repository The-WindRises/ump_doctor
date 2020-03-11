package it.swiftelink.com.vcs_doctor.ui.activity.scheduling;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.weiget.CalendarView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.DateTimeUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.scheduling.DaySchedulingdResModel;
import it.swiftelink.com.factory.model.scheduling.SchedulingResModel;
import it.swiftelink.com.factory.presenter.scheduling.SchedulingContract;
import it.swiftelink.com.factory.presenter.scheduling.SchedulingPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

/**
 * 我的排班Activity
 */
@BindEventBus
public class SchedulingActivity extends
        BasePresenterActivity<SchedulingContract.Presenter>
        implements SchedulingContract.View, OnSingleChooseListener {
    @BindView(R.id.lastMonth_iv)
    ImageView lastMonth_iv;
    @BindView(R.id.nextMonth_iv)
    ImageView nextMonth_iv;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.btn_Shift)
    Button btn_Shift;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.right_btn)
    TextView right_btn;
    @BindView(R.id.calendar)
    CalendarView calendarView;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout flowlayout;
    LayoutInflater mInflater;
    private String originalShiftId;
    private String schedulingDate;
    private List<DaySchedulingdResModel.DaySchedulingd> selectTime = new ArrayList<>();

    private List<DaySchedulingdResModel.DaySchedulingd> daySchedulingds;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheduling;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_title.setText(getString(R.string.work_schedule_rule));
        right_btn.setText(getString(R.string.shiftrecord));
        btn_Shift.setVisibility(View.GONE);
        date_tv.setText(DateUtils.getDate());
        right_btn.setVisibility(View.VISIBLE);
        calendarView.setOnSingleChooseListener(this);
        mInflater = LayoutInflater.from(this);
        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                String year = date[0] + "";
                String m = date[1] + "";
                StringBuilder stringBuilder = new StringBuilder(year);
                stringBuilder.append("-");
                if (m.length() != 2) {
                    stringBuilder.append("0" + m);
                } else {
                    stringBuilder.append(m);
                }
                date_tv.setText(stringBuilder.toString());
            }
        });
    }
    @Override
    protected SchedulingContract.Presenter initPresenter() {
        return new SchedulingPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick({R.id.btn_back, R.id.btn_Shift, R.id.right_btn, R.id.lastMonth_iv, R.id.nextMonth_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.btn_Shift:
                if (TextUtils.isEmpty(schedulingDate) || selectTime.size() <= 0) {
                    App.showToast(R.string.please_select_the_date_typesetting);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("schedulingDate", schedulingDate);
                bundle.putString("originalShiftId", originalShiftId);
                bundle.putSerializable("selectTime", (Serializable) selectTime);
                toActivity(bundle, ApplyActivity.class);
                break;
            case R.id.right_btn:
                toActivity(SchedRecordsActivity.class);
                break;
            case R.id.lastMonth_iv:
                calendarView.lastMonth();
                break;
            case R.id.nextMonth_iv:
                calendarView.nextMonth();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    /**
     * 我的排版
     *
     * @param resModel
     */
    @Override
    public void doctorschedulingList(SchedulingResModel resModel) {
        stateView.showContent();
        if (resModel != null) {
            List<SchedulingResModel.Scheduling> schedulings = resModel.getData();
            List<String> seleData = new ArrayList<>();
            for (SchedulingResModel.Scheduling scheduling : schedulings) {
                String date = scheduling.getDate().replace("-", ".");
                seleData.add(date);
            }
            String date = date_tv.getText().toString().replace('-', '.');
            calendarView
                    .setStartEndDate("1990.1", "2119.12")
                    .setInitDate(date)
                    .setMultiDate(seleData)
                    .init();
        }
    }

    /**
     * 每天的排班
     *
     * @param resModel
     */
    @Override
    public void doctorschedulingdDayList(DaySchedulingdResModel resModel) {
        stateView.showContent();


        if (resModel != null) {
            daySchedulingds = resModel.getData();
            try {
                if (daySchedulingds.size() > 0) {
                    String data = DateUtils.getDateFormat(DateUtils.DATE_FORMAT);
                    int tday = DateTimeUtils.daysBetween(schedulingDate, DateUtils.getDateFormat(DateUtils.DATE_FORMAT));
                    if (tday <= 3) {
                        App.showToast(R.string.modification_3days);
                        btn_Shift.setVisibility(View.GONE);
                    } else {
                        btn_Shift.setVisibility(View.VISIBLE);
                    }
                } else {
                    btn_Shift.setVisibility(View.GONE);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
            flowlayout.setAdapter(new TagAdapter(daySchedulingds) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    DaySchedulingdResModel.DaySchedulingd daySchedulingd = (DaySchedulingdResModel.DaySchedulingd) o;
                    TextView tv = (TextView) mInflater.inflate(R.layout.item_paiban, flowlayout, false);
                    tv.setText(daySchedulingd.getStartTime() + "-" + daySchedulingd.getEndTime());
                    return tv;
                }
            });

            flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    selectTime.clear();
                    if (!selectPosSet.isEmpty()) {
                        for (Integer integer : selectPosSet) {
                            selectTime.add(daySchedulingds.get(integer));
                            originalShiftId = daySchedulingds.get(integer).getShiftId();
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.doctorschedulingList(DateUtils.getDate());
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {

    }

    /**
     * 日历单选事件
     *
     * @param view
     * @param date
     */
    @Override
    public void onSingleChoose(View view, DateBean date) {
        int[] solar = date.getSolar();
        String year = solar[0] + "";
        String month = solar[1] + "";
        String day = solar[2] + "";
        StringBuffer sbDate = new StringBuffer("");
        sbDate.append(year);
        sbDate.append("-");
        if (month.length() != 2) {
            sbDate.append("0" + month);
        } else {
            sbDate.append(month);
        }
        sbDate.append("-");
        if (day.length() != 2) {
            sbDate.append("0" + day);
        } else {
            sbDate.append(day);
        }
        schedulingDate = sbDate.toString();
        mPresenter.doctorschedulingdDayList(sbDate.toString());
        mPresenter.doctorschedulingList(DateUtils.getDate());
    }
}

