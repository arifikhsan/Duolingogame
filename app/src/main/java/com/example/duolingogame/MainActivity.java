package com.example.duolingogame;

import android.animation.Animator;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RemoveAnswerListener {

    public List<Answer> listAnswers = new ArrayList<>();
    public List<String> myAnswers = new ArrayList<>();
    private List<String> actualAnswers = Arrays.asList("how", "are", "you");
    private List<String> availableAnswers = Arrays.asList(
            "morning",
            "are",
            "where",
            "not",
            "have",
            "how",
            "you");

    public FlexboxLayout answerBox;
    public View lineFirst, lineSecond;
    public String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerBox = findViewById(R.id.answerBox);
        lineFirst = findViewById(R.id.lineFirst);
        lineSecond = findViewById(R.id.lineSecond);

        for (String answer : availableAnswers) {
            TextView key = new TextView(answerBox.getContext());
            answerBox.addView(key);
            key.setBackgroundColor(Color.WHITE);
            key.setText(answer);
            key.setTextSize(18.0F);
            key.setPadding(40, 20, 40, 20);
            FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) key.getLayoutParams();
            layoutParams.setMargins(30, 30, 30, 30);
            key.setLayoutParams(layoutParams);
            key.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView answer = ((TextView) view);
                    Log.d(TAG, "onClick: " + answer.getX()
                            + " | " + answer.getY()
                            + " | " + answer.getText()
                    );
                    Log.d(TAG, "onClick: listAnswer: " + listAnswers.size() + " | " + "actualAnswer: " + actualAnswers.size());
                    if (listAnswers.size() < actualAnswers.size()) {
                        moveToAnswer(view);
                        Log.d(TAG, "onClick: current listAnswer: " + listAnswers.size() + " | " + "current actualAnswer: " + actualAnswers.size());
                    } else {
                        showAnswer();
                    }
                }
            });
        }
    }

    private void moveToAnswer(View view) {
        view.setOnClickListener(null);
        listAnswers.add(new Answer(
                view,
                view.getX(),
                view.getY(),
                ((TextView) view).getText().toString(),
                MainActivity.this
        ));
        myAnswers.add(((TextView) view).getText().toString());
        float topPosition = lineFirst.getY() - 120.0F;
        float leftPosition = lineFirst.getX();

        if (listAnswers.size() > 1) {
            float allWidth = 0F;
            for (Answer answer : listAnswers) {
                allWidth += answer.getView().getWidth() + 20F;
            }
            allWidth -= view.getWidth();
            leftPosition = lineFirst.getX() + allWidth;
        }

        if (listAnswers.size() == actualAnswers.size()) {
            showAnswer();
        }

        Animator.AnimatorListener completeMove = new Animator.AnimatorListener() {
            public void onAnimationRepeat( Animator p0) {
            }

            public void onAnimationCancel( Animator p0) {
            }

            public void onAnimationStart(Animator p0) {
            }

            public void onAnimationEnd( Animator p0) {
                if (listAnswers.size() == actualAnswers.size()) {
                    showAnswer();
                }

            }
        };



        view.animate()
                .setListener(completeMove)
                .x(leftPosition)
                .y(topPosition);
    }

    private void showAnswer() {
        boolean isCorrect = actualAnswers.equals(myAnswers);
        Log.d(TAG, "showAnswer: listAnswers: " + myAnswers.toString());
        Log.d(TAG, "showAnswer: actualAnswers: " + actualAnswers.toString());
        Log.d(TAG, "showAnswer: isCorrect: " + isCorrect);
        String answerMessage = isCorrect ? "Jawaban Anda benar" : "Jawaban Anda salah silahkan coba lagi";

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Result");
        alertDialog.setMessage(answerMessage);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onRemove(Answer answer) {
        Log.d(TAG, "onRemove: start remove");
        listAnswers.remove(answer);
        float allWidth = 0F;

        for (Answer currentAnswer : listAnswers) {
            View view = currentAnswer.getView();
            allWidth += currentAnswer.getView().getWidth() + 20F;
            if (currentAnswer == listAnswers.get(0)) {
                view.animate().x(0F);
            } else {
                allWidth -= view.getWidth();
                view.animate().x(allWidth);
            }
        }

        answer.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAnswer(view);
            }
        });
    }

}
