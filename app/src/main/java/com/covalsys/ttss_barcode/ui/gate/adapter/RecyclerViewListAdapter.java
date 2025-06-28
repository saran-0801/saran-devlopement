package com.covalsys.ttss_barcode.ui.gate.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.network.models.BarcodeList.ResultItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickHeaderItemDecoration.StickyHeaderInterface {

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private List<ResultItem> itemArrayList = new ArrayList<>();
    private Context context;

    public void setData(Context cxt, List<ResultItem> list) {
        itemArrayList = list;
        context = cxt;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            // Here Inflating your recyclerview item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout, parent, false);
            return new MyHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            // Here Inflating your header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_header_layout, parent, false);
            return new HeaderViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder commonHolder, int position) {

        try {
            if (commonHolder instanceof MyHolder && position != 0) {

                final MyHolder holder = (MyHolder) commonHolder;
                ResultItem resultItem = itemArrayList.get(position);
                if (position % 2 == 1) {
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    holder.itemView.setBackgroundColor(Color.parseColor("#F8F8F8"));
                }
                if (resultItem.getStatus().equalsIgnoreCase("Released")) {
                    holder.tvStatus.setTextColor(Color.parseColor("#26C24B"));
                    holder.tvStatus.setText("Released");
                } else {
                    holder.tvStatus.setTextColor(Color.parseColor("#E2AA3B"));
                    holder.tvStatus.setText("Pending");
                }
                holder.tvSlNo.setText(position + "");
                holder.tvDocNo.setText(resultItem.getDocNum());
                holder.tvItemCode.setText(resultItem.getItemcode());
                holder.tvDescription.setText(resultItem.getDescription());
                holder.tvSize.setText(resultItem.getSize());
                holder.tvUom.setText(resultItem.getUom());
                holder.tvSpecification.setText(resultItem.getSpec());
                holder.tvCoat.setText(resultItem.getCoat());
                holder.tvMillCode.setText(resultItem.getMillCode());
                holder.tvCustPartNo.setText(resultItem.getPartNo());
                holder.tvBatchNo.setText(resultItem.getBatchnum());
                holder.tvNetWt.setText(String.valueOf(resultItem.getQuantity()));
                holder.tvGrossWt.setText(resultItem.getGrosswt() + "");
                holder.tvPcs.setText(resultItem.getPcs() + "");

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
        return itemArrayList.size();
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
            return R.layout.recyclerview_header_layout;
        } else {
            return R.layout.recyclerview_item_layout;
        }
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {

    }

    @Override
    public boolean isHeader(int itemPosition) {
        return itemPosition == 0;
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvSlNo, tvDocNo, tvItemCode, tvDescription, tvSize, tvUom, tvSpecification, tvCoat, tvMillCode, tvCustPartNo, tvBatchNo, tvNetWt, tvGrossWt, tvPcs, tvStatus;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvSlNo = itemView.findViewById(R.id.recyclerview_item_layout_tv_sl_no);
            tvDocNo = itemView.findViewById(R.id.recyclerview_item_layout_tv_doc_no);
            tvItemCode = itemView.findViewById(R.id.recyclerview_item_layout_tv_item_code);
            tvDescription = itemView.findViewById(R.id.recyclerview_item_layout_tv_description);
            tvSize = itemView.findViewById(R.id.recyclerview_item_layout_tv_size);
            tvUom = itemView.findViewById(R.id.recyclerview_item_layout_tv_uom);
            tvSpecification = itemView.findViewById(R.id.recyclerview_item_layout_tv_specification);
            tvCoat = itemView.findViewById(R.id.recyclerview_item_layout_tv_coat);
            tvMillCode = itemView.findViewById(R.id.recyclerview_item_layout_tv_mill_code);
            tvCustPartNo = itemView.findViewById(R.id.recyclerview_item_layout_tv_cust_part_no);
            tvBatchNo = itemView.findViewById(R.id.recyclerview_item_layout_tv_batch_no);
            tvNetWt = itemView.findViewById(R.id.recyclerview_item_layout_tv_net_weight);
            tvGrossWt = itemView.findViewById(R.id.recyclerview_item_layout_tv_gross_weight);
            tvPcs = itemView.findViewById(R.id.recyclerview_item_layout_tv_pcs);
            tvStatus = itemView.findViewById(R.id.recyclerview_item_layout_tv_status);
        }
    }

    private class HeaderViewHolder extends MyHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
