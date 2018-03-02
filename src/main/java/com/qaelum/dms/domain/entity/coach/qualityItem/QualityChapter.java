package com.qaelum.dms.domain.entity.coach.qualityItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by einha on 2/20/2018.
 */
public class QualityChapter extends AbstractQualityItem {
    private List<QualityChapter> subChapters = new ArrayList<>();
    private List<QualityQuestion> questions = new ArrayList<>();

    public QualityChapter(String itemKey, String itemName) {
        super(itemKey, itemName);
    }

    public List<QualityChapter> getSubChapters() {
        return subChapters;
    }

    public List<QualityQuestion> getQuestions() {
        return questions;
    }

    @Override
    public List<AbstractQualityItem> getItemChildren() {
        itemChildren.clear();

        itemChildren.addAll(questions);
        itemChildren.addAll(subChapters);
        return  itemChildren;
    }
}
