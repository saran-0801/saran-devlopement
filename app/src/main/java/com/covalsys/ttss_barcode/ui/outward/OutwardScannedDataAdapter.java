package com.covalsys.ttss_barcode.ui.outward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.ttss_barcode.data.database.entities.OutwardMaster;
import com.covalsys.ttss_barcode.databinding.OutwardPalletAdapterLayoutBinding;
import com.covalsys.ttss_barcode.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Prasanth Muthu on 16-10-2020.
 */
public class OutwardScannedDataAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private Callback mCallback;
    private List<OutwardMaster> outList;
    private Context mContext;

    public List<OutwardMaster> getOutwardList() {
        return outList;
    }

    public interface Callback {
        void onOutCheckBoxUpdate(int position ,OutwardMaster models, boolean flag);
    }

    public OutwardScannedDataAdapter(List<OutwardMaster> mList, Context context) {
        this.outList = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        OutwardPalletAdapterLayoutBinding outwardAdapterLayoutBinding = OutwardPalletAdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(outwardAdapterLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return outList.size();
    }

    public void addCustomers(List<OutwardMaster> list) {
        outList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearList() {
        outList.clear();
    }

    public class ViewHolder extends BaseViewHolder {

        OutwardPalletAdapterLayoutBinding mBinding;

        public ViewHolder(OutwardPalletAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            OutwardMaster model = outList.get(position);

            mBinding.palletInfo.setText(model.getPalletCode());
            mBinding.palletLocationInfo.setText(model.getPalletLocationCode());
            mBinding.series.setText(String.valueOf(position+1));
            //mBinding.series.setText(String.valueOf(model.getSlno()));
            mBinding.outCheckBox.setChecked(model.getChBox());
            //mBinding.cardView.setOnClickListener(v -> mCallback.onPCustomerClick(position,model));
            mBinding.outCheckBox.setOnClickListener(v -> {
                if(mBinding.outCheckBox.isChecked()){
                    model.setChBox(true);
                    outList.set(position, model);
                    //mCallback.onOutCheckBoxUpdate(position, model, true);
                }else{
                    model.setChBox(false);
                    outList.set(position, model);
                    //mCallback.onOutCheckBoxUpdate(position, model, false);
                }
            });
        }
    }

    public void setOnClick(Callback onClick) {
        this.mCallback = onClick;
    }
}
