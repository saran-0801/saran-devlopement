package com.covalsys.ttss_barcode.ui.asset_tag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.database.entities.AssetCountLine;
import com.covalsys.ttss_barcode.databinding.AssetCountListAdapterLayoutBinding;
import com.covalsys.ttss_barcode.databinding.EmptyAdapterBinding;
import com.covalsys.ttss_barcode.ui.base.BaseViewHolder;

import java.util.List;

public class AssetCountingAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Callback mCallback;
    private List<AssetCountLine> inList;
    private String status;
    private Context mContext;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public String _UserCode;

    public interface Callback {
        void onPCustomerClick(int position , AssetCountLine models);
    }

    public AssetCountingAdapter(List<AssetCountLine> mList, Context context) {
        this.inList = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                AssetCountListAdapterLayoutBinding deliveryAdapterLayoutBinding = AssetCountListAdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new ViewHolder(deliveryAdapterLayoutBinding);
            case VIEW_TYPE_EMPTY:
            default:
                EmptyAdapterBinding emptyViewBinding = EmptyAdapterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (inList != null && !inList.isEmpty()) {
            return inList.size();
        } else {
            return 1;
        }
    }



    public void addList(List<AssetCountLine> list, String status) {
        this.inList = list;
        this.status = status;
    }

    public void clearList() {
        inList.clear();
    }

    @Override
    public int getItemViewType(int position) {
        if (inList != null && !inList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public class ViewHolder extends BaseViewHolder {

        AssetCountListAdapterLayoutBinding mBinding;

        public ViewHolder(AssetCountListAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            AssetCountLine model = inList.get(position);
            mBinding.series.setText(String.valueOf(position+1));
            mBinding.actQty.setText(String.valueOf(model.getActQty()+""));
            mBinding.sysQty.setText(String.valueOf(model.getSysQty()+""));
            mBinding.itemCode.setText(String.valueOf(model.getItemCode()));
            mBinding.itemName.setText(String.valueOf(model.getItemName()));
            mBinding.scanDate.setText(String.valueOf(model.getScanDate()));

            if(model.getStatus().equalsIgnoreCase("A")){
                mBinding.lineStatus.setText("Active");
                mBinding.lineStatus.setTextColor(mContext.getResources().getColor(R.color.green_dark));
            }else{
                mBinding.lineStatus.setText("Pending");
                mBinding.lineStatus.setTextColor(mContext.getResources().getColor(R.color.buttonHighlight));
            }

            mBinding.series.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            mBinding.actQty.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            mBinding.sysQty.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            mBinding.itemCode.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            mBinding.itemName.setTextColor(mContext.getResources().getColor(R.color.gray_dark));
            mBinding.scanDate.setTextColor(mContext.getResources().getColor(R.color.gray_dark));

            if(status.equalsIgnoreCase("O")){
                mBinding.actQty.setClickable(true);
                mBinding.actQty.setOnClickListener(v -> mCallback.onPCustomerClick(position, model));
                mBinding.actQty.setBackgroundResource(R.drawable.table_bg);
            }else{
                mBinding.actQty.setClickable(false);
            }
        }
    }

    public static class EmptyViewHolder extends BaseViewHolder{

        private EmptyAdapterBinding mBinding;

        public EmptyViewHolder(EmptyAdapterBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            this.mBinding.imageViewEmpty.setBackgroundResource(R.drawable.ic_undraw_empty);
        }
    }

    public void setOnClick(Callback onClick) {
        this.mCallback = onClick;
    }
}
