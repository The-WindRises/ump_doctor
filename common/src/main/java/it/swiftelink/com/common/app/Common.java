package it.swiftelink.com.common.app;

/**
 * @author qiujuer
 */

public class Common {
    public static final int SDKAPPID = 1400242259;

    /**
     * 网络请求的状态
     */
    public interface NetState {
        int STATE_EMPTY = 0x001;
        int STATE_RETRY = 0x002;
        int STATE_CONTENT = 0x003;
        int STATE_LOADING = 0x004;

    }



    /**
     * 本地sp存储
     */
    public interface SPApi {
        String TOKEN = "accessToken";
        String IS_LOGIN_AUTO = "isLoginAuto";

        String IS_IN_ROOM = "is_in_room";
    }

    public interface CommonStr {
        String LANGUAGE1 = "zh_CN";
        String LANGUAGE2 = "zh_TW";
        String LANGUAGE3 = "en_US";

        String INQUIRY_TYPE1 = "DRAFT";
        String INQUIRY_TYPE2 = "FINISH";
        String INQUIRY_TYPE3 = "ALL";


    }


    /**
     * 网络请求
     */
    public interface UrlApi {
        int POST_REGISTER = 0x001; //注册
        int Get_SMSCODE = 0x002; //获取短信
        int POST_LOGIN = 0x003; //登录
        int GET_CLINICINFO = 0x004; //获取医生信息
        int GET_ORDERSRANKINGLIST_CODE = 0x005; //获取订单排行榜
        int GET_BANNERLIST_CODE = 0x006; //获取首页轮播图
        int PUT_ONLINE_CODE = 0x007; //医生上线
        int GET_BALANCE_CODE = 0x008; //我的余额
        int POST_ADDBACK_CODE = 0x009; //绑定银行卡
        int POST_ADDBACKLIST_CODE = 0x0010; //银行卡列表
        int POST_DOCTORSCHEDULING_LIST_CODE = 0x0011; //我的排版
        int POST_DOCTORSCHEDULINGDAY_LIST_CODE = 0x0012; //每天的排班
        int GET_APPROVELIST_CODE = 0x0013; //调班记录
        int GET_RECORD_CODE = 0x0013; //我的收入
        int GET_MYEVALUATION_CODE = 0x0014; //我的评价
        int GET_CONSULTATIONLIST_CODE = 0x0015; //问诊记录
        int GET_DIAGNOSISORDERLIST_CODE = 0x0016; //问诊单列表
        int GET_UPDPWD_CODE = 0x0017; //修改密码
        int GET_UPDPAYPWD_CODE = 0x0018; //修改支付密码
        int GET_INQUIRYREPORTINFO_CODE = 0x0019; //修改支付密码
        int GET_RECIPEINFO_CODE = 0x0020; //修改支付密码
        int GET_TRTC_CONFIG = 0x0021; //视频通话
        int GET_TOAPPLYLIST = 0x0022; //获取当前可排班数据
        int GET_TOAPPLY = 0x0023; //提交排班
        int GET_MATCHORDER = 0x0024; //抢单接口
        int GET_PATIENTINFO = 0x0025; //会员聊天信息接口
        int GET_DIAGNOSISMONTHINFO = 0x0026; //获取当月在线数据
        int POST_ENDTRTC = 0x0027; //结束视频问诊
        int POST_DOCTORINFO = 0x0028; //我的页面获取医生信息
        int GET_HELP = 0x0029; //帮助中心
        int GET_MESSAGELIST = 0x0030; //消息列表
        int PUT_READ = 0x0031; //阅读消息
        int GET_EVALUATIONLISTSUCCESS = 0x0032; //评价标签
        int POST_EVALUATIONUPDATE = 0x0033; //评价
        int POST_FORGETPSD = 0x0034; //评价
        int GET_DEPARTMENTLIST = 0x0035;//科室列表
        int GET_UPLOAD = 0x0036;//上传文件
        int POST_AUTHINFO = 0x0037;//认证个人信息
        int POST_AUTH = 0x0038;//认证个人信息
        int GET_GETUSERINFO = 0x0039;//获取用户信息
        int GET_AGEERRMENT = 0x0040;//获取协议
        int GET_DOCTORPROFILE = 0x0041;//获取医生简介
        int GET_CHECKISEDIT = 0x0042;//检查用户是否是在更新状态
        int END_INQUIRY = 0x0043;
    }

    public interface RequstCode {
        int SELECT_ADDRESS = 0x001;
        int COMPEILE_ADDRESS = 0x002;
    }


    public interface EventbusType {
        int ISAUTH = 0x001;
        int ISLOGIN = 0x002;
        int VIDEO_FLOAT_DISMISS = 0x003;
    }

    public interface DoctorStatus {

        String TOASSESS = "ToAssess";
        String ASSESSREJECTED = "AssessRejected";
        String OPENEDVIRTUALCARE = "OpenedVirtualCare";
    }



}
