package it.swiftelink.com.vcs_doctor.ui.activity.auth;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.auth.DepartmentResModel;
import it.swiftelink.com.factory.presenter.auth.DepartmentContract;
import it.swiftelink.com.factory.presenter.auth.DepartmentPresenter;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.activity.auth.adapter.DepartmentChildrenAdapter;
import it.swiftelink.com.vcs_doctor.ui.activity.auth.adapter.DepartmentParentAdapter;

/**
 * @作者 Arvin
 * @时间 2019/8/6 14:35
 * @一句话描述 科室
 */
@BindEventBus
public class DepartmentActivity extends
        BasePresenterActivity<DepartmentContract.Presenter>
        implements DepartmentContract.View {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.departmentParentRv)
    RecyclerView departmentParentRv;
    @BindView(R.id.departmentChildrenRv)
    RecyclerView departmentChildrenRv;
    @BindView(R.id.stateView)
    StateView stateView;
    DepartmentParentAdapter departmentParentAdapter;
    DepartmentChildrenAdapter departmentChildrenAdapter;
    private String type = "Parent";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_department;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getDepartmentList(language);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tvTitle.setText(getString(R.string.department));
        departmentParentAdapter = new DepartmentParentAdapter();
        departmentChildrenAdapter = new DepartmentChildrenAdapter();
        departmentParentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DepartmentResModel.DataBean dataBean = (DepartmentResModel.DataBean) adapter.getData().get(position);
                if (dataBean.getChildren() != null && dataBean.getChildren().size() > 0) {
                    departmentChildrenAdapter.setNewData(dataBean.getChildren());
                } else {
                    Intent i = new Intent();
                    i.putExtra("department", dataBean.getName());
                    i.putExtra("departmentId", dataBean.getId());
                    setResult(101, i);
                    finish();

                }
            }
        });
        departmentChildrenAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DepartmentResModel.DataBean.ChildrenBean department = (DepartmentResModel.DataBean.ChildrenBean) adapter.getData().get(position);
                Intent i = new Intent();
                i.putExtra("department", department.getName());
                i.putExtra("departmentId", department.getId());
                setResult(101, i);
                finish();
            }
        });
        departmentParentRv.setLayoutManager(new LinearLayoutManager(this));
//        departmentParentRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        departmentParentRv.setAdapter(departmentParentAdapter);
        departmentChildrenRv.setLayoutManager(new LinearLayoutManager(this));
//        departmentChildrenRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        departmentChildrenRv.setAdapter(departmentChildrenAdapter);
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        out();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        out();
    }

    @Override
    protected DepartmentContract.Presenter initPresenter() {
        return new DepartmentPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @Override
    public void retry(int type) {
    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
    }

    @Override
    public void getDepartmentListSuccess(DepartmentResModel departmentResModel) {
        showContent();
        if (departmentResModel != null) {
            departmentParentAdapter.setNewData(departmentResModel.getData());
        }
    }


}
