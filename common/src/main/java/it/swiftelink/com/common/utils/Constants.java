package it.swiftelink.com.common.utils;


/**
 * @作者 Arvin
 * @时间 2019/7/24 10:18
 * @一句话描述 常量定义
 */

public class Constants {
    public static final String APKPATH = "/swiftelink/" + "umpdoct.apk";
    //发现url
    public static final String FIND_URL = "https://h5.youzan.com/v2/feature/X1npGa5BLN";
    //全科培训url
    public static final String GENERAL_PRACTICE_TRAINING_URL = "https://www.goldgptraining.com/";
    public static final String REGISTER_SMS_TYPE = "2";//注册sms请求type
    public static final int QUALIFICATION_POSITIVE = 100;
    public static final int QUALIFICATION_SIDE = 101;
    public static final int DOCTORSTITLE_POSITIVE = 200;
    public static final int DOCTORSTITLE_SIDE = 201;
    public static final int QUALIFICATION1_POSITIVE = 300;
    public static final int QUALIFICATION1_SIDE = 301;
    public static final int OTHER_POSITIVE = 500;
    public static final int OTHER_SIDE = 501;
    public static final int IDCARD_PIDE = 502;
    public static final int IdCard_SIDE = 503;
    public static final int DEPARTMENTCODE = 504;
    public static final int USERICON = 505;


    //主治医生
    public static final String ATTENDING = "ATTENDING";
    //住院医师
    public static final String RESIDENTS = "RESIDENTSRESIDENTS";
    //副主任医师
    public static final String DEPUTYCHIEFPHYSICIAN = "DEPUTYCHIEFPHYSICIAN";
    //主任医师
    public static final String CHIEFPHYSICIAN = "CHIEFPHYSICIAN";
    //男
    public static final String MALE = "MALE";
    //女
    public static final String FEMALE = "FEMALE";
    //中文简体
    public static final String ZH_CN = "zh_CN";
    //中文繁体
    public static final String ZH_TW = "zh_TW";
    //英文
    public static final String EN_US = "en_US";
    //去认证
    public static final String TOCERTIFIED = "ToCertified";
    //待认证审批
    public static final String CERTIFIEDPENDING = "CertifiedPending";
    ///认证驳回
    public static final String CERTIFIEDREJECTED = "CertifiedRejected";
    //去考核
    public static final String TOASSESS = "ToAssess";
    //考核不通过
    public static final String ASSESSREJECTED = "AssessRejected";
    //已开通视频问诊
    public static final String OPENEDVIRTUALCARE = "OpenedVirtualCare";
    //上线
    public static final String ONLINE = "Online";
    //下线
    public static final String OFFLINE = "Offline";

    //不强制更新
    public static final int ISFORCENO = 0;

    //强制更新
    public static final int ISFORCE = 1;

    //=============================================
    //MQTT 相关指令
    //抢单列表cmd
    public static final int ORDERLISTCMD = 101;
    //派单cmd
    public static final int MATCHORDERCMD = 102;
    //用户取消
    public static final int CANCELINTERROGATION = 103;

    //room订阅的topic
    //sit
//    public static final String ROOMTOPIC = "linuxvcs_" ;
    //uat
//    public static final String ROOMTOPIC = "uatvcs_";
    //生产
    public static String ROOMTOPIC = "uatvcs_";
    //本地
//    public static final String ROOMTOPIC = "defaultvcs_" ;
    //sit
//    public static final String SENDTOPIC = "linuxvcs_";
    //uat
//    public static final String SENDTOPIC = "uatvcs_";

    //生产
//    public static String SENDTOPIC = "productionvcs_";
    //生产
    public static String SENDTOPIC = "uatvcs_";
    //本地
//    public static final String SENDTOPIC = "defaultvcs_";
    //更新诊室的数据
    public static final int ROOMEVENTTYPE = 10001;
    //更新首页的数据的数据
    public static final int INDEXEVENTTYPE = 10002;
    // 广播 的action
    public static final String ORDERREFRESH = "com.android.orderRefresh";

    public static String API_BASE_URL = "https://beta.umpmedical.com:8844";

    public static String ENV_NAME = "";
}
