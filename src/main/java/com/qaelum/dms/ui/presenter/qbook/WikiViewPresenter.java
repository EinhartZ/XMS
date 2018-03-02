package com.qaelum.dms.ui.presenter.qbook;

import com.qaelum.dms.commons.dto.S3FileDTO;
import com.qaelum.dms.domain.dao.S3DAO;
import com.qaelum.dms.ui.view.qbook.IWikiView;

/**
 * Created by Einhart on 3/2/2018.
 * © QAELUM NV
 */
public class WikiViewPresenter implements IWikiView.WikiViewListener {
    private IWikiView iWikiView;

    public WikiViewPresenter(IWikiView iWikiView) {
        this.iWikiView = iWikiView;
        iWikiView.addListener(this);
    }

    @Override
    public void saveWiki(S3FileDTO wikiDTO, String content) {
        S3DAO.getInstance().writeWikiContent("", wikiDTO, content);
    }

    @Override
    public void loadWiki(String filePath) {
        String content = S3DAO.getInstance().readWikiContent("", filePath);
        iWikiView.showContent(content);
    }
}
