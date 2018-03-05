package com.qaelum.dms.ui.model.coach;

/**
 * Created by Einhart on 2/14/2018.
 * © QAELUM NV
 */
public class CoachChapterModel {

    private String text = "default";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        System.out.println("Saving answer text: " + text);
    }

    public void calculate() {
        this.text = "input: " + text;
    }
}
