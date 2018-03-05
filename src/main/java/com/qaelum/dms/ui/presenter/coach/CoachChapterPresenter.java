package com.qaelum.dms.ui.presenter.coach;

import com.qaelum.dms.commons.dto.QualityQuestionDTO;
import com.qaelum.dms.ui.model.coach.CoachChapterModel;
import com.qaelum.dms.ui.view.coach.ICoachChapterView;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class CoachChapterPresenter implements ICoachChapterView.CoachChapterViewListener {
    private ICoachChapterView view;
    private CoachChapterModel model;

    public CoachChapterPresenter(ICoachChapterView view, CoachChapterModel model) {
        this.view = view;
        this.model = model;

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
        System.out.println("Saving: " + questionDTO.getKey());
        System.out.println("Answer: " + questionDTO.getQuestionAnswer());
    }
}
