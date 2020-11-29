package com.ndhzs.task5.register;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ndhzs.task5.LoginActivity;
import com.ndhzs.task5.R;
import com.ndhzs.task5.TextWatcher.BaseTextWatcher;
import com.ndhzs.task5.TextWatcher.Password1Watcher;
import com.ndhzs.task5.TextWatcher.Password2Watcher;

public class MyDialog extends Dialog {

    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword1;
    private TextInputLayout tilPassword2;
    private TextInputLayout tilPhone;
    private TextInputEditText etUsername;
    private TextInputEditText etPassword1;
    private TextInputEditText etPassword2;
    private TextInputEditText etPhone;
    private CheckBox cbAgreement;
    private Button btnRegister;


    private final MyDialogListener myDialogListener;

    private final LoginActivity context;

    public MyDialog(LoginActivity context, MyDialogListener myDialogListener) {
        super(context);
        this.context = context;
        this.myDialogListener = myDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉系统的黑色矩形边框
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initView();
        initEvent();
    }

    public void initView() {

        View layout = LayoutInflater.from(context).inflate(R.layout.login_dlg_register, null);
        setContentView(layout);

        tilUsername = layout.findViewById(R.id.register_til_username);
        tilPassword1 = layout.findViewById(R.id.register_til_password1);
        tilPassword2 = layout.findViewById(R.id.register_til_password2);
        tilPhone = layout.findViewById(R.id.register_til_phone);

        etUsername = layout.findViewById(R.id.register_et_username);
        etPassword1 = layout.findViewById(R.id.register_et_password1);
        etPassword2 = layout.findViewById(R.id.register_et_password2);
        etPhone = layout.findViewById(R.id.register_et_phone);

        cbAgreement = layout.findViewById(R.id.register_cb_agreement);
        btnRegister = layout.findViewById(R.id.register_btn_register);

        //设置窗口
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.85); // 高度设置为屏幕的0.6
        lp.height = (int) (d.heightPixels * 0.7); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    private void initEvent() {
        etUsername.addTextChangedListener(new BaseTextWatcher(tilUsername));
        etPassword1.addTextChangedListener(new Password1Watcher(tilPassword1));
        etPassword2.addTextChangedListener(new Password2Watcher(tilPassword1, etPassword1, tilPassword2));
        etPhone.addTextChangedListener(new BaseTextWatcher(tilPhone));
        btnRegister.setOnClickListener(new MyOnClickListener());
    }

    public interface MyDialogListener {
        void ClosedClickListener(String username, String password);
    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String username = etUsername.getText().toString();
            String password2 = etPassword2.getText().toString();
            String phoneNum = etPhone.getText().toString();
            if (!(username.equals("") && username == null && password2.equals("") && password2 == null && phoneNum.equals("") && phoneNum == null)){
                SharedPreferences.Editor editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                editor.putString("username", username);
                editor.putString("password", password2);
                editor.putString("phoneNum", phoneNum);
                editor.apply();
                myDialogListener.ClosedClickListener(etUsername.getText().toString(), etPassword1.getText().toString());
            }else {
                Toast.makeText(context, "请完善用户名和密码！", Toast.LENGTH_LONG).show();
            }
        }
    }
}