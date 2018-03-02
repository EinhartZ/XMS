package com.qaelum.coach.model;

import com.qaelum.dms.domain.entity.coach.CoachProject;
import com.qaelum.dms.domain.entity.coach.CoachUtil;
import com.thoughtworks.xstream.XStream;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by einha on 2/20/2018.
 */
public class CoachUtilTest extends TestCase {
    protected XStream xStream;

    protected void setUp() {
        xStream = CoachUtil.getXStream();
    }

    private String getFile(String fileName){

        String result = "";

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    public void testReadCoachProjectFile() {
        System.out.println("reading...");
        String result = getFile("xml/test.xml");
        System.out.println(result);

        CoachProject coachProjectIn = new CoachProject("project_name", "project_key", "audit_key", "en_US");
        String xmlString = (xStream.toXML(coachProjectIn));
        InputStream stream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
        CoachProject coachProjectOut = (CoachProject) xStream.fromXML(stream);

        assert (coachProjectIn.getProjectUUID().equals(coachProjectOut.getProjectUUID()));
    }

    public void testWriteCoachProjectFile() {
        System.out.println("writing...");
        CoachProject coachProject = new CoachProject("project_name", "project_key", "audit_key", "en_US");
        System.out.println(xStream.toXML(coachProject));
        assert (true);
    }

}
