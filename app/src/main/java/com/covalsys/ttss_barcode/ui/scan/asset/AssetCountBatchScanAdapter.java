package com.covalsys.ttss_barcode.ui.scan.asset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.database.entities.AssetCountScanLine;
import com.covalsys.ttss_barcode.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AssetCountBatchScanAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<AssetCountScanLine> mModel;
    private Context mContext;
    private Callback mCallback;

    public AssetCountBatchScanAdapter(ArrayList<AssetCountScanLine> mInvoiceModel, Context mContext) {
        this.mModel = mInvoiceModel;
        this.mContext = mContext;
    }

    public void setOnClick(Callback onClick) {
        this.mCallback = onClick;
    }

    public interface Callback {
        void onDelete(int position , AssetCountScanLine models);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_asset_batch_list_adapter_layout, parent, false);
        return new InvoiceViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    public List<AssetCountScanLine> getList() {
        return mModel;
    }

    public void addData(List<AssetCountScanLine> list) {
        this.mModel = list;
    }

    public void clearList() {
        mModel.clear();
    }

    public class InvoiceViewHolder extends BaseViewHolder {
        public AppCompatTextView tvSlno, tvItemCode;
        ImageView imDel;
        LinearLayout layView;

        public InvoiceViewHolder(View itemView) {
            super(itemView);
            tvSlno = itemView.findViewById(R.id.tvSlnpo);
            imDel = itemView.findViewById(R.id.imDelete);
            //tvRemove = itemView.findViewById(R.id.tvRemove);
            tvItemCode = itemView.findViewById(R.id.tvItemCode);
            layView = itemView.findViewById(R.id.layView);
        }

        @Override
        public void onBind(int position) {

                AssetCountScanLine model = mModel.get(position);
                tvSlno.setText((position+1)+"");
                tvItemCode.setText(String.valueOf(model.getItemcode()));

                layView.setBackgroundResource(R.drawable.table_bg);
                tvSlno.setTextColor(mContext.getResources().getColor(R.color.text_black_87));
                tvItemCode.setTextColor(mContext.getResources().getColor(R.color.text_black_87));

                imDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onDelete(position, model);
                    }
                });

        }
    }
}
