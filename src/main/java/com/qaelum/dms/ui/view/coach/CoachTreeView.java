package com.qaelum.dms.ui.view.coach;

import com.qaelum.dms.domain.entity.coach.CoachProtocol;
import com.qaelum.dms.domain.entity.coach.ProtocolScheme;
import com.qaelum.dms.domain.entity.coach.qualityItem.AbstractQualityItem;
import com.qaelum.dms.domain.entity.coach.qualityItem.QualityChapter;
import com.qaelum.dms.domain.entity.coach.qualityItem.QualityQuestion;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TreeGrid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.ProgressBarRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by einha on 2/22/2018.
 */
public class CoachTreeView extends VerticalLayout implements ICoachTreeView{

    private String title = "CoachTreeView";
    private ProtocolScheme protocolScheme;
    private CoachProtocol coachProtocol;

    private static final String COL_TXT = "COL_TXT";
    private static final String COL_PROG = "COL_PROG";
    private static final String COL_KEY = "COL_KEY";

    private TreeGrid<AbstractQualityItem> treeGrid;
    private TreeData<AbstractQualityItem> data;
    private TreeDataProvider<AbstractQualityItem> dataProvider;

    private List<CoachTreeViewListener> listeners = new ArrayList<>();

    //TODO testing only
    public CoachTreeView() {
        protocolScheme = new ProtocolScheme("QUAADRIL", 2014, "en_US");
        coachProtocol = new CoachProtocol(protocolScheme);

        this.coachProtocol = coachProtocol;
//        addComponent(new Label(title));

        initCoachTree();

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
        treeGrid.setSizeFull();
        treeGrid.setHeightByRows(20);

        data = new TreeData<>();
        data.addItems(coachProtocol.getProtocolTree(), AbstractQualityItem::getItemChildren);
        dataProvider = new TreeDataProvider<>(data);
        treeGrid.setDataProvider(dataProvider);

//        treeGrid.setItems(coachProtocol.getProtocolTree(), AbstractQualityItem::getItemChildren);

        treeGrid.addColumn(AbstractQualityItem::getItemName).setId(COL_TXT).setCaption(protocolScheme.getProtocolKey());
        treeGrid.addColumn(qualityItem -> {return Math.random();}, new ProgressBarRenderer()).setId(COL_PROG);
        treeGrid.addColumn(AbstractQualityItem::getItemKey).setId(COL_KEY);

        setColumnFiltering();

        treeGrid.addItemClickListener(itemClick -> {
           selectItem(itemClick.getItem());
        });

    }

    private void setColumnFiltering() {
        HeaderRow filteringHeader = treeGrid.appendHeaderRow();
        TextField filteringField = getColumnFilterField();
        filteringField.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() == null || valueChangeEvent.getValue().trim().isEmpty()) {
                dataProvider.clearFilters();
            } else {
                dataProvider.setFilter(qualityItem -> {
                    if (qualityItem.getItemChildren() == null || qualityItem.getItemChildren().isEmpty()) {
                        return qualityItem.getItemName().contains(valueChangeEvent.getValue());
                    } else {
                        return qualityItem.flatten().anyMatch(childItem -> childItem.getItemName().contains(valueChangeEvent.getValue()));
                    }
                    }
                );
            }
        });

        filteringHeader.getCell(COL_TXT).setComponent(filteringField);
    }

    private TextField getColumnFilterField() {
        TextField filter = new TextField();
        filter.setWidth("100%");
        filter.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        filter.setPlaceholder("Filter");
        return filter;

    }

    private void selectItem(AbstractQualityItem qualityItem) {
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

        recursiveExpandChapter(parentChapter);

        for(QualityQuestion siblingQuestion : siblingQuestions) {
            if (siblingQuestion.getItemKey().equals(key)) {
                treeGrid.select(siblingQuestion);
            }
        }
    }

    private void recursiveExpandChapter(AbstractQualityItem qualityItem) {
        treeGrid.expand(qualityItem);
        if(qualityItem.getItemParent() != null) {
            recursiveExpandChapter(qualityItem.getItemParent());
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
