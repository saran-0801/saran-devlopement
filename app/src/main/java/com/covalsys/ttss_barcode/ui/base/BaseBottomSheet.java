package com.covalsys.ttss_barcode.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.covalsys.ttss_barcode.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by CBS on 02-02-2021.
 */

public abstract class BaseBottomSheet<T extends ViewDataBinding, V extends BaseViewModel> extends BottomSheetDialogFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private BaseActivity mActivity;
    private T mViewDataBinding;
    private V mViewModel;
    private View mRootView;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this,viewModelFactory).get(getViewModel());
        //mViewModel = getViewModel();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        initialization(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public boolean isNetworkConnected() {
        return mActivity != null && mActivity.isNetworkConnected();
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }


    private void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    protected abstract Class<V> getViewModel();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();
    public abstract void initialization(Bundle savedInstance);

    public void addFragment(Fragment fragment, @IdRes int layout){

        getChildFragmentManager()
                .beginTransaction()
                .add(layout,fragment)
                .addToBackStack(null)
                .commit();

    }

    public void replaceFragment(Fragment fragment, @IdRes int layout){


        getChildFragmentManager()
                .beginTransaction()
                .replace(layout,fragment)
                .addToBackStack(null)
                .commit();
    }


    public void hideLoading() {
        if (mActivity != null) {
            mActivity.hideLoading();
        }
    }

    public void showLoading() {
        if (mActivity != null) {
            mActivity.showLoading();
        }
    }


    public void showSnackBar(String message,int color) {
        if (mActivity != null) {
            mActivity.showSnackBar(message,color);
        }
    }

    public void showSnackBar(String message) {
        if (mActivity != null) {
            mActivity.showSnackBar(message);
        }
    }


    public void showAlert(String title, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(getContext()).setTitle(title).setMessage(msg).setCancelable(true)
                .setPositiveButton(getString(R.string.ok),null).create();
        dialog.show();
    }

    public ViewDataBinding getActivityBinding(){
        return getViewDataBinding();
    }

    public void showRetryAlert(String title, String msg, final Object c, final String method) {

        AlertDialog dialog = new AlertDialog.Builder(getContext()).setTitle(title).setMessage(msg).setCancelable(false)
                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            Method method1=c.getClass().getDeclaredMethod(method);
                            method1.invoke(c);

                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getBaseActivity().onBackPressed();
                    }
                }).create();
        dialog.show();
    }




}