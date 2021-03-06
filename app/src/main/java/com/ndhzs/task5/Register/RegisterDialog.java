package com.ndhzs.task5.Register;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ndhzs.task5.Net.SendNetRequest;
import com.ndhzs.task5.R;
import com.ndhzs.task5.TextWatcher.BaseTextWatcher;
import com.ndhzs.task5.TextWatcher.Password1Watcher;
import com.ndhzs.task5.TextWatcher.Password2Watcher;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Objects;

public class RegisterDialog extends Dialog {

    private static final String TAG = "123";
    private static final int SUCCEED = 0;
    private static final int FAIL = -1;
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
    private MHandler mHandler;

    private final RegisterDialogListener registerDialogListener;

    private final Context context;

    public RegisterDialog(Context context, RegisterDialogListener registerDialogListener) {
        super(context);
        this.context = context;
        this.registerDialogListener = registerDialogListener;
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

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            boolean isOk = true;
            String username = Objects.requireNonNull(etUsername.getText()).toString();
            String password2 = Objects.requireNonNull(etPassword2.getText()).toString();
            String phoneNum = Objects.requireNonNull(etPhone.getText()).toString();
            if (username.equals("")){
                isOk = false;
                tilUsername.setError("用户名不能为空！");
            }
            if (password2.equals("")){
                isOk = false;
                tilPassword1.setError("密码不能为空！");
            }
            if (phoneNum.length() != 11){
                isOk = false;
                tilPhone.setError("号码尾数不为11位！");
            }
            if (isOk) {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", password2);
                map.put("repassword", password2);
                mHandler = new MHandler(RegisterDialog.this);
                new SendNetRequest(mHandler).sendPostNetRequest("https://www.wanandroid.com/user/register", map);
            }
        }
    }

    private static class MHandler extends Handler {

        private final WeakReference<RegisterDialog> weakReference;

        public MHandler(RegisterDialog dialog) {
            this.weakReference = new WeakReference<>(dialog);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            RegisterDialog dialog= weakReference.get();

            String[] data = (String[]) msg.obj;

            if (dialog != null){
                switch (msg.what) {
                    case SUCCEED : {
                        Toast.makeText(dialog.context, "注册成功！", Toast.LENGTH_SHORT).show();
                        String username = data[0];
                        String password = data[1];
                        dialog.registerDialogListener.ClosedClickListener(username, password);
                        break;
                    }
                    case FAIL : {
                        Toast.makeText(dialog.context, "注册失败！", Toast.LENGTH_LONG).show();
                        dialog.tilUsername.setError(data[2]);
                        break;
                    }
                }
            }
        }
    }
}