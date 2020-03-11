package it.swiftelink.com.factory.net;

import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.factory.model.NoticeResModel;
import it.swiftelink.com.factory.model.PageParmModel;
import it.swiftelink.com.factory.model.auth.AuthInfoPramModel;
import it.swiftelink.com.factory.model.auth.AuthParmModel;
import it.swiftelink.com.factory.model.auth.DepartmentResModel;
import it.swiftelink.com.factory.model.auth.UploadResModel;
import it.swiftelink.com.factory.model.consultation.ConsultationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationResModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationSaveParmModel;
import it.swiftelink.com.factory.model.evaluation.EvaluationUpdateParmModel;
import it.swiftelink.com.factory.model.evaluation.MyEvaluationResModel;
import it.swiftelink.com.factory.model.help.HelpResModel;
import it.swiftelink.com.factory.model.home.BannerResModel;
import it.swiftelink.com.factory.model.home.OrdersRankingResModel;
import it.swiftelink.com.factory.model.home.StatisticsResModel;
import it.swiftelink.com.factory.model.income.BackResModel;
import it.swiftelink.com.factory.model.income.BalanceResModel;
import it.swiftelink.com.factory.model.income.MyIncomeRecordResModel;
import it.swiftelink.com.factory.model.income.MyIncomeResModel;
import it.swiftelink.com.factory.model.login.ForgetPsdParaModel;
import it.swiftelink.com.factory.model.login.LoginParamModel;
import it.swiftelink.com.factory.model.login.LoginResModel;
import it.swiftelink.com.factory.model.login.RegisterParamModel;
import it.swiftelink.com.factory.model.login.RegisterResModel;
import it.swiftelink.com.factory.model.login.SmsLoginParamModel;
import it.swiftelink.com.factory.model.main.VersionResModel;
import it.swiftelink.com.factory.model.message.MessageRemindResModel;
import it.swiftelink.com.factory.model.message.MessageResModel;
import it.swiftelink.com.factory.model.my.DoctorInforResModel;
import it.swiftelink.com.factory.model.room.ClinicinfoResModel;
import it.swiftelink.com.factory.model.room.DiagnosisOrderResModel;
import it.swiftelink.com.factory.model.room.DoctorProfileResModel;
import it.swiftelink.com.factory.model.room.InquiryReportResModel;
import it.swiftelink.com.factory.model.room.PatientInfoResModel;
import it.swiftelink.com.factory.model.room.RecipeInfoResModel;
import it.swiftelink.com.factory.model.scheduling.ApplyResModel;
import it.swiftelink.com.factory.model.scheduling.DaySchedulingdResModel;
import it.swiftelink.com.factory.model.scheduling.SchedulingResModel;
import it.swiftelink.com.factory.model.scheduling.SchedulingdRecordsResModel;
import it.swiftelink.com.factory.model.sms.SmsCodeParamModel;
import it.swiftelink.com.factory.model.sms.SmsCodeResModel;
import it.swiftelink.com.factory.model.user.AgrreementResModel;
import it.swiftelink.com.factory.model.user.EditUserParamModel;
import it.swiftelink.com.factory.model.user.UseInfoResModel;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @作者 Arvin
 * @时间 2019/9/2 9:26
 * @一句话描述 医生端Api
 */
public interface ApiService {
    //生产
//    String BaseUrl = Constants.API_BASE_URL;

    //Sit测试环境
//   String BaseUrl = "https://vcare.umpmedical.com:8899/";
    //uat环境
//     String BaseUrl = "https://vcare.umpmedical.com:8844/";
//    String BaseUrl = "http://192.168.0.169:8080/";
    String BaseUrlTrtc = "https://www.umpmedical.com:8855/";
    String BaseUploadUrl = "https://www.umpmedical.com:8877/";

    //注册
    @POST("api/register/doctorbytel")
    Observable<RegisterResModel> register(@Body RegisterParamModel registerParamModel);

    //获取短信验证码
    @POST("api/app/smsCode")
    Observable<SmsCodeResModel> getSmsCode(@Body SmsCodeParamModel smsCodeParamModel);

    //登录
    @POST("api/login/doctorbytel")
    Observable<LoginResModel> login(@Body LoginParamModel loginParamModel);

