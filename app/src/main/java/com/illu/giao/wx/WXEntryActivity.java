package com.illu.giao.wx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zhangyu.constants.Constants;
import com.zhangyu.event.BaseEvent;
import com.zhangyu.network.response.BaseBean;
import com.zhangyu.util.EventBusUtils;
import com.zhangyu.util.ToastUtils;
import com.zhangyu.util.WeChatHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import main.java.com.zhangyu.network.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WeChatHelper.getIWXAPI();
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Logger.d("qqqqq");
                String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WeChatHelper.APP_ID + "&secret=" + WeChatHelper.SECRET + "&code="
                        + ((SendAuth.Resp) baseResp).code + "&grant_type=authorization_code";

                final OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                final Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Logger.d("qqqqqqqqqq" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Logger.d("qqqqq");
                        String resp = response.body().string();
                        String openid = null;
                        String access_token = null;
                        try {
                            JSONObject jsonObject = new JSONObject(resp);
                            if (jsonObject.has("openid")) {
                                openid = jsonObject.getString("openid");
                            }
                            if (jsonObject.has("access_token")) {
                                access_token = jsonObject.getString("access_token");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (openid != null && access_token != null) {
                            if (!openid.equals("") && !access_token.equals("")) {
                                String url1 = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
                                OkHttpClient okHttpClient = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(url1)
                                        .build();
                                final Call call1 = okHttpClient.newCall(request);

                                Response response1 = call1.execute();
                                String res = response1.body().string();

                                try {
                                    JSONObject tmpJson = new JSONObject(res);
                                    if (tmpJson.has("headimgurl")) {
                                        String headimgurl = tmpJson.getString("headimgurl");
                                        Logger.d("yes");
                                        loginInWechat(openid, headimgurl);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                });

                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //拒绝授权
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //取消授权
                break;
            default:
                break;
        }
    }

    private void loginInWechat(String openid, final String headUrl) throws IOException {
        HttpUtils.INSTANCE.bindWechat(openid)
                .enqueue(new retrofit2.Callback<BaseBean<Object>>() {
                    @Override
                    public void onResponse(retrofit2.Call<BaseBean<Object>> call, retrofit2.Response<BaseBean<Object>> response) {
                        try {
                            if (response.body() != null && response.body().getCode() == 200) {
                                SPUtils.getInstance().put(Constants.WEATHER_WXLOGIN, true);
                                EventBusUtils.INSTANCE.sendEvent(new BaseEvent(200));
                            }
                            ToastUtils.showToast(response.body().getMsg());
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<BaseBean<Object>> call, Throwable t) {

                    }
                });
    }

}
