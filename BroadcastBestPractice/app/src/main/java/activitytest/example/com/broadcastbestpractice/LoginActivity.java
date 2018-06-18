package activitytest.example.com.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.prefs.PreferenceChangeEvent;

public class LoginActivity extends BaseActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //spmanager 本身的sharedPreferences
        pref = PreferenceManager.getDefaultSharedPreferences(this);




        //按顺序加载登陆页
        accountEdit = (EditText)findViewById(R.id.account);
        passwordEdit = (EditText)findViewById(R.id.password);
        //得到checkBox
        rememberPass = (CheckBox)findViewById(R.id.remember_pass);
        login = (Button)findViewById(R.id.login);

        //如果能拿到存储数据，就赋值
        boolean isRemember = pref.getBoolean("remember_password",false);
        if (isRemember){

            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);

        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //得到account 和 password的内容
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                //如果账号是admin ， 密码是123456，则认为登陆成功
                if (account.equals("admin") && password.equals("123456")){

                    editor = pref.edit();
                    //如果选中了就存储值
                    if (rememberPass.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",account);
                        editor.putString("password",password);

                    }
                    //如果取消选中就清空
                    else{

                        editor.clear();

                    }
                    //最后是提交
                    editor.apply();


                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{

                    Toast.makeText(LoginActivity.this,"account or password is invalid",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
