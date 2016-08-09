package example.tacademy.com.samplenetwork.autodata;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.tacademy.com.samplenetwork.R;
import example.tacademy.com.samplenetwork.URLImageView;

/**
 * Created by Tacademy on 2016-08-05.
 */
public class ProductView extends FrameLayout {
    public ProductView(Context context) {
        this(context,null);
    }

    @BindView(R.id.image_thumbnail)
    ImageView thumbView;
    @BindView(R.id.text_name)
    TextView nameView;
    @BindView(R.id.text_like)
    TextView likeView;
    @BindView(R.id.text_download)
    TextView downloadView;
    @BindView(R.id.text_description)
    TextView descriptionView;

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_product,this);
        ButterKnife.bind(this);
    }

    Product product;
    public void setProduct(Product product) {
        this.product = product;
        nameView.setText(product.getName());
        likeView.setText("" + product.getScore());
        downloadView.setText("" + product.getDownloadCount());
        descriptionView.setText(product.getDescription());
        Picasso.with(getContext())
                .load(product.getThumbnailUrl())
                .placeholder(R.drawable.ic_stub)
                .error(R.drawable.ic_error)
                .into(thumbView);
    }

}
