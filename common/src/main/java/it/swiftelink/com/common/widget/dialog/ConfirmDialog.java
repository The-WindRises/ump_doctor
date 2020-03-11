package it.swiftelink.com.common.widget.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import it.swiftelink.com.common.R;
import it.swiftelink.com.common.widget.nicedialog.BaseNiceDialog;
import it.swiftelink.com.common.widget.nicedialog.ViewHolder;


/**
 * Created by Administrator on 2018/11/22.
 */

public class ConfirmDialog extends BaseNiceDialog {
    private String title;
    private ConfirmSelect mConfirmSelect;
    private String cancel;
    private String confirm;
    private boolean isShowCancel;

    public static ConfirmDialog newInstance(String title, String cancelStr, String confirmStr, boolean isShowCancel) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("cancel", cancelStr);
        bundle.putString("confirm", confirmStr);
        bundle.putBoolean("isShowCancel", isShowCancel);
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        title = bundle.getString("title");
        cancel = bundle.getString("cancel");
        confirm = bundle.getString("confirm");
        isShowCancel = bundle.getBoolean("isShowCancel", true);
    }

    public ConfirmDialog setConfirmSelect(ConfirmSelect confirmSelect) {
        this.mConfirmSelect = confirmSelect;
        return this;
    }

    @Override
    public int intLayoutId() {
        return R.layout.confirm_dialog_layout;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {

        if (title != null) {
            holder.setText(R.id.title, title);
            holder.setText(R.id.cancel, cancel);
            holder.setText(R.id.ok, confirm);
        }

        View tvCancel = holder.getView(R.id.cancel);
        if (isShowCancel) {
            tvCancel.setVisibility(View.VISIBLE);
        } else {
            tvCancel.setVisibility(View.GONE);
        }


        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfirmSelect.onClickCancel();
                dismiss();
            }
        });

        holder.setOnClickListener(R.id.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfirmSelect.onClickQuery();
                dismiss();
            }
        });
    }


    public interface ConfirmSelect {
        void onClickCancel();

        void onClickQuery();
    }


}
