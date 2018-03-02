package com.qaelum.dms.ui.view.qbook;

import com.qaelum.dms.commons.dto.DmsFileDTO;
import com.qaelum.dms.commons.dto.S3FileDTO;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.alump.ckeditor.AbstractCKEditorTextField;
import org.vaadin.alump.ckeditor.CKEditorTextField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class WikiView extends VerticalLayout implements IWikiView {

    private S3FileDTO wikiDTO;

    final AbstractCKEditorTextField ckEditorTextField = new CKEditorTextField();
    private String editorInitialValue = "This is CKEditor !!";
    private HorizontalLayout controlHl = new HorizontalLayout();

    private List<WikiViewListener> listeners = new ArrayList<>();

    public WikiView() {
        addComponent(new Label("WIKI"));

        /**
         * CKEditor
         */

        ckEditorTextField.setValue(editorInitialValue);

        ckEditorTextField.setImmediate(true);
        ckEditorTextField.setHeight("440px");
        addComponent(ckEditorTextField);

        initControlHl();
        addComponent(controlHl);
    }

    public WikiView(S3FileDTO wikiDTO) {
        this();
        this.wikiDTO = wikiDTO;
    }


    private void initControlHl() {
        Button btnReset = new Button("RESET");
        btnReset.addClickListener(clickEvent -> {
            ckEditorTextField.setValue(editorInitialValue);
        });

        Button btnLoad = new Button("LOAD");
        btnLoad.addClickListener(clickEvent -> {
            loadWiki();
        });

        Button btnSave = new Button("SAVE");
        btnSave.addClickListener(clickEvent -> {
            saveWiki();
        });

        controlHl.addComponent(btnLoad);
        controlHl.addComponent(btnSave);
        controlHl.addComponent(btnReset);
    }

    @Override
    public void addListener(WikiViewListener listener) {
        listeners.add(listener);
    }

    public void loadWiki() {
        for (WikiViewListener listener : listeners) {
            listener.loadWiki(wikiDTO.getFilePath());
        }
    }

    private void saveWiki() {
        for (WikiViewListener listener : listeners) {
            listener.saveWiki(wikiDTO, ckEditorTextField.getValue());
        }
    }

    @Override
    public void showContent(String content) {
        ckEditorTextField.setValue(content);
    }

    /*
    public void loadVersionData(long fileId) {
        String schema = SecurityUtils.getAuthenticatedUser().getSchemaName();
        String versionData = S3DAO.getInstance().getDmsFileDAO().findVersionDataById(schema, fileId);
        String content = null;
        try {
            content = S3DAO.getInstance().getDmsFileDAO().readWikiContent(versionData, StandardCharsets.UTF_8);
        } catch (IOException e) {
            content = "-empty-";
            e.printStackTrace();
        }
        ckEditorTextField.setValue(content);
    }

    private void saveVersionData(long fileId) {
        String schema = SecurityUtils.getAuthenticatedUser().getSchemaName();
        String versionData = S3DAO.getInstance().getDmsFileDAO().findVersionDataById(schema, fileId);
        String content = ckEditorTextField.getValue();
        try {
            S3DAO.getInstance().getDmsFileDAO().writeWikiContent(versionData, StandardCharsets.UTF_8, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
