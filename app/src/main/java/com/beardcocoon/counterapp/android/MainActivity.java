package com.beardcocoon.counterapp.android;

import android.os.Bundle;

import com.beardcocoon.counterapp.android.fragment.ConnectFragment;
import com.beardcocoon.counterapp.android.fragment.CounterFragment;
import com.beardcocoon.counterapp.android.util.BusProvider;
import com.beardcocoon.counterapp.android.util.CastBroadcast;
import com.beardcocoon.counterapp.android.util.LogWrap;
import com.squareup.otto.Subscribe;

public class MainActivity extends BaseCastActivity {

    public static final String TAG_CAST_CONNECT_FRAG = "cast_connect_frag";
    public static final String TAG_COUNTER_FRAG = "counter_frag";

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isGMSReady()) {
            if (savedInstanceState == null) {
                onShowConnectScreen();
            }
        }

    }

    private void onShowConnectScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_placeholder,
                        ConnectFragment.getInstance(),
                        TAG_CAST_CONNECT_FRAG).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onCastApplicationReady(boolean ready) {
        if (ready) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_placeholder,
                            CounterFragment.getInstance(),
                            TAG_COUNTER_FRAG).commit();
        } else {
            onShowConnectScreen();
        }

    }


    @Subscribe
    public void onChromecastReceiverEvent(CastBroadcast event) {
        LogWrap.l(event.getEvent() + " " + event.getData());
        if (event.isOutGoing()) {
            LogWrap.l("Sending outgoing message " + event.getData());
            CastApplication.getInstance().sendMessage(event.getData());
        }
    }

}
