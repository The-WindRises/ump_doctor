package it.swiftelink.com.vcs_doctor.ui.activity.scheduling.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import it.swiftelink.com.factory.model.scheduling.SchedulingdRecordsResModel;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

/**
 * 排班申请记录适配器
 */
public class SchedRecordsAdapter extends BaseQuickAdapter<SchedulingdRecordsResModel.SchedulingdRecord, BaseViewHolder> {
    public SchedRecordsAdapter() {
        super(R.layout.item_schedrecords_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, SchedulingdRecordsResModel.SchedulingdRecord item) {
        String oldSchedTime = item.getOriginalShiftDate() + " " + item.getOriginalStartTime() + "-" + item.getOriginalEndTime();
        String shiftTime = item.getApplyShiftDate() + " " + item.getApplyStartTime() + "-" + item.getApplyEndTime();
        helper.setText(R.id.old_sched_time, oldSchedTime);
        helper.setText(R.id.shift_time, shiftTime);
        helper.setText(R.id.state, item.getApprovalStatus());
        helper.setText(R.id.rejectReasonTv, item.getRejectReason());
        RelativeLayout relativeLayout = helper.getView(R.id.rejectReasonLayout);
        RelativeLayout shiftTimeTitleLay = helper.getView(R.id.shiftTimeTitleLay);
        TextView stateTv = helper.getView(R.id.state);
        if ("By".equals(item.getApprovalStatus())) {
            stateTv.setTextColor(Color.parseColor("#3D93F5"));
            stateTv.setText(mContext.getString(R.string.adopt));
            relativeLayout.setVisibility(View.GONE);
        }
        if ("Did_Not_Pass".equals(item.getApprovalStatus())) {
            stateTv.setTextColor(Color.parseColor("#FC6719"));
            stateTv.setText(mContext.getString(R.string.no_passage));
            relativeLayout.setVisibility(View.VISIBLE);
        }
        if ("Pending".equals(item.getApprovalStatus())) {
            stateTv.setTextColor(Color.parseColor("#FC6719"));
            stateTv.setText(mContext.getString(R.string.daiapproved));
            relativeLayout.setVisibility(View.GONE);
        }
        if (item.getType().equals("Leave")) {
            shiftTimeTitleLay.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.getCreationDate())) {
            String createTime = DateUtils.convertToString(Long.parseLong(item.getCreationDate()), DateUtils.TIME_FORMAT);
            helper.setText(R.id.application_time, createTime);
        }
    }
}
