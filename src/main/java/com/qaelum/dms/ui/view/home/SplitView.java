package com.qaelum.dms.ui.view.home;

import com.qaelum.dms.ui.view.ViewManager;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class SplitView extends VerticalLayout {

    private ViewManager viewManager;

    private HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

    private VerticalLayout leftElement = null;
    private VerticalLayout rightElement = null;

    public SplitView(ViewManager viewManager) {
        this.viewManager = viewManager;

        initLeftElement();
        initRightElement();

        splitPanel.setFirstComponent(leftElement);
        splitPanel.setSecondComponent(rightElement);

//        leftElement.setWidth(100, Unit.PERCENTAGE);
//        rightElement.setWidth(100, Unit.PERCENTAGE);
//
//        splitPanel.setSizeFull();
        addComponent(splitPanel);
    }

    private void initLeftElement() {
        leftElement = new SplitLeftView(viewManager);
        leftElement.setMargin(true);
        leftElement.setSizeFull();
    }

    private void initRightElement() {
        rightElement = new SplitRightView(viewManager);
        rightElement.setMargin(true);
        rightElement.setSizeFull();
    }
}
