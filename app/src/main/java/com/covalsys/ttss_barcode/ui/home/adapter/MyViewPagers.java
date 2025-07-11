package com.covalsys.ttss_barcode.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.covalsys.ttss_barcode.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by CBS on 10-07-2020.
 */
public class MyViewPagers extends PagerAdapter {

    private final Context context;
    public MyViewPagers(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public @NotNull Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pager_image, null);
        ImageView imageView = view.findViewById(R.id.image);

        Glide.with(context).load(ResourcesCompat.getDrawable(context.getResources(), getImageAt(position), null))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

        container.addView(view);
        return view;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @Override
    public void destroyItem(@NotNull View container, int position, @NotNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    private int getImageAt(int position) {
        switch (position) {
            case 1:
                return R.drawable.img2;
            case 2:
                return R.drawable.img3;
            default:
                return R.drawable.img1;
        }
    }

}
