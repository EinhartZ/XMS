package com.qaelum.dms.ui.view.coach;

import com.qaelum.dms.commons.dto.IDmsFileDTO;
import com.qaelum.dms.commons.dto.QualityChapterDTO;
import com.qaelum.dms.commons.dto.QualityQuestionDTO;
import com.qaelum.dms.commons.dto.QuestionAnswerDTO;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class QualityChapterView extends VerticalLayout implements ICoachChapterView {

    private QualityChapterDTO chapterDTO;

    private String title = "QualityChapterView";

    private Label label = new Label();
    private Button btnSave = new Button(VaadinIcons.CLOUD_UPLOAD_O);
    private Accordion accQuestions = new Accordion();

    private Map<String, QualityQuestionView> questionViewMap = new HashMap<>();
    private List<CoachChapterViewListener> listeners = new ArrayList();

    public QualityChapterView() {
        initView();
        refreshView();

        addComponent(new Label(title));
        addComponent(label);
        addComponent(btnSave);
        addComponent(accQuestions);
    }

    private void initView() {
        btnSave.addClickListener(clickEvent -> {
           saveAllQuestions();
        });

        accQuestions.addSelectedTabChangeListener(selectedTabChangeEvent -> {
            for (CoachChapterViewListener listener : listeners) {
                if (selectedTabChangeEvent.isUserOriginated()) {
                    QualityQuestionDTO questionDTO = ((QualityQuestionView)selectedTabChangeEvent.getTabSheet().getSelectedTab()).getQuestionDTO();
                    listener.selectQuestion(questionDTO);
                }
            }
        });
    }

    /**
     *Redraw this view after updating the ChapterDTO
     */
    public void refreshView() {
        accQuestions.removeAllComponents();
        questionViewMap.clear();

        if(chapterDTO == null) {
            return;
        }

        for(QualityQuestionDTO questionDTO : chapterDTO.getQuestionDTOs()) {
            QualityQuestionView questionView = new QualityQuestionView(this, questionDTO);
            questionViewMap.put(questionDTO.getKey(), questionView);
            accQuestions.addTab(questionView, questionDTO.getKey(), VaadinIcons.QUESTION_CIRCLE_O);
        }
    }

    public void focusSelectedQuestion(QualityQuestionDTO selectedQuestionDTO) {
        if(selectedQuestionDTO == null) {
            return;
        }

        String selectedKey = selectedQuestionDTO.getKey();
        QualityQuestionView selectedView = questionViewMap.get(selectedKey);

        accQuestions.setSelectedTab(selectedView);
    }

    private void saveAllQuestions() {
        if (chapterDTO == null) return;
        questionViewMap.values().forEach(QualityQuestionView::syncDTO);
        for (CoachChapterViewListener listener : listeners) {
            listener.saveAllQuestions(chapterDTO);
        }
    }

    void saveQuestion(QualityQuestionDTO questionDTO) {
        for (CoachChapterViewListener listener : listeners) {
            listener.saveQuestion(questionDTO);
        }
    }

    /*
    Interface methods
     */
    @Override
    public void updateChapterDTO(QualityChapterDTO chapterDTO, QualityQuestionDTO selectedQuestionDTO) {
        if(!chapterDTO.equals(this.chapterDTO)) {
            this.chapterDTO = chapterDTO;
            refreshView();
        }

        focusSelectedQuestion(selectedQuestionDTO);
    }

    @Override
    public void updateLabel(String value) {
        this.label.setValue(value);
    }

    @Override
    public void loadQuestionAnswer(QualityQuestionDTO questionDTO, QuestionAnswerDTO answerDTO) {

    }

    @Override
    public void addListener(CoachChapterViewListener listener) {
        listeners.add(listener);
    }

}

class QualityQuestionView extends VerticalLayout {

    private QualityChapterView coachChapterView;
    private QualityQuestionDTO questionDTO;

    private Label keyLbl = new Label();
    private Label nameLbl = new Label();

    Binder<QuestionAnswerDTO> answerDTOBinder = new Binder<>();

    private TextArea textArea = new TextArea();

    private Button btnReset = new Button(VaadinIcons.ARROW_BACKWARD);
    private Button btnSave = new Button(VaadinIcons.CLOUD_UPLOAD_O);

    private Layout loProofs = new VerticalLayout();

    public QualityQuestionView(QualityChapterView coachChapterView, QualityQuestionDTO questionDTO) {
        this.coachChapterView = coachChapterView;
        this.questionDTO = questionDTO;
        initView();

        addComponent(nameLbl);
        addComponent(textArea);

        addComponent(btnReset);
        addComponent(btnSave);

        addComponent(loProofs);
        showProofs();
//        updateView();
    }

    private void initView() {
        keyLbl.setValue(questionDTO.getKey());
        nameLbl.setValue(questionDTO.getName());

        answerDTOBinder.bind(textArea, QuestionAnswerDTO::getAnswerTxt, QuestionAnswerDTO::setAnswerTxt);
        answerDTOBinder.readBean(questionDTO.getAnswerDTO());

        textArea.addValueChangeListener(valueChangeEvent -> {
            updateView();
        });

        btnReset.addClickListener(clickEvent -> {
            answerDTOBinder.readBean(questionDTO.getAnswerDTO());
            updateView();
        });

        btnSave.addClickListener(clickEvent -> {
            saveQuestion();
            updateView();
        });
    }

    private void showProofs() {
        loProofs.removeAllComponents();
        for(IDmsFileDTO fileDTO : questionDTO.getProofList()) {
            loProofs.addComponent(new Label(fileDTO.getFilePath()));
        }
    }

    private void updateView() {
        btnReset.setEnabled(answerDTOBinder.hasChanges());
        btnSave.setEnabled(answerDTOBinder.hasChanges());
    }

    private void saveQuestion() {
        syncDTO();
        coachChapterView.saveQuestion(questionDTO);
    }

    public void syncDTO() {
        if (!answerDTOBinder.hasChanges()) {
            return;
        }

        /*First new answer*/
        if(questionDTO.getAnswerDTO() == null) {
            QuestionAnswerDTO newAnswerDTO = new QuestionAnswerDTO();
            questionDTO.setAnswerDTO(newAnswerDTO);
        }

        try {
            answerDTOBinder.writeBean(questionDTO.getAnswerDTO());
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public String getQuestionKey() {
        return questionDTO.getKey();
    }

    public QualityQuestionDTO getQuestionDTO() {
        return questionDTO;
    }
}
