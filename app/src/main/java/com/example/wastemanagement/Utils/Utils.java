package com.example.wastemanagement.Utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wastemanagement.R;

public class Utils {

    @SuppressLint("ResourceType")
    public static ProgressDialog setupProgressDialog(Context context) {
        ProgressDialog progress = new ProgressDialog(context,R.style.ProgressDialog);
        progress.setTitle(context.getString(R.string.label_progress_title));
        progress.setMessage(context.getString(R.string.label_progress_payment));
         progress.setCancelable(false);

        return progress;
    }

    public static void setMyFragment(Fragment fragment, FragmentManager fragmentManager) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);

        fragmentTransaction.commit();

    }



}
