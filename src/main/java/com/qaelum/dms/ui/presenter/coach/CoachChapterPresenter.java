package com.qaelum.dms.ui.presenter.coach;

import com.qaelum.dms.commons.dto.QualityChapterDTO;
import com.qaelum.dms.commons.dto.QualityQuestionDTO;
import com.qaelum.dms.commons.dto.QuestionAnswerDTO;
import com.qaelum.dms.domain.dao.ICoachProjectDAO;
import com.qaelum.dms.ui.model.coach.CoachChapterModel;
import com.qaelum.dms.ui.view.coach.ICoachChapterView;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class CoachChapterPresenter implements ICoachChapterView.CoachChapterViewListener {
    private ICoachChapterView view;
    private CoachChapterModel model;
    private ICoachProjectDAO coachProjectDAO;

    public CoachChapterPresenter(ICoachChapterView view, ICoachProjectDAO coachProjectDAO) {
        this.view = view;
        this.coachProjectDAO = coachProjectDAO;

        view.addListener(this);
    }

    @Override
    public void buttonClick(String value) {
        model.setText(value);
        model.calculate();
        view.updateLabel(model.getText());
    }

    @Override
    public void saveQuestion(QualityQuestionDTO questionDTO) {
        //TODO save questionDTO to MODEL
        coachProjectDAO.saveQuestionAnswer(questionDTO);
    }

    @Override
    public void saveAllQuestions(QualityChapterDTO chapterDTO) {
        //TODO save questionDTO to MODEL
        for (QualityQuestionDTO questionDTO : chapterDTO.getQuestionDTOs()) {
            coachProjectDAO.saveQuestionAnswer(questionDTO);
        }
    }

}
