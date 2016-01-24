package com.mvpteach.liuguangli.mvpteach.mvplevel;

import com.mvpteach.liuguangli.mvpteach.mvplevel.bean.UserInfo;

/**
 * Created by liuguangli on 16/1/24.
 */
public interface IUserInfoView {
    /**
     * 显示loading框
     */
    public void showProcess(final boolean show);

    /**
     * 显示用户信息
     * @param userInfo
     */
    public void showUserInfo(UserInfo userInfo);

    /**
     * 回退到登录
     */
    public void toLogin();
}
