package com.qaelum.dms.commons.dto;

import com.qaelum.dms.domain.entity.coach.qualityItem.QualityQuestion;

/**
 * Created by Einhart on 2/15/2018.
 * © QAELUM NV
 */
public class QualityQuestionDTO {

    private String key;
    private String name;

    private String description;
    private QualityQuestion.QuestionType questionType;
    private String comment;

    public QualityQuestionDTO(String key, String name, String description, QualityQuestion.QuestionType questionType, String comment) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.questionType = questionType;
        this.comment = comment;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public QualityQuestion.QuestionType getQuestionType() {
        return questionType;
    }

    public String getComment() {
        return comment;
    }
}
