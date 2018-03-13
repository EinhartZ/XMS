package com.qaelum.dms.ui.view.home;

import com.qaelum.dms.ui.view.ViewManager;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class SplitLeftView extends VerticalLayout {
    private ViewManager viewManager;

    private String title = "I'm on the left";
    private VerticalLayout viewElement = null;

    public SplitLeftView(ViewManager viewManager) {
        this.viewManager = viewManager;

//        addComponent(new Label(title));

        initViewElement();

        addComponent(viewElement);
    }

    private void initViewElement() {
        viewElement = viewManager.getCoachTreeView();
        viewElement.setMargin(false);
        viewElement.setSizeFull();
    }
}