    //短息登录
    @POST("api/login/logintel")
    Observable<LoginResModel> smsLogin(@Body SmsLoginParamModel smsLoginParamModel);

    //获取医生信息
    @GET("api/doctor/clinicinfo")
    Observable<ClinicinfoResModel> getClinicinfo();

    //获取订单排行榜
    @GET("api/home/doctor/orders/rankinglist")
    Observable<OrdersRankingResModel> getOrdersRankinglist(@Query("language") String language);

    //获取首页轮播图
    @GET("api/home/banner")
    Observable<BannerResModel> getBannerList(@Query("language") String language, @Query("type") String type);

    //医生上下线
    @PUT("api/doctor/online")
    Observable<BaseResModel> online(@Query("onlineStatus") String onlineStatus);

    //我的账户余额
    @GET("api/purse/doctor/balance")
    Observable<BalanceResModel> getBalance();

    //绑定银行卡
    @POST("api/purse/doctor/bindcard")
    Observable<BalanceResModel> bindBackCard(@Query("name") String name, @Query("cardNo") String cardNo,
                                             @Query("bank") String bank, @Query("mobile") String mobile);

    //银行卡列表
    @GET("api/purse/doctor/banklist/{currPage}/{pageSize}")
    Observable<BackResModel> getBackList(@Path("currPage") int currPage, @Path("pageSize") int pageSize);

    //我的排班
    @GET("api/doctorscheduling/list")
    Observable<SchedulingResModel> doctorschedulingList(@Query("yearAndMouth") String yearAndMouth);

    //每天的排班
    @GET("api/doctorscheduling/day/list")
    Observable<DaySchedulingdResModel> doctorschedulingdDayList(@Query("date") String date);

    //调班记录
    @GET("api/doctorscheduling/approvelist/{currPage}/{pageSize}")
    Observable<SchedulingdRecordsResModel> chedulingdRecords(@Path("currPage") int currPage, @Path("pageSize") int pageSize);

    //查询我的收入
    @GET("api/bill/record")
    Observable<MyIncomeResModel> getMyIncome();

    //按月查询首支纪律
    @GET("api/bill/month/list")
    Observable<MyIncomeRecordResModel> getMyIncomeRecord(@Query("date") String date);

    //按月首页统计数据
    @GET("api/home/statistics")
    Observable<StatisticsResModel> diagnosisMonthinfo(@Query("doctorId") String doctorId);

    //我的评价
    @GET("/api/evaluation/find/list/{currentPage}/{pageSize}")
    Observable<MyEvaluationResModel> evaluationList(@Path("currentPage")
                                                            int currentPage, @Path("pageSize") int pageSize,
                                                    @Query("doctorStatus") String doctorStatus,
                                                    @Query("patientStatus") String patientStatus, @Query("doctorId") String doctorId);

    //问诊单列表
    @POST("api/diagnosis/waitinglist")
    Observable<DiagnosisOrderResModel> diagnosisOrderList(@Body PageParmModel pageParmModel);

    //问诊记录
    @GET("api/diagnosis/list/{pageSize}/{currPage}")
    Observable<ConsultationResModel> getConsultationList(@Path("currPage") int currPage,
                                                         @Path("pageSize") int pageSize,
                                                         @Query("status") String status, @Query("appType") String appType);

    //修改登录密码
    @PUT("api/login/updatePwdByDoctorAndPatient")
    Observable<BaseResModel> updatePwdByDoctorAndPatient(@Query("oldpwd") String oldpwd, @Query("newpwd") String newpwd, @Query("type") String type);

    //修改支付密码
    @POST("api/doctor/payment/setpassword")
    Observable<BaseResModel> updPayPwd(@Query("mobile") String mobile, @Query("smsCode") String smsCode, @Query("password") String password);

    /**
     * 处方详情
     *
     * @param
     * @return
     */
    @GET("api/prescription/info")
    Observable<InquiryReportResModel> getInquiryReportInfo(@Query("diagnosisId") String diagnosisId);

    /**
     * 问诊报告详情
     *
     * @param id
     * @return
     */
    @GET("api/diagnosis/report/info")
    Observable<RecipeInfoResModel> getRecipeInfo(@Query("id") String id);

