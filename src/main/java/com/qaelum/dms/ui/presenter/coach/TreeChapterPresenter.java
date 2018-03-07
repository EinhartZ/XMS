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


        QualityChapter selectedChapter = null;
        QualityQuestion selectedQuestion = null;

        QualityChapterDTO selectedChapterDTO = null;
        QualityQuestionDTO selectedQuestionDTO = null;

        if(qualityItem instanceof QualityChapter) {
            selectedChapter = (QualityChapter) qualityItem;
        } else if (qualityItem instanceof  QualityQuestion) {
            selectedChapter = qualityItem.getItemParent();

            selectedQuestion = (QualityQuestion) qualityItem;
            selectedQuestionDTO = new QualityQuestionDTO(selectedQuestion.getItemKey(), selectedQuestion.getItemName(), selectedQuestion.getQuestionType());
        } else {
            //Exception
        }

        selectedChapterDTO = new QualityChapterDTO(selectedChapter.getItemKey(), selectedChapter.getItemName());
        List<QualityQuestion> questionList = selectedChapter.getQuestions();
        for (QualityQuestion qualityQuestion : questionList) {
            QualityQuestionDTO qualityQuestionDTO = new QualityQuestionDTO(qualityQuestion.getItemKey(), qualityQuestion.getItemName(), qualityQuestion.getQuestionType());
            selectedChapterDTO.getQuestionDTOs().add(qualityQuestionDTO);
        }

        answerView.updateChapterDTO(selectedChapterDTO, selectedQuestionDTO);
        answerView.updateLabel(selectedChapterDTO.getKey() + " : " + selectedChapterDTO.getName());
    }

    @Override
    public void buttonClick(String value) {

    }

    @Override
    public void saveQuestion(QualityQuestionDTO questionDTO) {
        //NOP
    }

    @Override
    public void saveAllQuestions(QualityChapterDTO chapterDTO) {
        //NOP
    }

    @Override
    public void selectQuestionView(String key) {
        treeView.selectSiblingQuestion(key);
    }
}
