package com.policeapp.framework.network.Bean;

import java.io.Serializable;

/**
 * Created by Bizmo technologies
 */
public class TokenRefreshBody implements Serializable {

    private String refresh_token;

    public TokenRefreshBody(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
