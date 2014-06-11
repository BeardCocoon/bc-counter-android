package com.beardcocoon.counterapp.android.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by npike on 3/22/14.
 */
public class CastBroadcast {
    private JSONObject mData;
    private boolean mIsOutGoing = false;

    public static class Builder {
        private JSONObject mPayload = new JSONObject();
        private boolean mIsOutGoing = false;

        public Builder forEvent(String event) {
            try {
                mPayload.putOpt("event", event);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return this;
        }

        public Builder withObject(String key, JSONObject data) {
            try {
                mPayload.putOpt(key, data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return this;
        }

        public Builder withObject(String key, String json) {
            try {
                return withObject(key, new JSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return this;
        }

        public Builder withString(String key, String data) {
            try {
                mPayload.putOpt(key, data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return this;
        }

        public Builder withInteger(String key, int data) {
            try {
                mPayload.putOpt(key, data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return this;
        }

        public Builder withLong(String key, long data) {
            try {
                mPayload.putOpt(key, data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return this;
        }

        public Builder withBuilder(String key, Builder b) {
            try {
                mPayload.putOpt(key, b.getPayload());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return this;
        }

        public Builder outgoing(boolean outgoing) {
            mIsOutGoing = outgoing;

            return this;
        }

        public JSONObject getPayload() {
            return mPayload;
        }

        public CastBroadcast build() {
            return new CastBroadcast(mPayload, mIsOutGoing);
        }
    }

    public CastBroadcast(JSONObject data) {
       this(data, false);
    }

    public CastBroadcast(JSONObject data, boolean outgoing) {
        mData = data;
        mIsOutGoing = outgoing;
    }

    public JSONObject getData() {
        return mData;
    }

    public void setData(JSONObject data) {
        mData = data;
    }

    public void setOutGoing(boolean outgoing) {
        mIsOutGoing = outgoing;
    }

    public boolean isOutGoing() {
        return mIsOutGoing;
    }

    public String getEvent() {
        if (mData != null) {
            return mData.optString("event", null);
        }
        return null;
    }

    @Override
    public String toString() {
        return mData.toString();
    }
}
