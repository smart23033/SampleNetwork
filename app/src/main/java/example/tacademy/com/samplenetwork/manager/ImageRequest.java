package example.tacademy.com.samplenetwork.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Tacademy on 2016-08-08.
 */
public class ImageRequest extends NetworkRequest<Bitmap> {

    String url;

    public ImageRequest(String url) {
        this.url = url;
    }

    @Override
    public URL getURL() throws MalformedURLException {
        return new URL(url);
    }

    @Override
    protected Bitmap parse(InputStream is) {
//        주석달린 부분을 통해 원래 이미지 사이즈를 줄이는건데, 이렇게 안하고 라이브러리 쓰면 된다.
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inJustDecodeBounds = true;
//        Rect outrect =new Rect();
//        BitmapFactory.decodeStream(is, outrect, opts);
//        opts.inSampleSize = 2;

//        Bitmap bm = BitmapFactory.decodeStream(is, null, opts);

        Bitmap bm = BitmapFactory.decodeStream(is);
        return bm;
    }
}
