package com.qaelum.dms.ui.presenter.qbook;

import com.qaelum.dms.commons.dto.S3FileDTO;
import com.qaelum.dms.domain.dao.IDmsFileDAO;
import com.qaelum.dms.ui.view.qbook.IDmsTreeView;

import java.util.List;

/**
 * Created by Einhart on 3/9/2018.
 * © QAELUM NV
 */
public class DmsTreePresenter implements IDmsTreeView.DmsTreeViewListener {
    private IDmsTreeView dmsTreeView;
    private IDmsFileDAO dmsFileDAO;

    public DmsTreePresenter(IDmsTreeView dmsTreeView, IDmsFileDAO dmsFileDAO) {
        this.dmsTreeView = dmsTreeView;
        this.dmsFileDAO = dmsFileDAO;

        dmsTreeView.addListener(this);
    }

    @Override
    public void addFolder(String filePath) {
        dmsFileDAO.addFolder(filePath);
    }

    @Override
    public void removeFolder(String filePath) {
        dmsFileDAO.removeFolder(filePath);
    }

    @Override
    public void removeFile(String filePath) {
        dmsFileDAO.removeFile(filePath);
    }

    @Override
    public void removeFolderRecursive(S3FileDTO s3fileDTO) {
        dmsFileDAO.removeFolderRecursive(s3fileDTO);
    }
}
