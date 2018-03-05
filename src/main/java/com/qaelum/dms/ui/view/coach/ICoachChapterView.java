package com.qaelum.dms.ui.view.coach;

import com.qaelum.dms.commons.dto.QualityChapterDTO;
import com.qaelum.dms.commons.dto.QualityQuestionDTO;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public interface ICoachChapterView {
    void updateChapterDTO(QualityChapterDTO chapterDTO);

    void updateLabel(String value);

    void addListener(CoachChapterViewListener listener);

    void saveQuestion(QualityQuestionDTO questionDTO);
    /**
     * Listener Interface
     */
    interface CoachChapterViewListener {
        void buttonClick(String value);
        void saveQuestion(QualityQuestionDTO questionDTO);
    }
}
