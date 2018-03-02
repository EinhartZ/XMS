package com.qaelum.dms.ui.view.coach;

import com.qaelum.dms.commons.dto.QualityChapterDTO;
import com.qaelum.dms.commons.dto.QualityQuestionDTO;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class CoachAnswerView extends VerticalLayout implements ICoachAnswerView {

    private QualityChapterDTO chapterDTO;

    private String title = "CoachAnswerView";

    private TextArea textArea = new TextArea();
    private Label label = new Label();
    private Button button = new Button();

    private VerticalLayout answerElement = null;
    private List<QualityQuestionView> questionViewList = new ArrayList<>();

    private List<CoachAnswerViewListener> listeners = new ArrayList();

    public CoachAnswerView() {
        addComponent(new Label(title));

        initAnswerElement();
        addComponent(answerElement);
    }

    private void initAnswerElement() {
        answerElement = new VerticalLayout();

        answerElement.addComponent(textArea);
        answerElement.addComponent(label);
        answerElement.addComponent(button);

        button.addClickListener(clickEvent -> {
           buttonClick(clickEvent);
        });
    }

    public void setChapterDTO(QualityChapterDTO chapterDTO) {
        this.chapterDTO = chapterDTO;
    }

    /**
     *Redraw this view after updating the ChapterDTO
     */
    public void refreshView() {
        this.removeAllComponents();
        addComponent(answerElement);

        for(QualityQuestionDTO questionDTO : chapterDTO.getQuestionDTOList()) {
            QualityQuestionView questionView = new QualityQuestionView(questionDTO);
            addComponent(questionView);
        }

    }

    private void buttonClick(Button.ClickEvent event) {
        for (CoachAnswerViewListener listener : listeners) {
            listener.buttonClick(textArea.getValue());
        }
    }

    /*
    Interface methods
     */

    public void updateChapterDTO(QualityChapterDTO chapterDTO) {
        setChapterDTO(chapterDTO);
        refreshView();
    }

    @Override
    public void updateLabel(String value) {
        this.label.setValue(value);
    }

    @Override
    public void addListener(CoachAnswerViewListener listener) {
        listeners.add(listener);
    }

}

class QualityQuestionView extends VerticalLayout {
    private QualityQuestionDTO questionDTO;

    private Label keyLbl = new Label();
    private Label nameLbl = new Label();

    public QualityQuestionView(QualityQuestionDTO questionDTO) {
        this.questionDTO = questionDTO;
        init();
        addComponent(keyLbl);
        addComponent(nameLbl);
    }

    private void init() {
        keyLbl.setValue(questionDTO.getKey());
        nameLbl.setValue(questionDTO.getName());
    }
}
