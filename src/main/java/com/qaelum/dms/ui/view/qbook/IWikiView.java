package com.qaelum.dms.ui.view.qbook;

import com.qaelum.dms.commons.dto.S3FileDTO;

/**
 * Created by Einhart on 3/1/2018.
 * © QAELUM NV
 */
public interface IWikiView {
    void addListener(WikiViewListener listener);
    void showContent(String contentStr);

    interface WikiViewListener {
        void saveWiki(S3FileDTO wikiDTO, String content);
        void loadWiki(String filePath);
    }
}
