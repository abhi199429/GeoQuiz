package com.example.abhishek.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;



public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    Button true_value, false_value, submit;
    TextView questionView;
    ImageButton nextButton;
    ImageButton previousButton;
    Button cheat;
    private static final String Tag = "MainActivity";
    Record obj = new Record();
    int scoreThis = 0;
    HashSet<Integer> dummy = new HashSet<>();
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;

    private final void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueAnswer();
        int messageResId = 0;
        dummy.add(mCurrentIndex);
        obj.setSet(dummy);
        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                scoreThis++;
                obj.setScore(scoreThis);
                obj.setSet(dummy);

            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag, "OnCreate(Bundle) Started......");
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        true_value = (Button) findViewById(R.id.trueButton);
        false_value = (Button) findViewById(R.id.falseButton);
        nextButton = (ImageButton) findViewById(R.id.Next);
        previousButton = (ImageButton) findViewById(R.id.prev);
        submit = (Button) findViewById(R.id.submit_Button);
        cheat = (Button) findViewById(R.id.cheat_button);
        questionView = (TextView) findViewById(R.id.textView);
        int question = mQuestionBank[mCurrentIndex].getReourceId();
        questionView.setText(question);

        true_value.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                //Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                if(obj.getSet().contains(mCurrentIndex)){
                    Toast.makeText(MainActivity.this, "Answer has already been selected", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkAnswer(true);
                }
            }
        });

        false_value.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                //Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();

                if(obj.getSet().contains(mCurrentIndex)){
                    Toast.makeText(MainActivity.this, "Answer has already been selected", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkAnswer(false);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    int question = mQuestionBank[mCurrentIndex].getReourceId();
                    questionView.setText(question);
                    mIsCheater = false;

            }
        });

        questionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                int question = mQuestionBank[mCurrentIndex].getReourceId();
                questionView.setText(question);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCurrentIndex == 0)
                        Toast.makeText(MainActivity.this, "This is the first question, you must go next", Toast.LENGTH_SHORT).show();
                    else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    mIsCheater = false;
                    int question = mQuestionBank[mCurrentIndex].getReourceId();
                    questionView.setText(question);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double finalScore = (double)obj.score/6*100;
                Toast.makeText(MainActivity.this, "Your score for the test is " + (double)Math.round(finalScore) + "%.", Toast.LENGTH_SHORT).show();
            }
        });


        cheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueAnswer();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(Tag, "OnPostResume() called.....");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Tag, "onResume() called.....");

    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(Tag, "onPause() called.....");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag, "OnStart() Called.....");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag, "OnStop() Called.....");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag, "OnDestroy() Called.....");
    }


}