    @Headers("url_name:trct")
    @GET("rtc/config-data")
    Observable<TrtcConfigResModel> getTrtcConfig(@Query("uuid") String uuid, @Query("userId") String userId);


    //获取可排班的数据
    @GET("api/doctorscheduling/toApply")
    Observable<ApplyResModel> getToApplyList();

    //获取可排班的数据
    @POST("api/doctorscheduling/update")
    Observable<BaseResModel> getToApply(@Query("originalShiftId") String originalShiftId,
                                        @Query("applyShiftId") String applyShiftId,
                                        @Query("type") String type,
                                        @Query("applyReason") String applyReason);

    //抢单接口
    @GET("api/diagnosis/match")
    Observable<PatientInfoResModel> matchOrder(@Query("diagnosisId") String diagnosisId);

    //poll
    @GET("api/diagnosis/poll")
    Observable<PatientInfoResModel> getPatientInfo();

    //结束视频问诊
    @POST("api/diagnosis/end")
    Observable<BaseResModel> endTRTC(@Query("diagnosisId") String diagnosisId, @Query("isPassive") boolean isPassive);

    //我的页面获取用户医生信息
    @GET("api/doctor/Info")
    Observable<DoctorInforResModel> getDoctorInfo(@Query("language") String language);

    //帮助列表
    @GET("api/help/list")
    Observable<HelpResModel> getHelpList(@Query("language") String language);

    //获取评价标签
    @POST("api/evaluation/list")
    Observable<EvaluationResModel> getEvaluationList(@Body EvaluationParmModel evaluationParmModel);

    //消息列表
    @GET("api/message/list/{currPage}/{pageSize}")
    Observable<MessageResModel> getMessageList(@Path("currPage") int currPage, @Path("pageSize") int pageSize);

    //读消息
    @PUT("api/message/read")
    Observable<BaseResModel> readMessage(@Query("id") String id);

    //评论
    @POST("api/evaluation/update")
    Observable<BaseResModel> evaluationUpdate(@Body EvaluationUpdateParmModel evaluationUpdateParmModel);

    //忘记密码
    @POST("api/login/updatePwd")
    Observable<BaseResModel> forgetPsd(@Body ForgetPsdParaModel forgetPsdParaModel);


    //保存评价
    @POST("api/evaluation/save")
    Observable<BaseResModel> evaluationSave(@Body EvaluationSaveParmModel saveParmModel);

    //加载科室
    @GET("api/sectionoffice/list/{language}")
    Observable<DepartmentResModel> getDepartmentList(@Path("language") String language);

    //文件上传
    @Headers("url_name:upload")
    @Multipart
    @POST("file/upload")
    Observable<UploadResModel> uploadImage(@Part MultipartBody.Part part);

    @POST("api/doctor/auth/edit")
    Observable<BaseResModel> authInfo(@Body AuthInfoPramModel authInfoPramModel);

    @POST("api/doctor/authfile/edit")
    Observable<BaseResModel> auth(@Body AuthParmModel authParmModel);

    @GET("api/doctor/auth/info")
    Observable<UseInfoResModel> getUserInfo();

    @GET("api/Agreement/Agreement")
    Observable<AgrreementResModel> getAgrreement(@Query("language") String language, @Query("type") String type);

    //响铃
    @GET("api/diagnosis/exists/waitings")
    Observable<NoticeResModel> notice();

    @GET("api/message/remind")
    Observable<MessageRemindResModel> remindCount();

    @GET("api/doctor/synopsis")
    Observable<DoctorProfileResModel> getDoctorProfile();

    //版本更新
    @GET("api/app/log/check/version")
    Observable<VersionResModel> checkVersion(@Query("device") String device, @Query("type") String type);

    //下载apk
    @Streaming
    @GET
    Call<ResponseBody> downloadApk(@Url String url);

    //检查用户信息是否在修改状态
    @GET("api/doctor/data/editing/filter")
    Observable<BaseResModel> checkIsEdit();

    //修改用户信息
    @POST("api/doctor/data/editing")
    Observable<BaseResModel> editUserInfo(@Body EditUserParamModel editUserParamModel);

    @POST("api/diagnosis/end")
    Observable<BaseResModel> endVideoInquiry(@Query("diagnosisId") String diagnosisId, @Query("isPassive") boolean isPassive);

}
