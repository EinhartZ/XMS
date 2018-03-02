package com.qaelum.dms.domain.entity.coach.qualityItem;

/**
 * Created by einha on 2/20/2018.
 */
public class QualityQuestion extends AbstractQualityItem {
    private QuestionType questionType;

    public enum QuestionType {
        TEXTAREA, INTEGER, CHECKBOX, DATE, UNKNOWN;
        public static QuestionType lookUp(String value) {
            try {
                return valueOf(value);
            } catch (IllegalArgumentException e) {
//                System.out.println("QuestionType: UNKNOWN");
                return QuestionType.UNKNOWN;
            }
        }
    }

    public QualityQuestion(String itemKey, String itemName, String questionType) {
        super(itemKey, itemName);
//        System.out.println("QuestionKey: " + itemKey + " : " + itemName + " : " + questionType);
        this.questionType = QuestionType.lookUp(questionType);
    }

    public QuestionType getQuestionType() {
        return questionType;
    }
}

