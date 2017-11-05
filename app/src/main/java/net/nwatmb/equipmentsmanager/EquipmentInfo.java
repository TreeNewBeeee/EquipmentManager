package net.nwatmb.equipmentsmanager;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zxing.activity.CaptureActivity;

/**
 * Created by NWATMB on 2015/7/7.
 */
public class EquipmentInfo extends Fragment {

    private ImageButton mCamera;
    private TextView mTV;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_equipment_create,container,false);
        mCamera = (ImageButton) v.findViewById(R.id.camera);
        mTV = (TextView) v.findViewById(R.id.tv_ID);
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

        if (requestCode == 999){
            String result = data.getExtras().getString("result");
//            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
            mTV.setText(result);
        }
    }
}
