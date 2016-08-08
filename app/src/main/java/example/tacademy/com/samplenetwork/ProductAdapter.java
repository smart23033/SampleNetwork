package example.tacademy.com.samplenetwork;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import example.tacademy.com.samplenetwork.autodata.Product;
import example.tacademy.com.samplenetwork.autodata.ProductView;

/**
 * Created by Tacademy on 2016-08-05.
 */
public class ProductAdapter extends BaseAdapter {
    List<Product> items = new ArrayList<>();

    public void addAll(Product[] items){
        this.items.addAll(Arrays.asList(items));
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductView view;

        if(convertView == null){
            view = new ProductView(parent.getContext());
        }else{
            view = (ProductView)convertView;
        }
        view.setProduct(items.get(position));
        return view;
    }
}
