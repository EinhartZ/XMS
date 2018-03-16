package com.qaelum.dms.ui.view.coach;

import com.qaelum.dms.commons.dto.IDmsFileDTO;
import com.qaelum.dms.commons.dto.QualityChapterDTO;
import com.qaelum.dms.commons.dto.QualityQuestionDTO;
import com.qaelum.dms.commons.dto.QuestionAnswerDTO;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public interface ICoachChapterView {
    void updateChapterDTO(QualityChapterDTO chapterDTO, QualityQuestionDTO selectedQuestionDTO);

    void updateLabel(String value);

    void addListener(CoachChapterViewListener listener);

    /**
     * Listener Interface
     */
    interface CoachChapterViewListener {
        void buttonClick(String value);
        default void saveQuestionAnswer(QualityQuestionDTO questionDTO) {
            //NOP
        }

        void saveAllQuestionAnswer(QualityChapterDTO chapterDTO);

        default void saveQuestionProof(QualityQuestionDTO questionDTO) {
            //NOP
        }

        default void detachQuestionProof(QualityQuestionDTO questionDTO, IDmsFileDTO fileDTO) {
            //NOP
        }

        default void selectQuestion(QualityQuestionDTO questionDTO) {
            //NOP
        }
    }
}
