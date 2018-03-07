package com.qaelum.dms.commons.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    private List<QualityQuestionDTO> questionDTOs = new ArrayList<>();

    public QualityChapterDTO(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public QualityChapterDTO(String key, String name, List<QualityChapterDTO> chapterDTOs, List<QualityQuestionDTO> questionDTOs) {
        this.key = key;
        this.name = name;
        this.chapterDTOs = chapterDTOs;
        this.questionDTOs = questionDTOs;
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

    public List<QualityQuestionDTO> getQuestionDTOs() {
        return questionDTOs;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof QualityChapterDTO)) return false;
        QualityChapterDTO chapterDTO = (QualityChapterDTO) obj;
        return new EqualsBuilder().append(key, chapterDTO.getKey()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(key).toHashCode();
    }
}
