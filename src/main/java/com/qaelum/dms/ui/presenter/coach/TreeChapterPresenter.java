package com.qaelum.dms.ui.presenter.coach;

import com.qaelum.dms.commons.dto.QualityChapterDTO;
import com.qaelum.dms.commons.dto.QualityQuestionDTO;
import com.qaelum.dms.domain.entity.coach.qualityItem.AbstractQualityItem;
import com.qaelum.dms.domain.entity.coach.qualityItem.QualityChapter;
import com.qaelum.dms.domain.entity.coach.qualityItem.QualityQuestion;
import com.qaelum.dms.ui.view.coach.ICoachChapterView;
import com.qaelum.dms.ui.view.coach.ICoachTreeView;

import java.util.List;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class TreeChapterPresenter implements ICoachTreeView.CoachTreeViewListener, ICoachChapterView.CoachChapterViewListener {

    private ICoachTreeView treeView;
    private ICoachChapterView answerView;

    public TreeChapterPresenter(ICoachTreeView treeView, ICoachChapterView answerView) {
        this.treeView = treeView;
        this.answerView = answerView;

        treeView.addListener(this);
        answerView.addListener(this);
    }

    @Override
    public void selectItem(AbstractQualityItem qualityItem) {
        //TODO pass down a DTO, process it in the MODEL, pass the result to answerView
        answerView.updateLabel(qualityItem.getItemKey() + " : " + qualityItem.getItemName());

        if(qualityItem instanceof QualityChapter) {
            QualityChapterDTO qualityChapterDTO = new QualityChapterDTO(qualityItem.getItemKey(), qualityItem.getItemName());
            List<QualityQuestion> questionList = ((QualityChapter) qualityItem).getQuestions();
            for (QualityQuestion qualityQuestion : questionList) {
                qualityChapterDTO.getQuestionDTOs().add(new QualityQuestionDTO(qualityQuestion.getItemKey(), qualityQuestion.getItemName(), qualityQuestion.getQuestionType()));
            }
            answerView.updateChapterDTO(qualityChapterDTO);
        } else {

        }
    }

    @Override
    public void buttonClick(String value) {

    }

    @Override
    public void saveQuestion(QualityQuestionDTO questionDTO) {
        //NOP
    }
}
