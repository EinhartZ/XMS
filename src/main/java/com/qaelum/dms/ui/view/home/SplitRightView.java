package com.qaelum.dms.ui.view.home;

import com.qaelum.dms.ui.view.ViewManager;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class SplitRightView extends VerticalLayout {
    private ViewManager viewManager;

    private TabSheet tabsheet = new TabSheet();
    private VerticalLayout activeView;

    private MenuBar menuBar = new MenuBar();

    public SplitRightView(ViewManager viewManager) {
        this.viewManager = viewManager;

        initNavigation();
        addComponent(menuBar);
        setExpandRatio(menuBar, 1);

        activeView = new VerticalLayout();
        navigateTo(viewManager.getS3TreeView());
    }

    private void initNavigation() {
        menuBar.setWidth("100%");

        MenuBar.MenuItem itemTree = menuBar.addItem("", VaadinIcons.FILE_TREE, menuItem -> {
            navigateTo(viewManager.getS3TreeView());
        });

        MenuBar.MenuItem itemAcc = menuBar.addItem("", VaadinIcons.ACCORDION_MENU, menuItem -> {
            navigateTo(viewManager.getCoachChapterView());
        });

        MenuBar.MenuItem itemReport = menuBar.addItem("", VaadinIcons.DIPLOMA_SCROLL, menuItem -> {
            navigateTo(viewManager.getCoachReportView());
        });

        MenuBar.MenuItem itemConfig = menuBar.addItem("", VaadinIcons.HAMMER, menuItem -> {
            navigateTo(viewManager.getTestTreeView());
        });

    }

    private void navigateTo(VerticalLayout newView) {
        if(newView != activeView) {
            removeComponent(activeView);

//            newView.setSizeFull();
            newView.setMargin(false);
            addComponent(newView);
            setExpandRatio(newView, 10);

            activeView = newView;
        }
    }
}