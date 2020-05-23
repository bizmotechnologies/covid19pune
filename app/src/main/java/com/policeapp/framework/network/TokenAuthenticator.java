package com.policeapp.framework.network;

import android.content.Context;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;


/**
 * By Bizmo Technologies
 */

public class TokenAuthenticator implements Authenticator {

    private Context ctx;

    public TokenAuthenticator(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public Request authenticate(Proxy proxy, Response response) throws IOException {
        // Refresh your access_token using a synchronous api request
//       OAuthTokenService service = OplatoServiceGenerator.createService(OAuthTokenService.class,ctx.getString(R.string.USERNAME),ctx.getString(R.string.PASSWORD),ctx);
//        HashMap<String, String> params = new HaTokenAuthenticatorshMap<>();
//        params.put("grant_type","refresh_token");
//        params.put("refresh_token", NetworkCacheManager.getInstance(ctx).getData(Constants.REFRESH_TOKEN));
//        OtpSubmitResponseBean bean = null;
//        try {
//             bean = service.refreshToken(params);
//        }catch (RetrofitError error)
//        {
//            //error
//            Log.d("REFRESH TOKEN",error.toString());
//        }
//        if ( bean != null) {
//            String newAccessToken = bean.getAccess_token();
//            NetworkCacheManager.getInstance(ctx).storeData(Constants.ACCESS_TOKEN, bean.getAccess_token());
//            NetworkCacheManager.getInstance(ctx).storeData(Constants.REFRESH_TOKEN, bean.getRefresh_token());
//            ((BaseActivity)ctx).hideSpinner();
////        // Add new header to rejected request and retry it
//            return response.request().newBuilder()
//                    .header("Authorization","Bearer "+ newAccessToken)
//                    .build();
//        }else
//        {
            return null;
//        }
    }

    @Override
    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
        return null;
    }
}