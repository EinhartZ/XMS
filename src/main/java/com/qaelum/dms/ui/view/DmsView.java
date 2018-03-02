package com.qaelum.dms.ui.view;

import com.qaelum.dms.ui.view.home.SplitView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Einhart on 1/12/2018.
 * © QAELUM NV
 */
public class DmsView extends VerticalLayout implements View {

    private ViewManager viewManager;

    public DmsView() {

        viewManager = new ViewManager();

        VerticalLayout splitView = new SplitView(viewManager);
        addComponent(splitView);
    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
