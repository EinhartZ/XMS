package com.qaelum.dms.ui.view.qbook;

import com.qaelum.dms.commons.dto.S3FileDTO;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

/**
 * Created by Einhart on 3/14/2018.
 * © QAELUM NV
 */
public class CreateFileSub extends Window {
    private S3TreeView s3TreeView;
    private S3FileDTO parentFileDTO;

    private FormLayout form;
    private TextField tfName;

    private CheckBox cbFolder;
    private CheckBox cbWiki;
    private boolean isFolder;

    private Button btnConfirm;

    public CreateFileSub(S3TreeView s3TreeView, S3FileDTO parentFileDTO) {
        super("Add new file/folder");
        center();
        setModal(true);

        this.s3TreeView = s3TreeView;
        this.parentFileDTO = parentFileDTO;
        initView();

        form.addComponent(tfName);

        form.addComponent(cbWiki);
        form.addComponent(cbFolder);
        syncCbGroup();

        form.addComponent(btnConfirm);

        setContent(form);
    }

    private void initView() {
        form = new FormLayout();
        form.setMargin(true);
        form.setSizeUndefined();

        tfName = new TextField();

        cbFolder = new CheckBox();
        cbFolder.setIcon(VaadinIcons.FOLDER_ADD);
        cbWiki = new CheckBox();
        cbWiki.setIcon(VaadinIcons.FILE_O);

        tfName.addValueChangeListener(valueChangeEvent -> {
            if(valueChangeEvent.getValue() == null || valueChangeEvent.getValue().trim().isEmpty()) {
                btnConfirm.setEnabled(false);
            } else {
                btnConfirm.setEnabled(true);
            }
        });

        cbFolder.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.isUserOriginated()) {
                isFolder = true; //valueChangeEvent.getValue();
                syncCbGroup();
            }
        });

        cbWiki.addValueChangeListener(valueChangeEvent -> {
            if (valueChangeEvent.isUserOriginated()) {
                isFolder = false; //!valueChangeEvent.getValue();
                syncCbGroup();
            }
        });

        btnConfirm = new Button(VaadinIcons.CHECK, clickEvent -> {
            String fileName;
            if (isFolder) {
                fileName = tfName.getValue() + "/";
            } else {
                fileName = tfName.getValue();
            }
            createFile(fileName);
        });

        btnConfirm.setEnabled(false);
    }

    private void syncCbGroup() {
        cbFolder.setValue(isFolder);
        cbWiki.setValue(!isFolder);
    }

    private boolean validateFileName(String fileName) {
        //TODO check name format (no slash, no dot, no special signs...)
        return true;
    }

    private void createFile(String fileName) {
        if(validateFileName(fileName)) {
            s3TreeView.createFile(parentFileDTO, fileName);
            this.close();
        }
    }
}