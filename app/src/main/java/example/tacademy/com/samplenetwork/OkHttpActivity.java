package example.tacademy.com.samplenetwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import example.tacademy.com.samplenetwork.autodata.Product;
import example.tacademy.com.samplenetwork.autodata.TStore;
import example.tacademy.com.samplenetwork.manager.OkHttpNetworkManager;
import example.tacademy.com.samplenetwork.manager.OkHttpRequest;
import example.tacademy.com.samplenetwork.manager.TStoreOkHttpRequest;

public class OkHttpActivity extends AppCompatActivity {

    @BindView(R.id.edit_input)
    EditText keywordView;

    @BindView(R.id.list_tstore)
    ListView listView;

    //    ArrayAdapter<Product> mAdapter;
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
            TStoreOkHttpRequest request = new TStoreOkHttpRequest(keyword);
            OkHttpNetworkManager.getInstance().getNetworkData(request, new OkHttpNetworkManager.OnResultListener<TStore>() {
                @Override
                public void onSuccess(OkHttpRequest<TStore> request, TStore result) {
                    mAdapter.addAll(result.getProducts().getProductList());
                }

                @Override
                public void onFail(OkHttpRequest<TStore> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(OkHttpActivity.this, "message : " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @OnItemClick(R.id.list_tstore)
    public void onProductItemClick(AdapterView<?> parent, View view, int position, long id) {
        Product p = (Product)listView.getItemAtPosition(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PRODUCT_ID, p.getProductId());
        startActivity(intent);
    }
}
