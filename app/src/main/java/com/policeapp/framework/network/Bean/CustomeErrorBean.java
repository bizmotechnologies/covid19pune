package com.policeapp.framework.network.Bean;

import java.io.Serializable;

public class CustomeErrorBean implements Serializable {
    private boolean status;
    private String type,message;

    public boolean isStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
