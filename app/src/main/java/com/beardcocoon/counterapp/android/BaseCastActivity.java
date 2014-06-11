package com.beardcocoon.counterapp.android;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.MediaRouteChooserDialogFragment;
import android.support.v7.app.MediaRouteDialogFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.beardcocoon.counterapp.android.util.BusProvider;
import com.beardcocoon.counterapp.android.util.CastBroadcast;
import com.beardcocoon.counterapp.android.util.LogWrap;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.sample.castcompanionlibrary.cast.DataCastManager;
import com.google.sample.castcompanionlibrary.cast.callbacks.DataCastConsumerImpl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by npike on 5/10/14.
 */
public abstract class BaseCastActivity extends ActionBarActivity {
    private static final String TAG_FRAG_CAST_SELECT_DIAG = "frag_cast_select_diag";
    protected DataCastManager mDataCastManager;
    private DataCastConsumerImpl mCastConsumer;
    private boolean mGMSIsReady;
    private View mViewLoading;
    private View mViewGMSError;
    private View mViewRoot;

    public abstract int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        mViewRoot = findViewById(R.id.root);
        mViewGMSError = findViewById(R.id.viewGMSError);
        mViewLoading = findViewById(R.id.relativeLayoutLoading);

        // Is GMS available?
        if (ConnectionResult.SUCCESS != GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this)) {

            mViewRoot.setVisibility(View.GONE);
            mViewGMSError.setVisibility(View.VISIBLE);
        } else {
            mGMSIsReady = true;

            mDataCastManager = CastApplication.getDataCastManager(this);
            mCastConsumer = new DataCastConsumerImpl() {

                @Override
                public boolean onApplicationConnectionFailed(int errorCode) {
                    LogWrap.l("errorCode " + errorCode);
                    Toast.makeText(BaseCastActivity.this,
                            "Unable to connect to Chromecast.  Please try again.",
                            Toast.LENGTH_LONG).show();

                    onCastApplicationReady(false);

                    showLoading(false);

                    return true;
                }

                @Override
                public boolean onConnectionFailed(ConnectionResult result) {
                    LogWrap.l();
                    return false;
                }

                @Override
                public void onApplicationConnected(ApplicationMetadata appMetadata,
                                                   String applicationStatus, String sessionId,
                                                   boolean wasLaunched) {
                    super.onApplicationConnected(appMetadata, applicationStatus,
                            sessionId, wasLaunched);
                    LogWrap.l("onApplicationConnected: "
                            + (wasLaunched ? " and launched." : ""));
                    LogWrap.l("sessionId: " + sessionId);

                    onCastApplicationReady(true);

                    showLoading(false);
                }

                @Override
                public void onDisconnected() {
                    LogWrap.l();
                    super.onDisconnected();

                }

                @Override
                public void onMessageReceived(CastDevice castDevice,
                                              String namespace, String message) {
                    super.onMessageReceived(castDevice, namespace, message);
                    LogWrap.l("onMessageReceived: " + message);

                    JSONObject jsonMessage = null;
                    try {
                        jsonMessage = new JSONObject(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    BusProvider.getInstance().post(new CastBroadcast(jsonMessage));

                }

            };
        }
    }

    public void onShowCastSelectDialog() {
        MediaRouteChooserDialogFragment f = MediaRouteDialogFactory
                .getDefault().onCreateChooserDialogFragment();
        f.setRouteSelector(mDataCastManager.getMediaRouteSelector());
        f.show(getSupportFragmentManager(), TAG_FRAG_CAST_SELECT_DIAG);
    }

    public void showLoading(boolean loading) {
        mViewLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    public boolean isGMSReady() {
        return mGMSIsReady;
    }

    public DataCastManager getCastManager() {
        return mDataCastManager;
    }

    @Override
    protected void onResume() {
        super.onResume();

        int gmsResult = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS != gmsResult) {

            mViewRoot.setVisibility(View.GONE);
            mViewGMSError.setVisibility(View.VISIBLE);

            GooglePlayServicesUtil.getErrorDialog(gmsResult, this, 100).show();
        } else {
            mViewRoot.setVisibility(View.VISIBLE);
            mViewGMSError.setVisibility(View.GONE);

            mDataCastManager = CastApplication.getDataCastManager(this);
            mDataCastManager.addDataCastConsumer(mCastConsumer);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mDataCastManager != null) {
            mDataCastManager.removeDataCastConsumer(mCastConsumer);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        mDataCastManager.addMediaRouterButton(menu, R.id.media_route_menu_item);

        return true;
    }

    protected void onCastApplicationReady(boolean ready) {

    }
}
