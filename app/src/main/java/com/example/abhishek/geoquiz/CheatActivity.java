package com.example.abhishek.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

public class CheatActivity extends AppCompatActivity {

    private static final String Answer_True = "com.example.abhishek.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.abhishek.geoquiz.answer_shown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private InterstitialAd mInterstitialAd;
     public static Intent newIntent(Context packageContext, boolean answerIsTrue){

        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(Answer_True, answerIsTrue);
        return intent;
     }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        mAnswerIsTrue = getIntent().getBooleanExtra(Answer_True, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
           /* @Override
            public void onClick(View view) {

                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }
                else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }*/
           @Override
           public void onClick(View v) {
               if (mInterstitialAd.isLoaded()) {
                   mInterstitialAd.show();
               } else {
                   Log.d("TAG", "The interstitial wasn't loaded yet.");
               }
           }
        });

    }
}
