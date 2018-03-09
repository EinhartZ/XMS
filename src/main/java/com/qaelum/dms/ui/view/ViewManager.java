package com.qaelum.dms.ui.view;

import com.qaelum.dms.domain.dao.IDmsFileDAO;
import com.qaelum.dms.domain.dao.S3DAO;
import com.qaelum.dms.ui.model.coach.CoachChapterModel;
import com.qaelum.dms.ui.presenter.TreesPresenter;
import com.qaelum.dms.ui.presenter.coach.CoachChapterPresenter;
import com.qaelum.dms.ui.presenter.coach.TreeChapterPresenter;
import com.qaelum.dms.ui.presenter.qbook.DmsTreePresenter;
import com.qaelum.dms.ui.view.coach.*;
import com.qaelum.dms.ui.view.qbook.IDmsTreeView;
import com.qaelum.dms.ui.view.qbook.S3TreeView;
import com.qaelum.dms.ui.view.qbook.TestTreeView;
import com.qaelum.dms.ui.view.qbook.WikiSub;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class ViewManager {
    private CoachTreeView coachTreeView;

//    private DmsTreeView dmsTreeView;
    private S3TreeView s3TreeView;
    private TestTreeView testTreeView;

    private QualityChapterView coachChapterView;
    private CoachReportView coachReportView;

    private Map<String, WikiSub> wikiSubWindows;

    public ViewManager() {
        initAllViews();
    }

    private void initAllViews() {
        testTreeView = new TestTreeView();

        coachTreeView = new CoachTreeView();

        wikiSubWindows = new HashMap<>();
//        dmsTreeView = new DmsTreeView(wikiSubWindows);
        s3TreeView = new S3TreeView(wikiSubWindows);

        coachChapterView = new QualityChapterView();
        coachReportView = new CoachReportView();

        //link CoachTree and CoachAnswer
        TreeChapterPresenter treeChapterPresenter = new TreeChapterPresenter(coachTreeView, coachChapterView);

        //link ChapterView to ChapterModel
        CoachChapterModel chapterModel = new CoachChapterModel();
        CoachChapterPresenter chapterPresenter = new CoachChapterPresenter(coachChapterView, chapterModel);

        //link DMSTree to
        TreesPresenter treesPresenter = new TreesPresenter(s3TreeView, coachTreeView);

        //link DmsTree to Model
        IDmsFileDAO dmsFileDAO = S3DAO.getInstance();
        DmsTreePresenter dmsTreePresenter = new DmsTreePresenter(s3TreeView, dmsFileDAO);
    }

    public CoachTreeView getCoachTreeView() {
        return coachTreeView;
    }

//    public DmsTreeView getDmsTreeView() {
//        return dmsTreeView;
//    }

    public S3TreeView getS3TreeView() {
        return s3TreeView;
    }

    public QualityChapterView getCoachChapterView() {
        return coachChapterView;
    }

    public CoachReportView getCoachReportView() {
        return coachReportView;
    }

    public TestTreeView getTestTreeView() {
        return testTreeView;
    }
}
