package example.tacademy.com.samplenetwork.manager;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import example.tacademy.com.samplenetwork.autodata.Product;
import example.tacademy.com.samplenetwork.autodata.TStoreProductDetail;

/**
 * Created by Tacademy on 2016-08-08.
 */
public class TStoreDetailRequest extends NetworkRequest<Product>{
    private static final String TSTORE_DETAIL_URL = "http://apis.skplanetx.com/tstore/products/%s?version=1";
    String url;

    public TStoreDetailRequest(String productId){
        url = String.format(TSTORE_DETAIL_URL,productId);
    }

    @Override
    public URL getURL() throws MalformedURLException {
        return new URL(url);
    }

    @Override
    protected void setRequestProperty(HttpURLConnection conn) {
        super.setRequestProperty(conn);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("appKey","9e719482-ed73-3153-9f91-5bcb75e149d5");
    }

    @Override
    protected Product parse(InputStream is) {
        InputStreamReader reader = new InputStreamReader(is);
        Gson gson = new Gson();
        TStoreProductDetail result = gson.fromJson(reader,TStoreProductDetail.class);
        result.getTstore().getProduct().makePreviewUrlList();
        return result.getTstore().getProduct();
    }
}
