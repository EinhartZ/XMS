package com.qaelum.dms.ui.view.qbook;

import com.qaelum.dms.commons.dto.S3FileDTO;

import java.util.List;

/**
 * Created by Einhart on 3/7/2018.
 * © QAELUM NV
 */
public interface IDmsTreeView {

    void addListener(DmsTreeViewListener listener);

    interface DmsTreeViewListener {
        void attachProof(String docKey);
        void attachProof(List<String> docKeysList);

        void addFolder(String filePath);
        void removeFolder(String filePath);
        void removeFile(String filePath);

        void removeFolderRecursive(S3FileDTO s3fileDTO);
    }
}
