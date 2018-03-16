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

//        addComponent(new Label(title));
        addComponent(label);
        setExpandRatio(label, 1);
        addComponent(btnSave);
        setExpandRatio(btnSave, 1);


        VerticalLayout verticalLayout = new VerticalLayout(accQuestions);
        verticalLayout.setMargin(false);

        Panel panel = new Panel(verticalLayout);
        panel.setHeight("700px");

        addComponent(panel);
        setExpandRatio(panel, 8);
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

        accQuestions.setSizeFull();
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
            listener.saveAllQuestionAnswer(chapterDTO);
        }
    }

    void saveQuestionAnswer(QualityQuestionDTO questionDTO) {
        for (CoachChapterViewListener listener : listeners) {
            listener.saveQuestionAnswer(questionDTO);
        }
    }

    void saveQuestionProof(QualityQuestionDTO questionDTO) {
        for (CoachChapterViewListener listener : listeners) {
            listener.saveQuestionProof(questionDTO);
        }
    }

    void detachQuestionProof(QualityQuestionDTO questionDTO, IDmsFileDTO fileDTO) {
        for (CoachChapterViewListener listener : listeners) {
            listener.detachQuestionProof(questionDTO, fileDTO);
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
    public void addListener(CoachChapterViewListener listener) {
        listeners.add(listener);
    }

}

class QualityQuestionView extends VerticalLayout {

    private QualityChapterView coachChapterView;
    private QualityQuestionDTO questionDTO;
    private List<IDmsFileDTO> tempProofList;
    private boolean proofListDirty = false;

    private Label keyLbl = new Label();
    private Label nameLbl = new Label();

    Binder<QuestionAnswerDTO> answerDTOBinder = new Binder<>();

    private TextArea textArea = new TextArea();

    private Button btnReset = new Button(VaadinIcons.ARROW_BACKWARD);
    private Button btnSave = new Button(VaadinIcons.CLOUD_UPLOAD_O);

    private VerticalLayout vlProofs = new VerticalLayout();
    private HorizontalLayout hlButtons = new HorizontalLayout();

    public QualityQuestionView(QualityChapterView coachChapterView, QualityQuestionDTO questionDTO) {
        this.coachChapterView = coachChapterView;
        this.questionDTO = questionDTO;
        this.tempProofList = questionDTO.copyProofList();

        initView();

        addComponent(nameLbl);
        addComponent(textArea);

        addComponent(hlButtons);
        setComponentAlignment(hlButtons, Alignment.MIDDLE_RIGHT);

        addComponent(vlProofs);
        setComponentAlignment(vlProofs, Alignment.MIDDLE_CENTER);

        showProofs();
        updateView();
    }

    private void initView() {
        keyLbl.setValue(questionDTO.getKey());
        nameLbl.setValue(questionDTO.getName());

//        hlButtons.setSizeFull();
        hlButtons.setMargin(false);
        hlButtons.addComponent(btnReset);
//        hlButtons.setComponentAlignment(btnReset, Alignment.MIDDLE_CENTER);
        hlButtons.addComponent(btnSave);
//        hlButtons.setComponentAlignment(btnSave, Alignment.MIDDLE_CENTER);

        textArea.setSizeFull();
        vlProofs.setSizeFull();
        vlProofs.setMargin(false);

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
            saveQuestionAnswer();
            updateView();
        });
    }

    private void showProofs() {
        vlProofs.removeAllComponents();
        for(IDmsFileDTO fileDTO : tempProofList) {
            HorizontalLayout hlProof = new HorizontalLayout();

            hlProof.addComponent(new Button(VaadinIcons.MINUS_CIRCLE, clickEvent -> {
                //TODO manipulate real proof list ?!
                tempProofList.remove(fileDTO);

//                proofListDirty = true;
//                saveQuestionProofs();

                detachQuestionProof(fileDTO);
                vlProofs.removeComponent(hlProof);
            }));
            hlProof.addComponent(new Label(fileDTO.getFilePath()));

            vlProofs.addComponent(hlProof);
        }
    }

    private void detachQuestionProof(IDmsFileDTO fileDTO) {
        coachChapterView.detachQuestionProof(questionDTO, fileDTO);
    }

    private void saveQuestionProofs() {
        questionDTO.setProofList(tempProofList);
        coachChapterView.saveQuestionProof(questionDTO);
    }

    private void updateView() {
        btnReset.setEnabled(answerDTOBinder.hasChanges());
        btnSave.setEnabled(answerDTOBinder.hasChanges());
    }

    private void saveQuestionAnswer() {
        syncDTO();
        coachChapterView.saveQuestionAnswer(questionDTO);
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
