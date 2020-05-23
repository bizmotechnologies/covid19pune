package com.policeapp.framework.network.Interface;

/**
 * Created by Bizmo Technologies on 19/09/16.
 */
public interface NetworkResponseHandler {

    public void onSuccess(Object responseBean);

    public void onError(Object errorBean);
}
