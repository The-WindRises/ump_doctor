package it.swiftelink.com.vcs_doctor.util;

import android.util.Log;

import java.io.File;

import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.common.utils.LogcatHelper;
import it.swiftelink.com.common.utils.MyDate;
import it.swiftelink.com.factory.model.auth.UploadResModel;
import it.swiftelink.com.factory.net.NetWork;
import it.swiftelink.com.vcs_doctor.App;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

public class LogUpload {

    public static void upload(){
        _upload(true);
    }

    public static void upload(boolean showMessage){
        _upload(showMessage);
    }

    public static void _upload(final boolean showMessage){
        try{
            String loginId = App.getSPUtils().getString("LoginDoctorId");
            final LogcatHelper logcatHelper = LogcatHelper.getInstance(null,loginId);
            logcatHelper.stop();
            File file = new File(LogcatHelper.PATH_LOGCAT,"UMP-"+loginId+"-"+ MyDate.getFileName() + ".log");
            if (file != null && file.exists()) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("files[]", file.getName(), requestFile);
                Observable<UploadResModel> observable = NetWork.getRequest().uploadImage(filePart);
                NetWork.getInstance().baseNetRequest(observable, new NetWork.NetCallback<UploadResModel>() {
                    @Override
                    public void onError(ApiException e) {
                        logcatHelper.start();
                        if (showMessage) App.showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(UploadResModel uploadResModel) {
                        logcatHelper.start();
                        if (uploadResModel != null) {
                            if (showMessage) App.showToast("日志上传成功");
                        } else {
                            if (showMessage) App.showToast("日志上传失败");
                        }
                    }
                });
            }else{
                if (showMessage) App.showToast("日志文件不存在");
                logcatHelper.start();
            }
        }catch (Exception e){
            Log.e("LogUpload","",e);
        }
    }

}
