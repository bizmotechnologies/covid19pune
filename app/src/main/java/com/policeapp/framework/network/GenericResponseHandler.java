package com.policeapp.framework.network;

import android.content.Context;
import android.content.Intent;

import com.policeapp.R;
import com.policeapp.framework.BaseActivity;
import com.policeapp.framework.Fragments.AppDialogFragment;
import com.policeapp.framework.network.Bean.ErrorBean;
import com.policeapp.framework.network.Interface.ErrorConstants;
import com.policeapp.framework.network.Interface.NetworkResponseHandler;
import com.policeapp.framework.storage.Constants;
import com.policeapp.framework.storage.NetworkCacheManager;
import com.policeapp.source.postlogin.HomeActivity;

import retrofit.RetrofitError;

/**
 * Created by Bizmo Technologies
 */
public class GenericResponseHandler implements NetworkResponseHandler {

    private Context ctx;
    private NetworkResponseHandler mResposnseErrorHandler;
    private boolean canShownSpinner = true;

    public GenericResponseHandler(NetworkResponseHandler mResposnseErrorHandler, Context ctx) {
        this.mResposnseErrorHandler = mResposnseErrorHandler;
        this.ctx = ctx;
    }

    public GenericResponseHandler(NetworkResponseHandler mResposnseErrorHandler, Context ctx, boolean canShownSpinner) {
        this.mResposnseErrorHandler = mResposnseErrorHandler;
        this.ctx = ctx;
        this.canShownSpinner = canShownSpinner;
    }



    @Override
    public void onSuccess(Object responseBean) {

        if(mResposnseErrorHandler != null) {
            if(responseBean instanceof ErrorBean)
            {
                if(((ErrorBean) responseBean).isStatus())
                {
                    mResposnseErrorHandler.onSuccess(responseBean);
                }else
                {
                    mResposnseErrorHandler.onError(responseBean);
                }
            }else {
                mResposnseErrorHandler.onSuccess(responseBean);
            }
        }
        if(canShownSpinner)
        ((BaseActivity)ctx).hideSpinner();
    }

    @Override
    public void onError(Object bean) {

        if(bean != null && bean instanceof RetrofitError)
        {
            RetrofitError error = (RetrofitError) bean;

            if(error.getResponse() != null) {
                switch (error.getResponse().getStatus()) {
                    case 404:
                        if(error != null)
                            handleGenericErrors(error);
                        break;
                    default:
                            if(error != null)
                                handleGenericErrors(error);
                        break;
                }
            }else
                {
                    //this means that network is not available
                    AppDialogFragment dialog =  new AppDialogFragment();
                    dialog.setContent(ctx.getResources().getString(R.string.no_network), "Tracking System",null);
                    if(ctx != null && ctx instanceof BaseActivity) {
                        BaseActivity base = (BaseActivity) ctx;
                        if(((BaseActivity)ctx).isSavedInstanceStateDone())
                        dialog.show(base.getSupportFragmentManager(), AppDialogFragment.class.getSimpleName());
                    }

                }
            ((BaseActivity)ctx).hideSpinner();
            }
    }

    private void handleGenericErrors(RetrofitError bean) {
        AppDialogFragment dialog =  new AppDialogFragment();
        switch (""+bean.getResponse().getStatus())
        {
            case ErrorConstants.CUSTOM_ERROR:
                throwBackError(bean);
                break;
            case ErrorConstants.CUSTOM_ERROR_409:
                throwBackError(bean);
                break;
            case ErrorConstants.INTERNAL_SERVER_ERROR:
                dialog.setContent(ctx.getString(R.string.oopsMessage), "Tracking",null);
                if(ctx != null && ctx instanceof BaseActivity) {
                    BaseActivity base = (BaseActivity) ctx;
                    if(((BaseActivity)ctx).isSavedInstanceStateDone())
                    dialog.show(base.getSupportFragmentManager(), AppDialogFragment.class.getSimpleName());
                }
                break;
            case ErrorConstants.INVALID_ACCESS_TOKEN_ERROR:
                if((NetworkCacheManager.getInstance(ctx).getData(Constants.REFRESH_TOKEN) != null) && (NetworkCacheManager.getInstance(ctx).getData(Constants.REFRESH_TOKEN).length() > 0)) {
                    //logout the user and go to the Login page
//                    NetworkCacheManager.getInstance(ctx).clearAllData();
//                    DataCacheManager.getInstance(ctx).clearAllData();

                    Intent oplatoHomeActivity = new Intent(ctx, HomeActivity.class);
                    ctx.startActivity(oplatoHomeActivity);
                    ((HomeActivity) ctx).finish();
                    }
                    else {
                    //handle the standard prelogin exceptions
                    throwBackError(bean);
                     }
                break;
            case ErrorConstants.UNAUTHORISED_REQUEST:
                dialog.setContent(ctx.getString(R.string.oopsMessage), "Oplato",null);
                if(ctx != null && ctx instanceof BaseActivity) {
                    BaseActivity base = (BaseActivity) ctx;
                    if(((BaseActivity)ctx).isSavedInstanceStateDone())
                    dialog.show(base.getSupportFragmentManager(), AppDialogFragment.class.getSimpleName());
                }
                break;
            case ErrorConstants.UNSUPPORTED_CONTENT_TYPE:
                dialog.setContent(ctx.getString(R.string.oopsMessage), "Oplato",null);
                if(ctx != null && ctx instanceof BaseActivity) {
                    BaseActivity base = (BaseActivity) ctx;
                    if(((BaseActivity)ctx).isSavedInstanceStateDone())
                    dialog.show(base.getSupportFragmentManager(), AppDialogFragment.class.getSimpleName());
                }
                break;
            case ErrorConstants.NOT_FOUND:
                //check if we are handling the prelogin or post login case
                if(NetworkCacheManager.getInstance(ctx).getData(Constants.REFRESH_TOKEN)!= null && NetworkCacheManager.getInstance(ctx).getData(Constants.REFRESH_TOKEN).length()>0) {
                    //post login calls
                    dialog.setContent(ctx.getString(R.string.oopsMessage), "Oplato",null);
                    if(ctx != null && ctx instanceof BaseActivity) {
                        BaseActivity base = (BaseActivity) ctx;
                        if(((BaseActivity)ctx).isSavedInstanceStateDone())
                            dialog.show(base.getSupportFragmentManager(), AppDialogFragment.class.getSimpleName());
                    }
                }else
                {
                    throwBackError(bean);
                }
                break;
                default:
                    if(mResposnseErrorHandler != null)
                        mResposnseErrorHandler.onError(bean);
                    break;

        }
        ((BaseActivity)ctx).hideSpinner();

    }

    private void throwBackError(RetrofitError bean) {
        if(mResposnseErrorHandler != null ) {
            ErrorBean error = (ErrorBean) bean.getBodyAs(ErrorBean.class);
            mResposnseErrorHandler.onError(error);
        }
    }



}
