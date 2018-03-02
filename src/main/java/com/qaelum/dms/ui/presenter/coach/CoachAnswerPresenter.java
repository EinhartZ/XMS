package com.qaelum.dms.ui.presenter.coach;

import com.qaelum.dms.ui.model.coach.CoachAnswerModel;
import com.qaelum.dms.ui.view.coach.CoachAnswerView;
import com.qaelum.dms.ui.view.coach.ICoachAnswerView;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class CoachAnswerPresenter implements CoachAnswerView.CoachAnswerViewListener{
    private ICoachAnswerView view;
    private CoachAnswerModel model;

    public CoachAnswerPresenter(ICoachAnswerView view, CoachAnswerModel model) {
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
}
