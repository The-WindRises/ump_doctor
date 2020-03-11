package it.swiftelink.com.vcs_doctor.ui.fragment.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stx.xhb.xbanner.XBanner;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BasePresenterFragment;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.RxTimer;
import it.swiftelink.com.common.widget.dialog.ConfirmDialog;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.home.BannerResModel;
import it.swiftelink.com.factory.model.home.OrdersRankingResModel;
import it.swiftelink.com.factory.model.home.StatisticsResModel;
import it.swiftelink.com.factory.model.message.MessageRemindResModel;
import it.swiftelink.com.factory.model.room.DiagnosisOrderResModel;
import it.swiftelink.com.factory.presenter.home.HomeContract;
import it.swiftelink.com.factory.presenter.home.HomePresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.entity.Banner;
import it.swiftelink.com.vcs_doctor.event.MsgEvent;
import it.swiftelink.com.vcs_doctor.event.UpdateEvent;
import it.swiftelink.com.vcs_doctor.ui.activity.WebViewActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.auth.AuthenticationActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.login.LoginActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.message.MessageActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.room.ConsultationRoomActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.room.OutpatientAppointActivity;
import it.swiftelink.com.vcs_doctor.ui.activity.room.adapter.ConsultationRoomAdapter;
import it.swiftelink.com.vcs_doctor.util.DateUtils;
import it.swiftelink.com.vcs_doctor.util.UserLogin;

/**
 * A simple {@link Fragment} subclass.
 */
@BindEventBus
public class HomeFragment extends BasePresenterFragment<HomeContract.Presenter> implements HomeContract.View {
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.xbanner)
    XBanner xbanner;
    @BindView(R.id.diagnosis_month_info_tv)
    TextView diagnosisMonthInfoTv;
    @BindView(R.id.rcv_new_order)
    RecyclerView rcvNewOrder;
    @BindView(R.id.rcv_ranking_list)
    RecyclerView rcvRankingList;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.rankingIv)
    ImageView rankingIv;
    ConsultationRoomAdapter consultationRoomAdapter;
    OrderRefreshReceiver orderRefreshReceiver;
    IntentFilter intentFilter;
    private int totalPages = 0;
    private int currPage = 1;
    private int pageSize = 10;
    private String status = "WAITING";
    private String appType = "DOCTOR";
    private String userName = "";
    private String userIcon = "";
    private String userId = "";
    private String doctorId;
    private String patientId;
    private String patientName;
    private String patientHeadImg;
    private String doctorName;
    private String doctorHeadImg;
    private String doctorLanguage;


    private String income;
    private String inquiryDuration;
    private String inquiryAmount;

    public HomeFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    protected HomeContract.Presenter initPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        rcvNewOrder.setFocusable(false);
        rcvRankingList.setFocusable(false);
        consultationRoomAdapter = new ConsultationRoomAdapter();
        rcvNewOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcvNewOrder.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rcvNewOrder.setAdapter(consultationRoomAdapter);
        consultationRoomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (UserLogin.isLogin()) {
                    String doctorStatus = App.getSPUtils().getString("doctorStatus", "");
                    if (Constants.TOASSESS.equals(doctorStatus)
                            || Constants.ASSESSREJECTED.equals(doctorStatus)
                            || Constants.OPENEDVIRTUALCARE.equals(doctorStatus)) {
                        toActivity(ConsultationRoomActivity.class);
                    } else if (Constants.CERTIFIEDPENDING.equals(doctorStatus)) {
                        toAuthInfo(getString(R.string.to_be_auditedtips), doctorStatus);
                    } else if (Constants.CERTIFIEDREJECTED.equals(doctorStatus)) {
                        toAuthInfo(getString(R.string.authentication_rejection_resubmitted), doctorStatus);
                    } else if (Constants.TOCERTIFIED.equals(doctorStatus)) {
                        toAuthInfo(getString(R.string.decertification), doctorStatus);
                    }
                } else {
                    toActivity(LoginActivity.class);
                }
            }
        });
        xbanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Banner bannerBen = (Banner) model;
                Glide.with(getActivity()).load(bannerBen.getImageUrl()).into((ImageView) view);
            }
        });
        xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Banner bannerBen = (Banner) model;
                if ("".equals(bannerBen.getLinkAddr()) || null == bannerBen.getLinkAddr()) {
                    return;
                }
                WebViewActivity.show(getActivity(), bannerBen.getLinkAddr(), bannerBen.getTitle());


            }
        });
    }

    /**
     * 去认证
     */
    private void toAuthInfo(String content, final String doctorStatus) {
        ConfirmDialog.newInstance(content,
                getString(R.string.cancel), getString(R.string.confirm), true)
                .setConfirmSelect(new ConfirmDialog.ConfirmSelect() {
                    @Override
                    public void onClickCancel() {
                    }

                    @Override
                    public void onClickQuery() {
                        if ("CertifiedPending".equals(doctorStatus)) {
                            return;
                        } else {
                            toActivity(AuthenticationActivity.class);
                        }
                    }
                }).setMargin(50)
                .setOutCancel(true)
                .show(getFragmentManager());
    }

    @Override
    protected void initData() {
        super.initData();
        doctorId = App.getSPUtils().getString("LoginDoctorId", "");
        //获取轮播图列表
        mPresenter.getBannerList(language, "Doctor ");
        mPresenter.getOrdersRankinglist(language);
        mPresenter.diagnosisMonthinfo(doctorId);
        mPresenter.remindCount();
    }

    @Override
    protected StateView getStateView() {
        return stateView;
    }

    @OnClick({R.id.ll_video_inquiry, R.id.iv_msg, R.id.ll_outpatient_appointment, R.id.ll_general_training})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_video_inquiry:
                toConsultationRoom();
                break;
            case R.id.ll_outpatient_appointment:
