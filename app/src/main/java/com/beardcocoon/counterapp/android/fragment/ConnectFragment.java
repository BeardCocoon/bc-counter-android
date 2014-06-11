package com.beardcocoon.counterapp.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardcocoon.counterapp.android.R;
import com.beardcocoon.counterapp.android.util.LogWrap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConnectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConnectFragment extends Fragment {

    public static ConnectFragment getInstance() {
        ConnectFragment fragment = new ConnectFragment();
        return fragment;
    }

    public ConnectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogWrap.l();
        View v = inflater.inflate(R.layout.frag_connect, container, false);

        return v;
    }

}
