package com.covalsys.ttss_barcode.utils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.covalsys.ttss_barcode.R;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by CBS on 23,September,2020
 */

public class PhotoFullPopupWindow extends PopupWindow {

    View view;
    Context mContext;
    PhotoView photoView;
    ProgressBar loading;
    ViewGroup parent;
    private static PhotoFullPopupWindow instance = null;

    public PhotoFullPopupWindow(Context ctx, int layout, View v, String imageUrl, Bitmap bitmap) {
        super(((LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.popup_photo_full,
                null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        if (Build.VERSION.SDK_INT >= 21) {
            setElevation(5.0f);
        }
        this.mContext = ctx;
        this.view = getContentView();
        ImageView closeButton = this.view.findViewById(R.id.ib_close);
        ImageView deleteButton =  this.view.findViewById(R.id.ib_delete);
        deleteButton.setVisibility(View.GONE);
        setOutsideTouchable(true);

        setFocusable(true);
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(view -> {
            // Dismiss the popup window
            dismiss();
        });

        deleteButton.setOnClickListener(v1 -> {
            //delete image cache
           clearGlideCache();
            dismiss();
        });

        //Begin customising this popup
        photoView = (PhotoView) view.findViewById(R.id.image);
        loading = (ProgressBar) view.findViewById(R.id.loading);
        photoView.setMaximumScale(6);
        parent = (ViewGroup) photoView.getParent();
        //ImageUtils.setZoomable(imageView);
        if (bitmap != null) {
            loading.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= 21) {
                parent.setBackground(new BitmapDrawable(mContext.getResources(),
                        ImageUtils.fastBlur(Bitmap.createScaledBitmap(bitmap, 50, 50, true))));
               // photoView.setImageBitmap(bitmap);
            } else {
                onPalette(Palette.from(bitmap).generate());
            }
            photoView.setImageBitmap(bitmap);
        } else {
            loading.setIndeterminate(true);
            loading.setVisibility(View.VISIBLE);
            Glide.with(ctx).asBitmap()
                    .load(imageUrl)
                    .error(R.drawable.ic_add_image_100)
                    .placeholder(R.drawable.ic_add_image_100)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            loading.setIndeterminate(false);
                            loading.setBackgroundColor(Color.LTGRAY);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            if(Build.VERSION.SDK_INT >= 21){
                                parent.setBackground(new BitmapDrawable(mContext.getResources(),
                                        ImageUtils.fastBlur(Bitmap.createScaledBitmap(resource, 50, 50, true))));
                            } else {
                                onPalette(Palette.from(resource).generate());
                            }
                            photoView.setImageBitmap(resource);
                            loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(Target.SIZE_ORIGINAL)
                    .into(photoView);
            showAtLocation(v, Gravity.CENTER, 0, 0);
        }
    }

    public void onPalette(Palette palette) {
        if (null != palette) {
            ViewGroup parent = (ViewGroup) photoView.getParent().getParent();
            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
        }
    }

    public void clearGlideCache() {
        new Handler().postDelayed(() -> Glide.get(mContext).clearMemory(), 0);
        AsyncTask.execute(() -> Glide.get(mContext).clearDiskCache());
    }
}
