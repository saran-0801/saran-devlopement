package com.covalsys.ttss_barcode.ui.inward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.ttss_barcode.data.database.entities.InwardMaster;
import com.covalsys.ttss_barcode.databinding.InwardPalletAdapterLayoutBinding;
import com.covalsys.ttss_barcode.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Prasanth Muthu on 16-10-2020.
 */
public class InwardScannedDataAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private Callback mCallback;
    private List<InwardMaster> inList;
    private Context mContext;

    public List<InwardMaster> getInwardList() {
        return inList;
    }

    public interface Callback {
        void onInCheckBoxUpdate(int position ,InwardMaster models, boolean flag);
    }

    public InwardScannedDataAdapter(List<InwardMaster> mList, Context context) {
        this.inList = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        InwardPalletAdapterLayoutBinding inwardAdapterLayoutBinding = InwardPalletAdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(inwardAdapterLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return inList.size();
    }

    public void addCustomers(List<InwardMaster> list) {
        inList.addAll(list);
    }

    public void clearList() {
        inList.clear();
    }

    public class ViewHolder extends BaseViewHolder {

        InwardPalletAdapterLayoutBinding mBinding;

        public ViewHolder(InwardPalletAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            InwardMaster model = inList.get(position);

            mBinding.palletInfo.setText(model.getPalletCode());
            mBinding.palletLocationInfo.setText(model.getPalletLocationCode());
            mBinding.series.setText(String.valueOf(position+1));
            //mBinding.series.setText(String.valueOf(model.getSlno()));
            mBinding.inCheckBox.setChecked(model.getChBox());
            //mBinding.cardView.setOnClickListener(v -> mCallback.onPCustomerClick(position,model));
            mBinding.inCheckBox.setOnClickListener(v -> {
                if(mBinding.inCheckBox.isChecked()){
                    model.setChBox(true);
                    inList.set(position, model);
                    //mCallback.onInCheckBoxUpdate(position, model, true);
                }else{
                    model.setChBox(false);
                    inList.set(position, model);
                    //mCallback.onInCheckBoxUpdate(position, model, false);
                }
            });
        }
    }

    public void setOnClick(Callback onClick) {
        this.mCallback = onClick;
    }
}
