package com.qaelum.dms.ui.view.qbook;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.qaelum.dms.commons.dto.S3FileDTO;
import com.qaelum.dms.domain.dao.S3DAO;
import com.qaelum.dms.ui.model.qbook.S3TreeDataProvider;
import com.qaelum.dms.ui.presenter.qbook.DmsTreePresenter;
import com.vaadin.contextmenu.ContextMenu;
import com.vaadin.contextmenu.Menu;
import com.vaadin.contextmenu.MenuItem;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Einhart on 2/27/2018.
 * © QAELUM NV
 */
public class S3TreeView extends VerticalLayout implements IDmsTreeView{
    Tree<S3FileDTO> s3Tree;

    private Map<String, WikiSub> wikiSubWindows;
    private int maxSubWindows = 2;

    private List<DmsTreeViewListener> listeners = new ArrayList<>();

    public S3TreeView(Map<String, WikiSub> wikiSubWindows) {
        initTree();

        addComponent(s3Tree);

        this.wikiSubWindows = wikiSubWindows;
    }


    private void initTree() {
        s3Tree = new Tree<>();
        s3Tree.setSizeFull();

        S3FileDTO s3Root = new S3FileDTO("user_0001/");

        s3Tree.setDataProvider(new S3TreeDataProvider(s3Root));

        s3Tree.setItemCaptionGenerator(item -> {
            return item.getFileName();
        });

        s3Tree.setItemIconGenerator(item -> {
            if(item.isFolder()) {
                return VaadinIcons.FOLDER;
            }

            switch (item.getExtension()) {
                case "txt":
                    return VaadinIcons.FILE_FONT;
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

        /*
        Adding right click functionality
         */
        ContextMenu contextMenu = new ContextMenu(s3Tree, true);
        contextMenu.addContextMenuOpenListener(this::updateTreeContextMenu);
    }

    private void updateTreeContextMenu(ContextMenu.ContextMenuOpenListener.ContextMenuOpenEvent event) {
        event.getContextMenu().removeItems();
        S3FileDTO item = ((Tree.TreeContextClickEvent<S3FileDTO>)event.getContextClickEvent()).getItem();
        if(item != null) {
            s3Tree.select(item);

            event.getContextMenu().addItem("View", VaadinIcons.EYE, menuItem -> {
                openWikiPopup(item);
            });
            event.getContextMenu().addItem("Attach", VaadinIcons.PUZZLE_PIECE, menuItem -> {
                for (DmsTreeViewListener listener : listeners) {
                    listener.attachProof(item);
                }
            });


            event.getContextMenu().addSeparator();
            if(item.isFolder()) {
                MenuItem addMenu = event.getContextMenu().addItem("New", VaadinIcons.NEWSPAPER, menuItem -> {

                });

                addMenu.addItem("Folder", VaadinIcons.FOLDER_ADD, menuItem -> {
                    System.out.println("adding new folder...");
                    for(DmsTreeViewListener listener : listeners) {
                        listener.addFolder(item.getFilePath() + "Empty" + "/");
                    }

                    s3Tree.collapse(item);
                    s3Tree.getDataProvider().refreshAll();
                    s3Tree.expand(item);
                });
                addMenu.addItem("File", VaadinIcons.FILE_ADD, menuItem -> {
                    System.out.println("adding new file...");
                });
            }

            if(item.isFolder()) {
                MenuItem remove = event.getContextMenu().addItem("Delete", VaadinIcons.FOLDER_REMOVE, menuItem -> {
                    for (DmsTreeViewListener listener : listeners) {
                        listener.removeFolderRecursive(item);
                    }
                    System.out.println("deleting folder...");

                    s3Tree.getDataProvider().refreshAll();
                });
/*
                if(s3Tree.getDataProvider().hasChildren(item)) {
                    remove.setEnabled(false);
                }
*/
            } else {
                event.getContextMenu().addItem("Delete", VaadinIcons.FILE_REMOVE, menuItem -> {
                    for (DmsTreeViewListener listener : listeners) {
                        listener.removeFile(item.getFilePath());
                    }
                    System.out.println("deleting file...");

                    s3Tree.getDataProvider().refreshAll();
                });
            }

            event.getContextMenu().addSeparator();
            event.getContextMenu().addItem("Info", VaadinIcons.INFO_CIRCLE_O, menuItem -> {
                Notification.show(item.getFilePath());
            });
        }
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

    @Override
    public void addListener(DmsTreeViewListener listener) {
        listeners.add(listener);
    }
}
