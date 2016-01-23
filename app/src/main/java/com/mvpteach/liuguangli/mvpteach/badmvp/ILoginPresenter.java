package com.mvpteach.liuguangli.mvpteach.badmvp;

import com.loopj.android.http.RequestParams;
import com.mvpteach.liuguangli.mvpteach.badmvp.bean.UserInfo;

/**
 * Created by liuguangli on 16/1/18.
 */
public interface ILoginPresenter {
    /**
     * 登录
     * @param requestParams
     */
    public void loginToServer(RequestParams requestParams);
    /**
     * 登录成功
     */
    public void loginSuc(UserInfo userInfo);
    /**
     * 登录失败
     */
    public void loginError(int code, String msg);
}
