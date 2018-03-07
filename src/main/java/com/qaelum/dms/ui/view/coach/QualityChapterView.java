package com.qaelum.dms.ui.view.coach;

import com.qaelum.dms.commons.dto.QualityChapterDTO;
import com.qaelum.dms.commons.dto.QualityQuestionDTO;
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
    private QualityQuestionDTO selectedQuestionDTO;

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

    public void setChapterDTO(QualityChapterDTO chapterDTO) {
        this.chapterDTO = chapterDTO;
    }

    public void setSelectedQuestionDTO(QualityQuestionDTO selectedQuestionDTO) {
        this.selectedQuestionDTO = selectedQuestionDTO;
    }

    private void initView() {
        btnSave.addClickListener(clickEvent -> {
           saveAllQuestions();
        });

        accQuestions.addSelectedTabChangeListener(selectedTabChangeEvent -> {
            for (CoachChapterViewListener listener : listeners) {
                listener.selectQuestionView(((QualityQuestionView)selectedTabChangeEvent.getTabSheet().getSelectedTab()).getQuestionKey());
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

    public void focusSelectedQuestion() {
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
            setChapterDTO(chapterDTO);
            setSelectedQuestionDTO(selectedQuestionDTO);
            refreshView();
            focusSelectedQuestion();
        } else {
            setSelectedQuestionDTO(selectedQuestionDTO);
            focusSelectedQuestion();
        }
    }

    @Override
    public void updateLabel(String value) {
        this.label.setValue(value);
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

    Binder<QualityQuestionDTO> questionDTOBinder = new Binder<>();
    private TextArea textArea = new TextArea();

    private Button btnReset = new Button(VaadinIcons.ARROW_BACKWARD);
    private Button btnSave = new Button(VaadinIcons.CLOUD_UPLOAD_O);


    public QualityQuestionView(QualityChapterView coachChapterView, QualityQuestionDTO questionDTO) {
        this.coachChapterView = coachChapterView;
        this.questionDTO = questionDTO;
        initView();
//        addComponent(keyLbl);
        addComponent(nameLbl);
        addComponent(textArea);

        addComponent(btnReset);
        addComponent(btnSave);
        updateView();
    }

    private void initView() {
        keyLbl.setValue(questionDTO.getKey());
        nameLbl.setValue(questionDTO.getName());

        questionDTOBinder.bind(textArea, QualityQuestionDTO::getQuestionAnswer, QualityQuestionDTO::setQuestionAnswer);
        questionDTOBinder.readBean(questionDTO);

        textArea.addValueChangeListener(valueChangeEvent -> {
            updateView();
        });

        btnReset.addClickListener(clickEvent -> {
            questionDTOBinder.readBean(questionDTO);
            updateView();
        });

        btnSave.addClickListener(clickEvent -> {
            saveQuestion();
            updateView();
        });
    }

    private void updateView() {
        btnReset.setEnabled(questionDTOBinder.hasChanges());
        btnSave.setEnabled(questionDTOBinder.hasChanges());
    }

    private void saveQuestion() {
        syncDTO();
        coachChapterView.saveQuestion(questionDTO);
    }

    public void syncDTO() {
        if (!questionDTOBinder.hasChanges()) {
            return;
        }
        try {
                questionDTOBinder.writeBean(questionDTO);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public String getQuestionKey() {
        return questionDTO.getKey();
    }
}
