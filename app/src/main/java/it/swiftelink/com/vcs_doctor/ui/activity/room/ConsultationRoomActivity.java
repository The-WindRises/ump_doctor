package it.swiftelink.com.vcs_doctor.ui.activity.room;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hacknife.onpermission.OnPermission;
import com.hacknife.onpermission.Permission;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.suke.widget.SwitchButton;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.Application;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.GlideLoadUtils;
import it.swiftelink.com.common.widget.dialog.CustomDialog;
import it.swiftelink.com.common.widget.nicedialog.BaseNiceDialog;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.evaluation.EvaluationParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationSaveParmModel;
import it.swiftelink.com.factory.model.room.ClinicinfoModel;
import it.swiftelink.com.factory.model.room.ClinicinfoResModel;
import it.swiftelink.com.factory.model.room.DiagnosisOrderResModel;
import it.swiftelink.com.factory.model.room.DoctorProfileResModel;
import it.swiftelink.com.factory.model.room.MqttServiceParamModel;
import it.swiftelink.com.factory.model.room.PatientInfoResModel;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;
import it.swiftelink.com.factory.presenter.room.ConsultationRoomContract;
import it.swiftelink.com.factory.presenter.room.ConsultationRoomPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.event.CommentEvent;
import it.swiftelink.com.vcs_doctor.ui.activity.room.adapter.ConsultationRoomAdapter;
import it.swiftelink.com.vcs_doctor.util.DateUtils;
import it.swiftelink.com.vcs_doctor.util.MqttUtil;
import it.swiftelink.com.vcs_doctor.util.Voice_Utils;
import it.swiftelink.com.vcs_doctor.videoChat.ui.TRTCMainActivity;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

