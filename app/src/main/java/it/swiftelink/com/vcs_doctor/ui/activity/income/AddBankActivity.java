package it.swiftelink.com.vcs_doctor.ui.activity.income;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.utils.BindEventBus;
import it.swiftelink.com.common.utils.TxtUtils;
import it.swiftelink.com.common.widget.stateview.StateView;
import it.swiftelink.com.factory.model.income.BackCardParamModel;
import it.swiftelink.com.factory.presenter.income.AddBackPresenter;
import it.swiftelink.com.factory.presenter.income.AddBankContract;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.BasePresenterActivity;
import it.swiftelink.com.vcs_doctor.R;

/**
 * @作者 Arvin
 * @时间 2019/7/25 15:27
 * @一句话描述 添加银行卡
 */

@BindEventBus
public class AddBankActivity extends BasePresenterActivity<AddBankContract.Presenter> implements AddBankContract.View {


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.stateView)
    StateView stateView;
    @BindView(R.id.crdholder_et)
    EditText crdholderEt;
    @BindView(R.id.cardNumber_et)
    EditText cardNumberEt;
    @BindView(R.id.openingBank_et)
    EditText openingBankEt;
    @BindView(R.id.phoneNumber_et)
    EditText phoneNumberEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bank;
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        tv_title.setText(getString(R.string.adding_bank_cards));
    }

    @Override
    protected AddBankContract.Presenter initPresenter() {
        return new AddBackPresenter(this);
    }

    @Override
    protected StateView setStateView() {
        return stateView;
    }

    @OnClick({R.id.btn_back, R.id.confirm_btn, R.id.right_iv, R.id.openingBank_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                out();
                break;
            case R.id.confirm_btn:
                //添加银行卡操作
                addBackCard();
                break;
            case R.id.right_iv:
            case R.id.openingBank_et:
                //选择银行卡
                break;
        }
    }

    /**
     * 添加银行卡
     */
    private void addBackCard() {

        String crdholder = crdholderEt.getText().toString();
        if (TextUtils.isEmpty(crdholder)) {
            App.showToast(R.string.cardholder_not_empty);
            return;
        }
        String cardNumber = cardNumberEt.getText().toString();
        if (TextUtils.isEmpty(cardNumber)) {
            App.showToast(R.string.backcard_not_empty);
            return;
        }
        if (!TxtUtils.checkBankCard(cardNumber)) {
            App.showToast(R.string.bankcard_format_incorrect);
            return;
        }

        String openingBank = openingBankEt.getText().toString();

        if (TextUtils.isEmpty(openingBank)) {
            App.showToast(R.string.openingBank_not_empty);
            return;
        }
        String phoneNumber = phoneNumberEt.getText().toString();


        if (TextUtils.isEmpty(phoneNumber)) {
            App.showToast(R.string.phone_not_empty);
            return;
        }
        if (!TxtUtils.chickedPhone(phoneNumber)) {
            App.showToast(R.string.phone_format_Incorrect);
            return;
        }
        BackCardParamModel backCardParamModel = new BackCardParamModel();
        backCardParamModel.setName(crdholder);
        backCardParamModel.setCardNo(cardNumber);
        backCardParamModel.setBank(openingBank);
        backCardParamModel.setMobile(phoneNumber);
        mPresenter.bindBackCard(crdholder, cardNumber, openingBank, phoneNumber);
    }

    @Override
    public void bindBackCardSuccess() {
        stateView.showContent();
        App.showToast(R.string.addedSuccessfully);
    }

    @Override
    public void retry(int type) {

    }

    @Override
    public void showError(int type, String errorMsg) {
        showContent();
        if (type == Common.UrlApi.POST_ADDBACK_CODE) {
            App.showToast(R.string.failure_to_add);
        }
    }


}

