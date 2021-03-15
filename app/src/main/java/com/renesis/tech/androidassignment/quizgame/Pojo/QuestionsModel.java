package com.renesis.tech.androidassignment.quizgame.Pojo;

import java.util.ArrayList;

public class QuestionsModel {
    String question="";
    String type="";
    String correct_answer="";
    ArrayList<String> incorrectans ;

    public QuestionsModel(String question, String correct_answer, ArrayList<String> incorrectans) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrectans = incorrectans;
    }

    public QuestionsModel(String question, String type, String correct_answer, ArrayList<String> incorrectans) {
        this.question = question;
        this.type = type;
        this.correct_answer = correct_answer;
        this.incorrectans = incorrectans;
    }

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public ArrayList<String> getIncorrectans() {
        return incorrectans;
    }

    public void setIncorrectans(ArrayList<String> incorrectans) {
        this.incorrectans = incorrectans;
    }
}
