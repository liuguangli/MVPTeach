package com.mvpteach.liuguangli.mvpteach.badmvp;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;
import com.mvpteach.liuguangli.mvpteach.R;

/**
 * 登录功能：形式上使用了MVP架构，但本质上还是没完全执行MVP的思想
 */
public class LoginActivity extends AppCompatActivity  implements ILoginView {

    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ILoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginPresenter = new LoginPresenterImp(this);

    }


    /**
     * 提交登录
     */
    @Override
    public void attemptLogin() {

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!isEmailValid(email)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            cancel = true;
        }
        if (!cancel){
            /**形式上使用了MVP架构，但本质上还是没完全执行MVP的思想。因为这里已经暴露了，网络框架层的实现特性
             （如果那天不使用android－async－http这个框架了，那么这里就会受到影响）*/
            RequestParams request = new RequestParams();
            request.put("username",email);
            request.put("password",password);
            mLoginPresenter.loginToServer(request);
        }


    }

    /**
     * 显示错误信息
     * @param code        错误码
     * @param devMsg      技术性提示信息
     * @param friendlyMsg 用户提示信息
     */
    @Override
    public void showErrorInfo(int code, String devMsg, String friendlyMsg) {

    }

    /**
     * 登录成功
     */
    @Override
    public void loginSuc() {

    }

    /**
     *  显示进度
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProcess(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }



    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }







}

