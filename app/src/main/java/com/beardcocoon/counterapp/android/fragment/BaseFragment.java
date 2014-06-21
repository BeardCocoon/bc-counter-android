package com.beardcocoon.counterapp.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.beardcocoon.counterapp.android.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class BaseFragment extends Fragment {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Look up the AdView as a resource and load a request.
        AdView adView = (AdView) view.findViewById(R.id.adView);
        if (adView != null && shouldShowAds()) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("40056A7D65B6673384DE43998983638B")
                    .addTestDevice("399A08405C41D28A6EA43BA552F634FC")
                    .addTestDevice("FE2A7C80CAAC3372827433DD49534606").build();
            adView.loadAd(adRequest);
        }
    }

    protected boolean shouldShowAds() {
//        return CastApplication.getInstance().shouldShowAds();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

//        CastApplication.getInstance().getTracker().setScreenName(getClass().getSimpleName());
//        CastApplication.getInstance().getTracker().send(new HitBuilders.AppViewBuilder().build());
    }

}
