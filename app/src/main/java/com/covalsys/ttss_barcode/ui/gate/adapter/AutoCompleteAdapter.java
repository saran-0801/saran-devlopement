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
import com.covalsys.ttss_barcode.data.network.models.get.gate.SpinnerItem;

import java.util.ArrayList;

public class AutoCompleteAdapter extends ArrayAdapter<SpinnerItem> {

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
            return ((SpinnerItem) resultValue).getText();
        }
    };
    ArrayList<SpinnerItem> itemArrayList;

    public AutoCompleteAdapter(@NonNull Context context, ArrayList<SpinnerItem> list) {
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

        SpinnerItem spinnerItem = getItem(position);
        if (spinnerItem != null) {
            text.setText(spinnerItem.getText());
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return itemFilter;
    }
}
