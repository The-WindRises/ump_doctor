package it.swiftelink.com.factory.presenter.help;

import it.swiftelink.com.common.factory.BaseContract;
import it.swiftelink.com.factory.model.help.HelpResModel;

/**
 * @作者 Arvin
 * @时间 2019/7/24 20:41
 * @一句话描述 帮助契约类
 */

public class HelpContract extends BaseContract {

    public interface View extends BaseContract.View<Presenter> {
        void getHelpListSuccess(HelpResModel resModel);
    }

    public interface Presenter extends BaseContract.Presenter {
        void getHelpList(String language);
    }

}
