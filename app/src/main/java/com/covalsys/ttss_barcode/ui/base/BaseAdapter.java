package com.covalsys.ttss_barcode.ui.base;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by CBS on 09-07-2020.
 */
public abstract class BaseAdapter<T extends RecyclerView.ViewHolder, D> extends RecyclerView.Adapter<T> {

    public abstract void setData(List<D> data);
}
