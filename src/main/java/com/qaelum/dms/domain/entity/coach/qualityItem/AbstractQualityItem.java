package com.qaelum.dms.domain.entity.coach.qualityItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by einha on 2/20/2018.
 */
public abstract class AbstractQualityItem {
    protected String itemKey;
    protected String itemName;
    List<AbstractQualityItem> itemChildren;

    protected AbstractQualityItem(String itemKey, String itemName) {
        this.itemKey = itemKey;
        this.itemName = itemName;
        itemChildren = new ArrayList<>();
    }

    public String getItemKey() {
        return itemKey;
    }

    public String getItemName() {
        return itemName;
    }

    public List<AbstractQualityItem> getItemChildren() {
        return itemChildren;
    }
}
