package com.qaelum.dms.ui.view.qbook;

import com.vaadin.contextmenu.ContextMenu;
import com.vaadin.contextmenu.GridContextMenu;
import com.vaadin.contextmenu.Menu;
import com.vaadin.contextmenu.MenuItem;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Einhart on 3/8/2018.
 * © QAELUM NV
 */
public class TestTreeView extends VerticalLayout implements IDmsTreeView{

    Tree<String> tree = new Tree<>();

    public TestTreeView() {
        // An initial planet tree

        TreeData<String> treeData = new TreeData<>();

// Couple of childless root items
        treeData.addItem(null,"Mercury");
        treeData.addItem(null,"Venus");

// Items with hierarchy
        treeData.addItem(null,"Earth");
        treeData.addItem("Earth","The Moon");

        TreeDataProvider<String> inMemoryDataProvider = new TreeDataProvider<>(treeData);
        tree.setDataProvider(inMemoryDataProvider);
        tree.expand("Earth"); // Expand programmatically

        // Add Mars with satellites
        treeData.addItem(null, "Mars");
        treeData.addItem("Mars", "Phobos");
        treeData.addItem("Mars", "Deimos");
        inMemoryDataProvider.refreshAll();

        ContextMenu contextMenu = new ContextMenu(this, true);
        fillMenu(contextMenu);

        contextMenu.setAsContextMenuOf(tree);
        contextMenu.addContextMenuOpenListener(new ContextMenu.ContextMenuOpenListener() {
            @Override
            public void onContextMenuOpen(ContextMenuOpenEvent event) {
                String item = ((Tree.TreeContextClickEvent<String>)event.getContextClickEvent()).getItem();
                tree.select(item);
                Notification.show(item);
            }
        });

        addComponent(tree);
    }

    private void fillMenu(Menu menu) {
        final MenuItem itemPrint = menu.addItem("Print", e -> {
            if (tree.getSelectedItems().isEmpty()) {
                System.out.println("Nothing selected");
            } else {
                System.out.println("Printing..." + tree.getSelectedItems().iterator().next());
            }
        });

        // Checkable item
        final MenuItem item = menu.addItem("Checkable", e -> {
            Notification.show("checked: " + e.isChecked());
        });
        item.setCheckable(true);
        item.setChecked(true);

// Disabled item
        MenuItem item2 = menu.addItem("Disabled", e -> {
            Notification.show("disabled");
        });
        item2.setEnabled(false);
    }

    @Override
    public void addListener(DmsTreeViewListener listener) {

    }
}
