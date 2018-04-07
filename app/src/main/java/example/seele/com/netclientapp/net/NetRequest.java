package example.seele.com.netclientapp.net;

import java.util.List;
import java.util.Map;

import example.seele.com.netclientapp.entity.ResponseBody;
import example.seele.com.netclientapp.entity.UpFilePathsEntity;
import example.seele.com.netclientapp.entity.UserEntity;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by SEELE on 2018/4/6.
 */

public interface NetRequest {

    @HEAD("/simpleHeadFileSize")
//    @FormUrlEncoded
    //HEAD method must use Void as response type.
    Flowable<Response<Void>> simpleHeadFileSize(@Query("filename") String filname);

    @GET("/simpleGetUserInfo")
//    @FormUrlEncoded(only set on post)
    Flowable<Response<ResponseBody<UserEntity>>> simpleGetUserInfo(@Query("id") String id);


    @POST("/simplePostLogin")
    @FormUrlEncoded
    Flowable<Response<ResponseBody<UserEntity>>> simplePostLogin(@Field("name") String name,@Field("passwd") String passwd);


    @POST("/simpleMultipartFileUpload")
    @Multipart()
//    @FormUrlEncoded //java.lang.IllegalArgumentException: Only one encoding annotation is allowed.
      //上传一个文件
//    Flowable<Response<ResponseBody<UpFilePathsEntity>>> simpleMultipartFileUpload(@Part("userid") String id, @Part MultipartBody.Part file);
    Flowable<Response<ResponseBody<UpFilePathsEntity>>> simpleMultipartFileUpload(@Part("userid") String id, @Part List<MultipartBody.Part> files);

    @POST("/simpleMultipartFileUpload")
    @Multipart()
//    @FormUrlEncoded  java.lang.IllegalArgumentException: Only one encoding annotation is allowed.
    Flowable<ResponseBody<ResponseBody<String>>> simpleMultipartFileUpload(@FieldMap Map<String,String> params, MultipartBody.Part file);

}
