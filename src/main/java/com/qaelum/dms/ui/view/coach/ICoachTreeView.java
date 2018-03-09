package com.qaelum.dms.ui.view.coach;

import com.qaelum.dms.domain.entity.coach.qualityItem.AbstractQualityItem;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public interface ICoachTreeView {
    void updateTree();
    String getSelectedItem();

    void selectSiblingQuestion(String key);

    void addListener(CoachTreeViewListener listener);

    interface CoachTreeViewListener {
        //TODO change parameter to adaquat DTO
        void selectItem(AbstractQualityItem qualityItem);
    }
}
