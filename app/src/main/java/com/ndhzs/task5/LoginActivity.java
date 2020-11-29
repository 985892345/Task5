package com.ndhzs.task5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ndhzs.task5.MyDrawerLayout.MyDrawerLayout;
import com.ndhzs.task5.TextWatcher.BaseTextWatcher;
import com.ndhzs.task5.register.MyDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnForgetPassword;

    private Button btnRegister;
    private Button btnLogin;
    private Button btnQQ;
    private Button btnWechat;
    private CheckBox cbRemember;
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;

    private MyDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initEvent();
    }

    private void initView() {
        btnForgetPassword = findViewById(R.id.login_btn_forgetPassword);
        btnRegister = findViewById(R.id.login_btn_register);
        btnLogin = findViewById(R.id.login_btn_login);
        btnQQ = findViewById(R.id.login_btn_qq);
        btnWechat = findViewById(R.id.login_btn_wechat);

        cbRemember = findViewById(R.id.login_cb_remember);

        tilUsername = findViewById(R.id.login_til_username);
        tilPassword = findViewById(R.id.login_til_password);

        etUsername = findViewById(R.id.login_et_username);
        etPassword = findViewById(R.id.login_et_password);
    }

    private void initEvent() {
        SharedPreferences shared = getSharedPreferences("data", MODE_PRIVATE);

        btnForgetPassword.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnQQ.setOnClickListener(this);
        btnWechat.setOnClickListener(this);

        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final SharedPreferences.Editor editor = shared.edit();
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("remember_password", isChecked);
                editor.apply();
            }
        });

        etUsername.addTextChangedListener(new BaseTextWatcher(tilUsername));
        etPassword.addTextChangedListener(new BaseTextWatcher(tilPassword));

        cbRemember.setChecked(shared.getBoolean("remember_password", false));

        if (cbRemember.isChecked()){

            String username = shared.getString("username", null);
            if (username != null) {
                etUsername.setText(username);
                etPassword.setText(shared.getString("password", null));
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn_forgetPassword :
                Toast.makeText(this, "暂时不能改密码！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_btn_register :
                myDialog = new MyDialog(LoginActivity.this, new MyDialog.MyDialogListener() {
                    @Override
                    public void ClosedClickListener(String username, String password) {
                        etUsername.setText(username);
                        etPassword.setText(password);
                        myDialog.dismiss();
                    }
                });
                myDialog.show();
                break;
            case R.id.login_btn_login :
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", null);
                String password = sharedPreferences.getString("password", null);
                if (etUsername.getText().toString().equals(username) && etPassword.getText().toString().equals(password)){
                    Intent intent = new Intent(this, ContentActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_btn_qq :
                Toast.makeText(this, "未实现用QQ登陆！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_btn_wechat :
                Toast.makeText(this, "未实现用微信登陆！", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}