@BindEventBus
public class ConsultationRoomActivity extends
        BasePresenterActivity<ConsultationRoomContract.Presenter>
        implements ConsultationRoomContract.View
       {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.userIconIv)
    ImageView userIconIv;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.postsTv)
    TextView postsTv;
    @BindView(R.id.cumulativeTv)
    TextView cumulativeTv;
    @BindView(R.id.cumulative_ll)
    LinearLayout cumulativeLayout;
    @BindView(R.id.onlineTimetv)
    TextView onlineTimetv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @BindView(R.id.consulation_sr)
    SmartRefreshLayout mSmartRefreshLayout;
    TagFlowLayout tabLayout;
    private MediaPlayer mPlayer;
    ConsultationRoomAdapter consultationRoomAdapter;
    private int totalPages = 0;
    private int currPage = 1;
    private int pageSize = 10;
    private String userName = "";
    private String userIcon = "";
    private String userId = "";
    private String doctorId;
    private String patientId;
    private String patientName;
    private String patientHeadImg;
    private String doctorName;
    private String doctorHeadImg;
    private int patientScore = 5;
    private String onlineStatus;
    private String onLineType;
    //    private String doctorStatusLanguage;
    private String loginId;
    private String uuid;
    private CustomDialog distributeOrderDialod;
    private IntentFilter intentFilter;
    private OrderRefreshReceiver orderRefreshReceiver;
    public final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
    private List<String> tags = new ArrayList<>();
    private String mRoomTopic = "";
    private long lastTimeMillis; //记录最后一次点击时间
    private final long MIN_CLICK_INTERVAL = 2000; // 2s内不能重复点击

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
        super.onCreate(savedInstanceState);
        App.getSPUtils().put(Common.SPApi.IS_IN_ROOM, false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_consultation_room;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.consultation_room));
        initRefreshLayout();
        mRoomTopic = Constants.ROOMTOPIC + Application.getSPUtils().getString("LoginDoctorId");
        consultationRoomAdapter = new ConsultationRoomAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(consultationRoomAdapter);
        loginId = App.getSPUtils().getString("LoginDoctorId", "");
        //列表点击事件
        consultationRoomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isTimeEnabled()) {
                    DiagnosisOrderResModel dataBean = (DiagnosisOrderResModel) adapter.getData().get(position);
                    if (dataBean != null) {
                        toVideoChat(dataBean.getId());
                    }
                }
            }
        });
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    if (mPresenter != null) {
                        mPresenter.online(Constants.ONLINE);
                        onLineType = Constants.ONLINE;
                    }

                } else {
                    if (mPresenter != null) {
                        mPresenter.online(Constants.OFFLINE);
                        onLineType = Constants.OFFLINE;
                    }

                }
            }
        });
    }

    protected boolean isTimeEnabled() {
        long currentTimeMillis = System.currentTimeMillis();
        if ((currentTimeMillis - lastTimeMillis) > MIN_CLICK_INTERVAL) {
            lastTimeMillis = currentTimeMillis;
            return true;
        }
        return false;
    }

    public void toVideoChat(final String id) {
        new OnPermission(this).grant(new Permission() {
            @Override
            public String[] permissions() {
                return new String[]{Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
            }

            @Override
            public void onGranted(String[] permission) {
                if (id != null) {
                    App.getSPUtils().put("diagnosisId", id);
                    mPresenter.matchOrder(id);
                }
            }

            @Override
            public void onDenied(String[] permission) {
                App.showToast(R.string.denied_permissionc);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegistBroadcast();
        if (Voice_Utils.getInstance().isPlaying()) {
            Voice_Utils.getInstance().onPause();
            Log.i("xiangling", "响铃停止");
        }
    }

    @Override
    protected void onResume() {
        messageArrived();
        registBroadcast();
        super.onResume();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getClinicinfo();
        mPresenter.getDoctorProfile();
    }

    @OnClick({R.id.btn_back})
    public void onClicK(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
        }
    }

    @Override
    protected ConsultationRoomContract.Presenter initPresenter() {
        return new ConsultationRoomPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @Override
    public void getClinicinfoSuccess(ClinicinfoResModel clinicinfoResModel) {
        if (clinicinfoResModel != null && clinicinfoResModel.getData() != null) {
            ClinicinfoModel clinicinfoModel = clinicinfoResModel.getData();
            if (clinicinfoModel != null) {
                App.getSPUtils().put("doctorStatus", clinicinfoModel.getDoctorStatus());
//                doctorStatusLanguage = clinicinfoModel.getLanguage();
                GlideLoadUtils.getInstance().glideLoadCircle(this, clinicinfoModel.getHeadImg(), userIconIv, R.mipmap.img_dc_woman);
                nameTv.setText(clinicinfoModel.getName());
                userName = clinicinfoModel.getName();
                userId = clinicinfoModel.getUserId();
                userIcon = clinicinfoModel.getHeadImg();
                String todayScheduleTime = clinicinfoModel.getTodayScheduleTime();
                if (!todayScheduleTime.equals("")) {
                    cumulativeTv.setText(DateUtils.getDurationInStringH(this,Long.parseLong(todayScheduleTime)));
                }
                String todayOnlineTime = clinicinfoModel.getTodayOnlineTime();
                if (!todayOnlineTime.equals("")) {
                    onlineTimetv.setText(DateUtils.getDurationInStringHMS2(this,Long.parseLong(todayOnlineTime)));
                }
                onlineStatus = clinicinfoModel.getOnlineStatus();
                if (Constants.ATTENDING.equals(clinicinfoModel.getJobTitle())) {
                    //主治医生
                    postsTv.setText(getString(R.string.attending_doctor));
                } else if (Constants.RESIDENTS.equals(clinicinfoModel.getJobTitle())) {
                    //住院医师
                    postsTv.setText(getString(R.string.residents));
                } else if (Constants.DEPUTYCHIEFPHYSICIAN.equals(clinicinfoModel.getJobTitle())) {
                    //副主任医师
                    postsTv.setText(getString(R.string.deputy_chief_physician));

                } else if (Constants.CHIEFPHYSICIAN.equals(clinicinfoModel.getJobTitle())) {
                    //主任医师
                    postsTv.setText(getString(R.string.chief_physician));
                }
                if (Constants.ONLINE.equals(onlineStatus)) {
                    switchButton.setChecked(true);
                } else if (Constants.ONLINE.equals(onlineStatus)) {
                    switchButton.setChecked(false);
                }
                List<ClinicinfoModel.DoctorScheduling> doctorSchedulingList1 = clinicinfoModel.getDoctorSchedulingList();
                if (doctorSchedulingList1 != null) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    cumulativeLayout.removeAllViews();
                    for (ClinicinfoModel.DoctorScheduling doctorScheduling : doctorSchedulingList1) {
                        String startTime = doctorScheduling.getStartTime();
                        String endTime = doctorScheduling.getEndTime();
                        String beginDate = doctorScheduling.getBeginDate();
                        String endDate = doctorScheduling.getEndDate();
                        String resultDate = beginDate + " " + startTime + "-" + " " + endDate + " " + endTime;
                        TextView textView = new TextView(ConsultationRoomActivity.this);
                        textView.setTextColor(Color.parseColor("#FFFFFF"));
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                        textView.setText(resultDate);
                        textView.setLayoutParams(params);
                        textView.setPadding(0,10,0,0);
                        cumulativeLayout.addView(textView);

                    }
                }


//                tagFlowLayout.setAdapter(new TagAdapter(doctorSchedulingList1) {
//                    @Override
//                    public View getView(FlowLayout parent, int position, Object o) {
//                        TextView textView = new TextView(ConsultationRoomActivity.this);
//                        textView.setTextColor(Color.parseColor("#FFFFFF"));
//                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//                        textView.setText(o.toString());
//                        return textView;
//                    }
//                });
            }
        }
    }

    @Override
    public void onlineSuccess() {
        showContent();
        if (onLineType.equals(Constants.ONLINE)) {
            onlineStatus = Constants.ONLINE;
            messageArrived();
        } else if (onLineType.equals(Constants.OFFLINE)) {
            onlineStatus = Constants.OFFLINE;
            if (consultationRoomAdapter != null) {
                consultationRoomAdapter.setNewData(null);
            }
            //停止响铃
            if (Voice_Utils.getInstance().isPlaying()) {
                Voice_Utils.getInstance().onPause();
                Log.i("xiangling", "响铃停止");
            }
        }
    }

    /**
     * @param
     */
    public void messageArrived() {
        Log.i("收到EventBus消息刷新诊室数据", App.order.size() + "");
        if (onLineType == null || !Constants.ONLINE.equals(onLineType)) {
            return;
        }
        if (App.order.size() > 0 && !App.order.get("order").equals("")) {
            try {
                JSONObject jsonObject = new JSONObject(App.order.get("order"));
                int cmd = jsonObject.optInt("cmd");
                String data = jsonObject.optString("data");
                Gson gson = new Gson();
                switch (cmd) {
                    case Constants.ORDERLISTCMD:
                        List<DiagnosisOrderResModel> diagnosisOrderResModel
                                = gson.fromJson(!data.equals("") ? data : "", new TypeToken<List<DiagnosisOrderResModel>>() {
                        }.getType());
                        getOrderList(diagnosisOrderResModel);
                        break;
                    case Constants.MATCHORDERCMD:
                        PatientInfoResModel.PatientInfo patientInfo = gson.fromJson(!data.equals("") ? data : "", PatientInfoResModel.PatientInfo.class);
                        showDistributeOrderDialog(patientInfo);
                        break;
                    case Constants.CANCELINTERROGATION:
                        if (distributeOrderDialod != null && distributeOrderDialod.getDialog() != null && distributeOrderDialod.getDialog().isShowing()) {
                            distributeOrderDialod.getDialog().dismiss();
                        }
                        JSONObject jsonMesgObj = jsonObject.optJSONObject("data");
                        if (jsonMesgObj != null) {
                            String msg_cn = jsonMesgObj.optString("msg_cn");
                            String msg_tw = jsonMesgObj.optString("msg_tw");
                            String msg_us = jsonMesgObj.optString("msg_us");
                            if (language.equals(Constants.ZH_CN) && !TextUtils.isEmpty(msg_cn)) {
                                App.showToast(msg_cn);
                            } else if (language.equals(Constants.ZH_TW) && !TextUtils.isEmpty(msg_tw)) {
                                App.showToast(msg_tw);
                            } else if (language.equals(Constants.EN_US) && !TextUtils.isEmpty(msg_us)) {
                                App.showToast(msg_us);
                            }
                        }

                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 抢单成功
     */
    @Override
    public void matchOrderSuccess(PatientInfoResModel resModel) {
        showContent();
        if (resModel != null && resModel.getData() != null) {
            doctorId = resModel.getData().getDoctorId();
            patientId = resModel.getData().getPatientId();
            patientName = resModel.getData().getPatientName();
            patientHeadImg = resModel.getData().getPatientHeadImg();
            doctorName = resModel.getData().getDoctorName();
            doctorHeadImg = resModel.getData().getDoctorHeadImg();
            uuid = resModel.getData().getUuid();
            mPresenter.getTrtcConfig(uuid, doctorId);
        }
    }

    /**
     * 获取trtc成功 打开视频
     *
     * @param model
     */
    @Override
    public void getTrtcConfigSuccess(TrtcConfigResModel model) {
        showContent();
        if (model != null && model.getUsers() != null && model.getUsers().size() > 0) {
            TrtcConfigResModel.UsersBean usersBean = model.getUsers().get(0);
            App.getSPUtils().put("userId", usersBean.getUserId());
            App.getSPUtils().put("userSig", usersBean.getUserToken());
            App.getSPUtils().put("patientId", patientId);
            App.getSPUtils().put("patientName", patientName);
            App.getSPUtils().put("sdkAppId", model.getSdkappid());
            App.getSPUtils().put("patientHeadImg", patientHeadImg);
            App.getSPUtils().put("doctorName", doctorName);
            App.getSPUtils().put("doctorHeadImg", doctorHeadImg);
            App.getSPUtils().put("roomId", model.getRoomId());
            App.getSPUtils().put("startTime", System.currentTimeMillis());
            toActivity(TRTCMainActivity.class);
        }
    }

    @Override
    public void getEvaluationListSuccess(EvaluationResModel evaluationResModel) {
        showContent();
        if (evaluationResModel != null && evaluationResModel.getData() != null && evaluationResModel.getData().getDataList() != null) {
            List<EvaluationResModel.DataBean.DataListBean> listBeans
                    = evaluationResModel.getData().getDataList();
            List<String> dataList = new ArrayList<>();
            for (EvaluationResModel.DataBean.DataListBean dataListBean : listBeans) {
                dataList.add(dataListBean.getName());
            }
            tags.clear();
            tags.addAll(dataList);
            if (tabLayout != null) {
                tabLayout.getAdapter().notifyDataChanged();
            }


        }
    }

    @Override
    public void evaluationSave() {
        showContent();
//        App.showToast(R.string.evaluation_success);
    }

    @Override
    public void getDoctorProfile(DoctorProfileResModel doctorProfileResModel) {
        if (null != doctorProfileResModel && doctorProfileResModel.getData() != null) {
            App.getSPUtils().put("Synopsis", doctorProfileResModel.getData().getSynopsis());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void retry(int type) {
    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
        if (type == Common.UrlApi.GET_CLINICINFO) {
            App.showToast(errorMsg);
        } else if (type == Common.UrlApi.PUT_ONLINE_CODE) {
            switchButton.setChecked(false);
            App.showToast(errorMsg);
        } else if (type == Common.UrlApi.GET_MATCHORDER) {
            App.showToast(errorMsg);
        } else if (type == Common.UrlApi.GET_PATIENTINFO) {
            if (distributeOrderDialod != null && distributeOrderDialod.getDialog() != null && distributeOrderDialod.getDialog().isShowing()) {
                distributeOrderDialod.getDialog().dismiss();
            }
            App.showToast(errorMsg);
        } else if (type == Common.UrlApi.POST_EVALUATIONUPDATE) {
            App.showToast(errorMsg);
        } else if (type == Common.UrlApi.GET_TRTC_CONFIG) {
            mPresenter.getTrtcConfig(uuid, doctorId);
            App.showToast(errorMsg);
        } else if (type == Common.UrlApi.GET_DOCTORPROFILE) {
            App.showToast(errorMsg);
        }
    }




           //下拉刷新 初始化
           private void initRefreshLayout() {
               mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
               mSmartRefreshLayout.setEnableLoadMore(false);
               mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                   @Override
                   public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                       currPage = 1;
                       mPresenter.getClinicinfo();
                       refreshLayout.finishRefresh(500);
                   }
               });

           }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCommentEvent(CommentEvent commentEvent) {
        EvaluationParmModel parmModel = new EvaluationParmModel();
        parmModel.setLanguage(language);
        parmModel.setScore(5);
        parmModel.setType("Doctor");
        mPresenter.getEvaluationListSuccess(parmModel);
        startEvaluate();
    }

    /**
     * 派单的dialog
     */
    private void showDistributeOrderDialog(final PatientInfoResModel.PatientInfo patientInfo) {
        distributeOrderDialod = CustomDialog.newInstance(R.layout.distribute_order_dialog_layout)
                .setViewCreateListner(new CustomDialog.ViewCreateListener() {
                    @Override
                    public void onViewCreate(ViewGroup viewGroup, final BaseNiceDialog dialog) {
                        viewGroup.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        viewGroup.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (patientInfo != null) {

                                    String topicPatient = Constants.SENDTOPIC + patientInfo.getPatientId();
                                    MqttServiceParamModel mqttServiceParamModel = new MqttServiceParamModel();
                                    mqttServiceParamModel.setCmd(Constants.MATCHORDERCMD);
                                    mqttServiceParamModel.setSendTime(System.currentTimeMillis());
                                    mqttServiceParamModel.setData(patientInfo);
                                    Gson gson = new Gson();

                                    MqttUtil.getInstance().sendMes(topicPatient, gson.toJson(mqttServiceParamModel));

                                    MqttServiceParamModel selfMsg = new MqttServiceParamModel();
                                    selfMsg.setCmd(Constants.CANCELINTERROGATION);
                                    selfMsg.setSendTime(System.currentTimeMillis());
                                    selfMsg.setData("");
                                    MqttUtil.getInstance().sendMes(mRoomTopic, gson.toJson(selfMsg));
                                    App.getSPUtils().put("diagnosisId", patientInfo.getUuid());
                                    mPresenter.getTrtcConfig(patientInfo.getUuid(), patientInfo.getDoctorId());
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });
        if (distributeOrderDialod != null) {
            distributeOrderDialod.setMargin(30).setOutCancel(false).show(getSupportFragmentManager());
        }
    }

    /**
     * 评价
     */
    private void startEvaluate() {
        CustomDialog.
                newInstance(R.layout.dialog_evaluate)
                .setViewCreateListner(new CustomDialog.ViewCreateListener() {
                    @Override
                    public void onViewCreate(ViewGroup viewGroup, final BaseNiceDialog dialog) {
                        LinearLayout closeIv = viewGroup.findViewById(R.id.closeIv);
                        ImageView doctorIconIv = viewGroup.findViewById(R.id.doctorIconIv);
                        GlideLoadUtils.getInstance().glideLoadCircle(ConsultationRoomActivity.this, patientHeadImg, doctorIconIv, R.mipmap.icon_dc_man);
                        TextView doctorNameTv = viewGroup.findViewById(R.id.doctorNameTv);
                        doctorNameTv.setText(patientName);
                        final MaterialRatingBar rb_grade = viewGroup.findViewById(R.id.rb_grade);
                        rb_grade.setElevation((float) 5.0);
                        Button submit = viewGroup.findViewById(R.id.submit);
                        tabLayout = viewGroup.findViewById(R.id.tabLayout);
                        rb_grade.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                EvaluationParmModel parmModel = new EvaluationParmModel();
                                parmModel.setLanguage(language);
                                parmModel.setScore((int) rating);
                                parmModel.setType("Doctor");
                                patientScore = (int) rating;
                                mPresenter.getEvaluationListSuccess(parmModel);
                                tabLayout.getAdapter().notifyDataChanged();
                            }
                        });
                        closeIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                save();
                                dialog.dismiss();
                            }
                        });
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EvaluationSaveParmModel saveParmModel = new EvaluationSaveParmModel();
                                String id = App.getSPUtils().getString("diagnosisId");
                                StringBuilder stringBuilder = new StringBuilder();
                                Set<Integer> integers = tabLayout.getSelectedList();
                                for (Integer integer : integers) {
                                    stringBuilder.append(tags.get(integer) + ",");
                                }
                                saveParmModel.setDiagnosisId(id);
                                if (stringBuilder.toString().equals("")) {
                                    App.showToast(R.string.plase_sel_tag);
                                    return;
                                }
                                saveParmModel.setDoctorEvaluation(stringBuilder.toString());
                                saveParmModel.setDoctorScore(patientScore);
                                if (TextUtils.isEmpty(stringBuilder.toString())) {
                                    saveParmModel.setDoctorStatus("Not_Evaluation");
                                } else {
                                    saveParmModel.setDoctorStatus("Evaluation");
                                }
                                //动态处理
                                saveParmModel.setPatientStatus("Not_Evaluation");
                                saveParmModel.setType("Diagnosis");
                                mPresenter.evaluationSave(saveParmModel);
                                dialog.dismiss();
                            }
                        });
                        final LayoutInflater mInflater = LayoutInflater.from(ConsultationRoomActivity.this);
                        tabLayout.setAdapter(new TagAdapter(tags) {
                            @Override
                            public View getView(FlowLayout parent, int position, Object o) {
                                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_tv,
                                        tabLayout, false);
                                tv.setText(o.toString());
                                return tv;
                            }
                        });
                    }
                })
                .setMargin(50)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    //保存评价

    private void save() {
        EvaluationSaveParmModel saveParmModel = new EvaluationSaveParmModel();
        String id = App.getSPUtils().getString("diagnosisId");
        StringBuilder stringBuilder = new StringBuilder();
        Set<Integer> integers = tabLayout.getSelectedList();
        for (Integer integer : integers) {
            stringBuilder.append(tags.get(integer) + ",");
        }
        saveParmModel.setDiagnosisId(id);
        saveParmModel.setDoctorEvaluation(stringBuilder.toString());
        saveParmModel.setDoctorScore(patientScore);
        saveParmModel.setDoctorStatus("Not_Evaluation");
        //动态处理
        saveParmModel.setPatientStatus("Not_Evaluation");
        saveParmModel.setType("Diagnosis");
        mPresenter.evaluationSave(saveParmModel);
    }


    public void getOrderList(List<DiagnosisOrderResModel> diagnosisOrderResModel) {
        if (diagnosisOrderResModel != null) {
            if (consultationRoomAdapter != null) {
                consultationRoomAdapter.setNewData(diagnosisOrderResModel);
            }
            if (diagnosisOrderResModel.size() > 0) {

                if (!Voice_Utils.getInstance().isPlaying()) {
                    Voice_Utils.getInstance().start();
                    Log.i("xiangling", "响铃开始");
                }
            } else {
                if (Voice_Utils.getInstance().isPlaying()) {
                    Voice_Utils.getInstance().onPause();
                    Log.i("xiangling", "响铃停止");
                }

            }
        }
    }


    //广播接受收者刷新首页数据
    class OrderRefreshReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.ORDERREFRESH)) {
                messageArrived();
            }
        }
    }

    /**
     * 注册广播
     */
    public void registBroadcast() {
        //实例化广播对象
        orderRefreshReceiver = new OrderRefreshReceiver();
        //实例化广播过滤器，只拦截指定的广播
        intentFilter = new IntentFilter(Constants.ORDERREFRESH);
        //注册广播
        registerReceiver(orderRefreshReceiver, intentFilter);
    }

    /**
     * 解绑广播
     */
    public void unRegistBroadcast() {
        if (orderRefreshReceiver != null) {
            unregisterReceiver(orderRefreshReceiver);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}