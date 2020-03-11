package it.swiftelink.com.vcs_doctor.ui.activity.help;

import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.help.HelpResModel;
import it.swiftelink.com.factory.presenter.help.HelpContract;
import it.swiftelink.com.factory.presenter.help.HelpPresenter;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.entity.Level0Item;
import it.swiftelink.com.vcs_doctor.entity.Level1Item;


/**
 * @作者 Arvin
 * @时间 2019/7/23 11:43
 * @一句话描述 帮助中心
 */
@BindEventBus
public class HelpActivity extends BasePresenterActivity<HelpContract.Presenter> implements HelpContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.stateView)
    StateView stateView;
    ArrayList<MultiItemEntity> res = new ArrayList<>();
    HelpAdapter helpAdapter = new HelpAdapter(res);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.help_center));
        int lv0Count = 9;
        int lv1Count = 3;
        int personCount = 5;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(helpAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getHelpList(language);
    }

    @Override
    protected HelpContract.Presenter initPresenter() {
        return new HelpPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        out();
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {

    }

    @Override
    public void getHelpListSuccess(HelpResModel resModel) {
        showContent();
        if (resModel != null) {
            List<HelpResModel.DataBean> databeans = resModel.getData();
            if (databeans != null) {
                if (databeans != null && databeans.size() > 0) {
                    for (HelpResModel.DataBean dataBean : databeans) {
                        Level0Item lv0 = new Level0Item(dataBean.getName(), dataBean.getId());

                        List<HelpResModel.DataBean.HelpListBean> helps = dataBean.getHelpList();
                        if (helps != null && helps.size() > 0) {

                            for (HelpResModel.DataBean.HelpListBean helpBean : helps) {
                                Level1Item lv1 = new Level1Item(helpBean.getId(), helpBean.getHelpCategoryId(), helpBean.getTitle(), helpBean.getPlatform(), helpBean.getContent());
                                lv0.addSubItem(lv1);
                            }
                        }
                        res.add(lv0);
                    }
                }
                helpAdapter.setNewData(res);
            }

        }

    }
}
