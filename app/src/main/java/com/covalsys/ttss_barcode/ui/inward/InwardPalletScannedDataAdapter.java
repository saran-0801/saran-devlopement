package com.covalsys.ttss_barcode.ui.inward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.ttss_barcode.data.database.entities.Pallet;
import com.covalsys.ttss_barcode.databinding.InwardPalletAdapterLayoutBinding;
import com.covalsys.ttss_barcode.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by CBS on 16-10-2020.
 */
public class InwardPalletScannedDataAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private Callback mCallback;
    private List<Pallet> inList;
    private Context mContext;

    public interface Callback {
        void onPCustomerClick(int position ,Pallet models);
    }

    public InwardPalletScannedDataAdapter(List<Pallet> mList, Context context) {
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

    public void addCustomers(List<Pallet> list) {
        inList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearList() {
        inList.clear();
    }

   /* @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mFilterdCustomersList = customersList;
                } else {
                    List<CustomerDetailsModel.ResponseObject> filteredList = new ArrayList<>();
                    for (CustomerDetailsModel.ResponseObject row : customersList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for Name or Mobile Number match
                        if (row.getCardName().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getCardCode().contains(constraint)) {
                            filteredList.add(row);
                        }
                    }
                    mFilterdCustomersList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterdCustomersList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilterdCustomersList = (List<CustomerDetailsModel.ResponseObject>) results.values;
                notifyDataSetChanged();
            }
        };
    }
*/
    public class ViewHolder extends BaseViewHolder {

       InwardPalletAdapterLayoutBinding mBinding;

        public ViewHolder(InwardPalletAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            Pallet model = inList.get(position);

            mBinding.palletInfo.setText(model.getPalletCode());
            mBinding.series.setText(String.valueOf(model.getSlno()));
            mBinding.cardView.setOnClickListener(v -> mCallback.onPCustomerClick(position,model));
        }
    }

    public void setOnClick(Callback onClick) {
        this.mCallback = onClick;
    }
}
