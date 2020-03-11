package it.swiftelink.com.factory.presenter.auth;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * @Description:
 * @Author: klk
 * @CreateDate: 2020/1/6 13:24
 */
public class ProgressRequestBody extends RequestBody {

    private File mFile;
    private String mPath;
    private UploadCallbacks mListener;
    private long id;
    private String mMediaType;
    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage,long id);
    }

    public ProgressRequestBody(long id,final File file, final UploadCallbacks listener) {
        this.id = id;
        mFile = file;
        mListener = listener;
    }
    public ProgressRequestBody(final File file,String mediaType, final UploadCallbacks listener) {
        this.id = id;
        mFile = file;
        mListener = listener;
        mMediaType = mediaType;
    }


    @Override
    public MediaType contentType() {
        // i want to upload only images
        return MediaType.parse(mMediaType);
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                // update progress on UI thread
                handler.post(new ProgressUpdater(uploaded, fileLength));
            }
        } finally {
            in.close();
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            mListener.onProgressUpdate((int) (100 * mUploaded / mTotal),id);
        }
    }
}
