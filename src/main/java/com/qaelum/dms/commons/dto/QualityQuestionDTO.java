package com.qaelum.dms.commons.dto;

import com.qaelum.dms.domain.entity.coach.qualityItem.QualityQuestion;

import java.util.List;

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

//    private String questionAnswer;
    private QuestionAnswerDTO answerDTO;
    private List<IDmsFileDTO> proofList;


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

    public boolean hasAnswer() {
        return answerDTO != null;
    }

    public QuestionAnswerDTO getAnswerDTO() {
        return answerDTO;
    }

    public String getQuestionAnswer() {
        if(this.answerDTO == null) {
            return "-empty-";
        } else {
            return answerDTO.getAnswerTxt();
        }
    }

    public void setAnswerDTO(QuestionAnswerDTO answerDTO) {
        this.answerDTO = answerDTO;
    }

    public void setQuestionAnswer(String questionAnswer) {
        if(this.answerDTO == null) {
            answerDTO = new QuestionAnswerDTO(questionAnswer);
        } else {
            answerDTO.setAnswerTxt(questionAnswer);
        }
    }

    public List<IDmsFileDTO> getProofList() {
        return proofList;
    }

    public void setProofList(List<IDmsFileDTO> proofList) {
        this.proofList = proofList;
    }
}
