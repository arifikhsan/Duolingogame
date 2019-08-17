package com.example.duolingogame;
// Created by Arif Ikhsanudin on 8/18/2019.

import android.view.View;

public class Answer {
    private View view;
    private Float actualPositionX;
    private Float actualPositionY;
    private String text;
    private RemoveAnswerListener removeAnswerListener;

    public Answer(View view, Float actualPositionX, Float actualPositionY, String text, RemoveAnswerListener removeAnswerListener) {
        this.view = view;
        this.actualPositionX = actualPositionX;
        this.actualPositionY = actualPositionY;
        this.text = text;
        this.removeAnswerListener = removeAnswerListener;
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
}

interface RemoveAnswerListener {
    void onRemove(Answer answer);
}
