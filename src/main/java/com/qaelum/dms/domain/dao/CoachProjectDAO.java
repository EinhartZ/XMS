package com.qaelum.dms.domain.dao;

import com.qaelum.dms.commons.dto.IDmsFileDTO;
import com.qaelum.dms.commons.dto.QualityQuestionDTO;
import com.qaelum.dms.commons.dto.QuestionAnswerDTO;
import com.qaelum.dms.commons.dto.S3FileDTO;

import java.util.ArrayList;
import java.util.List;

public class CoachProjectDAO implements ICoachProjectDAO {
    private static CoachProjectDAO instance;

    public static CoachProjectDAO getInstance() {
        if(instance == null) {
            instance = new CoachProjectDAO();
        }
        return  instance;
    }

    @Override
    public void saveQuestionAnswer(QualityQuestionDTO questionDTO) {
        System.out.println("Q: " + questionDTO.getKey());
        if(questionDTO.hasAnswer()) {
            System.out.println("A: " + questionDTO.getAnswerDTO().getAnswerTxt());
        } else {
            System.out.println("- answer deleted");
        }
    }

    @Override
    public QuestionAnswerDTO findQuestionAnswer(QualityQuestionDTO questionDTO) {
        return findQuestionAnswer(questionDTO.getKey());
    }

    @Override
    public QuestionAnswerDTO findQuestionAnswer(String questionKey) {
        QuestionAnswerDTO answerDTO;

        if(questionKey.endsWith("1")) {
            answerDTO = new QuestionAnswerDTO("I'm number ONE !!");
        } else if (questionKey.endsWith("3")) {
            answerDTO = new QuestionAnswerDTO("Thr33 is the charm ...");
        } else {
            answerDTO = null;
        }

        return answerDTO;
    }

    @Override
    public void attachProof(QualityQuestionDTO questionDTO, IDmsFileDTO fileDTO) {
        attachProof(questionDTO.getKey(), fileDTO);
    }

    @Override
    public void attachProof(String questionKey, IDmsFileDTO fileDTO) {
        System.out.println("Q: " + questionKey);
        System.out.println("P: + " + fileDTO.getFileKey());
    }



    @Override
    public List<IDmsFileDTO> findQuestionProof(QualityQuestionDTO questionDTO) {
        return findQuestionProof(questionDTO.getKey());
    }

    @Override
    public List<IDmsFileDTO> findQuestionProof(String questionKey) {
        List<IDmsFileDTO> fileDTOList = new ArrayList<>();
        if (questionKey.endsWith("1")) {
            fileDTOList.add(new S3FileDTO("proofs/001.prf"));
            fileDTOList.add(new S3FileDTO("proofs/010.prf"));
            fileDTOList.add(new S3FileDTO("proofs/100.prf"));
        } else if (questionKey.endsWith("3")) {
            fileDTOList.add(new S3FileDTO("proofs/333.prf"));
        }

        return fileDTOList;
    }

    @Override
    public void detachProof(QualityQuestionDTO questionDTO, IDmsFileDTO fileDTO) {
        detachProof(questionDTO.getKey(), fileDTO);
    }

    @Override
    public void detachProof(String questionKey, IDmsFileDTO fileDTO) {
        System.out.println("Q: " + questionKey);
        System.out.println("P: - " + fileDTO.getFileKey());
    }

    @Override
    public void clearAllProofs(QualityQuestionDTO questionDTO) {
        clearAllProofs(questionDTO.getKey());
    }

    @Override
    public void clearAllProofs(String questionKey) {
        System.out.println("Q: " + questionKey);
        System.out.println("P: NULL");
    }
}
