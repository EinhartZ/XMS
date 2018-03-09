package com.qaelum.dms.ui.view.qbook;

import com.qaelum.dms.commons.dto.DmsFileDTO;
import com.qaelum.dms.ui.model.DmsFile;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.InMemoryDataProvider;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.Map;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class DmsTreeView extends VerticalLayout {

    private String title = "DMS";
    //TODO change to DTO

    private HorizontalLayout controlHl = new HorizontalLayout();
    private Tree<String> dmsTree = null;

    private Map<String, WikiSub> wikiSubWindows;
    private int maxSubWindows = 2;

    public DmsTreeView(Map<String, WikiSub> wikiSubWindows) {
        addComponent(new Label(title));

        initControlHl();
        initDmsTree();

        addComponent(controlHl);
        addComponent(dmsTree);

        this.wikiSubWindows = wikiSubWindows;
    }

    private void initControlHl() {
        Button btnOpen = new Button("OPEN");
        btnOpen.addClickListener(clickEvent -> {
           openWikiPopup(dmsTree.getSelectedItems().iterator().next());
        });
        controlHl.addComponent(btnOpen);
    }

    private void initDmsTree() {
        // An initial planet tree
        Tree<String> tree = new Tree<>();
        TreeData<String> treeData = new TreeData<>();

        // Couple of childless root items
        treeData.addItem(null,"Mercury");
        treeData.addItem(null,"Venus");

        // Items with hierarchy
        treeData.addItem(null,"Earth");
        treeData.addItem("Earth","The Moon");

        InMemoryDataProvider inMemoryDataProvider = new TreeDataProvider<>(treeData);
        tree.setDataProvider(inMemoryDataProvider);
        tree.expand("Earth"); // Expand programmatically

        tree.addItemClickListener(itemClick -> {
            //openWikiPopup(itemClick.getSelectedItem());
        });


        dmsTree = tree;
    }

    public void openWikiPopup(String fileName) {

        DmsFileDTO wikiDTO = new DmsFileDTO(DmsFile.FileType.FILE, fileName);
        wikiDTO.setFileId(2);
        wikiDTO.setVersionData("DMS_01_2");
        /*
        WikiSub sub = new WikiSub(wikiDTO);

        if(wikiSubWindows.size() >= maxSubWindows) {
            String msg = maxSubWindows + " is too many windows";
            //TODO clean up sysout
            System.out.println(msg);

            Notification notification = new Notification("This is the caption",
                    msg,
                    Notification.Type.HUMANIZED_MESSAGE);
            notification.show(Page.getCurrent());
            return;
        }

        if(!wikiSubWindows.containsKey(wikiDTO.getFileName())) {
            wikiSubWindows.put(wikiDTO.getFileName(), sub);
            sub.addCloseListener(closeEvent -> {
                wikiSubWindows.remove(wikiDTO.getFileName());
            });

            UI.getCurrent().addWindow(sub);
        } else {
            String msg = wikiDTO.getFileName() + " is already opened";
            //TODO clean up sysout
            System.out.println(msg);

            Notification notification = new Notification("This is the caption",
                    msg,
                    Notification.Type.HUMANIZED_MESSAGE);
            notification.show(Page.getCurrent());
        }
        */
    }
}
