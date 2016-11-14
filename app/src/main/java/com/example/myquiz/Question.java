package com.example.myquiz;

/**
 * Created by Sergio on 12/11/2016.
 */

public class Question {
    private static int ID;
    public String question;
    public String answer;
    public String option1;
    public String option2;
    public String option3;
    public char getQuestion;
    public char getOption1;
    public char getOption2;
    public char getOption3;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }
}
