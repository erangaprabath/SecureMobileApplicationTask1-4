package com.example.task02.GeoquizViewAndViewController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task02.MainActivity;
import com.example.task02.QuizModel.questions;
import com.example.task02.R;

import java.util.Arrays;
import android.util.Log;


public class QuizTask extends AppCompatActivity {
    private Button falseButton,trueButton,resit;
    private ImageButton nextButton,previousButton;
    private TextView quizTextView,resultTextView,cheatQuizCount;
    private int currentQuiz = 0;
    private int correctAnswers = 0;
    private static final  String TAG = "QuizTask";
    private static final String KEY_INDEX = "INDEX";
    private static final String SUCCSS_RATE = "FinishedRate";
    private static  final String CORRECT_ANSWER = "Answers";
    private static  final String CHEATED_COUNT = "Answers";
    private Button nextScreen;
    private int successRate = 0;
    private int[] cheatedQuizCount = {0};
    private static  final  int REQUEST_CHEAT = 0;
    private questions[] quizQuestions = new questions[]{
            new questions(R.string.question_ocean,true),
            new questions(R.string.question_mideast,false),
            new questions(R.string.question_africa,false),
            new questions(R.string.question_americas,true),
            new questions(R.string.question_asia,true),
            new questions(R.string.question_china,false),
            new questions(R.string.question_mountain,true),
            new questions(R.string.question_canada,false),
            new questions(R.string.question_france,true),
            new questions(R.string.question_sea,true),

    };
    private final boolean[] answersSelected = new boolean[quizQuestions.length];
    private boolean[] cheat = new boolean[quizQuestions.length];
    private static  final String IS_CHEATED = "cheated";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK){
            return;
        }if (requestCode == REQUEST_CHEAT){
            if (data == null){
                return;
            }
            cheat[currentQuiz] = CheatScreen.wasAnswerShow(data);
        }
    }



    private void updateQuizView(int quizIndex){
        int updatedQuiz = quizQuestions[quizIndex].getQuestionTextId();
        quizTextView.setText("NO : " + quizIndex + " " + getString(updatedQuiz));
        if (quizIndex == 0) {
            previousButton.setVisibility(View.INVISIBLE);
        } else {
            previousButton.setVisibility(View.VISIBLE);
        }
            trueButton.setEnabled(!answersSelected[quizIndex]);
            falseButton.setEnabled(!answersSelected[quizIndex]);

    }

    private void checkAnswers(boolean selectedAnswer){
        boolean correctAnswer = quizQuestions[currentQuiz].isAnswer();
        if (cheat[currentQuiz] && !answersSelected[currentQuiz]){
            Toast.makeText(this,"CHEATER",Toast.LENGTH_SHORT).show();
        }else {
            if (!answersSelected[currentQuiz]) {
                if (selectedAnswer == correctAnswer) {
                    Toast.makeText(this, R.string.correctAnswer, Toast.LENGTH_SHORT).show();
                    correctAnswers++;
                } else {
                    Toast.makeText(this, R.string.wrongAnswer, Toast.LENGTH_SHORT).show();
                }
                answersSelected[currentQuiz] = true;
                updateQuizView(currentQuiz);
            }
        }
    }

    private void resitQuiz(){
        currentQuiz = 0;
        correctAnswers = 0;
        Arrays.fill(answersSelected, false);
        updateQuizView(currentQuiz);
        resultTextView.setText("");
    }

    private void updateNextQuiz(){
        currentQuiz = (currentQuiz + 1) % quizQuestions.length;
        updateQuizView(currentQuiz);
        updateSuccessRate();
    }

    private void updatePreviousQuiz(){
        currentQuiz = (currentQuiz - 1 + quizQuestions.length) % quizQuestions.length;
        updateQuizView(currentQuiz);
        updateSuccessRate();
    }

    private void updateSuccessRate(){

       successRate = (int)(((double)correctAnswers / quizQuestions.length) * 100);
        resultTextView.setText("Success Rate is " + successRate + "%");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"on create (Bundle) call");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_task);
        cheat[currentQuiz] = false;
        if (savedInstanceState != null){
            currentQuiz = savedInstanceState.getInt(KEY_INDEX,0);
            successRate = savedInstanceState.getInt(SUCCSS_RATE,1);
            correctAnswers = savedInstanceState.getInt(CORRECT_ANSWER,2);
            cheat[currentQuiz] = savedInstanceState.getBoolean(IS_CHEATED,false);
            cheatedQuizCount = savedInstanceState.getIntArray(CHEATED_COUNT);
            if (cheatedQuizCount == null) {
                cheatedQuizCount = new int[]{0, 1};
            }

        }
//        cheatQuizCount = findViewById(R.id.cheatedQuizCount);
        quizTextView = findViewById(R.id.quizView);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        nextButton = findViewById(R.id.nextQuizButton);
        previousButton = findViewById(R.id.previousButton);
        resultTextView = findViewById(R.id.resultTextView);
        resit = findViewById(R.id.buttonResit);
        nextScreen = findViewById(R.id.NextScreen);
        resultTextView.setText("Success Rate is " + successRate + "%");

        updateQuizView(currentQuiz);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswers(true);
                updateNextQuiz();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswers(false);
                updateNextQuiz();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNextQuiz();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePreviousQuiz();
            }
        });
        resit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resitQuiz();
            }
        });
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = quizQuestions[currentQuiz].isAnswer();
                Intent i = CheatScreen.newIntent(QuizTask.this,answerIsTrue);
//                Intent intent = new Intent(QuizTask.this,CheatScreen.class);

                startActivityForResult(i,REQUEST_CHEAT);
            }
        });
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX,currentQuiz);
        outState.putInt(SUCCSS_RATE,successRate);
        outState.putInt(CORRECT_ANSWER,correctAnswers);
        outState.putBoolean(IS_CHEATED,cheat[currentQuiz]);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"on start called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"on pause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"on resume called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"on stop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"on destroy called");
    }
}





