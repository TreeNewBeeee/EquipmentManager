package net.nwatmb.equipmentsmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by NWATMB on 2015/7/7.
 */
public class EquipmentEdit extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView iv = new ImageView(getActivity());
        iv.setImageResource(R.drawable.logo);
        return iv;
    }
}
