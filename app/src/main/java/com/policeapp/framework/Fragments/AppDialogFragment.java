package com.policeapp.framework.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.policeapp.R;
import com.policeapp.framework.Widgets.AppButton;
import com.policeapp.framework.Widgets.AppRegularTextView;


public class AppDialogFragment extends DialogFragment {
    private View rootView;
    private AppRegularTextView mTitile;
    private AppRegularTextView mContent;
    private AppButton mAction ;
    private String title,content,actionText;
    private Runnable actionRunnable;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity())
        {
            @Override
            public void onBackPressed() {
//                    dismiss();
            }
        };
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.app_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
        initViews(dialog);

        return dialog;
    }

    private void initViews(Dialog dialog) {
        mTitile =  dialog.findViewById(R.id.dialog_title);
        mContent =  dialog.findViewById(R.id.dialog_content);
        mAction =  dialog.findViewById(R.id.dialog_button);

        if(title != null) {
            mTitile.setVisibility(View.VISIBLE);
            mTitile.setText(title);
        }
        if(content != null) {
            mContent.setVisibility(View.VISIBLE);
            mContent.setText(Html.fromHtml(content));
        }
        if(actionText != null)
            mAction.setText(actionText);
        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (actionRunnable != null)
                    actionRunnable.run();
                else
                    dismiss();
            }
        });
    }



    public void setContent(String content, String title, final Runnable actionRunnable)
    {
        this.content = content;
        this.actionRunnable  = actionRunnable;
    }

    public void setContent(String content, String title, String actionButtonTitle, final Runnable actionRunnable)
    {
        this.content = content;
        this.actionRunnable  = actionRunnable;
        this.actionText = actionButtonTitle;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commit();
        } catch (IllegalStateException e) {
        }
    }

    /*This method is used to adjust the top alignment of the model on the screen*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        getDialog().getWindow().setAttributes(params);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}


