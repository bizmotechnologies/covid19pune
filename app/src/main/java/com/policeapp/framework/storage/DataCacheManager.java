package com.policeapp.framework.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
public class DataCacheManager {

    private static DataCacheManager mManager ;
    private static SharedPreferences trakerPref;
    private final static String TRAKER_PREF="traker_data_cache";


    private DataCacheManager()
    {
    }

    public static DataCacheManager getInstance(Context ctx)
    {
        if(mManager == null)
        {
            mManager = new DataCacheManager();
            trakerPref = ctx.getSharedPreferences(TRAKER_PREF, Context.MODE_PRIVATE);
        }
        return mManager;
    }


    public void storeData(String key, String value)
    {
        SharedPreferences.Editor editor = trakerPref.edit();
        editor.putString(key,value);
        editor.commit();

    }

    public String getData(String key)
    {
       return trakerPref.getString(key,"");

    }

    public void storeData(String key, Object value, Class type)
    {
        SharedPreferences.Editor editor = trakerPref.edit();
        Gson gson = new Gson();
        editor.putString(key, gson.toJson(value,type));
        editor.commit();

    }

    public Object getData(String key, Class type)
    {
        Gson gson = new Gson();
        return gson.fromJson(trakerPref.getString(key,""),type);

    }

    public void storeBooleanData(String key, boolean value)
    {
        SharedPreferences.Editor editor = trakerPref.edit();
        editor.putBoolean(key,value);
        editor.commit();

    }

    public boolean getBooleanData(String key)
    {
        return trakerPref.getBoolean(key,false);
    }

    public void clearAllData()
    {
        SharedPreferences.Editor editor = trakerPref.edit();
        editor.clear();
        editor.commit();
    }

    public void clearData(String key)
    {
        SharedPreferences.Editor editor = trakerPref.edit();
        editor.remove(key).commit();
    }

}
