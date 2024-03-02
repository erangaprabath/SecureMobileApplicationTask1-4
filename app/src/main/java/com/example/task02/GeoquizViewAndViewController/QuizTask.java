package com.example.task02.GeoquizViewAndViewController;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task02.QuizModel.questions;
import com.example.task02.R;

import java.util.Arrays;

public class QuizTask extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton previousButton;
    private TextView quizTextView;
    private TextView resultTextView;
    private Button resit;
    private int currentQuiz = 0;
    private int correctAnswers = 0;

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
    private boolean[] answersSelected = new boolean[quizQuestions.length];
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
        int successRate = (int)(((double)correctAnswers / quizQuestions.length) * 100);
        resultTextView.setText("Success Rate is " + successRate + "%");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_task);

        quizTextView = findViewById(R.id.quizView);
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);
        nextButton = findViewById(R.id.nextQuizButton);
        previousButton = findViewById(R.id.previousButton);
        resultTextView = findViewById(R.id.resultTextView);
        resit = findViewById(R.id.buttonResit);

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
    }
}





