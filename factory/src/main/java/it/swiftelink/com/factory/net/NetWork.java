package it.swiftelink.com.factory.net;

import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.swiftelink.com.common.app.Application;
import it.swiftelink.com.common.app.Common;
import it.swiftelink.com.common.event.TokenInvalidEvent;
import it.swiftelink.com.common.factory.BaseResModel;
import it.swiftelink.com.common.net.ApiCode;
import it.swiftelink.com.common.net.ApiException;
import it.swiftelink.com.common.utils.Constants;
import it.swiftelink.com.common.utils.Utils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetWork {

    private static NetWork mInstance;
    private static Retrofit retrofit;
    private static volatile ApiService request = null;

    public static NetWork getInstance() {
        if (mInstance == null) {
            synchronized (NetWork.class) {
                if (mInstance == null) {
                    mInstance = new NetWork();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {


        // 指定缓存路径,缓存大小 50Mb
        Cache cache = new Cache(new File(Application.getInstance().getCacheDir(), "HttpCache"),
                1024 * 1024 * 50);

        // 初始化okhttp
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(cache)
                .addInterceptor(new HttpCacheInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .build();


        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getRequest() {
        if (request == null) {
            synchronized (ApiService.class) {
                request = retrofit.create(ApiService.class);
            }
        }
        return request;
    }

    /**
     * 添加统一header拦截器
     */
    static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String userToken = Application.getSPUtils().getString(Common.SPApi.TOKEN);
            String token = "";
            if (!TextUtils.isEmpty(userToken)) {
                token = userToken;
            }
            Request.Builder builder = request.newBuilder()
                    .header("accessToken", token)
                    .addHeader("api-version", Utils.getVersionName())
                    .addHeader("language", Application.getSPUtils().getString("appLanguage", Constants.ZH_CN))
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", Utils.getUserAgent());
            HttpUrl oldUrl = request.url();
            List<String> headerValues = request.headers("url_name");
            if (headerValues != null && headerValues.size() > 0) {
                builder.removeHeader("url_name");
                String header = headerValues.get(0);
                HttpUrl newBaseUrl = null;
                if ("trct".equals(header)) {
                    newBaseUrl = HttpUrl.parse(ApiService.BaseUrlTrtc);
                } else if ("upload".equals(header)) {
                    newBaseUrl = HttpUrl.parse(ApiService.BaseUploadUrl);
                } else {
                    newBaseUrl = HttpUrl.parse(Constants.API_BASE_URL);
                }
                HttpUrl newUrl = oldUrl.newBuilder().scheme(newBaseUrl.scheme())
                        .host(newBaseUrl.host())
                        .port(newBaseUrl.port())
                        .build();
                return chain.proceed(builder.url(newUrl).build());
            } else {
                return chain.proceed(builder.build());
            }
        }
    }

    /**
     * 添加缓存的拦截器
     */
    static class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            //设置缓存时间
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();

            cacheBuilder.maxAge(0, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
            cacheBuilder.maxStale(7, TimeUnit.DAYS);//这个是控制缓存的过时时间
            CacheControl cacheControl = cacheBuilder.build();

            if (!Application.isNetworkReachable()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);

            if (Application.isNetworkReachable()) {
                int maxAge = 60; // read from cache for 1 minute
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    public <T extends BaseResModel> void netRequest(Observable<T> observable, final NetCallback<T> callback) {

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ApiException apiException = ApiException.handleException(e);
                        callback.onError(apiException);
                    }

                    @Override
                    public void onNext(T resModel) {
                        if (resModel.getCode() == ApiCode.Http.UNAUTHORIZED) {
                            EventBus.getDefault().post(new TokenInvalidEvent());
                        }
                        callback.onSuccess(resModel);
                    }
                });
    }

    public <T extends BaseResModel> void netRequestNot401(Observable<T> observable, final NetCallback<T> callback) {

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ApiException apiException = ApiException.handleException(e);
                        callback.onError(apiException);
                    }

                    @Override
                    public void onNext(T resModel) {
                        callback.onSuccess(resModel);
                    }
                });
    }


    public <T> void baseNetRequest(Observable<T> observable, final NetCallback<T> callback) {

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ApiException apiException = ApiException.handleException(e);
                        callback.onError(apiException);
                    }

                    @Override
                    public void onNext(T resModel) {

                        callback.onSuccess(resModel);
                    }
                });
    }

    public interface NetCallback<T> {
        void onError(ApiException e);

        void onSuccess(T t);
    }

}
