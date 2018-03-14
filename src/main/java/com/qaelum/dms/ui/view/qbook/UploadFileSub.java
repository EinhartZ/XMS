package com.qaelum.dms.ui.view.qbook;

import com.qaelum.dms.commons.dto.S3FileDTO;
import com.vaadin.ui.Window;

/**
 * Created by Einhart on 3/14/2018.
 * © QAELUM NV
 */
public class UploadFileSub extends Window {
    private S3TreeView s3TreeView;
    private S3FileDTO parentFileDTO;

    public UploadFileSub(S3TreeView s3TreeView, S3FileDTO parentFileDTO) {
        super("Upload");
        center();
        setModal(true);

        this.s3TreeView = s3TreeView;
        this.parentFileDTO = parentFileDTO;

        initView();
    }

    private void initView() {

    }
}
