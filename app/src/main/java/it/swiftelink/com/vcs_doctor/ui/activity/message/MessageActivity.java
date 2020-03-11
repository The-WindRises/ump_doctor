package it.swiftelink.com.vcs_doctor.ui.activity.message;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.message.MessageResModel;
import it.swiftelink.com.factory.presenter.message.MessageContract;
import it.swiftelink.com.factory.presenter.message.MessagePresenter;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.activity.message.adapter.MessageAdapter;
import it.swiftelink.com.vcs_doctor.util.DateUtils;
import it.swiftelink.com.vcs_doctor.weight.SpacesItemDecoration;

@BindEventBus
public class MessageActivity
        extends BasePresenterActivity<MessageContract.Presenter>
        implements MessageContract.View,
        BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int totalPages = 0;
    private int currPage = 1;
    private int pageSize = 10;
    MessageAdapter messageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.message));
        messageAdapter = new MessageAdapter();
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        messageAdapter.setOnLoadMoreListener(this, recyclerView);
        recyclerView.setAdapter(messageAdapter);
        messageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MessageResModel.DataBean.DataListBean item = (MessageResModel.DataBean.DataListBean) adapter.getData().get(position);
                item.setStatus("AlreadyRead");
                mPresenter.readMessage(item.getId());
                Bundle bundle = new Bundle();
                bundle.putString("title", item.getTitle());
                bundle.putString("content", item.getContent());
                bundle.putString("date", DateUtils.convertToString(item.getCreationDate(), DateUtils.TIME_FORMAT));
                toActivity(bundle, MessageDetailsActivity.class);
                adapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    protected MessageContract.Presenter initPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getMessageList(currPage, pageSize);
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
    }

    @Override
    public void getMessageListSuccess(MessageResModel resModel) {

        showContent();
        messageAdapter.loadMoreComplete();
        swipeRefreshLayout.setRefreshing(false);
        if (resModel != null && resModel.getData() != null && resModel.getData().getDataList() != null) {
            totalPages = resModel.getData().getTotalPages();
            if (resModel.getData().getDataCount() == 0) {
                showEmpty();
            }
            if (currPage <= 1) {
                messageAdapter.setNewData(resModel.getData().getDataList());
            } else {

                messageAdapter.addData(resModel.getData().getDataList());
            }
        }
    }

    @Override
    public void readMessageSuccess() {
        showContent();
    }

    @Override
    public void onLoadMoreRequested() {
        currPage++;
        if (currPage > totalPages) {
            messageAdapter.loadMoreEnd();
        } else {
            mPresenter.getMessageList(currPage, pageSize);
        }
    }

    @Override
    public void onRefresh() {

        currPage = 1;
        mPresenter.getMessageList(currPage, pageSize);
    }
}


