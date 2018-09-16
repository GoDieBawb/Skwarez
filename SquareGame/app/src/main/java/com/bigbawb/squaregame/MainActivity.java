package com.bigbawb.squaregame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    static DrawView     v;
    static PalletteView p;
    static int width;
    static int height;
    static String color;
    private InterstitialAd mInterstitialAd;
    static HashMap<String, int[]> colors = new HashMap<>();
    static boolean firstInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!firstInit) {
            firstInit();
        }

        initAd();
        p     = new PalletteView(this);
        v     = new DrawView(this);
        setContentView(v);
        v.invalidate();

    }

    private void firstInit() {
        firstInit=true;
        initColors();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        color = "White";
    }

    private void initAd() {
        setContentView(R.layout.activity_main);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9434547190848397/2969955467");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                showPalletteView();
            }

        });

    }

    int switchCount = 0;
    public void showPalletteView() {

        if (switchCount == 3) {

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                switchCount = 0;
            }

            else {
                setContentView(p);
                p.invalidate();
            }

        }

        else {
            setContentView(p);
            p.invalidate();
            switchCount++;
        }

    }

    public void showDrawView() {
        setContentView(v);
        v.invalidate();
    }

    private void initColors() {
        colors.put("Black",   new int[] {0,0,0});
        colors.put("White",   new int[] {255,255,255});
        colors.put("Red",     new int[] {255,0,0});
        colors.put("Lime",    new int[] {0,255,0});
        colors.put("Blue",    new int[] {0,0,255});
        colors.put("Yellow",  new int[] {255,255,0});
        colors.put("Cyan",    new int[] {0,255,255});
        colors.put("Magenta", new int[] {255,0,255});
        colors.put("Brown",   new int[] {139,69,19});
        colors.put("Gray",    new int[] {128,128,128});
        colors.put("Maroon",  new int[] {128,0,0});
        colors.put("Orange",  new int[] {255,165,0});
        colors.put("Green",   new int[] {0,128,0});
        colors.put("Purple",  new int[] {128,0,128});
        colors.put("Teal",    new int[] {0,128,128});
        colors.put("Navy",    new int[] {0,0,128});
    }

    public static int[] toRGB(String color) {
        return colors.get(color);
    }

}
