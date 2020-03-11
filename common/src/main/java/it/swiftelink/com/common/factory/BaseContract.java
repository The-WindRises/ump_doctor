package it.swiftelink.com.common.factory;


import it.swiftelink.com.common.widget.recycler.BaseRecyclerAdapter;

/**
 * Created by Administrator on 2018/7/11.
 * mvp模式中公共的契约
 */

public class BaseContract {


    public interface View<T extends Presenter> {

        //显示进度条的方法
        void showLoading();

        void showRetry(int type);

        void showContent();

        void retry(int type);

        void showEmpty();

        //显示错误的方法
        void showError(int type, String errorMsg);

        //设置presenter的方法
        void setPresenter(T presenter);

    }

    public interface Presenter {
        //开始触发
        void start();

        //销毁触发
        void destroy();
    }


    // 基本的一个列表的View的职责
    interface RecyclerView<T extends Presenter, ViewMode> extends View<T> {
        // 界面端只能刷新整个数据集合，不能精确到每一条数据更新
        // void onDone(List<User> users);

        // 拿到一个适配器，然后自己自主的进行刷新
        BaseRecyclerAdapter<ViewMode> getRecyclerAdapter();

        // 当适配器数据更改了的时候触发
        void onAdapterDataChanged();
    }
}
