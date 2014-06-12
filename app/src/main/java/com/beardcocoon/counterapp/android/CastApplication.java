package com.beardcocoon.counterapp.android;

import android.app.Application;
import android.content.Context;

import com.google.sample.castcompanionlibrary.cast.DataCastManager;
import com.google.sample.castcompanionlibrary.cast.exceptions.NoConnectionException;
import com.google.sample.castcompanionlibrary.cast.exceptions.TransientNetworkDisconnectionException;

import org.json.JSONObject;

import java.io.IOException;

public class CastApplication extends Application {
    private static CastApplication INSTANCE;
    private static DataCastManager mCastMgr;
    @SuppressWarnings("unused")
    private static final String APP_ID_TEST = "EA1EDAB6";
    private static final String APP_ID_PROD = "8D2A8165";
    private static final String APP_ID = APP_ID_PROD;
    private static final String TAG = "CastApplication";
    public static final String NAMESPACE = "urn:x-cast:com.beardcocoon.counter.message";

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
    }

    public static CastApplication getInstance() {
        return INSTANCE;
    }

    public static DataCastManager getDataCastManager(Context ctx) {
        if (null == mCastMgr) {
            mCastMgr = DataCastManager.initialize(ctx, APP_ID, NAMESPACE);
        }
        mCastMgr.setContext(ctx);
        return mCastMgr;
    }

    public void sendMessage(JSONObject message) {
        try {
            mCastMgr.sendDataMessage(message.toString(),
                    CastApplication.NAMESPACE);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransientNetworkDisconnectionException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();
        }
    }
}
