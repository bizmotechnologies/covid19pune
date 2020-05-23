package com.policeapp.framework.network;

import android.content.Context;

import com.policeapp.R;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Bizmo Technologies
 */
public class TrakerPostLoginNetworkManager implements Callback{

    private GenericResponseHandler mHandler;


    public TrakerPostLoginNetworkManager(GenericResponseHandler handler) {

        this.mHandler = handler;
    }

    public RestAdapter getBaseAdapter(final Context ctx)
    {
        if(ctx!= null) {
            OkHttpClient client = new OkHttpClient();
//            client.setConnectTimeout(1, TimeUnit.MINUTES);
//            client.setReadTimeout(1, TimeUnit.MINUTES);
//            client.setAuthenticator(new TokenAuthenticator(ctx));

            TrakerRequestInterceptor interceptor = new TrakerRequestInterceptor(ctx);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(ctx.getString(R.string.base_url))
                    .setRequestInterceptor(interceptor)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(client))
                    .build();
            return restAdapter;
        }
        return null;
    }

    public RestAdapter getBaseAdapter(final Context ctx, boolean canshowSpinner)
    {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(1, TimeUnit.MINUTES);
        client.setReadTimeout(1, TimeUnit.MINUTES);
        client.setAuthenticator(new TokenAuthenticator(ctx));

        TrakerRequestInterceptor interceptor = new TrakerRequestInterceptor(ctx,canshowSpinner);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ctx.getString(R.string.base_url))
                .setRequestInterceptor(interceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(client))
                .build();

        return restAdapter;
    }



    @Override
    public void success(Object o, Response response) {
        if(mHandler != null)
        {
            mHandler.onSuccess(o);
        }
    }

    @Override
    public void failure(RetrofitError error) {

            if (mHandler != null)
                mHandler.onError(error);
    }
}
