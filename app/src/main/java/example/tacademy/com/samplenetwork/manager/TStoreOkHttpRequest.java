package example.tacademy.com.samplenetwork.manager;

import com.google.gson.Gson;

import example.tacademy.com.samplenetwork.autodata.TStore;
import example.tacademy.com.samplenetwork.autodata.TStoreResult;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * Created by Tacademy on 2016-08-09.
 */
public class TStoreOkHttpRequest extends OkHttpRequest<TStore> {
    Request request;

    public static final String SORT_ACCURACY = "R";
    public static final String SORT_LATEST = "L";
    public static final String SORT_DOWNLOAD = "D";

    public TStoreOkHttpRequest(String keyword){
        this(keyword,1,10,SORT_LATEST);
    }

    public TStoreOkHttpRequest(String keyword,int page, int count, String sort){
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("apis.skplanetx.com")
                .addPathSegment("tstore")
                .addPathSegment("products")
                .addQueryParameter("version","1")
                .addQueryParameter("page","" + page)
                .addQueryParameter("count","" + count)
                .addQueryParameter("searchKeyword", keyword)
                .addQueryParameter("order",sort)
                .build();

        request = new Request.Builder()
                .url(url)
                .header("Accept","application/json")
                .header("appKey","9e719482-ed73-3153-9f91-5bcb75e149d5")
                .build();
    }
    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    protected TStore parse(ResponseBody body) {
        Gson gson = new Gson();
        TStoreResult result = gson.fromJson(body.charStream(),TStoreResult.class);
        return result.getTstore();
    }
}
