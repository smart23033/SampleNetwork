package example.tacademy.com.samplenetwork.autodata;

import com.google.gson.annotations.SerializedName;

public class Products {

    @SerializedName("product")
    private Product[] productList;

    public Product[] getProductList() {
        return this.productList;
    }

    public void setProductList(Product[] productList) {
        this.productList = productList;
    }
}
