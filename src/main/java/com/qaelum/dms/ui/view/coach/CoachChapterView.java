package com.qaelum.dms.ui.view.coach;

import com.qaelum.dms.commons.dto.QualityChapterDTO;
import com.qaelum.dms.commons.dto.QualityQuestionDTO;
import com.vaadin.icons.VaadinIcons;
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
public class CoachChapterView extends VerticalLayout implements ICoachChapterView {

    private QualityChapterDTO chapterDTO;

    private String title = "CoachChapterView";

    private TextArea textArea = new TextArea();
    private Label label = new Label();
    private Button button = new Button();

//    private VerticalLayout answerElement = null;
    private List<QualityQuestionView> questionViewList = new ArrayList<>();

    private List<CoachChapterViewListener> listeners = new ArrayList();

    public CoachChapterView() {
        addComponent(new Label(title));

//        initAnswerElement();
//        addComponent(answerElement);
    }

    /*
    private void initAnswerElement() {
        answerElement = new VerticalLayout();

        answerElement.addComponent(textArea);
        answerElement.addComponent(label);
        answerElement.addComponent(btnSave);

        btnSave.addClickListener(clickEvent -> {
           buttonClick(clickEvent);
        });
    }
    */
    public void setChapterDTO(QualityChapterDTO chapterDTO) {
        this.chapterDTO = chapterDTO;
    }

    /**
     *Redraw this view after updating the ChapterDTO
     */
    public void refreshView() {
        this.removeAllComponents();
        addComponent(new Label(title));
//        addComponent(answerElement);

        for(QualityQuestionDTO questionDTO : chapterDTO.getQuestionDTOs()) {
            QualityQuestionView questionView = new QualityQuestionView(this, questionDTO);
            addComponent(questionView);
        }

    }

    private void buttonClick(Button.ClickEvent event) {
        for (CoachChapterViewListener listener : listeners) {
            listener.buttonClick(textArea.getValue());
        }
    }

    /*
    Interface methods
     */

    @Override
    public void updateChapterDTO(QualityChapterDTO chapterDTO) {
        setChapterDTO(chapterDTO);
        refreshView();
    }

    @Override
    public void updateLabel(String value) {
        this.label.setValue(value);
    }

    @Override
    public void addListener(CoachChapterViewListener listener) {
        listeners.add(listener);
    }

    @Override
    public void saveQuestion(QualityQuestionDTO questionDTO) {
        for (CoachChapterViewListener listener : listeners) {
            listener.saveQuestion(questionDTO);
        }
    }
}

class QualityQuestionView extends VerticalLayout {
    private ICoachChapterView coachChapterView;
    private QualityQuestionDTO questionDTO;

    private Label keyLbl = new Label();
    private Label nameLbl = new Label();

    private TextArea textArea = new TextArea();
    private Button btnSave = new Button(VaadinIcons.CLOUD_UPLOAD_O);


    public QualityQuestionView(ICoachChapterView coachChapterView, QualityQuestionDTO questionDTO) {
        this.coachChapterView = coachChapterView;
        this.questionDTO = questionDTO;
        init();
        addComponent(keyLbl);
        addComponent(nameLbl);
        addComponent(textArea);
        addComponent(btnSave);
    }

    private void init() {
        keyLbl.setValue(questionDTO.getKey());
        nameLbl.setValue(questionDTO.getName());
        textArea.setValue(questionDTO.getComment( ) == null ? "" : questionDTO.getComment());

        btnSave.addClickListener(clickEvent -> {
            buttonClick();
        });
    }

    private void buttonClick() {
        questionDTO.setQuestionAnswer(textArea.getValue());
        coachChapterView.saveQuestion(questionDTO);
    }
}
