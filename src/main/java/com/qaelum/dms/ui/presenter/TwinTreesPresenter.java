package com.qaelum.dms.ui.presenter;

import com.qaelum.dms.commons.dto.IDmsFileDTO;
import com.qaelum.dms.commons.dto.S3FileDTO;
import com.qaelum.dms.domain.dao.ICoachProjectDAO;
import com.qaelum.dms.ui.view.coach.ICoachTreeView;
import com.qaelum.dms.ui.view.qbook.IDmsTreeView;

import java.util.List;

/**
 * Created by Einhart on 3/7/2018.
 * © QAELUM NV
 */
public class TwinTreesPresenter implements IDmsTreeView.DmsTreeViewListener {

    private IDmsTreeView dmsTreeView;
    private ICoachTreeView coachTreeView;
    private ICoachProjectDAO coachProjectDAO;

    public TwinTreesPresenter(IDmsTreeView dmsTreeView, ICoachTreeView coachTreeView, ICoachProjectDAO coachProjectDAO) {
        this.dmsTreeView = dmsTreeView;
        this.coachTreeView = coachTreeView;

        this.coachProjectDAO = coachProjectDAO;

        dmsTreeView.addListener(this);
    }

    @Override
    public void attachProof(IDmsFileDTO fileDTO) {
        coachProjectDAO.attachProof(coachTreeView.getSelectedItem(), fileDTO);
    }

    @Override
    public void attachProof(List<String> docKeysList) {

    }

}
