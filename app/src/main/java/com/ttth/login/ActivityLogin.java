package com.ttth.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ttth.model.Account;

import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener{
    public static final String KEY_MESSAGE = "message";
    private static final int REQUEST_REGISTER = 0;
    private static final String TAG = "ACTIVITY_LOGIN";
    public static final String KEY_USER_NAME = "key_user_name";
    public static final String KEY_PASSWORD = "key_passsword";
    private static final String KEY_PRE_SAVE = "key_save";
    private static final String KEY_SAVE = "save";
    private EditText edtUserLogin, edtPassLogin;
    private Button btnLogin, btnRegister;
    private CheckBox cbSave;
    private ArrayList<Account> arrAccounts;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editSave;
    private boolean checkSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
        saveInfor();
    }

    private void initData() {
        arrAccounts = new ArrayList<>();
        arrAccounts.add(new Account("hihi","1234"));
        arrAccounts.add(new Account("haha","abcd"));
    }

    private void initView() {
        edtUserLogin = (EditText) this.findViewById(R.id.edtUserLogin);
        edtPassLogin = (EditText) this.findViewById(R.id.edtPassLogin);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
        btnRegister = (Button) this.findViewById(R.id.btnRegister);
        cbSave = (CheckBox) this.findViewById(R.id.cbSave);

//        user = edtUserLogin.getText().toString();
//        pass = edtPassLogin.getText().toString();

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        cbSave.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
            String user, pass;
            user = edtUserLogin.getText().toString();
            pass = edtPassLogin.getText().toString();
        switch (view.getId()){
            case R.id.btnLogin:
                if (user.isEmpty() && pass.isEmpty()){
                    Toast.makeText(this,"Mời nhập tên đăng nhập và mật khẩu.",Toast.LENGTH_LONG).show();
                }else if (user.isEmpty()){
                    Toast.makeText(this,"Mời nhập tên đăng nhập.",Toast.LENGTH_LONG).show();
                }else if (pass.isEmpty()){
                    Toast.makeText(this,"Mời nhập mật khẩu.",Toast.LENGTH_LONG).show();
                }else {
                    for(Account account : arrAccounts){
                        if (user.equals(account.getName()) && pass.equals(account.getPass())){
                            if (cbSave.isChecked()){
                                checkSave = mSharedPreferences.getBoolean(KEY_SAVE,true);
                                editSave.putString(KEY_USER_NAME,user);
                                editSave.putString(KEY_PASSWORD,pass);
                                editSave.commit();
                                Toast.makeText(this,"Lưu tài khoản thành công",Toast.LENGTH_SHORT).show();

                            }else {
                                editSave.clear();
                                editSave.commit();
                            }
                            Toast.makeText(this,"Đăng nhập thành công.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ActivityLogin.this, MainAcitvity.class);
                            startActivity(intent);
                        }else if (user.equals(account.getName())||user.equals(account.getPass())){
                            Toast.makeText(this,"Sai mật khẩu hoặc sai tên người dùng",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.btnRegister:
                Intent intent = new Intent(ActivityLogin.this,ActivityRegister.class);
                intent.putExtra(KEY_MESSAGE,"Đăng ký tài khoản");
                startActivityForResult(intent,REQUEST_REGISTER);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REGISTER){
           if (resultCode == RESULT_OK){
                Bundle bundle = data.getBundleExtra(ActivityRegister.KEY_DATA);
                String user = bundle.getString(ActivityRegister.KEY_USER);
                String pass = bundle.getString(ActivityRegister.KEY_PASS);
                edtUserLogin.setText(user);
                edtPassLogin.setText(pass);
                arrAccounts.add(new Account(user,pass));
                Log.e(TAG,"--------------"+arrAccounts);
            }
        }

    }
    public void saveInfor(){
        mSharedPreferences = getSharedPreferences(KEY_PRE_SAVE,MODE_PRIVATE);
        editSave = mSharedPreferences.edit();
        checkSave = mSharedPreferences.getBoolean(KEY_SAVE,false);
        if (checkSave == true){
            edtUserLogin.setText(mSharedPreferences.getString(KEY_USER_NAME,""));
            edtPassLogin.setText(mSharedPreferences.getString(KEY_PASSWORD,""));
            cbSave.setChecked(true);
        }
    }
}
