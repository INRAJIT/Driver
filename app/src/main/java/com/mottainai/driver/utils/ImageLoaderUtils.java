package com.mottainai.driver.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mottainai.driver.R;

public class ImageLoaderUtils {

    private static volatile ImageLoaderUtils instance;

    public static ImageLoaderUtils getInstance() {
        if(instance == null) {
            instance = new ImageLoaderUtils();
        }
        return instance;
    }

    public void updateProfileImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.mipmap.ic_launcher_round)
                .into(imageView);
    }

    public void loadImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }
}
