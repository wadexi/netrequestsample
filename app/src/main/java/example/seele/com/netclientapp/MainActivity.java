package example.seele.com.netclientapp;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.seele.com.netclientapp.entity.ResponseBody;
import example.seele.com.netclientapp.entity.UpFilePathsEntity;
import example.seele.com.netclientapp.entity.UserEntity;
import example.seele.com.netclientapp.net.MySubscriber;
import example.seele.com.netclientapp.net.RetrofitS;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Net.MainActivity";

    @BindView(R.id.simple_form_head)
    Button simpleFormHead;
    @BindView(R.id.simple_form_get)
    Button simpleFormGet;
    @BindView(R.id.simple_form_post)
    Button simpleFormPost;
    @BindView(R.id.simple_form_submit_file)
    Button simpleFormSubmitFile;
    @BindView(R.id.simple_form_down_file)
    Button simpleFormDownFile;

    RxPermissions rxPermissions;
    int grantedNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("网络请求客户端");
        rxPermissions = new RxPermissions(this);
    }

    @OnClick({R.id.simple_form_head, R.id.simple_form_get, R.id.simple_form_post, R.id.simple_form_submit_file, R.id.simple_form_down_file})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.simple_form_head:
                simpleFormHead();
                break;
            case R.id.simple_form_get:
                simpleFormGet();
                break;
            case R.id.simple_form_post:

                simpleFormPost();
                break;
            case R.id.simple_form_submit_file:
                grantedNum = 0;
                rxPermissions
                    .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) throws Exception {
                            if (permission.granted) {
                                // `permission.name` is granted !
                                grantedNum ++;
                                if(grantedNum == 2){
                                    simpleMultipartFileUpload();
                                }
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                // Denied permission without ask never again
                            } else {
                                // Denied permission with ask never again
                                // Need to go to the settings
                            }
                        }
                    });

                break;
            case R.id.simple_form_down_file:
                break;
        }
    }

    private void simpleMultipartFileUpload() {



        List<MultipartBody.Part> parts = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory(),"userimg1.png");
        Log.e(TAG, "simpleMultipartFileUpload: 文件：" + file.getAbsolutePath() + "存在：" + file.exists());
        RequestBody requestBody = RequestBody.create(MediaType.parse("octet-stream"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file","userimg1.png",requestBody);

        File file2 = new File(Environment.getExternalStorageDirectory(),"userimg2.png");
        Log.e(TAG, "simpleMultipartFileUpload: 文件：" + file2.getAbsolutePath() + "存在：" + file2.exists());
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("octet-stream"),file2);
        MultipartBody.Part part2 = MultipartBody.Part.createFormData("file","userimg2.png",requestBody2);
        parts.add(part);
        parts.add(part2);

        Flowable<Response<ResponseBody<UpFilePathsEntity>>> flowable = RetrofitS.getInstance().getClient().simpleMultipartFileUpload("userid",parts);

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<ResponseBody<UpFilePathsEntity>>() {
                    @Override
                    public void onNexto(ResponseBody<UpFilePathsEntity> responseBody) {
                        if(responseBody != null && responseBody.isSuccessful()){
                            Log.i(TAG, "onNexto-> body: \n" + responseBody.body().toString());
                        }else {
                            if(responseBody != null){
                                Log.e(TAG, "onNexto -> errMsg:" + responseBody.getErrMsg() );
                            }else {
                                Log.e(TAG, "onNexto : responseBody == null" );
                            }
                        }
                    }

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });

    }

    private void simpleFormPost() {

        Flowable<Response<ResponseBody<UserEntity>>> flowable = RetrofitS.getInstance().getClient().simplePostLogin("alan","123123");

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<ResponseBody<UserEntity>>() {
                    @Override
                    public void onNexto(ResponseBody<UserEntity> responseBody) {
                        if(responseBody != null && responseBody.isSuccessful()){
                            Log.i(TAG, "onNexto-> body: \n" + responseBody.body().toString());
                        }else {
                            if(responseBody != null){
                                Log.e(TAG, "onNexto -> errMsg:" + responseBody.getErrMsg() );
                            }else {
                                Log.e(TAG, "onNexto : responseBody == null" );
                            }
                        }
                    }

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });

    }

    private void simpleFormGet() {
        Flowable<Response<ResponseBody<UserEntity>>> flowable = RetrofitS.getInstance().getClient().simpleGetUserInfo("001");

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<ResponseBody<UserEntity>>() {
                    @Override
                    public void onNexto(ResponseBody<UserEntity> responseBody) {
                        if(responseBody != null && responseBody.isSuccessful()){
                            Log.i(TAG, "onNexto-> body: \n" + responseBody.body().toString());
                        }else {
                            if(responseBody != null){
                                Log.e(TAG, "onNexto -> errMsg:" + responseBody.getErrMsg() );
                            }else {
                                Log.e(TAG, "onNexto : responseBody == null" );
                            }
                        }
                    }

                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });

    }

    private void simpleFormHead() {
        Flowable<Response<Void>> flowable = RetrofitS.getInstance().getClient().simpleHeadFileSize("android-studio-ide-171.4408382-windows.exe");

        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Void>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Response<Void> response) {
                       if(response.isSuccessful()){
                           Log.i(TAG, "onNext-> filesize:" + response.headers().get("filesize"));
                       }else {
                           Log.e(TAG, "onNext-> code:" + response.code());
                           Log.e(TAG, "onNext-> msg:" + response.message());
                           try {
                               Log.e(TAG, "onNext-> headers:" + response.headers().toString());
                               Log.e(TAG, "onNext-> errorBody:" + response.errorBody().string());
                               Log.e(TAG, "onNext-> message:" + response.message());
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });

    }
}
