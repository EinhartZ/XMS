package com.qaelum.dms.domain.dao;

import com.qaelum.dms.commons.dto.IDmsFileDTO;
import com.qaelum.dms.commons.dto.QualityQuestionDTO;
import com.qaelum.dms.commons.dto.QuestionAnswerDTO;

import java.util.List;

public interface ICoachProjectDAO {
    void saveQuestionAnswer(QualityQuestionDTO questionDTO);
    QuestionAnswerDTO findQuestionAnswer(QualityQuestionDTO questionDTO);
    QuestionAnswerDTO findQuestionAnswer(String questionKey);

    void attachProof(QualityQuestionDTO questionDTO, IDmsFileDTO fileDTO);
    void attachProof(String questionKey, IDmsFileDTO fileDTO);
    List<IDmsFileDTO> findQuestionProof(QualityQuestionDTO questionDTO);
    List<IDmsFileDTO> findQuestionProof(String questionKey);

    void detachProof(QualityQuestionDTO questionDTO, IDmsFileDTO fileDTO);
    void detachProof(String questionKey, IDmsFileDTO fileDTO);

    void clearAllProofs(QualityQuestionDTO questionDTO);
    void clearAllProofs(String questionKey);
}
