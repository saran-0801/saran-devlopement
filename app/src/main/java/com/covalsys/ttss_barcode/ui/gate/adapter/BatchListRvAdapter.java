package com.covalsys.ttss_barcode.ui.gate.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.ttss_barcode.R;

import java.util.ArrayList;
import java.util.List;

public class BatchListRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickHeaderItemDecoration.StickyHeaderInterface {

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private List<String> stringList = new ArrayList<>();
    private Context context;

    private OnItemClickListener listener;

    public void setData(Context cxt, List<String> list) {
        stringList = list;
        context = cxt;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            // Here Inflating your recyclerview item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.batch_list_item_layout, parent, false);
            return new MyHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            // Here Inflating your header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.batch_list_header_layout, parent, false);
            return new HeaderViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder commonHolder, int position) {

        try {
            if (commonHolder instanceof MyHolder && position != 0) {

                final MyHolder holder = (MyHolder) commonHolder;
                String resultItem = stringList.get(position);
                if (position % 2 == 1) {
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    holder.itemView.setBackgroundColor(Color.parseColor("#F8F8F8"));
                }

                holder.tvSlNo.setText(position + "");
                holder.tvBatchNo.setText(resultItem);

            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;
        do {
            if (this.isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition -= 1;
        } while (itemPosition >= 0);
        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        if (headerPosition == 0) {
            return R.layout.batch_list_header_layout;
        } else {
            return R.layout.batch_list_item_layout;
        }
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {

    }

    @Override
    public boolean isHeader(int itemPosition) {
        return itemPosition == 0;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    private class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvSlNo, tvBatchNo;
        public ImageView ivDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            try {
                tvSlNo = itemView.findViewById(R.id.batch_list_item_layout_tv_sl_no);
                tvBatchNo = itemView.findViewById(R.id.batch_list_item_layout_tv_batch_no);
                ivDelete = itemView.findViewById(R.id.batch_list_item_layout_iv_delete);

                ivDelete.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class HeaderViewHolder extends MyHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
