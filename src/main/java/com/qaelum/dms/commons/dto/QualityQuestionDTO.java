package com.qaelum.dms.commons.dto;

import com.qaelum.dms.domain.entity.coach.qualityItem.QualityQuestion;

/**
 * Created by Einhart on 2/15/2018.
 * © QAELUM NV
 */
public class QualityQuestionDTO {

    private String key;
    private String name;

    private QualityQuestion.QuestionType questionType;
    private String description;
    private String comment;

    private String questionAnswer;

    public QualityQuestionDTO(String key, String name, QualityQuestion.QuestionType questionType) {
        this.key = key;
        this.name = name;
        this.questionType = questionType;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public QualityQuestion.QuestionType getQuestionType() {
        return questionType;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
