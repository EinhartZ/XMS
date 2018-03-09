package com.qaelum.dms.ui.view.qbook;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Einhart on 3/8/2018.
 * © QAELUM NV
 */
public class TestTreeView extends VerticalLayout implements IDmsTreeView{

    public TestTreeView() {
        // An initial planet tree
        Tree<String> tree = new Tree<>();
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

        addComponent(tree);
    }

    @Override
    public void addListener(DmsTreeViewListener listener) {

    }
}
