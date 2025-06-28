package com.covalsys.ttss_barcode.ui.stock_counting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.data.database.entities.StockCountLine;
import com.covalsys.ttss_barcode.databinding.EmptyAdapterBinding;
import com.covalsys.ttss_barcode.databinding.StockCountListAdapterLayoutBinding;
import com.covalsys.ttss_barcode.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Prasanth on 16-10-2020.
 */
public class StockCountingAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private Callback mCallback;
    private List<StockCountLine> inList;
    private String status;
    private Context mContext;
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    public interface Callback {
        void onPCustomerClick(int position , StockCountLine models);
    }

    public StockCountingAdapter(List<StockCountLine> mList, Context context) {
        this.inList = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                StockCountListAdapterLayoutBinding deliveryAdapterLayoutBinding = StockCountListAdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        if (inList != null && inList.size() > 0) {
            return inList.size();
        } else {
            return 1;
        }
    }

    public void addList(List<StockCountLine> list, String status) {
        this.inList = list;
        this.status = status;
    }

    @Override
    public int getItemViewType(int position) {
        if (inList != null && !inList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void clearList() {
        inList.clear();
    }

    public class ViewHolder extends BaseViewHolder {

        StockCountListAdapterLayoutBinding mBinding;

        public ViewHolder(StockCountListAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

                StockCountLine model = inList.get(position);
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
