package com.qaelum.dms.ui.view.qbook;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.qaelum.dms.commons.dto.S3FileDTO;
import com.qaelum.dms.domain.dao.S3DAO;
import com.qaelum.dms.ui.model.qbook.S3TreeDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Einhart on 2/27/2018.
 * © QAELUM NV
 */
public class S3TreeView extends VerticalLayout {
    private Label label = new Label("Amazon S3");

    Tree<S3FileDTO> s3Tree;
    private Map<String, WikiSub> wikiSubWindows;
    private int maxSubWindows = 2;

    private HorizontalLayout controlHl = new HorizontalLayout();

    public S3TreeView(Map<String, WikiSub> wikiSubWindows) {
        addComponent(label);

        initTree();
        initControlHl();

        addComponent(controlHl);
        addComponent(s3Tree);

        this.wikiSubWindows = wikiSubWindows;
    }

    private void initControlHl() {
        Button btnOpen = new Button("OPEN");
        btnOpen.addClickListener(clickEvent -> {
            if(s3Tree.getSelectedItems().isEmpty()) {
                return;
            } else {
                openWikiPopup(s3Tree.getSelectedItems().iterator().next());
            }
        });
        controlHl.addComponent(btnOpen);
    }

    private void initTree() {
        s3Tree = new Tree<>();
        S3FileDTO s3Root = new S3FileDTO("");
        s3Tree.setDataProvider(new S3TreeDataProvider(s3Root));

        s3Tree.setItemCaptionGenerator(item -> {
            return item.getFileName();
        });

        s3Tree.setItemIconGenerator(item -> {
            if(item.isFolder()) {
                return VaadinIcons.FOLDER;
            }

            switch (item.getExtension()) {
                case "xml":
                    return VaadinIcons.FILE_CODE;
                case "pdf":
                    return VaadinIcons.FILE_FONT;
                default:
                    return VaadinIcons.QUESTION_CIRCLE_O;
            }

        });

        s3Tree.addItemClickListener(itemClick -> {
           S3FileDTO s3FileDTO = itemClick.getItem();
            System.out.println(s3FileDTO.getFilePath());

            if(s3FileDTO.isFolder()) return;

            switch (s3FileDTO.getExtension()) {
                case "txt":
                    break;
                case "xml":
                    break;
                case "pdf":
                    break;
                default:
                    System.out.println("can not read this stuff...");

            }
        });


    }

    public void openWikiPopup(S3FileDTO s3FileDTO) {
        if(s3FileDTO.isFolder()) return;

        if(!s3FileDTO.getExtension().equals("xml")) return;

        WikiSub sub = new WikiSub(s3FileDTO);

        if(wikiSubWindows.size() >= maxSubWindows) {
            String msg = maxSubWindows + " is too many windows";

            Notification notification = new Notification("This is the caption",
                    msg,
                    Notification.Type.HUMANIZED_MESSAGE);
            notification.show(Page.getCurrent());
            return;
        }

        if(!wikiSubWindows.containsKey(s3FileDTO.getFilePath())) {
            wikiSubWindows.put(s3FileDTO.getFilePath(), sub);
            sub.addCloseListener(closeEvent -> {
                wikiSubWindows.remove(s3FileDTO.getFilePath());
            });

            UI.getCurrent().addWindow(sub);
        } else {
            String msg = s3FileDTO.getFileName() + " is already opened";

            Notification notification = new Notification("This is the caption",
                    msg,
                    Notification.Type.HUMANIZED_MESSAGE);
            notification.show(Page.getCurrent());
        }
    }

    private void recursiveListing(AmazonS3 s3, String bucketName, String path) {
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(path).withDelimiter("/");
        ListObjectsV2Result result = s3.listObjectsV2(request);
        List<String> folders = result.getCommonPrefixes();
        List<S3ObjectSummary> files = result.getObjectSummaries();

        for(S3ObjectSummary file: files) {
            if(!file.getKey().equals(path)) {
                System.out.println(file.getKey());
            }
        }

        if(!folders.isEmpty()) {
            for(String folder: folders) {
                System.out.println(folder);
                recursiveListing(s3, bucketName, folder);
            }
        }
    }
}