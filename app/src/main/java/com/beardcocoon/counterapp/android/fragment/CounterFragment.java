package com.beardcocoon.counterapp.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.beardcocoon.counterapp.android.R;
import com.beardcocoon.counterapp.android.util.BusProvider;
import com.beardcocoon.counterapp.android.util.CastBroadcast;

/**
 * A simple {@link Fragment} subclass.
 */
public class CounterFragment extends Fragment {

    public static CounterFragment getInstance() {
        return new CounterFragment();
    }

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_counter, container, false);

        final EditText editTextName = (EditText) v.findViewById(R.id.editTextName);
        Button buttonIncrement = (Button) v.findViewById(R.id.buttonIncrement);

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                BusProvider.getInstance().post(new CastBroadcast.Builder().outgoing(true).forEvent("nameChange").withString("name", editTextName.getText().toString()).build());

            }
        });

        buttonIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new CastBroadcast.Builder().outgoing(true).forEvent("increment").build());
            }
        });

        return v;
    }

}
