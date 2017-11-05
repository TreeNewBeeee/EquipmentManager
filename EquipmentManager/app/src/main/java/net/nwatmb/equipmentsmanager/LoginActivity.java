package net.nwatmb.equipmentsmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

    /**
     * 测试用户账户信息
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "zhangheng:1", "shangdejia:1", "qiaowei:1", "a:a", "1:1"
    };

    //界面定义
    private EditText mUserName;
    private EditText mPassword;
    private Button mLogin;

    //变量定义
    private boolean isRegisted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //相关组件
        mUserName = (EditText) findViewById(R.id.user_name);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.btn_login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin(mUserName.getText().toString(),mPassword.getText().toString());

            }

        });


    }

    private void checkLogin(String username,String password) {
        isRegisted = true;
        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");       //拆分用户名及密码
            if (pieces[0].equals(username)) {
                if (pieces[1].equals(password)){
                    //将用户名发至SystemPointer
                    //Toast.makeText(this, R.string.correct, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,SystemPointer.class);
                    intent.putExtra(SystemPointer.Extra_User_Name, username.toString());
                    startActivity(intent);

                    }
                else{
                    Toast.makeText(this, R.string.incorrect, Toast.LENGTH_SHORT).show();
                    clearTextView();
                }
                isRegisted = false;
            }

        }
        //没有该用户
        if (isRegisted){
            Toast.makeText(this, "无法登陆该用户，请注册！", Toast.LENGTH_SHORT).show();
            clearTextView();
        }


    }

    /**
     * A Method to Clear All TextView and Focus on mUserName
     */
    private void clearTextView() {
        mUserName.setText(null);
        mPassword.setText(null);
        mUserName.setFocusable(true);   //允许焦点
        mUserName.requestFocus();       //获取焦点
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
