package com.illu.giao.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

public class InstallUtil {

    private Activity mAct;
    private String mPath;

    public InstallUtil(Activity mAct, String mPath) {
        this.mAct = mAct;
        this.mPath = mPath;
    }

    public void install(){
        Intent intent = new Intent("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= 24){
//            Uri apkUri = FileProvider.getUriForFile(mAct, BuildConfig.APPLICATION_ID + ".provider", new File(mPath));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else {
            intent.setDataAndType(Uri.fromFile(new File(mPath)), "application/vnd.android.package-archive");
        }
        mAct.startActivity(intent);
    }
}
