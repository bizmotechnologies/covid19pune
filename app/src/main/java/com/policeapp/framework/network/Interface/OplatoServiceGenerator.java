package com.policeapp.framework.network.Interface;

import android.content.Context;
import android.util.Base64;

import com.policeapp.R;
import com.policeapp.framework.BaseActivity;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * By Bizmo Technologies
 */

public class OplatoServiceGenerator {

    public static <S> S createService(Class<S> serviceClass, Context ctx) {
        return createService(serviceClass, null, null,ctx);
    }

    public static <S> S createService(Class<S> serviceClass, String username, String password, final Context ctx) {
        if (username != null && password != null) {
            // concatenate username and password with colon for authentication
            String credentials = username + ":" + password;
            // create Base64 encodet string
            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(ctx.getString(R.string.base_url))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(new OkHttpClient()));

            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Authorization", basic);
                    request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    if(ctx instanceof BaseActivity) {
                        ((BaseActivity)ctx).showSpinner();
                    }
                }
            });
            RestAdapter adapter = builder.build();
            return adapter.create(serviceClass);
        }

        return null;
    }
}
