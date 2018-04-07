package example.seele.com.netclientapp.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import example.seele.com.netclientapp.Utils;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static example.seele.com.netclientapp.BuildConfig.BaseUrl;

/**
 * Created by SEELE on 2018/4/6.
 */

public class RetrofitS {
    private static final String TAG = "Net.RetrofitS";

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private Interceptor appInterceptor;
    private Interceptor netInterceptor;

    private NetRequest netRequest;


    private RetrofitS(){
        init();
    }

    private void init() {

        appInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Log.e(TAG,"==============================request:==================================\n\n");
                Log.e(TAG,"url:\n\t" + chain.request().url());
                Log.e(TAG,"method:\n\t" + chain.request().method());
                Log.e(TAG,"headers:\n\t" + chain.request().headers());
//                RequestBody requestBody = chain.request().body();
//                if(requestBody instanceof FormBody){
//                    FormBody formBody = (FormBody) requestBody.clone();
//                    Log.e(TAG, "params:\n");
//                    for (int i = 0; i < formBody.size();i++){
//                        Log.e(TAG,"name: " + formBody.name(i) + "   value:" + formBody.value(i));
//                    }
//
//                }


                Log.e(TAG,"==============================request:==================================\n\n");

                Response response = chain.proceed(chain.request());


//                Log.e(TAG, "============================reponse:=================================\n\n ");
//                Log.e(TAG, "url:\n\t " + response.request().url());
//                Log.e(TAG, "code:\n\t " + response.code());
//                Log.e(TAG, "message:\n\t " + response.message());
//                Log.e(TAG, "headers:\n\t " + response.headers().toString());
//                Log.e(TAG, "networkResponse:\n\t " + response.networkResponse());
                if(!response.isSuccessful()){
                    Log.e(TAG, "errorBody:\n\t " + Utils.buffer(response).string());
                }
//                Log.e(TAG, "============================reponse:=================================\n\n ");

                return response;
            }
        };

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .addNetworkInterceptor(appInterceptor)
                .build();

//      MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON
//        https://stackoverflow.com/questions/35984898/retrofit2-0-gets-malformedjsonexception-while-the-json-seems-correct
//        https://github.com/square/retrofit/issues/2497
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        netRequest = retrofit.create(NetRequest.class);

    }

    private static volatile RetrofitS retrofitS;

    public static RetrofitS getInstance(){
        if(retrofitS == null){
            synchronized (RetrofitS.class){
                if(retrofitS == null){
                    retrofitS = new RetrofitS();
                }
            }
        }
        return retrofitS;
    }

    public NetRequest getClient(){
        if(netRequest == null){
            synchronized (RetrofitS.class){
                if(netRequest == null){
                    getInstance().retrofit.create(NetRequest.class);
                }
            }
        }
        return netRequest;
    }
}
