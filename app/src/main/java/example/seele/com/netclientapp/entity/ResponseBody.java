package example.seele.com.netclientapp.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SEELE on 2018/4/6.
 */

public class ResponseBody<T> {

    @SerializedName("err_code")
    private int errCode = -1;

    @SerializedName("err_message")
    private String errMsg;

    @SerializedName("data")
    private T t;

    public boolean isSuccessful(){
        if(errCode == 0){
            return true;
        }
        return false;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public T body() {
        return t;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "errCode=" + errCode +
                ", errMsg=" + errMsg +
                ", t=" + t +
                '}';
    }
}
