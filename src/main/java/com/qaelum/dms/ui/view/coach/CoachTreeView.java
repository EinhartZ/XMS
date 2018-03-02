package com.qaelum.dms.ui.view.coach;

import com.qaelum.dms.domain.entity.coach.CoachProtocol;
import com.qaelum.dms.domain.entity.coach.ProtocolScheme;
import com.qaelum.dms.domain.entity.coach.qualityItem.AbstractQualityItem;
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
           selectItem(itemClick.getItem());
        });
    }

    private void selectItem(AbstractQualityItem qualityItem) {
        for (CoachTreeViewListener listener : listeners) {
            listener.selectItem(qualityItem);
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
