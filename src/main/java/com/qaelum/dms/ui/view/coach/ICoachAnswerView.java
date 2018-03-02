package com.qaelum.dms.ui.view.coach;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public interface ICoachAnswerView {
    void updateLabel(String value);

    void addListener(CoachAnswerView.CoachAnswerViewListener listener);

    /**
     * Listener Interface
     */
    interface CoachAnswerViewListener {
        void buttonClick(String value);
    }
}
