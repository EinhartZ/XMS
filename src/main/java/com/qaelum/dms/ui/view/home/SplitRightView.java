package com.qaelum.dms.ui.view.home;

import com.qaelum.dms.ui.model.coach.CoachChapterModel;
import com.qaelum.dms.ui.presenter.coach.CoachChapterPresenter;
import com.qaelum.dms.ui.presenter.coach.TreeChapterPresenter;
import com.qaelum.dms.ui.view.ViewManager;
import com.qaelum.dms.ui.view.coach.*;
import com.qaelum.dms.ui.view.qbook.S3TreeView;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class SplitRightView extends VerticalLayout {
    private ViewManager viewManager;

    private TabSheet tabsheet = new TabSheet();

    public SplitRightView(ViewManager viewManager) {
        this.viewManager = viewManager;

        initTabSheetElements();
        addComponent(tabsheet);
    }

    private void initTabSheetElements() {
        ICoachTreeView coachTreeView = viewManager.getCoachTreeView();

//        DmsTreeView dmsTreeView = viewManager.getDmsTreeView();
        S3TreeView s3TreeView = viewManager.getS3TreeView();

        ICoachChapterView chapterView = viewManager.getCoachChapterView();
        CoachReportView reportView = viewManager.getCoachReportView();

        //link CoachTree and CoachAnswer
        TreeChapterPresenter treeChapterPresenter = new TreeChapterPresenter(coachTreeView, chapterView);

        CoachChapterModel chapterModel = new CoachChapterModel();
        CoachChapterPresenter chapterPresenter = new CoachChapterPresenter(chapterView, chapterModel);

//        tabsheet.addTab(dmsTreeView, "KMS");
        tabsheet.addTab(s3TreeView, "KMS²3");

        tabsheet.addTab((Component) chapterView, "Chapter Details");
        tabsheet.addTab(reportView, "Report");
    }
}
