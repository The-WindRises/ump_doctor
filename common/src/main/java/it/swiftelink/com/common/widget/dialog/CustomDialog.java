package it.swiftelink.com.common.widget.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import it.swiftelink.com.common.R;
import it.swiftelink.com.common.widget.nicedialog.BaseNiceDialog;
import it.swiftelink.com.common.widget.nicedialog.ViewHolder;


public class CustomDialog extends BaseNiceDialog {
    private int contentLayoutId = -1;
    private FrameLayout mContentView;
    private ViewCreateListener mViewCreateListner;


    public static CustomDialog newInstance(int layoutRes) {
        Bundle bundle = new Bundle();
        bundle.putInt("layoutRes", layoutRes);
        CustomDialog dialog = new CustomDialog();
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
        contentLayoutId = bundle.getInt("layoutRes");
    }

    @Override
    public int intLayoutId() {
        return R.layout.custom_dialog_layout;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {

        if (contentLayoutId != -1&&mViewCreateListner!=null) {
            mContentView = holder.getView(R.id.fl_dialog_content);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(contentLayoutId, mContentView);
            mViewCreateListner.onViewCreate(mContentView,dialog);
        }

    }

    public CustomDialog setViewCreateListner(ViewCreateListener listner) {
        this.mViewCreateListner = listner;
        return this;
    }

    public interface ViewCreateListener {
        void onViewCreate(ViewGroup viewGroup, BaseNiceDialog dialog);
    }



}
