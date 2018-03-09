package com.qaelum.dms.ui.presenter;

import com.qaelum.dms.ui.view.coach.ICoachTreeView;
import com.qaelum.dms.ui.view.qbook.IDmsTreeView;

import java.util.List;

/**
 * Created by Einhart on 3/7/2018.
 * © QAELUM NV
 */
public class TreesPresenter implements IDmsTreeView.DmsTreeViewListener {

    private IDmsTreeView dmsTreeView;
    private ICoachTreeView coachTreeView;

    public TreesPresenter(IDmsTreeView dmsTreeView, ICoachTreeView coachTreeView) {
        this.dmsTreeView = dmsTreeView;
        this.coachTreeView = coachTreeView;

        dmsTreeView.addListener(this);
    }

    @Override
    public void attachProof(String docKey) {
        System.out.println("Attach Proof " + docKey + " to " + coachTreeView.getSelectedItem());
    }

    @Override
    public void attachProof(List<String> docKeysList) {

    }
}
