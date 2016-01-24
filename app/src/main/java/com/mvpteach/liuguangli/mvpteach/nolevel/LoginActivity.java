package com.mvpteach.liuguangli.mvpteach.nolevel;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mvpteach.liuguangli.mvpteach.R;
import com.mvpteach.liuguangli.mvpteach.mvplevel.bean.*;

import cz.msebera.android.httpclient.Header;

/**
 * 登录功能无层次的设计，视图逻辑和数据逻辑耦合
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

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

    }

    /**
     * 提交登录
     */
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
            loginToServer(email,password);
        }
    }

    /**
     * 登录到服务器
     * @param email
     * @param password
     */
    private void loginToServer(String email, String password) {
        showProcess(true);
        RequestParams request = new RequestParams();
        request.put("username",email);
        request.put("password",password);
        new AsyncHttpClient().post(this, "http://192.168.12.12/app/user/login", request, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //// TODO: 16/1/18 错误码解析和提示
                showErrorInfo(statusCode,responseString,responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                UserInfo userInfo = parseRes(responseString);
                saveInfo(userInfo);
                loginSuc();
            }
        });
    }

    private void saveInfo(UserInfo userInfo) {
        /*
        为了说明逻辑，这里没有实现，假设这里使用sharedPreferences实现，那么以后想换一种方式，
        涉及到UserInfo的地方都要修改了,而以我们的经验，通常一个App大部业务都会设计UserInfo */

        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_APPEND);
    }

    private UserInfo parseRes(String string) {
        //为了说明逻辑，这里没有实现
        return null;
    }

    /**
     * 显示错误信息
     * @param code        错误码
     * @param devMsg      技术性提示信息
     * @param friendlyMsg 用户提示信息
     */
    public void showErrorInfo(int code, String devMsg, String friendlyMsg) {
        Toast.makeText(this,devMsg,Toast.LENGTH_LONG).show();
    }

    /**
     * 登录成功
     */
    public void loginSuc() {

        Toast.makeText(this,getString(R.string.login_suc),Toast.LENGTH_LONG).show();
    }

    /**
     *  显示进度
     */
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

