package com.qaelum.dms.ui.view;

import com.qaelum.dms.ui.view.coach.*;
import com.qaelum.dms.ui.view.qbook.DmsTreeView;
import com.qaelum.dms.ui.view.qbook.S3TreeView;
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

    private CoachAnswerView coachAnswerView;
    private CoachReportView coachReportView;

    private Map<String, WikiSub> wikiSubWindows;

    public ViewManager() {
        initAllViews();
    }

    private void initAllViews() {
        coachTreeView = new CoachTreeView();

        wikiSubWindows = new HashMap<>();
//        dmsTreeView = new DmsTreeView(wikiSubWindows);
        s3TreeView = new S3TreeView(wikiSubWindows);

        coachAnswerView = new CoachAnswerView();
        coachReportView = new CoachReportView();
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

    public CoachAnswerView getCoachAnswerView() {
        return coachAnswerView;
    }

    public CoachReportView getCoachReportView() {
        return coachReportView;
    }

}
