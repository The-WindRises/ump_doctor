package it.swiftelink.com.vcs_doctor.weight;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.vcs_doctor.R;

public class UpdateDialog extends DialogFragment {
    private View view;

    private AppCompatTextView updlogTv;
    private Button upgradeNowBtn;
    private Button ignoreBtn;
    private Callback callback;
    private String updLog;
    private int isForce;
    private String appVersionName;

    public static UpdateDialog newInstance(int isForce, String updLog, String appVersionName) {
        UpdateDialog fragment = new UpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("isForce", isForce);
        bundle.putString("updLog", updLog);
        bundle.putString("appVersionName", appVersionName);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.updateDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view = inflater.inflate(R.layout.update_dialog, null);
        updlogTv = view.findViewById(R.id.updlogTv);
        upgradeNowBtn = view.findViewById(R.id.upgradeNowBtn);
        ignoreBtn = view.findViewById(R.id.ignoreBtn);
        String updLog = getArguments().getString("updLog");
        final int isForce = getArguments().getInt("isForce");
        String appVersionName = getArguments().getString("appVersionName");
        if (isForce == Constants.ISFORCE) {
            ignoreBtn.setVisibility(View.GONE);

        } else if (isForce == Constants.ISFORCENO) {
            ignoreBtn.setVisibility(View.VISIBLE);

        }
        updlogTv.setText(updLog.replace("\\n", "\n"));
        upgradeNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    if (isForce == Constants.ISFORCE) {
                        upgradeNowBtn.setText("正在升级");
                        upgradeNowBtn.setBackground(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.btn_def_drawal));

                    }
                    callback.onUpgradeNow();
                }
            }
        });
        ignoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onIgnore();
                }
            }
        });
        return view;
    }

    public interface Callback {
        void onUpgradeNow();

        void onIgnore();
    }


    @Override
    public void onStart() {
        super.onStart();
    }
}
