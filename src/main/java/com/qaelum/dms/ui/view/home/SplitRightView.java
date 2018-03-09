package com.qaelum.dms.ui.view.home;

import com.qaelum.dms.ui.model.coach.CoachChapterModel;
import com.qaelum.dms.ui.presenter.coach.CoachChapterPresenter;
import com.qaelum.dms.ui.presenter.coach.TreeChapterPresenter;
import com.qaelum.dms.ui.view.ViewManager;
import com.qaelum.dms.ui.view.coach.*;
import com.qaelum.dms.ui.view.qbook.S3TreeView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class SplitRightView extends VerticalLayout {
    private ViewManager viewManager;

    private TabSheet tabsheet = new TabSheet();
    private Layout activeView;

    private HorizontalLayout hlNavigation = new HorizontalLayout();

    private Button btn_1 = new Button(VaadinIcons.FILE_TREE);
    private Button btn_2 = new Button(VaadinIcons.ACCORDION_MENU);
    private Button btn_3 = new Button(VaadinIcons.DIPLOMA_SCROLL);
    private Button btn_4 = new Button(VaadinIcons.HAMMER);

    public SplitRightView(ViewManager viewManager) {
        this.viewManager = viewManager;

//        initTabSheetElements();
        initNavigation();
        activeView = viewManager.getS3TreeView();

        addComponent(hlNavigation);
        setExpandRatio(hlNavigation, 1);
        addComponent(activeView);
        setExpandRatio(activeView, 9);
    }

    private void initNavigation() {
        hlNavigation.setWidth("100%");

        hlNavigation.addComponent(btn_1);
        hlNavigation.addComponent(btn_2);
        hlNavigation.addComponent(btn_3);
        hlNavigation.addComponent(btn_4);

        btn_1.addClickListener(clickEvent -> {
            this.removeComponent(activeView);
            activeView = viewManager.getS3TreeView();
            addComponent(activeView);
        });

        btn_2.addClickListener(clickEvent -> {
            this.removeComponent(activeView);
            activeView = viewManager.getCoachChapterView();
            addComponent(activeView);
        });

        btn_3.addClickListener(clickEvent -> {
            this.removeComponent(activeView);
            activeView = viewManager.getCoachReportView();
            addComponent(activeView);
        });

        btn_4.addClickListener(clickEvent -> {
            this.removeComponent(activeView);
            activeView = viewManager.getTestTreeView();
            addComponent(activeView);
        });
    }

    private void initTabSheetElements() {
        S3TreeView s3TreeView = viewManager.getS3TreeView();

        ICoachChapterView chapterView = viewManager.getCoachChapterView();
        CoachReportView reportView = viewManager.getCoachReportView();

        tabsheet.addTab(s3TreeView, "KMS²3");

        tabsheet.addTab((Component) chapterView, "Chapter Details");
        tabsheet.addTab(reportView, "Report");
    }
}