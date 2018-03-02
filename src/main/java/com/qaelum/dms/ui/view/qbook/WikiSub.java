package com.qaelum.dms.ui.view.qbook;

import com.qaelum.dms.commons.dto.DmsFileDTO;
import com.qaelum.dms.commons.dto.S3FileDTO;
import com.qaelum.dms.ui.presenter.qbook.WikiViewPresenter;
import com.vaadin.ui.Window;

public class WikiSub extends Window {
    public WikiSub(S3FileDTO wikiDTO) {
        super(wikiDTO.getFileName());
        center();

        WikiView wikiView = new WikiView(wikiDTO);
        WikiViewPresenter wikiViewPresenter = new WikiViewPresenter(wikiView);

        wikiView.loadWiki();
        setContent(wikiView);
    }
}