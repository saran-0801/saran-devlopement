package com.covalsys.ttss_barcode.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import com.covalsys.ttss_barcode.BR;
import com.covalsys.ttss_barcode.R;
import com.covalsys.ttss_barcode.databinding.FragmentHomeBinding;
import com.covalsys.ttss_barcode.ui.base.BaseFragment;
import com.covalsys.ttss_barcode.ui.gate.GateActivity;

public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> implements HomeNavigator {

    public static final String TAG = HomeFragment.class.getSimpleName();

    @Override
    public int getBindingVariable() {
        return BR.homeViewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        viewModel.setNavigator(this);
        setUp();
        return dataBinding.getRoot();
    }

    private void setUp() {
        /*dataBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPrimaryAdapter.getFilter().filter(newText);
                return true;
            }
        });*/

       /* dataBinding.itemViewPager.setAdapter(new MyViewPagers(getActivity()));
        dataBinding.itemViewPager.startAutoScroll();
        dataBinding.itemViewPager.setCycle(true);*/
        //dataBinding.indicator.setViewPager(dataBinding.itemViewPager);

        /*LinearLayoutManager primaryManager = new LinearLayoutManager(getActivity());
        primaryManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dataBinding.primaryRecyclerView.setLayoutManager(primaryManager);
        dataBinding.primaryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        dataBinding.primaryRecyclerView.setAdapter(mPrimaryAdapter);*/

       /* dataBinding.tvPrimaryMore.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isPrimary", true);
            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_customers, bundle);
        });

        dataBinding.tvSecondaryMore.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isPrimary", false);
            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_customers, bundle);
        });*/

        if(viewModel.getPreferenceHelper().getUserDep().equalsIgnoreCase("SKID")){
            dataBinding.palletInward.setVisibility(View.VISIBLE);
            dataBinding.palletOutward.setVisibility(View.VISIBLE);
            dataBinding.layFirst.setVisibility(View.VISIBLE);
            dataBinding.laySpare.setVisibility(View.GONE);
            dataBinding.assetTag.setVisibility(View.GONE);
            dataBinding.layGate.setVisibility(View.GONE);
            dataBinding.invSpares.setVisibility(View.GONE);
            dataBinding.gateEntry.setVisibility(View.GONE);
            dataBinding.layAsset.setVisibility(View.GONE);
        } else if (viewModel.getPreferenceHelper().getUserDep().equalsIgnoreCase("STORE")) {
            dataBinding.palletInward.setVisibility(View.GONE);
            dataBinding.palletOutward.setVisibility(View.GONE);
            dataBinding.invSpares.setVisibility(View.VISIBLE);
            dataBinding.gateEntry.setVisibility(View.GONE);
            dataBinding.assetTag.setVisibility(View.GONE);
            dataBinding.layFirst.setVisibility(View.GONE);
            dataBinding.laySpare.setVisibility(View.VISIBLE);
            dataBinding.layGate.setVisibility(View.GONE);
            dataBinding.layAsset.setVisibility(View.GONE);
        } else if (viewModel.getPreferenceHelper().getUserDep().equalsIgnoreCase("SECURITY")) {
            dataBinding.palletInward.setVisibility(View.GONE);
            dataBinding.palletOutward.setVisibility(View.GONE);
            dataBinding.invSpares.setVisibility(View.GONE);
            dataBinding.gateEntry.setVisibility(View.VISIBLE);
            dataBinding.assetTag.setVisibility(View.GONE);
            dataBinding.layFirst.setVisibility(View.GONE);
            dataBinding.laySpare.setVisibility(View.GONE);
            dataBinding.layGate.setVisibility(View.VISIBLE);
            dataBinding.layAsset.setVisibility(View.GONE);
        }else if (viewModel.getPreferenceHelper().getUserDep().equalsIgnoreCase("ASSET")){
            dataBinding.palletInward.setVisibility(View.GONE);
            dataBinding.palletOutward.setVisibility(View.GONE);
            dataBinding.invSpares.setVisibility(View.GONE);
            dataBinding.gateEntry.setVisibility(View.GONE);
            dataBinding.assetTag.setVisibility(View.VISIBLE);
            dataBinding.layFirst.setVisibility(View.GONE);
            dataBinding.laySpare.setVisibility(View.GONE);
            dataBinding.layGate.setVisibility(View.GONE);
            dataBinding.layAsset.setVisibility(View.VISIBLE);
        } else {
            dataBinding.palletInward.setVisibility(View.VISIBLE);
            dataBinding.palletOutward.setVisibility(View.VISIBLE);
            dataBinding.invSpares.setVisibility(View.VISIBLE);
            dataBinding.gateEntry.setVisibility(View.VISIBLE);
            dataBinding.assetTag.setVisibility(View.VISIBLE);
            dataBinding.layFirst.setVisibility(View.VISIBLE);
            dataBinding.laySpare.setVisibility(View.VISIBLE);
            dataBinding.layGate.setVisibility(View.VISIBLE);
            dataBinding.layAsset.setVisibility(View.VISIBLE);
        }

        dataBinding.palletInward.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isPrimary", false);
            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.action_nav_home_to_nav_check_in, bundle);
        });

        dataBinding.palletOutward.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isPrimary", false);
            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.action_nav_home_to_nav_check_out, bundle);
        });

        dataBinding.invSpares.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isPrimary", false);
            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.action_nav_home_to_nav_inv, bundle);
        });

        dataBinding.gateEntry.setOnClickListener(v -> {
            startActivity(GateActivity.newIntent(requireContext()));
            /*Bundle bundle = new Bundle();
            bundle.putBoolean("isPrimary", false);
            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.action_nav_home_to_nav_inv, bundle);*/
        });

        dataBinding.assetTag.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isPrimary", false);
            Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment)).navigate(R.id.action_nav_home_to_nav_asset, bundle);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //Set title
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Home");
        }
    }
}
