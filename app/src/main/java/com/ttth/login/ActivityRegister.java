package com.ttth.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Thanh Hang on 13/12/16.
 */

public class ActivityRegister extends Activity {
    public static final String KEY_USER = "user name";
    public static final String KEY_PASS = "pass word";
    public static final String KEY_DATA = "data";
    private EditText edtUserRegister,edtPassRegister;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(ActivityLogin.KEY_MESSAGE);
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        edtUserRegister = (EditText) this.findViewById(R.id.edtUserRegister);
        edtPassRegister = (EditText) this.findViewById(R.id.edtPassRegister);
        btnSave = (Button) this.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUserRegister.getText().toString();
                String pass = edtPassRegister.getText().toString();
                if (user.isEmpty() && pass.isEmpty()){
                    Toast.makeText(ActivityRegister.this,"Mời nhập tên đăng nhập và mật khẩu.",Toast.LENGTH_LONG).show();
                }else if (user.isEmpty()){
                    Toast.makeText(ActivityRegister.this,"Mời nhập tên đăng nhập.",Toast.LENGTH_LONG).show();
                }else if (pass.isEmpty()){
                    Toast.makeText(ActivityRegister.this,"Mời nhập mật khẩu.",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ActivityRegister.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(KEY_USER,user);
                        bundle.putString(KEY_PASS,pass);
                        intent.putExtra(KEY_DATA,bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                }
            }
        });
    }
}
