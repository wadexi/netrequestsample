package example.seele.com.netclientapp.net;

import android.util.Log;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by SEELE on 2018/4/6.
 */

public abstract class MySubscriber<T> implements Subscriber2<T> {

    private static final String TAG = "Net.MySubscriber";
    @Override
    public final void onNext(Response<T> response) {
        Log.e(TAG, "============================reponse:=================================\n\n ");
        if(response.isSuccessful()){

            Log.e(TAG, "onNext-> headers:\n" + response.headers().toString());
            Log.e(TAG, "onNext-> headers:\n" + response.body().toString());

        }else {
            Log.e(TAG, "onNext-> code:" + response.code());
            Log.e(TAG, "onNext-> msg:" + response.message());
            try {
                Log.e(TAG, "onNext-> headers:" + response.headers().toString());
                Log.e(TAG, "onNext-> errorBody:" + response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.e(TAG, "============================reponse:=================================\n\n ");
        onNexto(response.body());
    }

//    @Override
//    public void onNexto(T responseBody) {
//
//    }
}
