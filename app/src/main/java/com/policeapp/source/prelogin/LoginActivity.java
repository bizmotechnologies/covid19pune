package com.policeapp.source.prelogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.policeapp.framework.BaseActivity;
import com.policeapp.framework.storage.Constants;
import com.policeapp.framework.storage.DataCacheManager;
import com.policeapp.source.postlogin.HomeActivity;
import com.policeapp.source.prelogin.fragment.PreLoginMasterFragment;

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(NO_LAYOUT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }

        if(DataCacheManager.getInstance(this).getData(Constants.IS_LOGIN)!= null &&
                DataCacheManager.getInstance(this).getData(Constants.IS_LOGIN).length()>0){
            Intent postLoginIntent = new Intent(this, HomeActivity.class);
            startActivity(postLoginIntent);
            finish();
        }
        initUiComponents();
    }

    private void initUiComponents() {
        if(getToolbar() != null)
            getToolbar().setVisibility(View.GONE);

        makeFragmentTransaction(new PreLoginMasterFragment(),true);
    }
}

