package com.example.duolingogame;

import android.animation.Animator;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RemoveAnswerListener {

    public List<Answer> listAnswers = new ArrayList<>();
    private List<String> actualAnswers = Arrays.asList("how", "are", "you");
    private List<String> availableAnswers = Arrays.asList(
            "morning",
            "are",
            "where",
            "not",
            "have",
            "How",
            "you");

    public FlexboxLayout answerBox;
    public View lineFirst, lineSecond;

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
            key.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        moveToAnswer(view);
    }

    private void moveToAnswer(View view) {
        if (listAnswers.size() < actualAnswers.size()) {
//            view.setOnClickListener(null);
            String a = ((TextView) view).getText().toString();
            listAnswers.add(new Answer(view, view.getX(), view.getY(), a, MainActivity.this));
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

            Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    if (listAnswers.size() == actualAnswers.size()) {
                        showAnswer();
                    }
                }
            };

            view.animate()
                    .setListener(animatorListener)
                    .x(leftPosition)
                    .y(topPosition);
        }
    }

    private void showAnswer() {
//        Boolean isCorrect = actualAnswers.equals(listAnswers);
        boolean isCorrect = true;
        for (int i = 0; i < actualAnswers.size(); i++) {
            if (!listAnswers.get(i).getText().equals(actualAnswers.get(i))) {
                isCorrect = false;
            }
        }
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
