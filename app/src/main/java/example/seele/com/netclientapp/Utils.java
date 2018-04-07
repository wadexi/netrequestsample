package example.seele.com.netclientapp;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by SEELE on 2018/4/6.
 */

public class Utils {

    public static ResponseBody buffer(final Response response) throws IOException {
        ResponseBody responseBody = response.body();

        try{
            Buffer buffer = new Buffer();
            responseBody.source().readAll(buffer);
            return ResponseBody.create(responseBody.contentType(), responseBody.contentLength(), buffer);
        }finally {
//            responseBody.close();
        }

    }
}
