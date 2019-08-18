package com.example.duolingogame;
// Created by Arif Ikhsanudin on 8/18/2019.

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Answer {
    private View view;
    private Float actualPositionX;
    private Float actualPositionY;
    private String text;
    private RemoveAnswerListener removeAnswerListener;

    Answer(View view, Float actualPositionX, Float actualPositionY, String text, final RemoveAnswerListener removeAnswerListener) {
        this.view = view;
        this.actualPositionX = actualPositionX;
        this.actualPositionY = actualPositionY;
        this.text = text;
        this.removeAnswerListener = removeAnswerListener;
        View back = this.view;
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.animate()
                            .x(Answer.this.actualPositionX)
                            .y(Answer.this.actualPositionY);
                    RemoveAnswerListener removeAnswerListener1 = Answer.this.getRemoveAnswerListener();
                    if (removeAnswerListener1 != null) {
                        removeAnswerListener1.onRemove(Answer.this);
                    }

                }
            });
        }
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Float getActualPositionX() {
        return actualPositionX;
    }

    public void setActualPositionX(Float actualPositionX) {
        this.actualPositionX = actualPositionX;
    }

    public Float getActualPositionY() {
        return actualPositionY;
    }

    public void setActualPositionY(Float actualPositionY) {
        this.actualPositionY = actualPositionY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RemoveAnswerListener getRemoveAnswerListener() {
        return removeAnswerListener;
    }

    public void setRemoveAnswerListener(RemoveAnswerListener removeAnswerListener) {
        this.removeAnswerListener = removeAnswerListener;
    }

    public Answer() {
        view = null;
        actualPositionX = 0F;
        actualPositionY = 0F;
        text = " ";
        removeAnswerListener = null;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "view=" + view +
                ", actualPositionX=" + actualPositionX +
                ", actualPositionY=" + actualPositionY +
                ", text='" + text + '\'' +
                ", removeAnswerListener=" + removeAnswerListener +
                '}';
    }


}
