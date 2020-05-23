package com.policeapp.framework.network;

import android.content.Context;


import com.policeapp.framework.BaseActivity;
import com.policeapp.framework.storage.Constants;
import com.policeapp.framework.storage.NetworkCacheManager;

import retrofit.RequestInterceptor;

/**
 * Created by Bizmo Technologies
 */
public class TrakerRequestInterceptor implements RequestInterceptor {

    private Context ctx;
    private boolean canShowSpinner =  true;

    public TrakerRequestInterceptor(Context ctx) {
        this.ctx = ctx;
    }

    public TrakerRequestInterceptor(Context ctx, boolean canShowSpinner) {
        this.ctx = ctx;
        this.canShowSpinner = canShowSpinner;
    }

    @Override
    public void intercept(RequestFacade request) {

        String secCookie = NetworkCacheManager.getInstance(ctx).getData(Constants.ACCESS_TOKEN);
        if(secCookie != null && secCookie.length()>0) {
//            request.addHeader("Authorization","Bearer "+secCookie);
            }
        request.addHeader("Content-Type","application/x-www-form-urlencoded");
//        secCookie = null;
        if(ctx instanceof BaseActivity && canShowSpinner) {
            ((BaseActivity)ctx).showSpinner();
        }

    }




}
