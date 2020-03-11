package it.swiftelink.com.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.fragment.app.Fragment;


public class GlideLoadUtils {

    private String TAG = "ImageLoader";

    /**
     * 借助内部类 实现线程安全的单例模式
     * 属于懒汉式单例，因为Java机制规定，内部类SingletonHolder只有在getInstance()
     * 方法第一次调用的时候才会被加载（实现了lazy），而且其加载过程是线程安全的。
     * 内部类加载的时候实例化一次instance。
     */
    public GlideLoadUtils() {
    }

    private static class GlideLoadUtilsHolder {
        private final static GlideLoadUtils INSTANCE = new GlideLoadUtils();
    }

    public static GlideLoadUtils getInstance() {
        return GlideLoadUtilsHolder.INSTANCE;
    }

    /**
     * @param activity
     * @param image
     * @param imageView
     * @param default_image
     */
    public void glideLoad(Activity activity, int image, ImageView imageView, int default_image) {
        if (activity != null && !activity.isDestroyed()) {
            try {
                Glide.with(activity).load(image).apply(new RequestOptions().placeholder(default_image)
                        .error(default_image).centerCrop()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * @param image
     * @param imageView
     * @param default_image
     */
    public void glideLoad(Context Context, String image, ImageView imageView, int default_image) {
        if (Context != null ) {
            try {
                Glide.with(Context).load(image).apply(new RequestOptions().placeholder(default_image)
                        .error(default_image).centerCrop()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }
    /**
     * @param image
     * @param imageView
     * @param default_image
     */
    public void glideLoad(Context Context, int image, ImageView imageView, int default_image) {
        if (Context != null ) {
            try {
                Glide.with(Context).load(image).apply(new RequestOptions().placeholder(default_image)
                        .error(default_image).centerCrop()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * @param url           加载图片的url地址  String
     * @param imageView     加载图片的ImageView 控件
     * @param default_image 图片展示错误的本地图片 id
     */
    public void glideLoadCircle(Activity activity, String url, ImageView imageView, int default_image) {
        if (activity != null && !activity.isDestroyed()) {
            try {
                Glide.with(activity).load(url).apply(new RequestOptions().placeholder(default_image)
                        .error(default_image).centerCrop().circleCrop()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * @param url           加载图片的url地址  String
     * @param imageView     加载图片的ImageView 控件
     * @param default_image 图片展示错误的本地图片 id
     */
    public void glideLoadCircle(Fragment fragment, String url, ImageView imageView, int default_image) {
        if (fragment != null && fragment.getActivity()!=null&&!fragment.getActivity().isDestroyed()) {
            try {
                Glide.with(fragment).load(url).apply(new RequestOptions().placeholder(default_image)
                        .error(default_image).centerCrop().circleCrop()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    /**
     * @param url           加载图片的url地址  String
     * @param imageView     加载图片的ImageView 控件
     * @param default_image 图片展示错误的本地图片 id
     */
    public void glideLoadCircle(Context mContext, String url, ImageView imageView, int default_image) {
        if (mContext != null ) {
            try {
                Glide.with(mContext).load(url).apply(new RequestOptions().placeholder(default_image)
                        .error(default_image).centerCrop().circleCrop()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void glideLoadCenterInside(Activity activity, int image, ImageView imageView, int default_image) {
        if (activity != null && !activity.isDestroyed()) {
            try {
                Glide.with(activity).load(image).apply(new RequestOptions().placeholder(default_image)
                        .error(default_image).centerInside()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideLoad(Activity activity, String url, ImageView imageView, int default_image) {
        if (activity != null && !activity.isDestroyed()) {
            try {
                Glide.with(activity).load(url).apply(new RequestOptions().placeholder(default_image)
                        .error(default_image).centerCrop()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,activity is Destroyed");
        }
    }

    public void glideLoad(Fragment fragment, String url, ImageView imageView, int default_image) {
        if (fragment != null && fragment.getActivity() != null && !fragment.getActivity().isDestroyed()) {
            try {
                Glide.with(fragment).load(url).apply(new RequestOptions().placeholder(default_image)
                        .error(default_image).centerCrop()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,fragment is null");
        }
    }

    public void glideLoad(android.app.Fragment fragment, String url, ImageView imageView, int default_image) {
        if (fragment != null && fragment.getActivity() != null && !fragment.getActivity().isDestroyed()) {
            try {
                Glide.with(fragment).load(url).apply(new RequestOptions().placeholder(default_image)
                        .error(default_image).centerCrop()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,android.app.Fragment is null");
        }
    }

    public void glideLoadGif(Context context, ImageView imageView, int gif_image) {
        if (context != null) {
            try {
                Glide.with(context).load(gif_image).apply(new RequestOptions().centerCrop()).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Picture loading failed,android.app.Fragment is null");
        }
    }
}
