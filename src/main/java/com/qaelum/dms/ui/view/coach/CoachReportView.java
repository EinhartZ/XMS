package com.qaelum.dms.ui.view.coach;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class CoachReportView extends VerticalLayout {

    private String title = "CoachReportView";
    private VerticalLayout reportElement = null;

    private Button button = null;

    public CoachReportView() {
        addComponent(new Label(title));

        initReportElement();
        addComponent(reportElement);

        initButton();
        addComponent(button);
    }

    private void initReportElement() {
        reportElement = new VerticalLayout();
        reportElement.addComponent(new Label("There is no report..."));
    }

    private void initButton() {
        button = new Button("delete this button!");
    }
}


