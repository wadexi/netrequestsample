package example.seele.com.netclientapp.net;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import example.seele.com.netclientapp.entity.ResponseBody;
import retrofit2.Response;

/**
 * Created by SEELE on 2018/4/6.
 */

public interface Subscriber2<T> extends org.reactivestreams.Subscriber<Response<T>> {


    void onNexto(T responseBody);


    @Override
    void onSubscribe(Subscription s);

    @Override
    void onNext(Response<T> tResponse);

    @Override
    void onError(Throwable t);

    @Override
    void onComplete();
}
