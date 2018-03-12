package com.qaelum.dms.ui.view.qbook;

import com.qaelum.dms.commons.dto.IDmsFileDTO;
import com.qaelum.dms.commons.dto.S3FileDTO;

import java.util.List;

/**
 * Created by Einhart on 3/7/2018.
 * © QAELUM NV
 */
public interface IDmsTreeView {

    void addListener(DmsTreeViewListener listener);

    interface DmsTreeViewListener {
        default void attachProof(IDmsFileDTO fileDTO) {
            //NOP
        };
        default void attachProof(List<String> docKeysList) {
            //NOP
        };

        default void addFolder(String filePath) {
            //NOP
        };
        default void removeFolder(String filePath) {
            //NOP
        };
        default void removeFile(String filePath) {
            //NOP
        };

        default void removeFolderRecursive(S3FileDTO s3fileDTO) {
            //NOP
        };
    }
}
