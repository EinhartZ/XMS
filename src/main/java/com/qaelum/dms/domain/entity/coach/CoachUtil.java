package com.qaelum.dms.domain.entity.coach;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import java.io.File;

/**
 * Created by einha on 2/20/2018.
 */
public class CoachUtil {
    private static String ROOT = "C:\\qaelum\\QC3\\qms\\qualitycoach";

    private static String RESOURCES_FOLDER = "resources";
    private static String DATA_FOLDER = "data";

    private static final String CHAPTERS_CSV = "chapters.csv";
    private static final String QUESTIONS_CSV = "questions.csv";
    private static final String TREE_CSV = "tree.csv";

    private static XStream xStream = null;

    public static XStream getXStream() {
        if(xStream == null) {
            XStream xStream = new XStream(new DomDriver()) {
                /*
                Define anonymous class to deal with unrecognised (legacy) XML tags
                e.g. <gtSnapshots>
                */
                protected MapperWrapper wrapMapper(MapperWrapper next) {
                    return new MapperWrapper(next) {
                        public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                            try {
                                return definedIn != Object.class || realClass(fieldName) != null;
                            } catch(CannotResolveClassException cnrce) {
                                return false;
                            }
                        }
                    };
                }
            };
            xStream.processAnnotations(CoachProject.class);

            return xStream;
        } else {
            return xStream;
        }
    }

    public static String getROOT() {
        return ROOT;
    }

    public static String getResourcesFolder() {
        return RESOURCES_FOLDER;
    }

    public static String getDataFolder() {
        return DATA_FOLDER;
    }

    public static String getChaptersCsv() {
        return CHAPTERS_CSV;
    }

    public static String getQuestionsCsv() {
        return QUESTIONS_CSV;
    }

    public static String getTreeCsv() {
        return TREE_CSV;
    }

    private static String getFolderPath(ProtocolScheme scheme) {
        return (getROOT() + File.separator + getResourcesFolder() + File.separator +
                scheme.getProtocolKey() + "_" + scheme.getYear() + File.separator + scheme.getLanguage());
    }

    public static File getChapterDictFile(ProtocolScheme scheme) {
        return new File(getFolderPath(scheme) + File.separator + getChaptersCsv());
    }

    public static File getQuestionDictFile(ProtocolScheme scheme) {
        return new File(getFolderPath(scheme) + File.separator + getQuestionsCsv());
    }

    public static File getProtocolTreeFile(ProtocolScheme scheme) {
        return new File(getFolderPath(scheme) + File.separator + getTreeCsv());
    }



}
