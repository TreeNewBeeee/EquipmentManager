package net.nwatmb.equipmentsmanager;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxing.activity.CaptureActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by NWATMB on 2015/7/7.
 */
public class EquipmentInfo extends Fragment {

    private ImageButton mCamera;
    private String spareID;
    private LinearLayout mContentBlock;  //内容块
    private LinearLayout mCameraWrap;    //照相机块
    private TextView mName;
    private TextView mSpareDivision;
    private TextView mSpareHouse;
    private TextView mLocation;
    private TextView mSpareSystem;
    private TextView mSpareType;
    private TextView mLabel;
    private TextView mModel;
    private TextView mSN;
    private TextView mSeller;
    private TextView mManufacture;
    private TextView mPrice;
    private TextView mOrderDate;
    private TextView mTestPeriod;
    private TextView mQuantity;
    private TextView mCreateDate;
    private TextView mUpdateDate;
    private TextView mOperator;

    /*数据库读取*/
    private static final String url_spare_detials =
            "http://192.168.8.105/androidHTTP/DB_OPE/get_spare_details.php";
    private static final String url_id_name =
            "http://192.168.8.105/androidHTTP/DB_OPE/get_id_name.php";
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_SPARE = "spare_info";
    private static final String TAG_AUTHORITY = "result";


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_equipment_info,container,false);
        /* regist the module */
        mCamera = (ImageButton) v.findViewById(R.id.camera);
        mContentBlock = (LinearLayout) v.findViewById(R.id.content_block);
        mCameraWrap = (LinearLayout) v.findViewById(R.id.camera_wrap);
        mName = (TextView) v.findViewById(R.id.name);
        mSpareDivision = (TextView) v.findViewById(R.id.spare_division);
        mSpareHouse = (TextView) v.findViewById(R.id.spare_house);
        mLocation = (TextView) v.findViewById(R.id.location);
        mSpareSystem = (TextView) v.findViewById(R.id.spare_system);
        mSpareType = (TextView) v.findViewById(R.id.spare_type);
        mLabel = (TextView) v.findViewById(R.id.label);
        mModel = (TextView) v.findViewById(R.id.model);
        mSN = (TextView) v.findViewById(R.id.SN);
        mSeller = (TextView) v.findViewById(R.id.seller);
        mManufacture = (TextView) v.findViewById(R.id.manufacture);
        mPrice = (TextView) v.findViewById(R.id.price);
        mOrderDate = (TextView) v.findViewById(R.id.orderDate);
        mTestPeriod = (TextView) v.findViewById(R.id.testPeriod);
        mQuantity = (TextView) v.findViewById(R.id.quantity);
        mCreateDate = (TextView) v.findViewById(R.id.createDate);
        mUpdateDate = (TextView) v.findViewById(R.id.updateDate);
        mOperator = (TextView) v.findViewById(R.id.operator);


        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                Intent startScan = new Intent(EquipmentInfo.this.getActivity(), CaptureActivity.class);
                startActivityForResult(startScan, 999);
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*扫码后获取备件ID*/
        if (requestCode == 999){
            spareID = data.getExtras().getString("result");
            mContentBlock.setVisibility(View.VISIBLE); // 显示表单
            mCameraWrap.setVisibility(View.GONE);  // 隐藏相机按钮
//            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            // TODO: 后期二维码扫描后为URL格式，需提取相应参数
            LoadSpareDetail spareDetail = new LoadSpareDetail();
            JSONObject object = new JSONObject();
            try {
                object = spareDetail.execute().get();
                // 获取最后经手人
                String[] vars = new String[]{"user_id",object.getString("user_id")};
                getIdName getOperatorName = new getIdName();
                String operator = getOperatorName.execute(vars).get();
                // 获取部门名称
                vars = new String[]{"division_id",object.getString("division_id")};
                getIdName getDivisionName = new getIdName();
                String division = getDivisionName.execute(vars).get();
                // 获取库房名称
                vars = new String[]{"spare_house_id",object.getString("spare_house_id")};
                getIdName getSpareHouseName = new getIdName();
                String spareHouse = getSpareHouseName.execute(vars).get();
                // 获取所属系统
                vars = new String[]{"spare_system_id",object.getString("spare_system_id")};
                getIdName getSystemName = new getIdName();
                String system = getSystemName.execute(vars).get();
                // 获取备件分类
                vars = new String[]{"spare_type_id",object.getString("spare_type_id")};
                getIdName getTypeName = new getIdName();
                String type = getTypeName.execute(vars).get();


                mName.setText(object.getString("name"));
                mSpareDivision.setText(division);
                mSpareHouse.setText(spareHouse);
                mLocation.setText(object.getString("location"));
                mSpareSystem.setText(system);
                mSpareType.setText(type);
                mLabel.setText(object.getString("label"));
                mModel.setText(object.getString("model"));
                mSN.setText(object.getString("sn"));
                mSeller.setText(object.getString("seller"));
                mManufacture.setText(object.getString("manufacturer"));
                mPrice.setText(object.getString("orderprice"));
                mOrderDate.setText(object.getString("orderdate"));
                mTestPeriod.setText(object.getString("testperiod"));
                mQuantity.setText(object.getString("quantity"));
                mCreateDate.setText(object.getString("created_at"));
                mUpdateDate.setText(object.getString("updated_at"));
                mOperator.setText(operator);



            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @SuppressLint("StaticFieldLeak")
    class getIdName extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            int success;
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair(strings[0], strings[1]));
            //Log.d("PARAMS", params.toString());
            JSONObject json = jsonParser.makeHttpRequest(url_id_name, "GET", params);
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    String name = json.getString("name");
                    return name;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

    /**
     * Background Async Task to Get Spare info
     */
    @SuppressLint("StaticFieldLeak")
    class LoadSpareDetail extends AsyncTask<Void, Void, JSONObject> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getString(R.string.loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         */
        @Override
        protected JSONObject doInBackground(Void... voids) {
            int success;
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("spareID", spareID));
            //Log.d("PARAMS", params.toString());
            JSONObject json = jsonParser.makeHttpRequest(url_spare_detials, "GET", params);
            Log.d("Spare Details", json.toString());
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray userOBJ = json.getJSONArray(TAG_SPARE);
                    JSONObject user = userOBJ.getJSONObject(0);
                    return user;
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
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            pDialog.dismiss();
        }
    }


}
