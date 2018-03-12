package com.qaelum.dms.commons.dto;

public class QuestionAnswerDTO {
    private boolean initiated = false;
    private String answerTxt;

    public QuestionAnswerDTO() {
    }

    public QuestionAnswerDTO(String answerTxt) {
        initiated = true;
        this.answerTxt = answerTxt;
    }

    public boolean isInitiated() {
        return initiated;
    }

    public String getAnswerTxt() {
        return answerTxt;
    }

    public void setAnswerTxt(String answerTxt) {
        this.answerTxt = answerTxt;
    }
}
