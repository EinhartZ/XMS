package com.qaelum.dms.ui.view.coach;

import com.qaelum.dms.domain.entity.coach.CoachProtocol;
import com.qaelum.dms.domain.entity.coach.ProtocolScheme;
import com.qaelum.dms.domain.entity.coach.qualityItem.AbstractQualityItem;
import com.qaelum.dms.domain.entity.coach.qualityItem.QualityChapter;
import com.qaelum.dms.domain.entity.coach.qualityItem.QualityQuestion;
import com.vaadin.ui.Label;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by einha on 2/22/2018.
 */
public class CoachTreeView extends VerticalLayout implements ICoachTreeView{

    private String title = "CoachTreeView";

    private CoachProtocol coachProtocol;
    private TreeGrid<AbstractQualityItem> treeGrid = null;

    private List<CoachTreeViewListener> listeners = new ArrayList<>();

    //TODO testing only
    public CoachTreeView() {
        ProtocolScheme protocolScheme = new ProtocolScheme("QUAADRIL", 2014, "en_US");
        CoachProtocol coachProtocol = new CoachProtocol(protocolScheme);

        this.coachProtocol = coachProtocol;
        addComponent(new Label(title));

        initCoachTree();
        treeGrid.setSizeFull();
        addComponent(treeGrid);
    }

    public CoachTreeView(CoachProtocol coachProtocol) {
        this.coachProtocol = coachProtocol;
        addComponent(new Label(title));

        initCoachTree();
        addComponent(treeGrid);
    }

    private void initCoachTree() {
        treeGrid = new TreeGrid<>();
        treeGrid.setItems(coachProtocol.getProtocolTree(), AbstractQualityItem::getItemChildren);

        treeGrid.addColumn(AbstractQualityItem::getItemKey).setCaption("key");
        treeGrid.addColumn(AbstractQualityItem::getItemName).setCaption("name");

        treeGrid.addItemClickListener(itemClick -> {
           clickItem(itemClick.getItem());
        });
    }

    private void clickItem(AbstractQualityItem qualityItem) {
        for (CoachTreeViewListener listener : listeners) {
            listener.selectItem(qualityItem);
        }
    }

    @Override
    public void selectSiblingQuestion(String key) {
        if(treeGrid.getSelectedItems().isEmpty()) return;
        AbstractQualityItem selectedItem = treeGrid.getSelectedItems().iterator().next();
        //TODO BAD CODE
        QualityChapter parentChapter = null;
        if(selectedItem instanceof QualityChapter) {
            parentChapter = (QualityChapter) selectedItem;
        } else if (selectedItem instanceof  QualityQuestion) {
            parentChapter = selectedItem.getItemParent();
        } else {
            //NOP
        }

        List<QualityQuestion> siblingQuestions = parentChapter.getQuestions();

        for(QualityQuestion siblingQuestion : siblingQuestions) {
            if (siblingQuestion.getItemKey().equals(key)) {
                treeGrid.select(siblingQuestion);
            }
        }
    }

    @Override
    public String getSelectedItem() {
        if(treeGrid.getSelectedItems().isEmpty()) {
            return " X ";
        } else {
            return treeGrid.getSelectedItems().iterator().next().getItemKey();
        }
    }

    @Override
    public void updateTree() {

    }

    @Override
    public void addListener(CoachTreeViewListener listener) {
        listeners.add(listener);
    }
}
