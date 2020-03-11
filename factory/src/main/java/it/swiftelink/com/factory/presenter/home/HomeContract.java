package it.swiftelink.com.factory.presenter.home;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.factory.model.PageParmModel;
import it.swiftelink.com.factory.model.home.BannerResModel;
import it.swiftelink.com.factory.model.home.OrdersRankingResModel;
import it.swiftelink.com.factory.model.home.StatisticsResModel;
import it.swiftelink.com.factory.model.message.MessageRemindResModel;
import it.swiftelink.com.factory.model.room.ClinicinfoResModel;
import it.swiftelink.com.factory.model.room.DiagnosisOrderResModel;
import it.swiftelink.com.factory.model.room.PatientInfoResModel;
import it.swiftelink.com.factory.model.videoChat.TrtcConfigResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/24 20:41
 * @一句话描述 首页契约类
 */

public class HomeContract extends BaseContract {

    public interface View extends BaseContract.View<Presenter> {

        //获取订单排行榜
        void getOrdersRankinglist(OrdersRankingResModel model);

        //获取轮播图
        void getBannerList(BannerResModel model);

        void diagnosisMonthinfo(StatisticsResModel resModel);

        void remindCount(MessageRemindResModel messageRemindResModel);

//        /**
//         * 问诊单列表
//         */
//        void diagnosisOrderList(DiagnosisOrderResModel resModel);

//        /**
//         * 抢单接口
//         */
//        void matchOrderSuccess();
//
//
//        /**
//         * 获取病人相关聊天信息
//         */
//        void getPatientInfoSuccess(PatientInfoResModel resModel);
//
//        //trtc成功
//        void getTrtcConfigSuccess(TrtcConfigResModel model);

    }


    public interface Presenter extends BaseContract.Presenter {
        //获取订单排行榜
        void getOrdersRankinglist(String language);

        void remindCount();

        //获取轮播图
        void getBannerList(String language, String type);

        void diagnosisMonthinfo(String doctorId);
//
//        /**
//         * 问诊单列表
//         */
//        void diagnosisOrderList(PageParmModel pageParmModel);

//        /**
//         * 医生端抢单
//         *
//         * @param diagnosisId
//         */
//        void matchOrder(String diagnosisId);
//
//        /**
//         * 获取病人相关聊天信息
//         */
//        void getPatientInfo();
//
//        /**
//         * 获取trtc 相关
//         */
//        void getTrtcConfig(String uuid, String userId);
    }

}
