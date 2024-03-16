package com.example.task02.GeoquizViewAndViewController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.task02.R;

public class CheatScreen extends AppCompatActivity {
    public final  static  String EXTRA_ANSER = "ANSWER";
    public final  static  String EXTRA_ANSER_SHOW = "ANSWER_SHOW";
    private boolean answerIsTrue;
    private TextView answerTextView;
    private Button showAnswer;
    private static final String EXTRA_ANSER_SAVE = "FinishedRate";

    public static Intent newIntent(Context packageContext,boolean answerTrue){
        Intent i = new Intent(packageContext,CheatScreen.class);
        i.putExtra(EXTRA_ANSER,answerTrue);
        return  i;
    }
    public static boolean wasAnswerShow(Intent result){
        return result.getBooleanExtra(EXTRA_ANSER_SHOW,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            answerIsTrue = savedInstanceState.getBoolean(EXTRA_ANSER_SAVE,false);
        }
        setContentView(R.layout.activity_cheat_screen);
        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSER,false);
        answerTextView = findViewById(R.id.textView);
        showAnswer = findViewById(R.id.button);
        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerIsTrue){
                    answerTextView.setText(R.string.TRUE);
                }else {
                    answerTextView.setText(R.string.FALSE);
                }
                showAnswer(true);
            }
        });



    }
    private void showAnswer(boolean isAnswerShow){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSER_SHOW,isAnswerShow);
        setResult(RESULT_OK,data);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_ANSER_SAVE,answerIsTrue);
    }
}