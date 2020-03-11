package it.swiftelink.com.vcs_doctor.ui.activity.evaluation.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.factory.model.evaluation.MyEvaluationResModel;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.util.DateUtils;
import it.swiftelink.com.vcs_doctor.weight.MyRatingBar;

public class MyEvaluationAdapter extends BaseQuickAdapter<MyEvaluationResModel.MyEvaluation, BaseViewHolder> {


    public MyEvaluationAdapter() {
        super(R.layout.item_evaluatio_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyEvaluationResModel.MyEvaluation item) {

        if (item != null) {
            final LayoutInflater mInflater = LayoutInflater.from(mContext);
            helper.setText(R.id.order, item.getNo());
            // 评价的状态 设置字体颜色
            TextView stateTextView = helper.getView(R.id.state);
            LinearLayout patientWap = helper.getView(R.id.patientWap);
            LinearLayout doctorWap = helper.getView(R.id.doctor_wap);
            String patientStatus = item.getPatientStatus();
            String doctorStatus = item.getDoctorStatus();
            if (item.getPatientName() != null) {
                helper.setText(R.id.userName, item.getPatientName());
            }
            if ("Evaluation".equals(patientStatus)) {
                //会员已评价
                patientWap.setVisibility(View.VISIBLE);
                String diagnosisStartTime = DateUtils.convertToString(item.getDiagnosisStartTime(), DateUtils.TIME_FORMAT);
                helper.setText(R.id.evaluationTime, diagnosisStartTime);
                helper.setText(R.id.rating, item.getPatientScore() + mContext.getString(R.string.label_min));
                //评分的控价
                MyRatingBar myRatingBar = helper.getView(R.id.myRatingBar);
                myRatingBar.setStarRating(item.getPatientScore());
                myRatingBar.setIsIndicator(true);
                final TagFlowLayout flowlayout = helper.getView(R.id.id_flowlayout);
                if (item.getPatientEvaluation() != null) {
                    String patientEvaluatione = item.getPatientEvaluation();
                    List<String> patientEvaluations = new ArrayList<>();
                    if (!TextUtils.isEmpty(patientEvaluatione)) {
                        if (patientEvaluatione.contains(",")) {
                            patientEvaluations.addAll(Arrays.asList(patientEvaluatione.split(",")));

                        } else {
                            patientEvaluations.add(patientEvaluatione);
                        }
                        flowlayout.setAdapter(new TagAdapter(patientEvaluations) {
                            @Override
                            public View getView(FlowLayout parent, int position, Object o) {
                                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_tv_evaluate,
                                        flowlayout, false);
                                tv.setText(o.toString());
                                return tv;
                            }
                        });
                    }
                }

            } else {
                //会员待评价
                patientWap.setVisibility(View.GONE);
            }

            if ("Evaluation".equals(doctorStatus)) {
                //医生已评价
                doctorWap.setVisibility(View.VISIBLE);
                stateTextView.setText(mContext.getString(R.string.evaluated));
                //--------------------医生评分部分，根据后台数据动态显示
                helper.setText(R.id.doctor_rating, item.getDoctorScore() +  mContext.getString(R.string.label_min));
                String diagnosisStartTime = DateUtils.convertToString(item.getDiagnosisStartTime(), DateUtils.TIME_FORMAT);
                helper.setText(R.id.evaluationTime, diagnosisStartTime);
                MyRatingBar doctorMyRatingBar = helper.getView(R.id.doctor_MyRatingBar);
                doctorMyRatingBar.setStarRating(item.getDoctorScore());
                doctorMyRatingBar.setIsIndicator(true);
                final TagFlowLayout doctorFlowlayout = helper.getView(R.id.id_flowlayout_doctor);
                doctorFlowlayout.setEnabled(false);
                if (item.getDoctorEvaluation() != null) {
                    String doctorEvaluatione = item.getDoctorEvaluation();
                    List<String> doctorEvaluationes = new ArrayList<>();
                    if (!TextUtils.isEmpty(doctorEvaluatione)) {

                        if (doctorEvaluatione.contains(",")) {
                            doctorEvaluationes.addAll(Arrays.asList(doctorEvaluatione.split(",")));
                        } else {
                            doctorEvaluationes.add(doctorEvaluatione);
                        }
                        doctorFlowlayout.setAdapter(new TagAdapter(doctorEvaluationes) {
                            @Override
                            public View getView(FlowLayout parent, int position, Object o) {
                                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_tv_evaluate,
                                        doctorFlowlayout, false);
                                tv.setText(o.toString());
                                return tv;
                            }
                        });
                    }
                }

            } else {
                //医生待评价
                doctorWap.setVisibility(View.GONE);
                stateTextView.setText(mContext.getString(R.string.to_be_evaluated));

            }
            //--------------------会员评分部分
            //用户头像
            ImageView iconView = helper.getView(R.id.icon);
            if (item.getPatientHeadImg() != null) {
                GlideLoadUtils.getInstance().glideLoadCircle(mContext, item.getPatientHeadImg(), iconView, R.mipmap.user_icon);
            }
//            helper.setText(R.id.userName, "李文文");
            //根据后台数据状态显示是否有评论时间
            if ("Not_Evaluation".equals(doctorStatus)) {
                helper.getView(R.id.btn_evaluate_wap).setVisibility(View.VISIBLE);
                helper.addOnClickListener(R.id.btn_evaluate);
            } else {
                helper.getView(R.id.btn_evaluate_wap).setVisibility(View.GONE);
            }

        }
    }

}
