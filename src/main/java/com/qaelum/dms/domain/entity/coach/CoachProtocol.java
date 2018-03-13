package com.qaelum.dms.domain.entity.coach;

import com.qaelum.dms.domain.entity.coach.qualityItem.AbstractQualityItem;
import com.qaelum.dms.domain.entity.coach.qualityItem.QualityChapter;
import com.qaelum.dms.domain.entity.coach.qualityItem.QualityQuestion;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by einha on 2/20/2018.
 */
public class CoachProtocol {
    private ProtocolScheme protocolScheme;

    private File chapterDictFile;
    private File questionDictFile;
    private File protocolTreeFile;

    private Map<String, String> chapterDict = new LinkedHashMap<>();
    private Map<String, QualityQuestion> questionDict = new LinkedHashMap<>();
    private List<AbstractQualityItem> protocolTree = new ArrayList<>();

    public CoachProtocol(ProtocolScheme protocolScheme) {
        this.protocolScheme = protocolScheme;

        loadChapterDictionary();
        loadQuestionDictionary();
        loadProtocolTree();
    }

    public ProtocolScheme getProtocolScheme() {
        return protocolScheme;
    }

    public Map<String, String> getChapterDict() {
        return chapterDict;
    }

    public Map<String, QualityQuestion> getQuestionDict() {
        return questionDict;
    }

    public List<AbstractQualityItem> getProtocolTree() {
        return protocolTree;
    }

    public void loadChapterDictionary() {
        if(chapterDictFile == null) {
            chapterDictFile = CoachUtil.getChapterDictFile(protocolScheme);
        }

        chapterDict.clear();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(chapterDictFile),
                            "Cp1252"
                    )
            );

            String line = null;
            while((line = reader.readLine()) != null && line.contains(";")) {
                if(!line.startsWith("COMMENT")) {
                    String[] splitValues = line.split(";");
                    chapterDict.put(splitValues[0], splitValues[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadQuestionDictionary() {
        if(questionDictFile == null) {
            questionDictFile = CoachUtil.getQuestionDictFile(protocolScheme);
        }

        questionDict.clear();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(questionDictFile),
                            "Cp1252"
                    )
            );

            String line = null;
            while((line = reader.readLine()) != null && line.contains(";")) {
                if(!line.startsWith("COMMENT")) {
                    String[] splitValues = line.split(";");

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 1; i < splitValues.length - 1; i++ ) {
                        stringBuilder.append(splitValues[i]);
                        stringBuilder.append(";");
                    }

                    while (stringBuilder.charAt(stringBuilder.length() - 1) == ';') {
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    }

                    QualityQuestion qualityQuestion = new QualityQuestion(splitValues[0], stringBuilder.toString(), splitValues[splitValues.length-1]);
                    questionDict.put(splitValues[0], qualityQuestion);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProtocolTree() {
        if(protocolTreeFile == null) {
            protocolTreeFile = CoachUtil.getProtocolTreeFile(protocolScheme);
        }

        protocolTree.clear();

        LinkedHashMap<String, QualityChapter> mainChapters = new LinkedHashMap<>();
        LinkedHashMap<String, ArrayList<String>> treeStructureQuestions = new LinkedHashMap<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(protocolTreeFile),
                            "Cp1252"
                    )
            );

            String line = null;
            while((line = reader.readLine()) != null ) {
                if(line.contains(";")) {
                    if(!line.startsWith("COMMENT")) {
                        String[] splitValues = line.split(";");
                        if(mainChapters.get(splitValues[0]) == null) {
                            QualityChapter qualityChapter = new QualityChapter(splitValues[0], chapterDict.get(splitValues[0]));
                            mainChapters.put(splitValues[0], qualityChapter);
                        }

                        defineChapter(splitValues, mainChapters.get(splitValues[0]), 1);
                        defineQuestion(splitValues, treeStructureQuestions);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(QualityChapter chapter : mainChapters.values()) {
            combineChapterQuestions(chapter, treeStructureQuestions);
            protocolTree.add(chapter);
        }
    }

    private void defineQuestion(String[] values, LinkedHashMap<String, ArrayList<String>> treeStructureQuestions) {
        String key = null;
        int index = 0;
        while(values[index].trim().length() > 0 && index < values.length - 1) {
            key = values[index++];
        }
        if(treeStructureQuestions.get(key) == null) {
            treeStructureQuestions.put(key, new ArrayList<String>());
        }
        treeStructureQuestions.get(key).add(values[values.length - 1]);
    }

    private void defineChapter(String[] values, QualityChapter qualityChapter, int index) {
        if(index < values.length - 1 && values[index].trim().length() != 0) {
            String chapterKey = values[index].trim();
            QualityChapter tempChapter = null;
            for(QualityChapter chapter : qualityChapter.getSubChapters()) {
                if(chapterKey.equalsIgnoreCase(chapter.getItemKey())) {
                    tempChapter = chapter;
                }
            }
            if(tempChapter == null) {
                tempChapter = new QualityChapter(chapterKey, chapterDict.get(chapterKey));
                qualityChapter.getSubChapters().add(tempChapter);
                tempChapter.setItemParent(qualityChapter);
            }
            defineChapter(values, tempChapter, ++index);
        }
    }

    private void combineChapterQuestions(QualityChapter qualityChapter, LinkedHashMap<String, ArrayList<String>> treeStructureQuestions) {
        if(treeStructureQuestions.get(qualityChapter.getItemKey()) != null) {
            ArrayList<String> list = treeStructureQuestions.get(qualityChapter.getItemKey());
            for(String questionKey : list) {
                QualityQuestion qualityQuestion = questionDict.get(questionKey);
                if(qualityQuestion != null) {
                    qualityChapter.getQuestions().add(qualityQuestion);
                    qualityQuestion.setItemParent(qualityChapter);
                } else {
                    System.out.println("No question found for " + questionKey);
                }
            }
        }

        for(QualityChapter chapter : qualityChapter.getSubChapters()) {
            combineChapterQuestions(chapter, treeStructureQuestions);
        }
    }
}

