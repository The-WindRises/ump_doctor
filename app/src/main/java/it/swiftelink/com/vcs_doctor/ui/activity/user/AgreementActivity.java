package it.swiftelink.com.vcs_doctor.ui.activity.user;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.user.AgrreementResModel;
import it.swiftelink.com.factory.presenter.user.AgreementContract;
import it.swiftelink.com.factory.presenter.user.AgreementPresenter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;

public class AgreementActivity extends BasePresenterActivity<AgreementContract.Presenter> implements AgreementContract.View {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.agreementTv)
    AppCompatTextView agreementTv;
    @BindView(R.id.stateView)
    StateView stateView;

    @OnClick({R.id.btn_back, R.id.tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.tv_title:
                break;
        }
    }

    @Override
    protected AgreementContract.Presenter initPresenter() {
        return new AgreementPresenter(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();

    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter.getAgrreement(language, "DoctorRegistrationAgreement");

    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement;
    }

    @Override
    public void getAgrreementSuess(AgrreementResModel agrreementResModel) {
        showContent();
        if (agrreementResModel != null) {
            App.getSPUtils().put("agrreementId", agrreementResModel.getData().getId());
            tvTitle.setText(agrreementResModel.getData().getTitle());
            agreementTv.setText(Html.fromHtml(agrreementResModel.getData().getContent()));
        }
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {

        showContent();

    }
}
