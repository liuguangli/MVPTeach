package com.mvpteach.liuguangli.mvpteach.badmvp;

/**
 * Created by liuguangli on 16/1/18.
 */
public interface ILoginView {
    /**
     * 提交登录
     */
    public void attemptLogin();
    /**
     * 显示loading框
     */
    public void showProcess(final boolean show);

    /**
     * 显示错误信息
     * @param code        错误码
     * @param devMsg      技术性提示信息
     * @param friendlyMsg 用户提示信息
     */
    public void showErrorInfo(int code, String devMsg, String friendlyMsg);

    /**
     * 处理登录成功
     */
    public void loginSuc();
}
