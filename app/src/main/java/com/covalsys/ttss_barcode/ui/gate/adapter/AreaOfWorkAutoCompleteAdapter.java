package com.covalsys.ttss_barcode.ui.gate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.network.models.get.gate.AreaOfWorkModel;

import java.util.ArrayList;
import java.util.List;

public class AreaOfWorkAutoCompleteAdapter extends ArrayAdapter<AreaOfWorkModel.Result> {

    private final Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((AreaOfWorkModel.Result) resultValue).getName();
        }
    };
    ArrayList<AreaOfWorkModel.Result> itemArrayList;

    public AreaOfWorkAutoCompleteAdapter(@NonNull Context context, List<AreaOfWorkModel.Result> list) {
        super(context, 0, list);
        itemArrayList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_layout, parent, false);
        }

        TextView text = convertView.findViewById(R.id.spinner_item_layout_tv_text);

        AreaOfWorkModel.Result spinnerItem = getItem(position);
        if (spinnerItem != null) {
            text.setText(spinnerItem.getName());
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return itemFilter;
    }
}
