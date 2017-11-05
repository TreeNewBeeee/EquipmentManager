package net.nwatmb.equipmentsmanager;



import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.zxing.activity.CaptureActivity;

import java.sql.Connection;
import java.util.Calendar;


/**
 * Created by NWATMB on 2015/7/7.
 */
public class EquipmentCreate extends Fragment {
    private String mUsername;

    private ImageButton mCamera;
    private TextView mID;

    private LinearLayout mContentBlock;  //内容块
    private LinearLayout mCameraWrap;    //照相机块

    private Button mDateInUse;            //投用日期
    private int mYear;
    private int mMonth;
    private int mDay;

    private String uID;                     // user ID of equipment
    private String uYear, uMonth, uDay;     // user date

    private TextView mName;
    private String uName;

    private EditText mPosition;
    private String uPosition;

    private EditText mBrand;
    private String uBrand;

    private EditText mType;
    private String uType;

    private EditText mSN;
    private String uSN;

    private Spinner mSystem;
    private String uSystem;
    private String[] ListOfSystem = new String[]{
            "请选择...",
            "INDRA/OPE",
            "INDRA/TVS",
            "二所/OPE",
            "二所/TVS"
    };

    private String uTimeInput;   //入库时间即当天

    private Spinner mTestLog;
    private String uTestLog;
    private String[] ListOfTested = new String[]{
            "请选择...",
            "未测试",
            "已测试"
    };

    private TextView mLastOperator;
    private String uLastOperator;

    private EditText mRemark;
    private String uRemark;

    private Button mSave;
    private int saved_num;

    private Button mUpdate;
    private int ii;

    private String url = "jdbc:mysql://127.0.0.1:3306/equipment";
    private String user = "root";
    private String passwd = "860451";


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_equipment_create, container, false);
        mCamera = (ImageButton) v.findViewById(R.id.camera);
        mID = (TextView) v.findViewById(R.id.tv_ID);
        mContentBlock = (LinearLayout) v.findViewById(R.id.content_block);
        mCameraWrap = (LinearLayout) v.findViewById(R.id.camera_wrap);
        mDateInUse = (Button) v.findViewById(R.id.tv_time_in_use);
        mName = (TextView) v.findViewById(R.id.tv_name);
        mPosition = (EditText) v.findViewById(R.id.tv_position);
        mBrand = (EditText) v.findViewById(R.id.tv_brand);
        mType = (EditText) v.findViewById(R.id.tv_type);
        mSN = (EditText) v.findViewById(R.id.tv_SN);
        mSystem = (Spinner) v.findViewById(R.id.tv_system);
        mTestLog = (Spinner) v.findViewById(R.id.tv_test_log);
        mLastOperator = (TextView) v.findViewById(R.id.tv_last_operater);
        mRemark = (EditText) v.findViewById(R.id.tv_remark);
        mSave = (Button) v.findViewById(R.id.save_item);
        mUpdate = (Button) v.findViewById(R.id.update_items);



        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、update data to MySQL from local cache
                for (ii=0;ii<20;ii++)
                {
                    if (EquipmentPKG.getsID()[ii] != null)
                    {
                        Connection conn = DatabaseUtil.openConnection(url, user, passwd);
                        DatabaseUtil.query(conn,"SELECT * FROM `equipment` WHERE `Name` LIKE 'RDCU'");
                    }
                }

                //2、reset index in EquipmentPKG
                EquipmentPKG.setIndex(0);
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uID != null) {
                    saved_num = EquipmentPKG.getIndex();   //从普通类中获取index
                    if (saved_num == 20)     //缓存已满，提示上传
                    {
                        Toast.makeText(getActivity(), R.string.full_num, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        EquipmentPKG.setEquipmentPKG(uID, uName, uPosition,
                                uBrand, uType, uSN, uSystem,
                                uTimeInput, "", uTestLog, uLastOperator, uRemark);
                        // It is a test
                        Toast.makeText(getActivity(), EquipmentPKG.getsID()[1], Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });


        Bundle data = getArguments();
        mUsername = data.getString("USER_NAME");
        mLastOperator.setText(mUsername);
        uLastOperator = mUsername;


        mRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                uRemark = s.toString();

            }
        });

        mTestLog.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, ListOfTested));          //list of all systems
        mTestLog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uTimeInput = ListOfTested[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mSystem.setAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, ListOfSystem));          //list of all systems
        mSystem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uSystem = ListOfSystem[position];
                //Toast.makeText(getActivity(),uSystem,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mSN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                uSN = s.toString();

            }
        });

        mType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                uType = s.toString();

            }
        });

        mBrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                uBrand = s.toString();

            }
        });

        mPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                uPosition = s.toString();

            }
        });

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                mID.setText(s);   //Test line
                uName = s.toString();    // save the equipment name into cache

            }
        });

        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startScan = new Intent(EquipmentCreate.this.getActivity(), CaptureActivity.class);
                startActivityForResult(startScan, 999);
            }
        });

        mDateInUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = System.currentTimeMillis();   //get system time
                final Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTimeInMillis(time);
                mYear = mCalendar.get(Calendar.YEAR);   //get current date
                mMonth = mCalendar.get(Calendar.MONTH);
                mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
                uTimeInput = mYear + "-" + mMonth + "-" + mDay;  //设置入库时间为当天
                new DatePickerDialog(EquipmentCreate.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String sYear = String.valueOf(year);
                        String sMonth = String.valueOf(monthOfYear + 1);
                        String sDay = String.valueOf(dayOfMonth);
                        //display user chosen date
                        mDateInUse.setText(sYear + "-" + sMonth + "-" + sDay);
                        //save user chosen date
                        uYear = sYear;
                        uMonth = sMonth;
                        uDay = sDay;             // save the date into cache
                    }
                }, mYear, mMonth, mDay).show();     //make current date as default
            }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999) {
            String result = data.getExtras().getString("result");
//            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            mContentBlock.setVisibility(View.VISIBLE);
            mCameraWrap.setVisibility(View.GONE);
            mID.setText(result);
            uID = result;         // save the ID into cache
        }
    }





}


