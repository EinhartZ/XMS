package com.qaelum.dms.ui.view.qbook;

import com.qaelum.dms.commons.dto.S3FileDTO;
import com.vaadin.ui.*;
import com.vaadin.ui.dnd.FileDropTarget;

/**
 * Created by Einhart on 3/14/2018.
 * © QAELUM NV
 */
public class UploadFileSub extends Window {
    private S3TreeView s3TreeView;
    private S3FileDTO parentFileDTO;

    private VerticalLayout dropPane;

    public UploadFileSub(S3TreeView s3TreeView, S3FileDTO parentFileDTO) {
        super("Upload");
        center();
        setModal(true);

        this.s3TreeView = s3TreeView;
        this.parentFileDTO = parentFileDTO;

        initView();
        setContent(dropPane);
    }

    private void initView() {
        final Label infoLabel = new Label("Drop me !");
        infoLabel.setWidth(240.0f, Unit.PIXELS);

        dropPane = new VerticalLayout(infoLabel);
        dropPane.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
        dropPane.addStyleName("drop-area");
        dropPane.setSizeUndefined();

        ProgressBar progress = new ProgressBar();
        progress.setIndeterminate(true);
        progress.setVisible(false);
        dropPane.addComponent(progress);

        new FileDropTarget<>(dropPane, fileDropEvent -> {
            final int fileSizeLimit = 2 * 1024 * 1024; // 2MB
            fileDropEvent.getFiles().forEach(html5File -> {
                final String fileName = html5File.getFileName();
                if (html5File.getFileSize() > fileSizeLimit) {
                    Notification.show(">2MB", Notification.Type.WARNING_MESSAGE);
                } else {
//                    s3TreeView.uploadNewFile(parentFileDTO, );
                    progress.setVisible(true);
                }
            });
        });
    }
}
