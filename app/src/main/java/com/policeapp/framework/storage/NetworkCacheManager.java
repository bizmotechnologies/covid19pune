package com.policeapp.framework.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bizmo Technologies
 */
public class NetworkCacheManager {

    private static NetworkCacheManager manager;
    private final static String QUP_PREF ="traker_network_cache";
    private static SharedPreferences qUpPref;

    private NetworkCacheManager() {

    }

    public static NetworkCacheManager getInstance(Context ctx)
    {
        if(manager == null) {
            manager = new NetworkCacheManager();
            qUpPref = ctx.getSharedPreferences(QUP_PREF, Context.MODE_PRIVATE);
        }
        return manager;
    }

    public void storeData(String key, String value)
    {
        SharedPreferences.Editor editor = qUpPref.edit();
        editor.putString(key,value);
        editor.commit();

    }

    public String getData(String key)
    {
        return qUpPref.getString(key,"");

    }

    public void clearAllData()
    {
        SharedPreferences.Editor editor = qUpPref.edit();
        editor.clear();
        editor.commit();
    }


}
