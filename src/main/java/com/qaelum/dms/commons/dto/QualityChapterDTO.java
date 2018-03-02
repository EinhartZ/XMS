package com.qaelum.dms.commons.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Einhart on 2/15/2018.
 * © QAELUM NV
 */
public class QualityChapterDTO {

    private String key;
    private String name;

    private List<QualityChapterDTO> chapterDTOs = new ArrayList<>();
    private List<QualityQuestionDTO> questionDTOList = new ArrayList<>();

    public QualityChapterDTO(String key, String name, List<QualityChapterDTO> chapterDTOs, List<QualityQuestionDTO> questionDTOList) {
        this.key = key;
        this.name = name;
        this.chapterDTOs = chapterDTOs;
        this.questionDTOList = questionDTOList;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public List<QualityChapterDTO> getChapterDTOs() {
        return chapterDTOs;
    }

    public List<QualityQuestionDTO> getQuestionDTOList() {
        return questionDTOList;
    }
}
