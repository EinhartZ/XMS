package com.qaelum.coach.model;

import com.qaelum.dms.domain.entity.coach.CoachProtocol;
import com.qaelum.dms.domain.entity.coach.ProtocolScheme;
import com.qaelum.dms.domain.entity.coach.qualityItem.AbstractQualityItem;
import com.qaelum.dms.domain.entity.coach.qualityItem.QualityQuestion;
import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

/**
 * Created by einha on 2/21/2018.
 */
public class CoachProtocolTest extends TestCase {
    protected ProtocolScheme protocolScheme;

    protected  void setUp() {
        protocolScheme = new ProtocolScheme("QUAADRIL", 2014, "en_US");
    }

    public void testConstructor() {
        CoachProtocol coachProtocol = new CoachProtocol(protocolScheme);
        Map<String, String> chapterDict = coachProtocol.getChapterDict();
        Map<String, QualityQuestion> questionDict = coachProtocol.getQuestionDict();
        List<AbstractQualityItem> protocolTree = coachProtocol.getProtocolTree();
        assert (true);
    }
}