//                toConsultationRoom();
                toActivity(OutpatientAppointActivity.class);
                break;
            case R.id.ll_general_training:
                WebViewActivity.show(getActivity(),
                        Constants.GENERAL_PRACTICE_TRAINING_URL, getString(R.string.general_practice_training));
                break;
            case R.id.iv_msg:
                toActivity(MessageActivity.class);
                break;
        }
    }


    /**
     * 去问诊
     */
    public void toConsultationRoom() {
        if (UserLogin.isLogin()) {
            String doctorStatus = App.getSPUtils().getString("doctorStatus", "");
            if (Constants.TOASSESS.equals(doctorStatus)
                    || Constants.ASSESSREJECTED.equals(doctorStatus)
                    || Constants.OPENEDVIRTUALCARE.equals(doctorStatus)) {
                toActivity(ConsultationRoomActivity.class);
            } else if (Constants.CERTIFIEDPENDING.equals(doctorStatus)) {
                toAuthInfo(getString(R.string.to_be_auditedtips), doctorStatus);
            } else if (Constants.CERTIFIEDREJECTED.equals(doctorStatus)) {
                toAuthInfo(getString(R.string.authentication_rejection_resubmitted), doctorStatus);
            } else if (Constants.TOCERTIFIED.equals(doctorStatus)) {
                toAuthInfo(getString(R.string.decertification), doctorStatus);
            }

        } else {
            toActivity(LoginActivity.class);
        }
    }

    /**
     * 订单排行榜
     *
     * @param model
     */

    @Override
    public void getOrdersRankinglist(OrdersRankingResModel model) {
        if (model != null) {
            if (model.getData().size() > 0) {
                Glide.with(this).load(model.getData().get(0).getOrderLeaderboardImg())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                if (rankingIv == null) {
                                    return false;
                                }
                                if (rankingIv.getScaleType() != ImageView.ScaleType.FIT_XY) {
                                    rankingIv.setScaleType(ImageView.ScaleType.FIT_XY);
                                }
                                ViewGroup.LayoutParams params = rankingIv.getLayoutParams();
                                int vw = rankingIv.getWidth() - rankingIv.getPaddingLeft() - rankingIv.getPaddingRight();
                                float scale = (float) vw / (float) resource.getIntrinsicWidth();
                                int vh = Math.round(resource.getIntrinsicHeight() * scale);
                                params.height = vh + rankingIv.getPaddingTop() + rankingIv.getPaddingBottom();
                                rankingIv.setLayoutParams(params);
                                return false;
                            }
                        }).into(rankingIv);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        doctorLanguage = App.getSPUtils().getString("doctorLanguage", "");
        doctorId = App.getSPUtils().getString("LoginDoctorId", "");
        xbanner.startAutoPlay();
        mPresenter.remindCount();
        messageArrived();
        registBroadcast();
    }

    @Override
    public void onPause() {
        super.onPause();
        RxTimer.cancel();
        xbanner.stopAutoPlay();
        unRegistBroadcast();
    }

    /**
     * 处理轮播图
     *
     * @param model
     */
    @Override
    public void getBannerList(BannerResModel model) {
        showContent();
        final List<BannerResModel.BannerModel> list = model.getData();
        final List<Banner> banners = new ArrayList<>();
        for (BannerResModel.BannerModel bannerModel : list) {
            Banner banner = new Banner();
            banner.setImageUrl(bannerModel.getImage());
            banner.setLinkAddr(bannerModel.getLinkAddr());
            banner.setTitle(bannerModel.getTitle());
            banners.add(banner);
        }
        xbanner.setBannerData(banners);
    }

    @Override
    public void diagnosisMonthinfo(StatisticsResModel resModel) {
        if (resModel != null && resModel.getData() != null) {

            DecimalFormat df = new DecimalFormat("#0.00");
            if (null != resModel.getData().getIncome() && !"".equals(resModel.getData().getIncome())) {
                income=df.format(Double.valueOf(resModel.getData().getIncome()));
//                tvMonthlyIncome.setText(df.format(Double.valueOf(resModel.getData().getIncome())));

            }
            inquiryAmount=resModel.getData().getNumberOfOrders();
//            tvMonthlyInquiryAmount.setText(resModel.getData().getNumberOfOrders());
            String onLinen = resModel.getData().getOnLineTime();
            if (!TextUtils.isEmpty(onLinen)) {
                int time = Integer.parseInt(onLinen) * 60 * 1000;
                inquiryDuration=DateUtils.getDurationInStringHMS(time);
//                tvMonthlyInquiryDuration.setText(DateUtils.getDurationInStringHMS(time));
            }

            String result=getString(R.string.current_month_income)+income+"           "+getString(R.string.monthly_consultation_volume)+inquiryAmount
            +"           "+getString(R.string.current_month_online_hours)+inquiryDuration;
            diagnosisMonthInfoTv.setText(result);



        }
    }

    @Override
    public void remindCount(MessageRemindResModel messageRemindResModel) {
        if (!messageRemindResModel.getData().equals("0")) {
            ivMsg.setImageResource(R.mipmap.icon_msg);
        } else {
            ivMsg.setImageResource(R.mipmap.icon_msg_no_read);
        }
    }

    public void messageArrived() {
        Log.i("收到EventBus消息刷新首页数据", App.order.size() + "");
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
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getOrderList(List<DiagnosisOrderResModel> diagnosisOrderResModel) {
        if (diagnosisOrderResModel != null) {
            consultationRoomAdapter.setNewData(diagnosisOrderResModel);
        } else {
            consultationRoomAdapter.setNewData(new ArrayList<DiagnosisOrderResModel>());

        }
    }


    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
    }

    /**
     * 刷新首页数据
     *
     * @param
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(UpdateEvent updateEvent) {
        doctorId = App.getSPUtils().getString("LoginDoctorId", "");
        if (mPresenter != null) {
            mPresenter.diagnosisMonthinfo(doctorId);
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
        getActivity().registerReceiver(orderRefreshReceiver, intentFilter);
    }

    /**
     * 解绑广播
     */
    public void unRegistBroadcast() {
        if (orderRefreshReceiver != null) {
            getActivity().unregisterReceiver(orderRefreshReceiver);
        }
    }
}
