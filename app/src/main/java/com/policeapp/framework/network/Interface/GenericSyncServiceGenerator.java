package com.policeapp.framework.network.Interface;

import android.content.Context;

import com.policeapp.R;
import com.policeapp.framework.network.TokenAuthenticator;
import com.policeapp.framework.network.TrakerRequestInterceptor;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class GenericSyncServiceGenerator {


    public static <S> S createService(Class<S> serviceClass, final Context ctx) {

        com.squareup.okhttp.OkHttpClient client = new com.squareup.okhttp.OkHttpClient();
        client.setConnectTimeout(1, TimeUnit.MINUTES);
        client.setReadTimeout(1, TimeUnit.MINUTES);
        client.setAuthenticator(new TokenAuthenticator(ctx));

            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(ctx.getString(R.string.base_url))
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient(client));

            builder.setRequestInterceptor(new TrakerRequestInterceptor(ctx,false));
            RestAdapter adapter = builder.build();
            return adapter.create(serviceClass);
    }
}
