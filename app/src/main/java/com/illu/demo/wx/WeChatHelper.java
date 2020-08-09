package com.illu.demo.wx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.illu.baselibrary.App;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * 使用前需要在清单文件中注册, 存放的路径一定要  <微信申请的包名.wxapi>
 * <activity
 * android:name="com.tencent.tmgp.zqrs.wxapi.WXEntryActivity"
 * android:exported="true"
 * android:launchMode="singleTask"
 * android:screenOrientation="portrait"
 * android:theme="@style/Theme.AppCompat.NoActionBar.Transparent" />
 *
 * <style name="Theme.AppCompat.NoActionBar.Transparent" parent="@style/Theme.AppCompat.NoActionBar">
 * <item name="android:windowNoTitle">true</item>
 * <item name="android:windowBackground">@android:color/transparent</item>
 * <item name="android:windowIsTranslucent">true</item>
 * </style>
 */
public class WeChatHelper {

    public static final String APP_ID = "";
    public static final String SECRET = "";

    private static IWXAPI api;

    public static void regToWx(Context context) {
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);

        api.registerApp(APP_ID);
        ReceiverUtils.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                api.registerApp(APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
    }

    public static IWXAPI getIWXAPI() {
        if (api == null) {
            regToWx(App.Companion.getINSTANCE());
        }
        return api;
    }

    public static int SHARE_TYPE_SESSION = 1;
    public static int SHARE_TYPE_FRIEND = 0;

    /**
     * @param shareType 1:分享好友  2:分享朋友圈
     * @param localRes  图片资源
     */
    public static void shareToFriend(Context context, int shareType, int localRes) {
        WXMediaMessage msg = new WXMediaMessage();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), localRes);
        Bitmap thumBitmap = bitmap.createScaledBitmap(bitmap, 120, 150, true);
        WXImageObject imgObj = new WXImageObject(bitmap);
        msg.mediaObject = imgObj;
        bitmap.recycle();
        msg.description = "ss";
        msg.thumbData = bitmapToByteArray(thumBitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;

        if (shareType == SHARE_TYPE_SESSION) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        WeChatHelper.getIWXAPI().sendReq(req);
    }


    /**
     * 将BItmap转化为byte格式的数组
     */
    private static byte[] bitmapToByteArray(Bitmap bitmap, boolean recycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (recycle) {
            bitmap.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
