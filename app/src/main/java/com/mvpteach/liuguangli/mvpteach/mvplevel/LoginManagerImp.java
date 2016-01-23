package com.mvpteach.liuguangli.mvpteach.mvplevel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mvpteach.liuguangli.mvpteach.mvplevel.bean.LoginInfo;
import com.mvpteach.liuguangli.mvpteach.mvplevel.bean.UserInfo;

import cz.msebera.android.httpclient.Header;

/**
 * Created by liuguangli on 16/1/18.
 */
public class LoginManagerImp implements ILoginManager {
    private ILoginPresenter presenter;

    public LoginManagerImp(ILoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void login(LoginInfo info) {
        RequestParams request = new RequestParams();
        request.put("username",info.getUserName());
        request.put("password",info.getPassword());
        new AsyncHttpClient().post(null, "http://192.168.12.12/app/user/login", request, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //// TODO: 16/1/18 错误码解析和提示
                presenter.loginError(statusCode, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                UserInfo userInfo = new UserInfo();
                // TODO: 16/1/18
                presenter.loginSuc(userInfo);
            }
        });
    }
}
