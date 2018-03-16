package com.qaelum.dms.ui.presenter.qbook;

import com.qaelum.dms.commons.dto.S3FileDTO;
import com.qaelum.dms.domain.dao.IDmsFileDAO;
import com.qaelum.dms.ui.view.qbook.IDmsTreeView;

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
    public void createFile(String filePath) {
        if(dmsFileDAO.fileExist(filePath)) {
            dmsTreeView.showMsg("FILE EXIST");
        } else {
            dmsFileDAO.createFile(filePath);
        }
    }

    @Override
    public void removeFile(S3FileDTO s3FileDTO) {
        dmsFileDAO.removeFile(s3FileDTO);
    }

    @Override
    public void removeFolderRecursive(S3FileDTO s3fileDTO) {
        dmsFileDAO.removeFolderRecursive(s3fileDTO);
    }
}
