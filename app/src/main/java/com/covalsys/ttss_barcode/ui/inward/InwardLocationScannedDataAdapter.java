package com.covalsys.ttss_barcode.ui.inward;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.ttss_barcode.data.database.entities.PalletLocation;
import com.covalsys.ttss_barcode.databinding.InwardAdapterLayoutBinding;
import com.covalsys.ttss_barcode.ui.base.BaseViewHolder;

import java.util.List;

/**
 * Created by CBS on 16-10-2020.
 */
public class InwardLocationScannedDataAdapter extends RecyclerView.Adapter<BaseViewHolder>  {

    private Callback mCallback;
    private List<PalletLocation> inList;
    private Context mContext;

    public interface Callback {
        void onPCustomerClick(int position ,PalletLocation models);
    }

    public InwardLocationScannedDataAdapter(List<PalletLocation> mList, Context context) {
        this.inList = mList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        InwardAdapterLayoutBinding inwardAdapterLayoutBinding = InwardAdapterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

    public void addCustomers(List<PalletLocation> list) {
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

       InwardAdapterLayoutBinding mBinding;

        public ViewHolder(InwardAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            PalletLocation model = inList.get(position);

            mBinding.palletInfo.setText(model.getPalletLocationCode());
            mBinding.cardView.setOnClickListener(v -> mCallback.onPCustomerClick(position,model));
        }

    }

    public void setOnClick(Callback onClick) {
        this.mCallback = onClick;
    }
}
