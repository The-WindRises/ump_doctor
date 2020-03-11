package it.swiftelink.com.vcs_doctor.ui.activity.videochat;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

//import com.github.chrisbanes.photoview.PhotoView;
import com.tencent.imsdk.TIMImage;
import com.tencent.qcloud.tim.uikit.utils.FileUtil;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

import java.io.File;

import it.swiftelink.com.vcs_doctor.R;

public class PhotoViewDialog extends Dialog {
    public TIMImage mCurrentOriginalImage;
    private Context context;
    private String imagePath;

    private boolean isSelf;

    public PhotoViewDialog(Context context, String imagePath, boolean isSelf, TIMImage mCurrentOriginalImage) {
        super(context);
        this.context = context;
        this.imagePath = imagePath;
        this.isSelf = isSelf;
        this.mCurrentOriginalImage = mCurrentOriginalImage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_photo_view, null);
//        setContentView(view);
////        initView(view);
//        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
//        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

    }

//    void initView(View view) {
//        Uri uri = FileUtil.getUriFromPath(imagePath);
//        PhotoView photoView = view.findViewById(R.id.photo_view);
//        if (isSelf || mCurrentOriginalImage == null) {
//            photoView.setImageURI(uri);
//        } else {
//            if (mCurrentOriginalImage != null) {
//                String path = TUIKitConstants.IMAGE_DOWNLOAD_DIR + mCurrentOriginalImage.getUuid();
//                File file = new File(path);
//                if (file.exists()) {
//                    photoView.setImageURI(FileUtil.getUriFromPath(file.getPath()));
//                } else {
//                    photoView.setImageURI(uri);
//                }
//            }
//
//        }
//    }
}
