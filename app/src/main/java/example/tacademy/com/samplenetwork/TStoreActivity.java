package example.tacademy.com.samplenetwork;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import example.tacademy.com.samplenetwork.autodata.Product;
import example.tacademy.com.samplenetwork.autodata.TStoreResult;

public class TStoreActivity extends AppCompatActivity {

    @BindView(R.id.edit_input)
    EditText keywordView;

    @BindView(R.id.list_tstore)
    ListView listView;

    ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tstore);

        ButterKnife.bind(this);

        mAdapter = new ProductAdapter();

        listView.setAdapter(mAdapter);
    }

    @OnClick(R.id.btn_search)
    public void onSearch(View view){
        String keyword = keywordView.getText().toString();
        if(!TextUtils.isEmpty(keyword)){
            new TStoreSearchTask().execute(keyword);
        }
    }

    @OnItemClick(R.id.list_tstore)
    public void onProductItemClick(AdapterView<?> parent, View view, int position, long id){
        Product p = (Product)listView.getItemAtPosition(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(p.getTinyUrl()));
        startActivity(intent);
    }

//    url은  가이드 문서에 있다
    private static final String TSTORE_URL = "http://apis.skplanetx.com/tstore/products?version=1&page=1&count=10&searchKeyword=%s&order=L";

    class TStoreSearchTask extends AsyncTask<String,Integer,TStoreResult>{

        @Override
        protected TStoreResult doInBackground(String... strings) {
            String keyword = strings[0];
            try {
                String urlText = String.format(TSTORE_URL, URLEncoder.encode(keyword,"utf-8"));
                URL url = new URL(urlText);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Accept","application/json");
                conn.setRequestProperty("appKey","9e719482-ed73-3153-9f91-5bcb75e149d5");
                int code = conn.getResponseCode();
                if (code >= 200 && code <300){
                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                    Gson쓰면 아래 코드 다 없어도 된다! 참고로 parseJson은 내가 만들어준 코드였음
//                    인터페이스만들어서 오버라이딩 받았던거임.
//                    StringBuilder sb = new StringBuilder();
//                    String line;
//                    while((line=br.readLine()) != null) {
//                        sb.append(line).append("\r\n");
//                    }
//                    TStoreResult result = new TStoreResult();
//                    JSONObject jobject = new JSONObject(sb.toString());
//                    result.parseJson(jobject);
                    Gson gson = new Gson();
                    TStoreResult result = gson.fromJson(br,TStoreResult.class);
                    return result;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(TStoreResult s) {
            super.onPostExecute(s);
            if( s != null){
                mAdapter.addAll(s.getTstore().getProducts().getProductList());
            }else{
                Toast.makeText(TStoreActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
