package net.nwatmb.equipmentsmanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends Activity{


    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();
//    ArrayList<HashMap<String, String>> productsList;

    // single product url
    private static final String url_user_detials =
            "http://192.168.8.105/androidHTTP/DB_OPE/get_user_details.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USER = "user_info";
    private static final String TAG_AUTHORITY = "result";


    // products JSONArray
//    JSONArray users = null;

    //界面定义
    private EditText etUserName;
    private EditText etPassword;
    private Button mLogin;

    //变量定义
    private String keyPass;
    private String name;    // 用户输入
    private String passwd;  // 查询返回密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //for avoid error of android.os.NetworkOnMainThreadException
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);

        }


        //相关组件
        etUserName = (EditText) findViewById(R.id.user_name);
        etPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.btn_login);


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etUserName.getText().toString();
                passwd = etPassword.getText().toString();
                LoadUserDetail userDetail = new LoadUserDetail();

                try {
                    keyPass = userDetail.execute().get();
//                    Log.d("keypass",keyPass);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (keyPass.equals("keyPairSuccess")){
                    checkLogin();
                }else {
                    Toast.makeText(getApplicationContext(), R.string.incorrect, Toast.LENGTH_SHORT).show();
                    clearTextView();
                }
           }
        });
    }

    private void checkLogin() {
        Intent intent = new Intent(LoginActivity.this, SystemPointer.class);
        intent.putExtra(SystemPointer.Extra_User_Name, name);
        startActivity(intent);

    }

    /**
     * A Method to Clear All TextView and Focus on mUserName
     */
    private void clearTextView() {
        etUserName.setText(null);
        etPassword.setText(null);
        etUserName.setFocusable(true);   //允许焦点
        etUserName.requestFocus();       //获取焦点
    }


    /**
     * Background Async Task to Get user info
     */
    @SuppressLint("StaticFieldLeak")
    class LoadUserDetail extends AsyncTask<Void, Void, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(getString(R.string.loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         */
        @Override
        protected String doInBackground(Void... voids) {
            int success;
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("password",passwd));
//            Log.d("PARAMS", params.toString());
            JSONObject json = jsonParser.makeHttpRequest(url_user_detials, "GET", params);
//            Log.d("User Details", json.toString());
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray userOBJ = json.getJSONArray(TAG_USER);
                    JSONObject user = userOBJ.getJSONObject(0);
//                    Log.d("Database Password", user.getString(TAG_PASSWD));
                    return user.getString(TAG_AUTHORITY);
                } else {
                    JSONArray userOBJ = json.getJSONArray(TAG_USER);
                    JSONObject user = userOBJ.getJSONObject(0);
//                    Log.d("Database Password", user.getString(TAG_PASSWD));
                    return user.getString(TAG_AUTHORITY);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.d("PostExecuted", s);
            pDialog.dismiss();

        }
    }

}


