package com.qaelum.dms.ui.presenter.coach;

import com.qaelum.dms.commons.dto.IDmsFileDTO;
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
    public void saveQuestionAnswer(QualityQuestionDTO questionDTO) {
        coachProjectDAO.saveQuestionAnswer(questionDTO);
    }

    @Override
    public void saveAllQuestionAnswer(QualityChapterDTO chapterDTO) {
        for (QualityQuestionDTO questionDTO : chapterDTO.getQuestionDTOs()) {
            coachProjectDAO.saveQuestionAnswer(questionDTO);
        }
    }

    @Override
    public void saveQuestionProof(QualityQuestionDTO questionDTO) {
        //TODO save questionDTO to MODEL
    }

    @Override
    public void detachQuestionProof(QualityQuestionDTO questionDTO, IDmsFileDTO fileDTO) {
        coachProjectDAO.detachProof(questionDTO, fileDTO);
    }
}
