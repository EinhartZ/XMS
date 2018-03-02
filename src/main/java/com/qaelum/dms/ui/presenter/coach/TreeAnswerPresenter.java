package com.qaelum.dms.ui.presenter.coach;

import com.qaelum.dms.domain.entity.coach.qualityItem.AbstractQualityItem;
import com.qaelum.dms.ui.view.coach.ICoachAnswerView;
import com.qaelum.dms.ui.view.coach.ICoachTreeView;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class TreeAnswerPresenter implements ICoachTreeView.CoachTreeViewListener, ICoachAnswerView.CoachAnswerViewListener{

    private ICoachTreeView treeView;
    private ICoachAnswerView answerView;

    public TreeAnswerPresenter(ICoachTreeView treeView, ICoachAnswerView answerView) {
        this.treeView = treeView;
        this.answerView = answerView;

        treeView.addListener(this);
        answerView.addListener(this);
    }

    @Override
    public void selectItem(AbstractQualityItem qualityItem) {
        //TODO pass down a DTO, process it in the MODEL, pass the result to answerView
        answerView.updateLabel(qualityItem.getItemKey() + " : " + qualityItem.getItemName());
    }

    @Override
    public void buttonClick(String value) {

    }
}
