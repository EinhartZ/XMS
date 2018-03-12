package com.qaelum.dms.ui.view.coach;

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

    void loadQuestionAnswer(QualityQuestionDTO questionDTO, QuestionAnswerDTO answerDTO);

    void addListener(CoachChapterViewListener listener);

    /**
     * Listener Interface
     */
    interface CoachChapterViewListener {
        void buttonClick(String value);
        default void saveQuestion(QualityQuestionDTO questionDTO) {
            //NOP
        };
        void saveAllQuestions(QualityChapterDTO chapterDTO);

        default void selectQuestion(QualityQuestionDTO questionDTO) {
            //NOP
        };
    }
}
