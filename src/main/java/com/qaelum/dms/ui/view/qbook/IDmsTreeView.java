package com.qaelum.dms.ui.view.qbook;

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
    }
}
